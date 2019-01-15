package org.BBAssignment.CommonUtils;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommonMethods {
	
	protected WebDriver driver;
	public void selectDropDown(List<WebElement> list,String text) {
		
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getText().equalsIgnoreCase(text)) {
				
				list.get(i).click();
			}
		}
		
		
	}
	
	public String randomString() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder(10);
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String output = sb.toString();
		
		return output;
	}
	
	

}
