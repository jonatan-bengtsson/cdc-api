package com.tingcore.cdc.service;

import org.apache.commons.lang3.StringUtils;
import org.hashids.Hashids;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author palmithor
 * @since 2017-09-27
 */
@Service
public class HashIdService {

    private static final String SALT = "6d01516d-27a8-427d-ade2-0284efcd2c53";
    private static final String ID_LOWER_CASE = "id";
    private static final String ID_CAMEL_CASE = "Id";
    private static final String ID_UPPER_CASE = "ID";
    private static final String IDS_LOWER_CASE = "ids";
    private static final String IDS_CAMEL_CASE = "Ids";
    private static final String IDS_UPPER_CASE = "IDs";
    static final int HASH_LENGTH = 10;

    @Bean
    public Hashids hashids() {
        return new Hashids(SALT, HASH_LENGTH);
    }
    private final Hashids hashids;


    public HashIdService() {
        this.hashids = new Hashids(SALT, HASH_LENGTH);
    }

    /**
     * Decode hash with single Long value
     *
     * @param hash the has to decode
     * @return Optional of the decoded Long.
     */
    public Optional<Long> decode(final String hash) {
        final long[] decode = hashids.decode(hash);

        return decode.length > 0 ? Optional.of(decode[0]) : Optional.empty();
    }

    public String encode(final Long value) {
        return hashids.encode(value);
    }

    public static boolean isIdKey(final String currentKey) {
        return StringUtils.equalsAny(currentKey, ID_UPPER_CASE, ID_LOWER_CASE) ||
                StringUtils.endsWithAny(currentKey, ID_UPPER_CASE, ID_CAMEL_CASE, ID_LOWER_CASE, IDS_UPPER_CASE, IDS_CAMEL_CASE, IDS_LOWER_CASE);
    }
}
