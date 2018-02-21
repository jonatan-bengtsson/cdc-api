package com.tingcore.cdc.crm.service;

import com.tingcore.cdc.crm.constant.AttributeConstant;
import com.tingcore.cdc.crm.constant.FieldConstant;
import com.tingcore.cdc.crm.model.BaseAttributeModel;
import com.tingcore.cdc.crm.model.BooleanAttribute;
import com.tingcore.cdc.crm.model.StringAttribute;
import com.tingcore.cdc.crm.request.BaseUpdateCustomerRequest;
import com.tingcore.cdc.crm.request.UpdateBusinessCustomerRequest;
import com.tingcore.cdc.crm.request.UpdatePrivateCustomerRequest;
import com.tingcore.commons.api.utils.JsonUtils;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueListRequest;
import com.tingcore.users.model.AttributeValueRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author moa.mackegard
 * @since 2017-11-10.
 */

public class AttributeValueMapper {

    private static final Logger LOG = LoggerFactory.getLogger(AttributeValueMapper.class);

    public static <T extends BaseUpdateCustomerRequest> AttributeValueListRequest toAttributeValueListRequest (final T request, final List<AttributeResponse> cachedAttributes) {
        AttributeValueListRequest list = createAttributeValueListRequest();
        Optional.ofNullable(request.getTimezone())
                .ifPresent(timeZone -> addAttributeToList(list, timeZone, cachedAttributes, AttributeConstant.TIMEZONE));
        Optional.ofNullable(request.getAddresses())
                .ifPresent(addressList -> addressList.forEach(address -> addAttributeToList(list, address, cachedAttributes, AttributeConstant.ADDRESS)));
        Optional.ofNullable(request.getPhoneNumbers())
                .ifPresent(numberList -> numberList.forEach(phoneNumber -> addAttributeToList(list, phoneNumber, cachedAttributes, AttributeConstant.PHONE_NUMBER)));
        Optional.ofNullable(request.getLanguage())
                .ifPresent(language -> addAttributeToList(list, language, cachedAttributes, AttributeConstant.LANGUAGE));

        if(request.getClass().equals(UpdatePrivateCustomerRequest.class)){
            addPrivateCustomerAttributes(list,(UpdatePrivateCustomerRequest) request,cachedAttributes);
        } else if(request.getClass().equals(UpdateBusinessCustomerRequest.class)) {
            addBusinessCustomerAttributes(list, (UpdateBusinessCustomerRequest) request, cachedAttributes);
        }

        return list;
    }

    private static AttributeValueListRequest addPrivateCustomerAttributes(final AttributeValueListRequest list, final UpdatePrivateCustomerRequest privateCustomerRequest, final List<AttributeResponse> cachedAttributes){
        Optional.ofNullable(privateCustomerRequest.getSocialSecurityNumber())
                .ifPresent(socialSecurityNumber -> addAttributeToList(list, socialSecurityNumber, cachedAttributes, AttributeConstant.SOCIAL_SECURITY_NUMBER));
        Optional.ofNullable(privateCustomerRequest.getFirstName())
                .ifPresent(firstName -> addAttributeToList(list, firstName, cachedAttributes, AttributeConstant.FIRST_NAME));
        Optional.ofNullable(privateCustomerRequest.getLastName())
                .ifPresent(lastName -> addAttributeToList(list, lastName, cachedAttributes, AttributeConstant.LAST_NAME));
        Optional.ofNullable(privateCustomerRequest.getApprovedMarketInfo())
                .ifPresent(marketInfoList -> marketInfoList.forEach(marketInfo -> addAttributeToList(list, marketInfo, cachedAttributes, AttributeConstant.APPROVED_MARKET_INFO)));
        Optional.ofNullable(privateCustomerRequest.getApprovedPrivacyPolicies())
                .ifPresent(privacyPolicies -> privacyPolicies.forEach(privacy -> addAttributeToList(list, privacy, cachedAttributes, AttributeConstant.APPROVED_PRIVACY_POLICY)));
        Optional.ofNullable(privateCustomerRequest.getLicensePlates())
                .ifPresent(licensePlateList -> licensePlateList.forEach(licensePlate -> addAttributeToList(list, licensePlate, cachedAttributes, FieldConstant.LICENSE_PLATE)));
        Optional.ofNullable(privateCustomerRequest.getApprovedTermsConditions())
                .ifPresent(termsConditionsList -> termsConditionsList.forEach(termsConditions -> addAttributeToList(list, termsConditions, cachedAttributes, AttributeConstant.APPROVED_TERMS_CONDITIONS)));
        return list;
    }

