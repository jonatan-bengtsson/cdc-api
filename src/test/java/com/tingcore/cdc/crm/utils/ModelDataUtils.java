package com.tingcore.cdc.crm.utils;

        import com.tingcore.cdc.crm.model.*;
        import com.tingcore.cdc.utils.CommonDataUtils;


public class ModelDataUtils {
    public static OrganizationNumber createOrganizationNumber () {
        return new OrganizationNumber(CommonDataUtils.getNextId(), CommonDataUtils.randomNumberStr(1000,9999), "SE");
    }

    public static AddressCRM createAddress () {
        return new AddressCRM(CommonDataUtils.getNextId(), CommonDataUtils.randomNumberStr(1000,2000), CommonDataUtils.randomNumberStr(1000,2000), CommonDataUtils.randomNumberStr(1000,2000),
                CommonDataUtils.randomNumberStr(1000,2000),CommonDataUtils.randomNumberStr(1000,2000),CommonDataUtils.randomNumberStr(1000,2000));
    }

    public static ApprovedAgreement createApprovedAgreement () {
        return new ApprovedAgreement(CommonDataUtils.getNextId(), CommonDataUtils.randomNumberStr(1000,2000));
    }

    public static SocialSecurityNumber createSocialSecurityNumber () {
        return new SocialSecurityNumber(CommonDataUtils.getNextId(), CommonDataUtils.randomNumberStr(19000101,20180000), "SE");
    }

    public static ApprovedMarketInfo createApprovedMarketInfo () {
        return new ApprovedMarketInfo(CommonDataUtils.getNextId(),CommonDataUtils.randomNumberStr(19000101,20180000),"SE");
    }

    public static ApprovedPrivacy createApprovedPrivacy () {
        return new ApprovedPrivacy(CommonDataUtils.getNextId(),CommonDataUtils.randomNumberStr(19000101,20180000));
    }

    public static LicensePlate createLicensePlate () {
        return new LicensePlate(CommonDataUtils.getNextId(), CommonDataUtils.randomNumberStr(000,999)+"-"+CommonDataUtils.randomNumberStr(000,999), "SE");
    }

    public static PhoneNumber createPhoneNumber () {
        return new PhoneNumber(CommonDataUtils.getNextId(),CommonDataUtils.randomNumberStr(10000,20000),"SE",CommonDataUtils.randomNumberStr(1000,5000));
    }

    public static StringAttribute createStringAttribute() {
        return new StringAttribute(CommonDataUtils.getNextId(), CommonDataUtils.randomNumberStr(100,500));
    }

}
