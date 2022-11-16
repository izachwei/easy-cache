package com.easycache.cacheapi.core.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Assert;


/**
 * MD5util
 *
 * @author zw35
 */
public class MD5Util {

    private MD5Util() {
    }

    public static String toMD5(String... strings) {
        Assert.notNull(strings, "Original string is required");
        String finalString = String.join("", strings);
        return DigestUtils.md5Hex(finalString);
    }

}
