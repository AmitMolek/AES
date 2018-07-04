package root.unittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CoverageTestSignIn.class , TestClientSignIn.class})
public class AllTests {

}
