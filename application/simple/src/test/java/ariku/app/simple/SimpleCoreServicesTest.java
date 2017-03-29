package ariku.app.simple;

import ariku.test.ArikuServiceTestRunner;
import org.junit.Test;

/**
 * @author sammontakoja
 */
public class SimpleCoreServicesTest {

    @Test
    public void runTests() {
        new ArikuServiceTestRunner().runTests(new SimpleCoreServices());
    }


}