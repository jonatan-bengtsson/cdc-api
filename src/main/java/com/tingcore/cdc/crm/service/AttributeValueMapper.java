package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.model.BaseAttributeModel;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.commons.api.service.HashIdService;
import org.springframework.stereotype.Component;
import com.tingcore.commons.api.utils.JsonUtils;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueListRequest;
import com.tingcore.users.model.AttributeValueRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author moa.mackegard
 * @since 2017-11-10.
 */

@Component
public class AttributeValueMapper {

    private static final Logger LOG = LoggerFactory.getLogger(AttributeValueMapper.class);

    public static AttributeValueListRequest toAttributeValueListRequest (User request, final List<AttributeResponse> cachedAttributes) {
        AttributeValueListRequest list = createAttributeValueListRequest();
        // todo write a test were you try to update an attribute that does not exists in the database
        Optional.ofNullable(request.getOrganizationNumber()).map(organizationNumber -> list.addAttributeValuesItem(toAttributeValueRequest(organizationNumber, findAttributeId(cachedAttributes, AttributeConstant.ORGANIZATION_NUMBER).orElse(null))));
        Optional.ofNullable(request.getSocialSecurityNumber()).map(socialSecurityNumber -> list.addAttributeValuesItem(toAttributeValueRequest(socialSecurityNumber, findAttributeId(cachedAttributes, AttributeConstant.SOCIAL_SECURITY_NUMBER).orElse(null))));
        //Optional.ofNullable(request.getEmail()).map(email -> list.addAttributeValuesItem(toAttributeValueRequest(email))); // todo should email really be both an attribute and a value directly on the user?
        Optional.ofNullable(request.getTimezone()).map(timeZone -> list.addAttributeValuesItem(toAttributeValueRequest(timeZone, findAttributeId(cachedAttributes, AttributeConstant.TIMEZONE).orElse(null))));
        Optional.ofNullable(request.getApprovedMarketInfo()).map(approvedMarketInfo -> list.addAttributeValuesItem(toAttributeValueRequest(approvedMarketInfo,findAttributeId(cachedAttributes,AttributeConstant.APPROVED_MARKET_INFO).orElse(null))));
        Optional.ofNullable(request.getApprovedPrivacy()).map(approvedPrivacy -> list.addAttributeValuesItem(toAttributeValueRequest(approvedPrivacy, findAttributeId(cachedAttributes,AttributeConstant.APPROVED_PRIVACY).orElse(null))));
        Optional.ofNullable(request.getAddress()).ifPresent(addresslist -> addresslist.stream().forEach(address -> list.addAttributeValuesItem(toAttributeValueRequest(address,findAttributeId(cachedAttributes,AttributeConstant.ADDRESS).orElse(null)))));
        Optional.ofNullable(request.getPhoneNumbers()).ifPresent(numberList -> numberList.stream().forEach(phoneNumber -> list.addAttributeValuesItem(toAttributeValueRequest(phoneNumber,findAttributeId(cachedAttributes,AttributeConstant.PHONE_NUMBER).orElse(null)))));
        Optional.ofNullable(request.getLicensePlates()).ifPresent(licensePlateList -> licensePlateList.stream().forEach(licensePlate -> list.addAttributeValuesItem(toAttributeValueRequest(licensePlate, findAttributeId(cachedAttributes, AttributeConstant.LICENSE_PLATES).orElse(null)))));
        Optional.ofNullable(request.getApprovedAgreements()).ifPresent(agreements -> agreements.stream().forEach(agreement -> list.addAttributeValuesItem(toAttributeValueRequest(agreement,findAttributeId(cachedAttributes,AttributeConstant.APPROVED_AGREEMENTS).orElse(null)))));
        Optional.ofNullable(request.getFirstName()).map(firstName -> list.addAttributeValuesItem(toAttributeValueRequest(firstName,findAttributeId(cachedAttributes, AttributeConstant.FIRST_NAME).orElse(null))));
        Optional.ofNullable(request.getLastName()).map(lastName -> list.addAttributeValuesItem(toAttributeValueRequest(lastName, findAttributeId(cachedAttributes, AttributeConstant.LAST_NAME).orElse(null))));
        Optional.ofNullable(request.getName()).map(name -> list.addAttributeValuesItem(toAttributeValueRequest(name, findAttributeId(cachedAttributes, AttributeConstant.NAME).orElse(null))));
        Optional.ofNullable(request.getHasChargingAccess()).map(chargingAccess -> list.addAttributeValuesItem(toAttributeValueRequest(chargingAccess, findAttributeId(cachedAttributes, AttributeConstant.HAS_CHARGING_ACCESS).orElse(null)))); // todo boolean?
        Optional.ofNullable(request.getLanguage()).map(language -> list.addAttributeValuesItem(toAttributeValueRequest(language, findAttributeId(cachedAttributes, AttributeConstant.LANGUAGE).orElse(null))));

        return list;
    }

    public static Optional<Long> findAttributeId (List<AttributeResponse> attributes, String attributeName) {
        return attributes.stream().filter(attributeResponse -> attributeResponse.getName().equals(attributeName)).map(attributeResponse -> attributeResponse.getId()).findAny();
    }

    private static AttributeValueListRequest createAttributeValueListRequest () {
        List<AttributeValueRequest> list = new ArrayList<>();
        AttributeValueListRequest attributeValueListRequest = new AttributeValueListRequest();
        attributeValueListRequest.setAttributeValues(list);
        return attributeValueListRequest;
    }

    private static <T extends BaseAttributeModel> AttributeValueRequest toAttributeValueRequest (T attributeModel, Long attributeId) {
        return createAttributeValueRequest(attributeId, JsonUtils.toJson(attributeModel.copyWithoutId()).orElse(null), attributeModel.getId());
    }


    private static AttributeValueRequest createAttributeValueRequest (Long attributeId, String attributeValue, Long attributeValueId) {
        AttributeValueRequest request = new AttributeValueRequest();
        request.setAttributeId(attributeId);
        request.setValue(attributeValue);
        request.setValueId(attributeValueId);
        return request;
    }


}
