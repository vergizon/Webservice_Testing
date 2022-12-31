package ru.netology.web;

import static org.junit.jupiter.api.Assertions.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setupAll () {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearsDown(){
        driver.quit();
        driver=null;
    }

    @Test
    void Positivetest (){
        driver.get("http://localhost:9999/");
        List<WebElement> list = driver.findElements(By.className("input__control"));
        list.get(0).sendKeys("Александр");
        list.get(1).sendKeys("+79265612740");


        //driver.findElement(By.className("input__control")).sendKeys("Alex");
        //driver.findElement(By.className()).sendKeys("+79265612740");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",text);
    }
}
