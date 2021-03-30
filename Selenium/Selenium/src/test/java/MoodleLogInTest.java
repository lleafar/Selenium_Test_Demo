import org.json.simple.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class MoodleLogInTest {

    public static WebDriver driver;
    private static WebDriverWait wait;
    private GerenciadorArquivoData data;
    private JSONObject dados;

    @Before
    public void iniciando() {
        data = new GerenciadorArquivoData();
    }

    @BeforeClass
    public static void setUp() {
//      PARA EXECUTAR O BROWSER EM MODO HEADLESS
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        FirefoxOptions op = new FirefoxOptions();
        op.setBinary(firefoxBinary);
        op.setHeadless(true);

        driver = new FirefoxDriver(op);
        wait = new WebDriverWait(driver, 20);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @Test
    public void login() {
        dados = data.getJsonDataObject("LogInData.json", "valido");

        driver.get((String) dados.get("urlLogin"));
        driver.findElement(By.id("username")).sendKeys((String) dados.get("username"));
        driver.findElement(By.id("password")).sendKeys((String) dados.get("password"));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("loginbtn")));
        driver.findElement(By.id("loginbtn")).click();

        driver.get((String) dados.get("urlProfile"));
        wait.until(driver -> driver.getCurrentUrl() != dados.get("urlLogin"));

        assertEquals(driver.getCurrentUrl(), dados.get("urlProfile"));
    }

    @Test
    public void loginInvalido(){
        dados = data.getJsonDataObject("LogInData.json", "invalido");

        driver.get((String) dados.get("urlLogin"));
        driver.findElement(By.id("username")).sendKeys((String) dados.get("username"));
        driver.findElement(By.id("password")).sendKeys((String) dados.get("password")); // senha errada aqui
        wait.until(ExpectedConditions.elementToBeClickable(By.id("loginbtn")));
        driver.findElement(By.id("loginbtn")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("accesshide")));

        assertEquals(dados.get("msgErro"), driver.findElement(By.className("accesshide")).getText());
    }

    @AfterClass
    public static void fechaBrowser() {
        driver.quit();
    }

}
