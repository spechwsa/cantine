package acceptance.cantine;

import cucumber.api.CucumberOptions;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith( Cucumber.class )
@CucumberOptions( strict = true, monochrome = true, plugin = { "pretty", "html:target/site/cucumber" }, features = {
        "src/test/resources/cantine" } )
public class RunCucumberTest {

}
