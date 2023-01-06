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
    void tearDown(){
        driver.quit();
        driver=null;
    }

    @Test
    void shouldSendSuccessfulFormSuitAllRequires (){
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Сергеев-Петров Александр");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79195645454");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",text.trim());
    }
    @Test
    void shouldNotSendSuccessfulFormFillingEnglishName () {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Alex");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79195645454");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id= name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",text.trim());
    }

    @Test
    void shouldNotSendSuccessfulFormDueToEmptyCheckBox () {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Сергеев-Петров Александр");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79195645454");
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"agreement\"].input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }

    @Test
    void shouldNotSendSuccessfulFormDueToEmptyNameBox () {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("+79195645454");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldNotSendSuccessfulFormDueToEmptyPhoneBox () {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Сергеев-Петров Александр");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldNotSendSuccessfulFormFillingPhoneBoxByLetters () {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("Сергеев-Петров Александр");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys(":Жаба");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldNotSendSuccessfulFormFillingNameBoxByFigures () {
        driver.findElement(By.cssSelector("[data-test-id=\"name\"] input")).sendKeys("111");
        driver.findElement(By.cssSelector("[data-test-id=\"phone\"] input")).sendKeys(":+79195645454");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=\"name\"].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

}
