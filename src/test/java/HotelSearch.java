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

public class HotelSearch {
    @Test
    public void searchHotel() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://www.kurs-selenium.pl/demo/");
        driver.findElement(By.id("s2id_autogen8")).click();
        driver.findElement(By.xpath("//div[@id='select2-drop']//input")).sendKeys("Dubai");
        driver.findElement(By.xpath("//div[@class='select2-result-label']//span")).click();
        driver.findElement(By.name("checkin")).sendKeys("17/04/2025");
        driver.findElement(By.name("checkout")).click();
        driver.findElements(By.xpath("//td[@class='day ' and text()='30']"))
                .stream()
                .filter(el -> el.isDisplayed())
                .findFirst()
                .ifPresent(el -> el.click());
        driver.findElement(By.name("travellers")).click();
        driver.findElement(By.id("adultPlusBtn")).click();
        driver.findElement(By.id("childPlusBtn")).click();
        driver.findElement(By.xpath("//button[text()= ' Search']")).click();
        List<String> hotelNames = driver.findElements(By.xpath("//h4[contains(@class,'list_title')]//b"))
                .stream()
                .map(el -> el.getAttribute("textContent"))
                .collect(Collectors.toList());
        hotelNames.forEach(System.out::println);

        Assert.assertEquals("Jumeirah Beach Hotel", hotelNames.get(0));
        Assert.assertEquals("Oasis Beach Tower", hotelNames.get(1));
        Assert.assertEquals("Rose Rayhaan Rotana", hotelNames.get(2));
        Assert.assertEquals("Hyatt Regency Perth", hotelNames.get(3));
    }
    @Test
    public void searchHoteNoResults() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://www.kurs-selenium.pl/demo/");
        driver.findElement(By.name("checkin")).sendKeys("17/04/2025");
        driver.findElement(By.name("checkout")).click();
        driver.findElements(By.xpath("//td[@class='day ' and text()='30']"))
                .stream()
                .filter(el -> el.isDisplayed())
                .findFirst()
                .ifPresent(el -> el.click());
        driver.findElement(By.name("travellers")).click();
        driver.findElement(By.id("childPlusBtn")).click();
        driver.findElement(By.xpath("//button[text()= ' Search']")).click();
        WebElement info = driver.findElement(By.className("itemscontainer"));
        Assert.assertEquals("No Results Found", info.getText());

    }
}

