package tasks.prod;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class HealthCheckIT {

    @Test
    public void healthCheck() throws MalformedURLException {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
        DesiredCapabilities cap = new DesiredCapabilities(new ChromeOptions());
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.15.2:4444"), cap);
        try {
            driver.navigate().to("http://192.168.15.2:9999/tasks");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            String version = driver.findElement(By.id("version")).getText();
            Assert.assertTrue(version.startsWith("build"));
        } finally {
            driver.quit();
        }
    }
}
