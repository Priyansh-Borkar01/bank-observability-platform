package com.npst.observability.util;

public final class MaskingUtil {
    public MaskingUtil()
    {

    }
    public static String maskAccountNumber(String accountNumber)
    {
        if(accountNumber == null || accountNumber.length()<4)
        {
            return accountNumber;
        }
        return "XXXXXX" + accountNumber.substring(accountNumber.length() - 4);
    }

    public static String maskMobile(String mobile) {
        if (mobile == null || mobile.length() != 10)
            return mobile;

        return mobile.substring(0,2) +
                "XXXX" +
                mobile.substring(6);
    }

    public static String maskPan(String pan) {
        if (pan == null || pan.length() != 10)
            return pan;

        return pan.substring(0,5) +
                "XXXX" +
                pan.substring(9);
    }
    }

