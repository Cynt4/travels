package pl.selenium.demo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.selenium.demo.pages.HotelSearchPage;
import pl.selenium.demo.pages.LoggedUserPage;
import pl.selenium.demo.pages.SignUpPage;

import java.util.List;

public class SignUpTest extends BaseTest {
    @Test
    public void signUpTest() {
        String firstname = "Bartek";
        String lastname = "Tester";
        int randomNumber = (int) (Math.random()*1000);
        String email = "tester" + randomNumber + "@test.pl";

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.setFirstName(firstname);
        signUpPage.setLastName(lastname);
        signUpPage.setPhone("+48 999999999");
        signUpPage.setEmail(email);
        signUpPage.setPassword("Test123");
        signUpPage.confirmPassword("Test123");
        signUpPage.signUp();
        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        Assert.assertTrue(loggedUserPage.getHeadingText().contains(lastname));
        Assert.assertEquals("Hi, " + firstname + " " + lastname, loggedUserPage.getHeadingText());
    }
    @Test
    public void signUpTest2() {
        String firstname = "Bartek";
        String lastname = "Tester";
        int randomNumber = (int) (Math.random()*1000);
        String email = "tester" + randomNumber + "@test.pl";

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.fillSignUpForm(firstname, lastname, "+48 999999999", email, "Test123");

        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);
        Assert.assertTrue(loggedUserPage.getHeadingText().contains(lastname));
        Assert.assertEquals("Hi, " + firstname + " " + lastname, loggedUserPage.getHeadingText());
    }
    @Test
    public void searchWithNoInformationTest() {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signUp();
        List<String> errors = signUpPage.getErrors();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(errors.contains("The Email field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The First name field is required."));
        softAssert.assertTrue(errors.contains("The Last Name field is required."));
    }
    @Test
    public void searchWithWrongEmailAddressTest() {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSignUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.setFirstName("Bartek");
        signUpPage.setLastName("Testowy");
        signUpPage.setPhone("+48 999999999");
        signUpPage.setEmail("email");
        signUpPage.setPassword("Test123");
        signUpPage.confirmPassword("Test123");
        signUpPage.signUp();

        Assert.assertTrue(signUpPage.getErrors().contains("The Email field must contain a valid email address."));
    }
}

