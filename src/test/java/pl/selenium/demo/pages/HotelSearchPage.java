package pl.selenium.demo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class HotelSearchPage {
    @FindBy(id = "s2id_autogen8")
    private WebElement searchHotelSpan;
    @FindBy(xpath = "//div[@id='select2-drop']//input")
    private WebElement searchHotelInput;
    @FindBy(xpath = "//div[@class='select2-result-label']//span")
    private WebElement hotelMatch;
    @FindBy(name = "checkin")
    private WebElement checkinInput;
    @FindBy(name = "checkout")
    private WebElement checkinOutput;
    @FindBy(name="travellers")
    private WebElement travellersInput;
    @FindBy(id="adultPlusBtn")
    private WebElement adultPlusBtn;
    @FindBy(id="childPlusBtn")
    private WebElement childPlusBtn;
    @FindBy(xpath="//button[text()= ' Search']")
    private WebElement searchBtn;
    @FindBy(xpath="//li[@id='li_myaccount']")
    private List<WebElement> myAccountLink;
    @FindBy(xpath="//a[text()='  Sign Up']")
    private List<WebElement> signUpLink;
    public HotelSearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setCity(String cityName) {
        searchHotelSpan.click();
        searchHotelInput.sendKeys(cityName);
        hotelMatch.click();
    }

    public void setDates(String checkin, String checkout) {
        checkinInput.sendKeys(checkin);
        checkinOutput.sendKeys(checkout);
    }

    public void setTravellers(int adultsToAdd, int childrenToAdd) {
        travellersInput.click();
        addTraveler(adultPlusBtn, adultsToAdd);
        addTraveler(childPlusBtn, childrenToAdd);
    }

    private void addTraveler(WebElement travelerBtn, int numberOfTravelers) {
        for (int i = 0; i < numberOfTravelers; i++) {
            travelerBtn.click();
        }
    }

    public void performSearch() {
        searchBtn.click();
    }
    public void openSignUpForm() {
        myAccountLink.stream().filter(WebElement::isDisplayed).findFirst().ifPresent(WebElement::click);
        signUpLink.get(1).click();
    }
}
