package com.tingcore.cdc.payments.pricing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.charging.model.ConnectorPrice;
import com.tingcore.cdc.payments.pricing.model.ConnectorPriceProfile;
import com.tingcore.cdc.payments.pricing.model.PriceUnit;
import com.tingcore.cdc.payments.pricing.repository.PricingRepository;
import com.tingcore.cdc.service.TimeService;
import com.tingcore.emp.pricing.profile.response.PriceProfileResponse;
import com.tingcore.emp.pricing.rule.PriceComponent;
import com.tingcore.emp.pricing.rule.PriceComponentType;
import com.tingcore.emp.pricing.rule.PriceRules;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class PricingService {

    private final PricingRepository repository;
    private final ObjectMapper objectMapper;
    private final TimeService timeService;

    public PricingService(final PricingRepository repository, final ObjectMapper objectMapper, final TimeService timeService) {
        this.repository = repository;
        this.objectMapper = objectMapper;
        this.timeService = timeService;
    }

    public List<ConnectorPrice> priceForConnectors(final List<ConnectorId> connectorIds) {
        List<Long> ids = connectorIds.stream().map(cid -> cid.id).collect(Collectors.toList());
        return repository
                .getCurrentProfileForConnectors(ids)
                .stream()
                .map(this::profileToConnectorPrice)
                .collect(Collectors.toList());
    }

    private ConnectorPrice profileToConnectorPrice(final ConnectorPriceProfile connectorPriceProfile) {
        if (!connectorPriceProfile.getProfile().isPresent()) {
            return new ConnectorPrice(new ConnectorId(connectorPriceProfile.getConnectorId()), "free of charge");
        }

        try {
            PriceProfileResponse profile = connectorPriceProfile.getProfile().get();
            String currency = profile.getCurrency();
            PriceRules priceRules = objectMapper.readValue(profile.getContent(), PriceRules.class);
            return priceRules
                    .getSegments()
                    .stream()
                    .filter(priceRule -> priceRule.getRestriction().getDaysRange().contains(timeService.currentDayOfWeek()))
                    .filter(priceRule -> priceRule.getRestriction().getTimeRange().contains(timeService.currentTime()))
                    .flatMap(rule -> rule.getComponents().stream())
                    .map(component -> new ConnectorPrice(new ConnectorId(connectorPriceProfile.getConnectorId()), toString(component, currency)))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            return new ConnectorPrice(new ConnectorId(connectorPriceProfile.getConnectorId()), "free of charge");
        }
    }

    private String toString(final PriceComponent component, final String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        String price = component.getPrice().setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_UP).toPlainString();
        PriceUnit unit;
        if (PriceComponentType.TIME.equals(component.getType()) && 60L == component.getStepSize()) {
            unit = PriceUnit.MIN;
        } else if (PriceComponentType.TIME.equals(component.getType()) && 3600L == component.getStepSize()) {
            unit = PriceUnit.H;
        } else {
            unit = PriceUnit.KWH;
        }
        return format("%s\u00A0%s/%s", price, currency.getCurrencyCode(), unit);
    }
}
