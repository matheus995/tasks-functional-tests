package tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class TasksTest {

    public WebDriver acessarAplicacao() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//        ChromeOptions chromeOptions = new ChromeOptions();
        DesiredCapabilities cap = new DesiredCapabilities(new ChromeOptions());
        WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL("http://192.168.15.2:4444"), cap);
        } catch (MalformedURLException e) {
            fail(e.getMessage());
        }
//        driver.navigate().to("http://localhost:8001/tasks");
        driver.navigate().to("http://192.168.15.2:8001/tasks");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() {
        WebDriver driver = acessarAplicacao();

        try {
            // clickar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            // escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via selenium");

            // escrever data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");

            // clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            // validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataNoPassado() {
        WebDriver driver = acessarAplicacao();

        try {
            // clickar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            // escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via selenium");

            // escrever data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2020");

            // clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            // validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() {
        WebDriver driver = acessarAplicacao();

        try {
            // clickar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            // escrever data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2020");

            // clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            // validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() {
        WebDriver driver = acessarAplicacao();

        try {
            // clickar em Add Todo
            driver.findElement(By.id("addTodo")).click();

            // escrever descrição
            driver.findElement(By.id("task")).sendKeys("Teste via selenium");

            // clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            // validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", message);
        } finally {
            driver.quit();
        }
    }
}
