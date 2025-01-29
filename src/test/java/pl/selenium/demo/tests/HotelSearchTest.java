package pl.selenium.demo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.selenium.demo.pages.HotelSearchPage;
import pl.selenium.demo.pages.ResultsPage;

import java.util.List;

public class HotelSearchTest extends BaseTest {
    @Test
    public void searchHotelTest() {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setCity("Dubai");
        hotelSearchPage.setDates("17/04/2025", "24/04/2025");
        hotelSearchPage.setTravellers(2,1);
        hotelSearchPage.performSearch();

        ResultsPage resultsPage = new ResultsPage(driver);
        List<String> hotelNames = resultsPage.getHotelNames();

        Assert.assertEquals("Jumeirah Beach Hotel", hotelNames.get(0));
        Assert.assertEquals("Oasis Beach Tower", hotelNames.get(1));
        Assert.assertEquals("Rose Rayhaan Rotana", hotelNames.get(2));
        Assert.assertEquals("Hyatt Regency Perth", hotelNames.get(3));
    }
    @Test
    public void searchHoteNoResultsTest() {

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setDates("17/04/2025", "24/04/2025");
        hotelSearchPage.setTravellers(0,1);
        hotelSearchPage.performSearch();

        ResultsPage resultsPage = new ResultsPage(driver);

        Assert.assertTrue(resultsPage.resultHeading.isDisplayed());
        Assert.assertEquals("No Results Found", resultsPage.getHeadingText());
    }
}

