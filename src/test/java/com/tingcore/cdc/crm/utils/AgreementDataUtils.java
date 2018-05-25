package com.tingcore.cdc.crm.utils;

import com.tingcore.cdc.utils.CommonDataUtils;

/**
 * @author palmithor
 * @since 2018-05-25
 */
public class AgreementDataUtils {


    public static String getValidJson(final boolean active) {
        return "{" +
                "\"id\":" + CommonDataUtils.getNextId() + "," +
                "\"version\": 1," +
                "\"created\": 1527228438000," +
                "\"updated\": 1527228438000," +
                "\"organizationId\": " + CommonDataUtils.getNextId() + "," +
                "\"name\": \"v0.0.1\"," +
                "\"active\": " + active + "," +
                "\"content\": \"string\"" +
                "  }";
    }
}
