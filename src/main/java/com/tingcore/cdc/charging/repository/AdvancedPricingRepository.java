package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.charging.model.ConnectorPrice;
import com.tingcore.cdc.service.TimeService;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.emp.pricing.client.api.v1.PricingProfileRestApi;
import com.tingcore.emp.pricing.profile.Source;
import com.tingcore.emp.pricing.profile.response.PriceProfileAssociationResponse;
import com.tingcore.emp.pricing.profile.response.PriceProfileResponse;
import com.tingcore.emp.pricing.rule.PriceComponent;
import com.tingcore.emp.pricing.rule.PriceComponentType;
import com.tingcore.emp.pricing.rule.PriceRules;
import com.tingcore.payments.cpo.model.ApiPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.tingcore.cdc.constant.SpringProfilesConstant.ADVANCED_PRICING;
import static java.lang.String.format;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Repository
@Profile(ADVANCED_PRICING)
public class AdvancedPricingRepository extends AbstractApiRepository implements PriceStore {

    private static final Logger LOG = LoggerFactory.getLogger(AdvancedPricingRepository.class);
    private static final String FREE_OF_CHARGE = "free of charge";

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
    public Integer getTimeout() {
        return defaultTimeout;
    }

    @Override
    public List<ConnectorPrice> priceForConnectors(final List<ConnectorId> connectorIds) {

        final List<ConnectorPrice> failed = new ArrayList<>();

        List<ConnectorPrice> successful = connectorIds
                .parallelStream()
                .map(connectorId -> getConnectorAssociations(connectorId, failed))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(List::stream)
                .collect(groupingBy(PriceProfileAssociationResponse::getConnectorId))
                .entrySet()
                .stream()
                .map(entry -> retrieveConnectorPriceIfAny(entry.getKey(), entry.getValue()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());

        return Stream
                .concat(successful.stream(), failed.stream())
                .collect(toList());
    }

    private Optional<List<PriceProfileAssociationResponse>> getConnectorAssociations(final ConnectorId connectorId, final List<ConnectorPrice> failed) {
        ApiResponse<List<PriceProfileAssociationResponse>> response = execute(priceProfileApi.getCurrentAssociation(connectorId.id));
        if (response.hasError()) {
            if (HttpStatus.NOT_FOUND.value() == response.getError().getStatusCode()) {
                LOG.warn("Couldn't find price for connectorId {}, returning free of charge", connectorId);
                failed.add(new ConnectorPrice(connectorId, FREE_OF_CHARGE));
            }
            LOG.warn("Unexpected HTTP error {} while fetching price for connectorId {}, skipping", response.getError().getStatusCode(), connectorId);
            return Optional.empty();
        }
        return response.getResponseOptional();
    }

    private Optional<ConnectorPrice> retrieveConnectorPriceIfAny(final Long connectorId, final List<PriceProfileAssociationResponse> associations) {
        List<ConnectorPrice> prices = associations.stream()
                .map(a -> fetchProfileAndConvertToConnectorPrice(connectorId, a))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());

        ConnectorPrice connectorPrice = getRealPriceOrFreeOfChargeOrNothing(prices);
        return Optional.ofNullable(connectorPrice);
    }

    private ConnectorPrice getRealPriceOrFreeOfChargeOrNothing(final List<ConnectorPrice> prices) {
        return prices.stream()
                .filter(p -> !FREE_OF_CHARGE.equals(p.price))
                .findFirst()
                .orElseGet(() -> prices.stream()
                        .filter(p -> FREE_OF_CHARGE.equals(p.price))
                        .findFirst()
                        .orElse(null)
                );
    }

    private Optional<ConnectorPrice> fetchProfileAndConvertToConnectorPrice(final Long connectorId, final PriceProfileAssociationResponse association) {
        try {
            ApiResponse<PriceProfileResponse> response = execute(priceProfileApi.getPriceProfile(association.getOrganizationId(), association.getProfileId()));
            if (response.hasError()) {
                if (HttpStatus.NOT_FOUND.value() == response.getError().getStatusCode()) {
                    LOG.warn("Couldn't find price for connectorId {}, returning free of charge", connectorId);
                    return Optional.of(new ConnectorPrice(new ConnectorId(connectorId), FREE_OF_CHARGE));
                }
                LOG.warn("Unexpected HTTP error {} while fetching price for connectorId {}, skipping", response.getError().getStatusCode(), connectorId);
                return Optional.empty();
            }

            PriceProfileResponse profile = response.getResponse();

            // ignore profiles not having source = ALL
            if (!profile.getSource().equals(Source.ALL)) {
                return Optional.empty();
            }

            return profileToConnectorPrice(connectorId, profile);
        } catch (Exception e) {
            LOG.warn("Couldn't understand advanced-price, returning free of charge. {}", e.getMessage());
            return Optional.of(new ConnectorPrice(new ConnectorId(connectorId), FREE_OF_CHARGE));
        }
    }

    private Optional<ConnectorPrice> profileToConnectorPrice(final Long connectorId, final PriceProfileResponse profile) throws java.io.IOException {
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

}
