package com.tingcore.cdc.crm.service;

        import com.tingcore.cdc.crm.constant.AttributeConstant;
        import com.tingcore.cdc.crm.constant.FieldConstant;
        import com.tingcore.cdc.crm.request.UpdateBusinessCustomerRequest;
        import com.tingcore.cdc.crm.request.UpdatePrivateCustomerRequest;
        import com.tingcore.cdc.crm.utils.AttributeDataUtils;
        import com.tingcore.cdc.crm.utils.UserDataUtils;
        import com.tingcore.users.model.AttributeResponse;
        import com.tingcore.users.model.AttributeValueListRequest;
        import com.tingcore.users.model.AttributeValueRequest;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.test.context.junit4.SpringRunner;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author moa.mackegard
 * @since 2018-01-11.
 */

@RunWith(SpringRunner.class)
public class AttributeValueMapperTest {

    @Test
    public void assertAttributeSavedWithCorrectId() throws Exception {
        List<AttributeResponse> mockCachedAttributes = AttributeDataUtils.allAttributes();
        UpdatePrivateCustomerRequest privateCustomerRequest = UserDataUtils.createUpdatePrivateCustomerRequest();
        UpdateBusinessCustomerRequest businessCustomerRequest = UserDataUtils.createUpdateBusinessCustomerRequest();

        AttributeValueListRequest privateCustomerList = AttributeValueMapper.toAttributeValueListRequest(privateCustomerRequest,mockCachedAttributes);
        AttributeValueListRequest businessCustomerList = AttributeValueMapper.toAttributeValueListRequest(businessCustomerRequest,mockCachedAttributes);
        List<AttributeValueRequest> mappedAttributesPrivateCustomer = privateCustomerList.getAttributeValues();
        List<AttributeValueRequest> mappedAttributesBusinessCustomer = businessCustomerList.getAttributeValues();

        Map<String, Long> attributeIdMap = createAttributeIdMap(mockCachedAttributes);

        AttributeValueRequest approvedAgreement = findMappedAttributeById(mappedAttributesPrivateCustomer, attributeIdMap, AttributeConstant.APPROVED_AGREEMENTS);
        AttributeValueRequest address = findMappedAttributeById(mappedAttributesPrivateCustomer, attributeIdMap, AttributeConstant.ADDRESS);
        AttributeValueRequest socialSecurityNumber = findMappedAttributeById(mappedAttributesPrivateCustomer, attributeIdMap, AttributeConstant.SOCIAL_SECURITY_NUMBER);
        AttributeValueRequest phoneNumbers = findMappedAttributeById(mappedAttributesPrivateCustomer, attributeIdMap, AttributeConstant.PHONE_NUMBER);
        AttributeValueRequest timezone = findMappedAttributeById(mappedAttributesPrivateCustomer, attributeIdMap, AttributeConstant.TIMEZONE);
        AttributeValueRequest firstName = findMappedAttributeById(mappedAttributesPrivateCustomer, attributeIdMap, AttributeConstant.FIRST_NAME);
        AttributeValueRequest lastName = findMappedAttributeById(mappedAttributesPrivateCustomer, attributeIdMap, AttributeConstant.LAST_NAME);
        AttributeValueRequest licensePlate = findMappedAttributeById(mappedAttributesPrivateCustomer, attributeIdMap, FieldConstant.LICENSE_PLATE);
        AttributeValueRequest name = findMappedAttributeById(mappedAttributesBusinessCustomer, attributeIdMap, FieldConstant.NAME);
        AttributeValueRequest organizationNumber = findMappedAttributeById(mappedAttributesBusinessCustomer, attributeIdMap, FieldConstant.ORGANIZATION_NUMBER);
        AttributeValueRequest contactPhoneNumber = findMappedAttributeById(mappedAttributesBusinessCustomer, attributeIdMap, FieldConstant.CONTACT_PHONE_NUMBER);
        AttributeValueRequest contactEmail = findMappedAttributeById(mappedAttributesBusinessCustomer, attributeIdMap, FieldConstant.CONTACT_EMAIL);
        AttributeValueRequest contactFirstName = findMappedAttributeById(mappedAttributesBusinessCustomer, attributeIdMap, FieldConstant.CONTACT_FIRST_NAME);
        AttributeValueRequest contactLastName = findMappedAttributeById(mappedAttributesBusinessCustomer, attributeIdMap, FieldConstant.CONTACT_LAST_NAME);
//        AttributeValueRequest activationDate = findMappedAttributeById(mappedAttributesSystemUser, attributeIdMap, FieldConstant.ACTIVATION_DATE);

        assertThat(approvedAgreement.getValue()).contains(FieldConstant.AGREEMENT_ID);
        assertThat(address.getValue()).contains(FieldConstant.ADDRESS);
        assertThat(socialSecurityNumber.getValue()).contains(FieldConstant.SOCIAL_SECURITY_NUMBER);
        assertThat(phoneNumbers.getValue()).contains(FieldConstant.PHONE_NUMBER);
        assertThat(timezone.getValue()).contains(FieldConstant.VALUE);
        assertThat(firstName.getValue()).contains(FieldConstant.VALUE);
        assertThat(lastName.getValue()).contains(FieldConstant.VALUE);
        assertThat(licensePlate.getValue()).contains(FieldConstant.LICENSE_PLATE);
        assertThat(name.getValue()).contains(FieldConstant.VALUE);
        assertThat(organizationNumber.getValue()).contains(FieldConstant.ORGANIZATION_NUMBER);
        assertThat(contactPhoneNumber.getValue()).contains(FieldConstant.PHONE_NUMBER);
        assertThat(contactEmail.getValue()).contains(FieldConstant.VALUE);
        assertThat(contactFirstName.getValue()).contains(FieldConstant.VALUE);
        assertThat(contactLastName.getValue()).contains(FieldConstant.VALUE);
//        assertThat(activationDate.getValue()).contains(FieldConstant.VALUE);

    }

    private AttributeValueRequest findMappedAttributeById (List<AttributeValueRequest> mappedAttributes, Map<String, Long> attributeIdMap, String attributeName) {
        return mappedAttributes.stream().filter(attributeValueRequest -> attributeValueRequest.getAttributeId().equals(attributeIdMap.get(attributeName))).findFirst().get();
    }

    private Map<String, Long> createAttributeIdMap (List<AttributeResponse> mockCachedAttributes) {
        Map<String, Long> attributeIdMap =new HashMap<>();
        mockCachedAttributes.stream().forEach(attributeResponse -> attributeIdMap.put(attributeResponse.getName(),attributeResponse.getId()));
        return attributeIdMap;
    }

}
