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
        int randomNumber = (int) (Math.random() * 1000);

        LoggedUserPage loggedUserPage = new HotelSearchPage(driver)
                .openSignUpForm()
                .setFirstName("Bartek")
                .setLastName("Testowy")
                .setPhone("+48 999999999")
                .setEmail("tester" + randomNumber + "@test.pl")
                .setPassword("Test123")
                .confirmPassword("Test123")
                .signUp();
        Assert.assertTrue(loggedUserPage.getHeadingText().contains("Testowy"));
        Assert.assertEquals("Hi, Bartek Testowy", loggedUserPage.getHeadingText());
    }

    @Test
    public void searchWithNoInformationTest() {
        SignUpPage signUpPage = new HotelSearchPage(driver)
                .openSignUpForm();
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
        SignUpPage signUpPage = new HotelSearchPage(driver)
                .openSignUpForm()
                .setFirstName("Bartek")
                .setLastName("Testowy")
                .setPhone("+48 999999999")
                .setEmail("email")
                .setPassword("Test123")
                .confirmPassword("Test123");
        signUpPage.signUp();

        Assert.assertTrue(signUpPage.getErrors().contains("The Email field must contain a valid email address."));
    }
}

