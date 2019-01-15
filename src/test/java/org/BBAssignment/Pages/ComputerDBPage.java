package org.BBAssignment.Pages;

import java.util.List;

import org.BBAssignment.CommonUtils.CommonMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ComputerDBPage extends CommonMethods{
	
	// Element Locators START
	final String addComputerBtn_css      = "a[id='add']";
	final String computerNameTxtBox_css  = "input[id='name']";
	final String introDate_css           = "input[id='introduced']";
	final String DiscDate_css            = "input[id='discontinued']";
	final String companyDropDown_css     = "select[id='company'] option";
	final String createComputerBtn_css   = "input[value='Create this computer']";
	final String searchBoxCompFilter_css = "input[id='searchbox']";
	final String filterByNameBtn_css     = "input[id='searchsubmit']";
	final String alertMessage_css        = "div[class='alert-message warning']";
	final String fstRowComNameLink_xpath = "//table[@class='computers zebra-striped']//tr[1]//td[1]//a";
	final String deleteComputer_btn      = "input[value='Delete this computer']";
	final String saveComputerBtn_css     ="input[value='Save this computer']";
	final String fstRowIntroDate_xpath   = "//table[@class='computers zebra-striped']//tr[1]//td[2]";
	final String fstRowDiscDate_xpath    = "//table[@class='computers zebra-striped']//tr[1]//td[3]";
	final String introDateLabel_css      ="label[for='introduced']";
	final String DiscDateLabel_css      ="label[for='discontinued']";
	// Element Locators END
	
	public ComputerDBPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		super.driver =driver;
		
	}
	
	@FindBy(css=addComputerBtn_css)// using find the element add computer button
	private WebElement addComputerBtn;
	
	public WebElement getAddComputerBtn() {
		return addComputerBtn;// return the element found
	}
	
	@FindBy(css=computerNameTxtBox_css)
	private WebElement computerNameTxtBox;
	
	public WebElement getComputerNameTxtBox() {
		return computerNameTxtBox;
	}
	
	@FindBy(css=introDate_css)
	private WebElement introDate;
	
	public WebElement getIntroDate() {
		return introDate;
	}
	
	
	@FindBy(css=DiscDate_css)
	private WebElement DiscDate;
	
	public WebElement getDiscDate() {
		return DiscDate;
	}
	
	@FindBy(css=companyDropDown_css)
	private List<WebElement> companyDropDown;
	
	public List<WebElement> getcompanyDropDownList() {
		return companyDropDown;
	}
	
	@FindBy(css=createComputerBtn_css)
	private WebElement createComputerBtn;
	
	public WebElement getCreateComputerBtn() {
		return createComputerBtn;
	}
	
	@FindBy(css=searchBoxCompFilter_css)
	private WebElement searchBoxCompFilter;
	
	public WebElement getSearchBoxCompFilter() {
		return searchBoxCompFilter;
	}
	
	@FindBy(css=filterByNameBtn_css)
	private WebElement filterByNameBtn;
	
	public WebElement getFilterByNameBtn() {
		return filterByNameBtn;
	}
	
	@FindBy(css=alertMessage_css)
	private WebElement alertMessage;
	
	public WebElement getAlertMessage() {
		return alertMessage;
	}
	
	@FindBy(xpath=fstRowComNameLink_xpath)
	private WebElement fstRowComNameLink;
	
	public WebElement getFstRowComNameLink() {
		return fstRowComNameLink;
	}
	@FindBy(css=deleteComputer_btn)
	private WebElement deleteComputer;
	
	public WebElement getDeleteComputer() {
		return deleteComputer;
	}
	
	@FindBy(css=saveComputerBtn_css)
	private WebElement saveComputerBtn;
	
	public WebElement getsaveComputerBtn() {
		return saveComputerBtn;
	}
	
	@FindBy(xpath=fstRowIntroDate_xpath)
	private WebElement fstRowIntroDate;
	
	public WebElement getFstRowIntroDate() {
		return fstRowIntroDate;
	}
	
	
	@FindBy(xpath=fstRowDiscDate_xpath)
	private WebElement fstRowDiscDate;
	
	public WebElement getFstRowDiscDate() {
		return fstRowDiscDate;
	}
	
	
	@FindBy(css=introDateLabel_css)
	private WebElement introDateLabel;
	
	public WebElement getIntroDateLabel() {
		return introDateLabel;
	}
	
	@FindBy(css=DiscDateLabel_css)
	private WebElement DiscDateLabel;
	
	public WebElement getDiscDateLabel() {
		return DiscDateLabel;
	}
	
	
	

	public void addComputer(String compName, String introDate,String DiscDate,String company) {
		getAddComputerBtn().click();
		getComputerNameTxtBox().sendKeys(compName);
		getIntroDate().sendKeys(introDate);
		getDiscDate().sendKeys(DiscDate);
		selectDropDown(getcompanyDropDownList(), company);
	    getCreateComputerBtn().click();
	}
	

	public void searchForComputer(String compName) {
		getSearchBoxCompFilter().sendKeys(compName);
		getFilterByNameBtn().click();
	}
	public void deleteComputer(String compName) {
		searchForComputer(compName);
		getFstRowComNameLink().click();
		getDeleteComputer().click();
	}
	
	public void updateComputer(String compName,String newIntroDate,String newDescDate,String company) {
		searchForComputer(compName);
		getFstRowComNameLink().click();
		getIntroDate().clear();
		getIntroDate().sendKeys(newIntroDate);
		getDiscDate().clear();
		getDiscDate().sendKeys(newDescDate);
		selectDropDown(getcompanyDropDownList(), company);
		getsaveComputerBtn().click();
	}

}
