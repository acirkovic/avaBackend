import java.util.Random;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang.StringUtils.isBlank;

public class UtilsClass {

    private static final String DEFAULT_DOMAIN = "@1secmail.net";
    private static final String TEMPLATE_MAIL_ADDRESS = "https://www.1secmail.com/?login=";

    public static String randomizeEmail() {
        return randomizeEmail(DEFAULT_DOMAIN);
    }

    public static String randomizeEmail(String domain) {
        int length = 10;
        if (isBlank(domain))
            domain = DEFAULT_DOMAIN;
        return (randomAlphanumeric(length) + domain.toLowerCase());
    }

    public static String randomizePassword(int length) {
        return ("A1" + randomAlphanumeric(length).toLowerCase() + "!");
    }

    public static String randomizeUsername() {
        int length = 10;
        return ("A" + randomAlphabetic(length).toLowerCase());
    }

    public static String createURLforEmail(String email) {
        String[] trimedEmail = email.split("@", 2);
        return (TEMPLATE_MAIL_ADDRESS + trimedEmail[0] + "&domain=" + trimedEmail[1]);
    }
}
