package org.cmdutils.util;

public class Utils {
    public static Integer toInteger(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            return null;
        }
    }
}
