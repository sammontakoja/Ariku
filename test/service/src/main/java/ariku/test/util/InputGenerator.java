package ariku.test.util;

import java.util.UUID;

/**
 * @author sammontakoja
 */
public class InputGenerator {

    public static String randomUsername() {
        return UUID.randomUUID().toString();
    }

}
