package pl.selenium.demo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.selenium.demo.tests.BaseTest;

import java.util.List;
import java.util.stream.Collectors;

public class SignUpTest extends BaseTest {
    @Test
    public void signUpTest() {
        String firstname = "Bartek";
        String lastname = "Tester";
        int randomNumber = (int) (Math.random()*1000);
        String email = "tester" + randomNumber + "@test.pl";
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
    @Test
    public void searchWithNoInformationTest() {
        driver.findElements(By.xpath("//li[@id='li_myaccount']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();
        WebElement errorMessages = driver.findElement(By.className("alert-danger"));
        Assert.assertEquals(errorMessages.getText(), "The Email field is required.\n" +
                "The Password field is required.\n" +
                "The Password field is required.\n" +
                "The First name field is required.\n" +
                "The Last Name field is required.");
    }
    @Test
    public void searchWithWrongEmailAddressTest() {
        driver.findElements(By.xpath("//li[@id='li_myaccount']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();
        driver.findElement(By.name("firstname")).sendKeys("Tester");
        driver.findElement(By.name("lastname")).sendKeys("Tester");
        driver.findElement(By.name("phone")).sendKeys("+48 999999999");
        driver.findElement(By.name("email")).sendKeys("tester.pl");
        driver.findElement(By.name("password")).sendKeys("test123");
        driver.findElement(By.name("confirmpassword")).sendKeys("test123");
        driver.findElement(By.xpath("//button[text()=' Sign Up']")).click();
        List<String> errors = driver. findElements(By.xpath("//div[@class='alert alert-danger']//p")).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        Assert.assertTrue(errors.contains("The Email field must contain a valid email address."));
    }
}

