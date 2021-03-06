package com.tingcore.cdc.crm.service.v1;

        import com.tingcore.cdc.crm.constant.AttributeConstant;
        import com.tingcore.cdc.crm.constant.FieldConstant;
        import com.tingcore.cdc.crm.request.UpdateBusinessCustomerRequest;
        import com.tingcore.cdc.crm.request.UpdatePrivateCustomerRequest;
        import com.tingcore.cdc.crm.utils.AttributeDataUtils;
        import com.tingcore.cdc.crm.utils.UserDataUtils;
        import com.tingcore.users.model.v1.request.AttributeValueRequest;
        import com.tingcore.users.model.v1.response.AttributeResponse;
        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.test.context.junit4.SpringRunner;

        import java.io.IOException;
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
    private List<AttributeResponse> mockCachedAttributes;
    private Map<String, Long> attributeIdMap;
    private List<AttributeValueRequest> privateCustomerList;
    private List<AttributeValueRequest> businessCustomerList;

    @Before
    public void setup() throws IOException {
        mockCachedAttributes = AttributeDataUtils.allAttributes();
        attributeIdMap = createAttributeIdMap(mockCachedAttributes);

        UpdatePrivateCustomerRequest privateCustomerRequest = UserDataUtils.createUpdatePrivateCustomerRequest();
        UpdateBusinessCustomerRequest businessCustomerRequest = UserDataUtils.createUpdateBusinessCustomerRequest();

        privateCustomerList = AttributeValueMapper.toAttributeValueListRequest(privateCustomerRequest,mockCachedAttributes).getAttributeValues();
        businessCustomerList = AttributeValueMapper.toAttributeValueListRequest(businessCustomerRequest,mockCachedAttributes).getAttributeValues();
    }

    @Test
    public void assertAttributeSavedWithCorrectId() throws Exception {

        assertThat(findPrivateCustomerAttributeRequest(AttributeConstant.APPROVED_TERMS_CONDITIONS).getValue()).contains(FieldConstant.URL);
        assertThat(findPrivateCustomerAttributeRequest(AttributeConstant.ADDRESS).getValue()).contains(AttributeConstant.ADDRESS);
        assertThat(findPrivateCustomerAttributeRequest(AttributeConstant.SOCIAL_SECURITY_NUMBER).getValue()).contains(AttributeConstant.SOCIAL_SECURITY_NUMBER);
        assertThat(findPrivateCustomerAttributeRequest(AttributeConstant.FIRST_NAME).getValue()).contains(AttributeConstant.FIRST_NAME);
        assertThat(findPrivateCustomerAttributeRequest(AttributeConstant.LAST_NAME).getValue()).contains(AttributeConstant.LAST_NAME);
        assertThat(findPrivateCustomerAttributeRequest(AttributeConstant.PHONE_NUMBER).getValue()).contains(AttributeConstant.PHONE_NUMBER);
        assertThat(findPrivateCustomerAttributeRequest(AttributeConstant.TIMEZONE).getValue()).contains(AttributeConstant.TIMEZONE);

        assertThat(findBusinessCustomerAttributeRequest(AttributeConstant.NAME).getValue()).contains(AttributeConstant.NAME);
        assertThat(findBusinessCustomerAttributeRequest(AttributeConstant.ORGANIZATION_NUMBER).getValue()).contains(AttributeConstant.ORGANIZATION_NUMBER);
        assertThat(findBusinessCustomerAttributeRequest(AttributeConstant.CONTACT_FIRST_NAME).getValue()).contains(AttributeConstant.CONTACT_FIRST_NAME);
        assertThat(findBusinessCustomerAttributeRequest(AttributeConstant.CONTACT_LAST_NAME).getValue()).contains(AttributeConstant.CONTACT_LAST_NAME);
        assertThat(findBusinessCustomerAttributeRequest(AttributeConstant.CONTACT_PHONE_NUMBER).getValue()).contains(FieldConstant.PHONE_NUMBER);
        assertThat(findBusinessCustomerAttributeRequest(AttributeConstant.CONTACT_EMAIL).getValue()).contains(AttributeConstant.CONTACT_EMAIL);
        assertThat(findBusinessCustomerAttributeRequest(AttributeConstant.LICENSE_PLATE).getValue()).contains(AttributeConstant.LICENSE_PLATE);

        assertThat(findBusinessCustomerAttributeRequest(AttributeConstant.APPROVED_PRIVACY_POLICY).getValue()).contains(FieldConstant.URL);
        assertThat(findBusinessCustomerAttributeRequest(AttributeConstant.APPROVED_MARKET_INFO).getValue()).contains(FieldConstant.URL);

    }

    private AttributeValueRequest findPrivateCustomerAttributeRequest (String name) {
        return findMappedAttributeById(privateCustomerList, attributeIdMap, name);
    }

    private AttributeValueRequest findBusinessCustomerAttributeRequest (String name) {
        return findMappedAttributeById(businessCustomerList, attributeIdMap, name);
    }

    private AttributeValueRequest findMappedAttributeById(List<AttributeValueRequest> mappedAttributes, Map<String, Long> attributeIdMap, String attributeName) {
        return mappedAttributes.stream().filter(attributeValueRequest -> attributeValueRequest.getAttributeId().equals(attributeIdMap.get(attributeName))).findFirst().get();
    }

    private Map<String, Long> createAttributeIdMap(List<AttributeResponse> mockCachedAttributes) {
        Map<String, Long> attributeIdMap = new HashMap<>();
        mockCachedAttributes.forEach(attributeResponse -> attributeIdMap.put(attributeResponse.getName(),attributeResponse.getId()));
        return attributeIdMap;
    }

}
