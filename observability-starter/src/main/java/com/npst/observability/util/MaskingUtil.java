package com.npst.observability.util;

public final class MaskingUtil {

    private MaskingUtil() {
    }

    // 123456789012 → XXXXXXXX9012
    public static String maskAccountNumber(String accountNumber) {

        if (accountNumber == null || accountNumber.length() < 4) {
            return accountNumber;
        }

        return "XXXXXXXX" +
                accountNumber.substring(accountNumber.length() - 4);
    }

    // 9876543210 → 98XXXX3210
    public static String maskMobile(String mobile) {

        if (mobile == null || mobile.length() != 10) {
            return mobile;
        }

        return mobile.substring(0, 2)
                + "XXXX"
                + mobile.substring(6);
    }

    // 1234512345678901 → 12345XXXX8901
    public static String maskPan(String pan) {

        if (pan == null || pan.length() < 9) {
            return pan;
        }

        return pan.substring(0, 5)
                + "XXXX"
                + pan.substring(pan.length() - 4);
    }
}