package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.charging.model.ConnectorPrice;
import com.tingcore.cdc.service.TimeService;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.emp.pricing.client.api.v1.PricingProfileRestApi;
import com.tingcore.emp.pricing.profile.response.PriceProfileResponse;
import com.tingcore.emp.pricing.rule.PriceComponent;
import com.tingcore.emp.pricing.rule.PriceComponentType;
import com.tingcore.emp.pricing.rule.PriceRules;
import com.tingcore.payments.cpo.model.ApiPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tingcore.cdc.constant.SpringProfilesConstant.ADVANCED_PRICING;
import static java.lang.String.format;

@Repository
@Profile(ADVANCED_PRICING)
public class AdvancedPricingRepository extends AbstractApiRepository implements PriceStore {

    private static final Logger LOG = LoggerFactory.getLogger(AdvancedPricingRepository.class);

    private final Integer defaultTimeout;
    private final PricingProfileRestApi priceProfileApi;
    private final TimeService timeService;
    private final ObjectMapper objectMapper;

    public AdvancedPricingRepository(
            final ObjectMapper objectMapper,
            final PricingProfileRestApi priceProfileApi,
            final TimeService timeService,
            @Value("${app.payments-pricing.default-timeout:20}") final Integer defaultTimeout
    ) {
        super(objectMapper);
        this.priceProfileApi = priceProfileApi;
        this.timeService = timeService;
        this.defaultTimeout = defaultTimeout;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<ConnectorPrice> priceForConnectors(final List<ConnectorId> connectorIds) {
        return connectorIds
                .parallelStream()
                .map(connectorId -> execute(priceProfileApi.getCurrentAssociation(connectorId.id)))
                .map(ApiResponse::getResponseOptional)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(associations -> associations.stream().findFirst())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(association -> ProfileReference.of(association.getOrganizationId(), association.getProfileId(), association.getConnectorId()))
                .distinct()
                .map(this::toConnectorPrice)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<ConnectorPrice> toConnectorPrice(final ProfileReference reference) {
        try {
            Long connectorId = reference.connectorId;
            ApiResponse<PriceProfileResponse> request = execute(priceProfileApi.getPriceProfile(reference.orgId, reference.profileId));
            if (request.hasError()) {
                return Optional.empty();
            }

            PriceProfileResponse profile = request.getResponse();
            String currency = profile.getCurrency();
            PriceRules priceRules = objectMapper.readValue(profile.getContent(), PriceRules.class);
            return priceRules
                    .getSegments()
                    .stream()
                    .filter(priceRule -> priceRule.getRestriction().getDaysRange().contains(timeService.currentDayOfWeek()))
                    .filter(priceRule -> priceRule.getRestriction().getTimeRange().contains(timeService.currentTime()))
                    .flatMap(rule -> rule.getComponents().stream())
                    .map(component -> new ConnectorPrice(new ConnectorId(connectorId), toString(component, currency)))
                    .findFirst();
        } catch (Exception e) {
            LOG.warn("Couldn't understand advanced-price, skipping. {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Integer getTimeout() {
        return defaultTimeout;
    }

    private String toString(final PriceComponent component, final String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        String price = component.getPrice().setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_UP).toPlainString();
        ApiPrice.UnitEnum unit;
        if (PriceComponentType.TIME.equals(component.getType()) && 60L == component.getStepSize()) {
            unit = ApiPrice.UnitEnum.MIN;
        } else if (PriceComponentType.TIME.equals(component.getType()) && 3600L == component.getStepSize()) {
            unit = ApiPrice.UnitEnum.H;
        } else {
            unit = ApiPrice.UnitEnum.KWH;
        }
        return format("%s\u00A0%s/%s", price, currency.getCurrencyCode(), unit.getValue());
    }

    private static class ProfileReference {
        private final Long orgId;
        private final String profileId;
        private final Long connectorId;

        private ProfileReference(final Long orgId, final String profileId, final Long connectorId) {
            this.orgId = orgId;
            this.profileId = profileId;
            this.connectorId = connectorId;
        }

        static ProfileReference of(final Long orgId, final String profileId, final Long connectorId) {
            return new ProfileReference(orgId, profileId, connectorId);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final ProfileReference that = (ProfileReference) o;
            return Objects.equals(orgId, that.orgId) &&
                    Objects.equals(connectorId, that.connectorId) &&
                    Objects.equals(profileId, that.profileId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orgId, profileId, connectorId);
        }
    }
}
