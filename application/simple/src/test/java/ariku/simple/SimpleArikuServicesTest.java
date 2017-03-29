package ariku.simple;

import ariku.test.ArikuServiceTestRunner;
import org.junit.Test;

/**
 * @author sammontakoja
 */
public class SimpleArikuServicesTest {

    @Test
    public void runTests() {
        new ArikuServiceTestRunner().runTests(new SimpleArikuServices());
    }


}