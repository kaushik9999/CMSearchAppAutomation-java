package org.BBAssignment.Tests;

import java.util.Map;
import org.BBAssignment.Pages.ComputerDBPage;
import org.BBAssignment.frameworkUtils.WebDriverBase;
import org.openqa.selenium.support.Color;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class ComputerDBTests extends WebDriverBase {

	ComputerDBPage cdp;
	static String computerName;
	static String computerNameToUpdate;

	@BeforeClass
	public void initialize() {
		cdp = new ComputerDBPage(driver); // initializing the object of ComputerBDPage
	}

	/**
	 * Creates computer, searches for the same and verifies the record is present in
	 * the list
	 **/
	@Test(dataProvider = "SampleAppTestData", priority = 1, enabled = true)
	public void addComputerWithValidDetails(Map<String, String> data)  {
		String random = cdp.randomString();
		computerName = data.get("ComputerName") + random; // generating a random computer name to keep it unique
		cdp.addComputer(computerName, data.get("InrtoDate"), data.get("DiscDate"), data.get("CompanyName"));
		Reporter.log("Add a Computer");
		String alertMes = cdp.getAlertMessage().getText(); // get alert text
		Assert.assertEquals("Assert the success alert is displayed after adding computer",
				"Done! Computer " + computerName + " has been created", alertMes);
		cdp.searchForComputer(computerName);
		String comName = cdp.getFstRowComNameLink().getText();
		Assert.assertEquals("Assert the created computer is retrived on filter search", computerName, comName);
	}

	/**
	 * Deletes an existing computer from the list computer
	 **/
	@Test(dataProvider = "SampleAppTestData", priority = 3, dependsOnMethods = {
			"addComputerWithValidDetails" }, enabled = true)
	public void deleteAComputerFromTheList(Map<String, String> data)  {
		cdp.deleteComputer(computerName);
		String alertMsg = cdp.getAlertMessage().getText();// get alert text
		Assert.assertEquals("Assert the computer is deleted alert is displayed", "Done! Computer has been deleted",
				alertMsg);// assert text

	}

	/**
	 * update the dates existing computer from the list and verify the updated
	 * details
	 **/
	@Test(dataProvider = "SampleAppTestData", priority = 5, enabled = true)
	public void updateCompWithNewDates(Map<String, String> data)  {
		String random = cdp.randomString();
		computerNameToUpdate = data.get("ComputerName") + random;
		cdp.addComputer(computerNameToUpdate, data.get("InrtoDate"), data.get("DiscDate"), data.get("CompanyName"));
		cdp.updateComputer(computerNameToUpdate, data.get("UpdatedIntroDate"), data.get("UpdatedDiscDate"),
				data.get("CompanyName"));
		cdp.searchForComputer(computerNameToUpdate);
		String actualIntroDate = cdp.getFstRowIntroDate().getText();// get updated date text
		String actualDiscDate = cdp.getFstRowDiscDate().getText();// get updated date text
		Assert.assertEquals("Assert introduction date after updating", data.get("ExpIntroDate"), actualIntroDate);//assert updated dates
		Assert.assertEquals("Assert Discontinued date after updating", data.get("ExpDiscDate"), actualDiscDate);//assert updated dates
		cdp.deleteComputer(computerNameToUpdate);
	}

	/**
	 * Below method covers all possible invalid scenario's for Introduced date with
	 * multiple data, refer to the TestData.xlsx under TestData folder
	 **/
	@Test(dataProvider = "SampleAppTestData", priority = 7, enabled = true)
	public void createCompInvalidIntroDate(Map<String, String> data) {
		String random = cdp.randomString();
		computerNameToUpdate = data.get("ComputerName") + random;// concatenate a random string to the name to identify it uniquely 
		cdp.addComputer(computerNameToUpdate, data.get("InrtoDate"), data.get("DiscDate"), data.get("CompanyName"));// call add computer method
		String color = cdp.getIntroDateLabel().getCssValue("color");// get css value of the element for validation
		String hex = Color.fromString(color).asHex();
		System.out.println(hex);
		Assert.assertTrue(hex.equals("#9d261d"));

	}

	/**
	 * Below method covers all possible invalid scenario's for Discontinued date
	 * with multiple data, refer to the TestData.xlsx under TestData folder
	 **/
	@Test(dataProvider = "SampleAppTestData", priority = 9, enabled = true)
	public void createCompInvalidDiscDate(Map<String, String> data) throws InterruptedException {
		String random = cdp.randomString();
		computerNameToUpdate = data.get("ComputerName") + random;
		cdp.addComputer(computerNameToUpdate, data.get("InrtoDate"), data.get("DiscDate"), data.get("CompanyName"));
		String color = cdp.getDiscDateLabel().getCssValue("color");
		String hex = Color.fromString(color).asHex();
		System.out.println(hex);
		Assert.assertTrue(hex.equals("#9d261d"));

	}

	/**
	 * Below method covers all possible invalid scenario's for both Introduced date
	 * and Discontinued date with multiple data, refer to the TestData.xlsx under
	 * TestData folder
	 **/
	@Test(dataProvider = "SampleAppTestData", priority = 11, enabled = true)
	public void createCompInvalidIntroAndDiscDate(Map<String, String> data)  {
		String random = cdp.randomString();
		computerNameToUpdate = data.get("ComputerName") + random;
		cdp.addComputer(computerNameToUpdate, data.get("InrtoDate"), data.get("DiscDate"), data.get("CompanyName"));
		String color = cdp.getIntroDateLabel().getCssValue("color");
		String color1 = cdp.getDiscDateLabel().getCssValue("color");
		String hex = Color.fromString(color).asHex();
		String hex1 = Color.fromString(color1).asHex();
		Assert.assertTrue(hex.equals("#9d261d"));
		Assert.assertTrue(hex1.equals("#9d261d"));

	}

	/**
	 * Covers all positive edge cases like 0001-01-01
	 **/
	@Test(dataProvider = "SampleAppTestData", priority = 13, enabled = true)
	public void createCompIntroDateEdgeCasePos(Map<String, String> data)  {
		String random = cdp.randomString();
		computerNameToUpdate = data.get("ComputerName") + random;
		cdp.addComputer(computerNameToUpdate, data.get("InrtoDate"), data.get("DiscDate"), data.get("CompanyName"));
		String alertMes = cdp.getAlertMessage().getText();
		Assert.assertEquals("Assert the success alert is displayed after adding computer",
				"Done! Computer " + computerNameToUpdate + " has been created", alertMes);
		cdp.deleteComputer(computerNameToUpdate);

	}

	/**
	 * Covers all Negetive edge cases like 0000-01-01
	 **/
	@Test(dataProvider = "SampleAppTestData", priority = 15, enabled = true)
	public void createCompIntroDateEdgeCaseNeg(Map<String, String> data) throws InterruptedException {
		String random = cdp.randomString();
		computerNameToUpdate = data.get("ComputerName") + random;
		cdp.addComputer(computerNameToUpdate, data.get("InrtoDate"), data.get("DiscDate"), data.get("CompanyName"));
		String color = cdp.getIntroDateLabel().getCssValue("color");
		String hex = Color.fromString(color).asHex();
		System.out.println(hex);
		Assert.assertTrue("Assert the error is displayed for introduced date", hex.equals("#9d261d"));

	}

	/**
	 * Covers all Negetive edge cases like 0000-01-01
	 **/
	@Test(dataProvider = "SampleAppTestData", priority = 17, enabled = true)
	public void createCompDiscDateEdgeCaseNeg(Map<String, String> data)  {
		String random = cdp.randomString();
		computerNameToUpdate = data.get("ComputerName") + random;
		cdp.addComputer(computerNameToUpdate, data.get("InrtoDate"), data.get("DiscDate"), data.get("CompanyName"));
		String color = cdp.getDiscDateLabel().getCssValue("color");
		String hex = Color.fromString(color).asHex();
		System.out.println(hex);
		Assert.assertTrue("Assert the error is displayed for introduced date", hex.equals("#9d261d"));

	}
}
