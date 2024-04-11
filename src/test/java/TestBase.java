import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;

public class TestBase {

    public AndroidDriver driver;
    private DriverFactory driverFactory = new DriverFactory();
    @Before
    public void setDriver() throws MalformedURLException {
        driver = driverFactory.setUp();

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    @After
    public void tearDown() {
        driver.quit();
    }
}
