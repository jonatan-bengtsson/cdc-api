package com.tingcore.cdc.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tingcore.commons.core.utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonTestUtils {


    public static <T> T jsonFileToObject(String file, Class<T> valueType) {
        try {
            String filePath = ClassLoader.getSystemClassLoader().getResource(file).getFile();
            return JsonUtils.getObjectMapper().readValue(new File(filePath), valueType);
        } catch (NullPointerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonFileToListObject(String file, final TypeReference<T> typeReference) {
        try {
            String filePath = ClassLoader.getSystemClassLoader().getResource(file).getFile();
            return JsonUtils.getObjectMapper().readValue(new File(filePath), typeReference);
        } catch (NullPointerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String jsonFileToString(String file) {
        try {
            String filePath = ClassLoader.getSystemClassLoader().getResource(file).getFile();
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (NullPointerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
