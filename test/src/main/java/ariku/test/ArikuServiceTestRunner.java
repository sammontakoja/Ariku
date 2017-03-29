package ariku.test;

import ariku.CoreServices;
import ariku.test.suite.AddCompetitionOwnerRecordTestSuite;
import ariku.test.suite.CreateCompetitionTestSuite;
import ariku.test.suite.UserLoginTestSuite;

import java.util.Arrays;

/**
 * @author sammontakoja (Ari Aaltonen)
 */
public class ArikuServiceTestRunner {

    public void runTests(CoreServices coreServices) {
        Arrays.asList(
                new UserLoginTestSuite(),
                new CreateCompetitionTestSuite(),
                new AddCompetitionOwnerRecordTestSuite())
                .forEach(testSuite -> testSuite.runAllTests(coreServices));
    }

}