    private static AttributeValueListRequest addBusinessCustomerAttributes(final AttributeValueListRequest list, final UpdateBusinessCustomerRequest request, final List<AttributeResponse> cachedAttributes){
        Optional.ofNullable(request.getOrganizationNumber())
                .ifPresent(organizationNumber -> addAttributeToList(list, organizationNumber, cachedAttributes, AttributeConstant.ORGANIZATION_NUMBER));
        Optional.ofNullable(request.getName())
                .ifPresent(name -> addAttributeToList(list,name, cachedAttributes, AttributeConstant.NAME));
        Optional.ofNullable(request.getApprovedMarketInfo())
                .ifPresent(marketInfoList -> marketInfoList.forEach(marketInfo -> addAttributeToList(list, marketInfo, cachedAttributes, AttributeConstant.APPROVED_MARKET_INFO)));
        Optional.ofNullable(request.getApprovedPrivacyPolicies())
                .ifPresent(privacyPolicies -> privacyPolicies.forEach(privacy -> addAttributeToList(list, privacy, cachedAttributes, AttributeConstant.APPROVED_PRIVACY_POLICY)));
        Optional.ofNullable(request.getLicensePlates())
                .ifPresent(licensePlates -> licensePlates.forEach(licensePlate -> addAttributeToList(list, licensePlate, cachedAttributes, FieldConstant.LICENSE_PLATE)));
        Optional.ofNullable(request.getApprovedTermsConditions())
                .ifPresent(termsConditionsList -> termsConditionsList.forEach(termsConditions -> addAttributeToList(list, termsConditions, cachedAttributes, AttributeConstant.APPROVED_TERMS_CONDITIONS)));
        Optional.ofNullable(request.getContactEmail())
                .ifPresent(contactEmail -> addAttributeToList(list, contactEmail, cachedAttributes, AttributeConstant.CONTACT_EMAIL));
        Optional.ofNullable(request.getContactFirstName())
                .ifPresent(contactFirstName -> addAttributeToList(list, contactFirstName, cachedAttributes, AttributeConstant.CONTACT_FIRST_NAME));
        Optional.ofNullable(request.getContactLastName())
                .ifPresent(contactLastName -> addAttributeToList(list, contactLastName, cachedAttributes, AttributeConstant.CONTACT_LAST_NAME));
        Optional.ofNullable(request.getContactPhoneNumber())
                .ifPresent(contactPhoneNumbers -> contactPhoneNumbers.forEach(contactPhoneNumber -> addAttributeToList(list, contactPhoneNumber, cachedAttributes, AttributeConstant.CONTACT_PHONE_NUMBER)));

        return list;
    }

    private static void addAttributeToList (AttributeValueListRequest list, BaseAttributeModel stringAttribute, final List<AttributeResponse> cachedAttributes, String attributeName) {
        list.addAttributeValuesItem(toAttributeValueRequest(stringAttribute,findAttributeId(cachedAttributes, attributeName).orElse(null)));
    }

    private static Optional<Long> findAttributeId(List<AttributeResponse> attributes, String attributeName) {
        return attributes.stream().filter(attributeResponse -> attributeResponse.getName().equals(attributeName)).map(AttributeResponse::getId).findAny();
    }

    private static AttributeValueListRequest createAttributeValueListRequest () {
        List<AttributeValueRequest> list = new ArrayList<>();
        AttributeValueListRequest attributeValueListRequest = new AttributeValueListRequest();
        attributeValueListRequest.setAttributeValues(list);
        return attributeValueListRequest;
    }

    private static <T extends BaseAttributeModel> AttributeValueRequest toAttributeValueRequest(final T attributeModel, final Long attributeId) {
        if (attributeModel instanceof StringAttribute) {
            return createAttributeValueRequest((StringAttribute) attributeModel, attributeId);
        } else if (attributeModel instanceof BooleanAttribute) {
            return createAttributeValueRequest(attributeId, ((BooleanAttribute) attributeModel).getValue().toString(), attributeModel.getId());
        } else {
            return createAttributeValueRequest(attributeId, JsonUtils.toJson(attributeModel.copyWithoutId()).orElse(null), attributeModel.getId());
        }
    }

    private static AttributeValueRequest createAttributeValueRequest(StringAttribute attribute, Long attributeId) {
        return createAttributeValueRequest(attributeId, attribute.getValue(), attribute.getId());
    }


    private static AttributeValueRequest createAttributeValueRequest (Long attributeId, String attributeValue, Long attributeValueId) {
        AttributeValueRequest request = new AttributeValueRequest();
        request.setAttributeId(attributeId);
        request.setValue(attributeValue);
        request.setValueId(attributeValueId);
        return request;
    }

    public static Map<String, Long> createAttributeIdMap (List<AttributeResponse> mockCachedAttributes) {
        Map<String, Long> attributeIdMap =new HashMap<>();
        mockCachedAttributes.forEach(attributeResponse -> attributeIdMap.put(attributeResponse.getName(), attributeResponse.getId()));
        return attributeIdMap;
    }


}