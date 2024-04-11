import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Feature("Тест 5-го модуля")
public class FindElements extends TestBase{

    @AndroidFindBy(xpath = "//android.widget.Button[@text=\"Войти в конференцию\"]")
    MobileElement el1;
    @AndroidFindBy(xpath = "//android.widget.Button[@text=\"Отмена\"]")
    MobileElement cancel;

    @AndroidFindBy(id = "us.zoom.videomeetings:id/txtTitle")
    MobileElement  titleText;

    @AndroidFindBy(id = "us.zoom.videomeetings:id/edtConfNumber")
    MobileElement placeEnter;

    @AndroidFindBy(id = "us.zoom.videomeetings:id/btnGotoVanityUrl")
    MobileElement textUnderPlace;

    @AndroidFindBy(id = "us.zoom.videomeetings:id/edtScreenName")
    MobileElement placeText;




    @Description("Тест приложения ZOOM")
    @Story("Zoom тест")
    @Test
    public void sampleTest() {

        Assert.assertTrue(el1.isDisplayed());
        Assert.assertTrue(el1.isEnabled());
        Assert.assertFalse(el1.isSelected());

        swipe(Direction.LEFT);
        swipe(Direction.RIGHT);

        Assert.assertFalse(rotateToLandscape());

        el1.click();


        wait2Sec(MobileBy.id("us.zoom.videomeetings:id/txtTitle"));


        driver.rotate(ScreenOrientation.PORTRAIT);

        cancel.isDisplayed();
        titleText.isDisplayed();
        placeEnter.isDisplayed();
        textUnderPlace.isDisplayed();
        placeText.isDisplayed();

        Assert.assertEquals("Войти в конференцию",titleText.getText());

        MobileElement buttonJoin = (MobileElement) driver.findElementByXPath("//android.widget.Button[@text=\"Войти\"]");
        buttonJoin.isDisplayed();
        MobileElement textUnderButtonJoin = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("textContains(\"Если вы получили ссылку с приглашением, коснитесь ссылки, чтобы войти в конференцию\")"));
        textUnderButtonJoin.isDisplayed();
        MobileElement titleJoinOption = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("textContains(\"ПАРАМЕТРЫ ВХОДА\")"));
        titleJoinOption.isDisplayed();
        MobileElement textSwitch = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator("textContains(\"Не подключать звук\")"));
        textSwitch.isDisplayed();
        MobileElement switchWithText = (MobileElement) driver.findElementById("us.zoom.videomeetings:id/chkNoAudio");
        switchWithText.isDisplayed();

        Assert.assertFalse(buttonJoin.isEnabled());

        String text = placeText.getText();
        placeText.clear();
        Assert.assertNotEquals(text, placeText.getText());
        placeText.sendKeys(text);
        Assert.assertEquals(text, placeText.getText());


    }

    @Step("Явное ожидание")
    private void wait2Sec(By by){
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private enum Direction{
        RIGHT,
        LEFT
    }

    @Step("Swipe {direction}")
    private void swipe(Direction direction){
        int edge = 5;
        int press = 300;
        Dimension dims = driver.manage().window().getSize();
        PointOption<?> pointOptionStart = PointOption.point(dims.width/2,dims.height/2);
        PointOption<?> pointOptionEnd;
        switch (direction) {
            case RIGHT:
                pointOptionEnd = PointOption.point(dims.width - edge, dims.height/2);
                break;
            case LEFT:
                pointOptionEnd = PointOption.point(edge, dims.height/2);
                break;
            default:
                throw new IllegalArgumentException("Свайп не поддерживается! ");

        }

        new TouchAction<>(driver)
                .press(pointOptionStart)
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(press)))
                .moveTo(pointOptionEnd)
                .release()
                .perform();
    }

    @Step("Проверка поворота Landscape")
    private boolean rotateToLandscape(){
        try{
            driver.rotate(ScreenOrientation.LANDSCAPE);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
