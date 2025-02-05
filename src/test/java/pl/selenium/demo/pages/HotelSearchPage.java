package pl.selenium.demo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class HotelSearchPage {
    private WebDriver driver;
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
    @FindBy(name = "travellers")
    private WebElement travellersInput;
    @FindBy(id = "adultPlusBtn")
    private WebElement adultPlusBtn;
    @FindBy(id = "childPlusBtn")
    private WebElement childPlusBtn;
    @FindBy(xpath = "//button[text()= ' Search']")
    private WebElement searchBtn;
    @FindBy(xpath = "//li[@id='li_myaccount']")
    private List<WebElement> myAccountLink;
    @FindBy(xpath = "//a[text()='  Sign Up']")
    private List<WebElement> signUpLink;

    public HotelSearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public HotelSearchPage setCity(String cityName) {
        searchHotelSpan.click();
        searchHotelInput.sendKeys(cityName);
        hotelMatch.click();
        return this;
    }

    public HotelSearchPage setDates(String checkin, String checkout) {
        checkinInput.sendKeys(checkin);
        checkinOutput.sendKeys(checkout);
        return this;
    }

    public HotelSearchPage setTravellers(int adultsToAdd, int childrenToAdd) {
        travellersInput.click();
        addTraveler(adultPlusBtn, adultsToAdd);
        addTraveler(childPlusBtn, childrenToAdd);
        return this;
    }

    private void addTraveler(WebElement travelerBtn, int numberOfTravelers) {
        for (int i = 0; i < numberOfTravelers; i++) {
            travelerBtn.click();
        }
    }

    public ResultsPage performSearch() {
        searchBtn.click();
        return new ResultsPage(driver);
    }

    public SignUpPage openSignUpForm() {
        myAccountLink.stream().filter(WebElement::isDisplayed).findFirst().ifPresent(WebElement::click);
        signUpLink.get(1).click();
        return new SignUpPage(driver);
    }
}
