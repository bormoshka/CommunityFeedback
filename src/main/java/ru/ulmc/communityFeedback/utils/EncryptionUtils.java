package ru.ulmc.communityFeedback.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

/**
 * Created by 45 on 16.07.2016.
 */
public class EncryptionUtils {

    public static String createHash(String inputString) {
        String hash = SHA512(inputString);
        for (int i = 0; i < 20; i++) {
            hash = SHA512(hash);
        }
        return hash;
    }

    public static String SHA256(String str) {
        return DigestUtils.sha256Hex(str);
    }

    public static String SHA512(String str) {
        return DigestUtils.sha512Hex(str);
    }
}

