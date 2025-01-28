import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SignUpTest {
    @Test
    public void signUp() {
        String firstname = "Bartek";
        String lastname = "Tester";
        int randomNumber = (int) (Math.random()*1000);
        String email = "tester" + randomNumber + "@test.pl";
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://www.kurs-selenium.pl/demo/");
        driver.findElements(By.xpath("//li[@id='li_myaccount']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']"))
                .get(1)
                .click();
        driver.findElement(By.name("firstname")).sendKeys(firstname);
        driver.findElement(By.name("lastname")).sendKeys(lastname);
        driver.findElement(By.name("phone")).sendKeys("+48 999999999");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("test123");
        driver.findElement(By.name("confirmpassword")).sendKeys("test123");
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();

        WebElement heading = driver.findElement(By.className("RTL"));
        Assert.assertTrue(heading.getText().contains(lastname));
        Assert.assertEquals("Hi, " + firstname + " " + lastname, heading.getText());

    }

}

