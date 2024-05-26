package uz.sqbtransactionmanagement.util;

import uz.sqbtransactionmanagement.model.GenericParam;

import java.util.List;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern PHONE_PATTERN = Pattern.compile("\\+[1-9]\\d{1,14}");
    private static final Pattern WALLET_PATTERN = Pattern.compile("999\\d{13}");

    public static boolean validateParameters(List<GenericParam> params) {
        for (GenericParam param : params) {
            if ("phone".equals(param.getParamKey())) {
                if (!PHONE_PATTERN.matcher(param.getParamValue()).matches()) {
                    return false;
                }
            } else if ("wallet".equals(param.getParamKey())) {
                if (!WALLET_PATTERN.matcher(param.getParamValue()).matches() || !isValidLuhn(param.getParamValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValidLuhn(String number) {
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}
