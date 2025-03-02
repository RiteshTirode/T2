package Ritesh_Academy.AddListofElementToCart;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AddListOfElements {

	static boolean flag = false;
	
	public static void main(String[] args) throws InterruptedException 
	{

		WebDriverManager.chromedriver().setup();

		System.out.println("Enter Products to add in Cart =>");
		String[] productList = GetUserInputOfProduct();
		/*
		 * for (String str : strinArray) { System.out.println(str); }
		 */

		if (flag == true) {
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			WebDriverWait webWait = new WebDriverWait(driver, Duration.ofSeconds(8));
			driver.get("https://rahulshettyacademy.com/seleniumPractise/");
			 AddtoCart(driver,webWait,productList);
			
		}
	}

	

	private static String[] GetUserInputOfProduct() {
		Scanner scanner = new Scanner(System.in);
		ArrayList<String> list = new ArrayList();
		while (true) {
			System.out.println("Enter a value: ");
			String value = scanner.nextLine();
			list.add(value);
			System.out.println("Do you want to add more? (Yes/No): ");
			String resp = scanner.nextLine().toLowerCase();
			if (resp.equals("no")) {
				break;
			}

		}
		String[] stringArray = list.toArray(new String[0]);
		flag = true;
		return stringArray;
	}

	private static void AddtoCart(WebDriver driver, WebDriverWait webWait, String[] productList)
	{
		List<WebElement> productsonPage=driver.findElements(By.cssSelector("h4.product-name"));
		int j=0;
		//System.out.println(productsonPage.get(5).getText());
		for(int i=0;i<productsonPage.size();i++) {
			
			// Brocolli - 1 Kg
           // Brocolli, 1 kg
			// format it to get actual vegetable name
			String[] prdName=productsonPage.get(i).getText().split("-");
			String formatedString=prdName[0].trim();
			//System.out.println(formatedString);
			
			
            // convert array into array list for easy search
     		
			List<String> inputProductList=Arrays.asList(productList);
			// check whether name you extracted is present in arrayList or not-
			if(inputProductList.contains(formatedString)) {
				j++;
				// click on Add to cart
				driver.findElements(By.xpath("//div[@class='product-action']/button")).get(i).click();
			}
			if(j==productList.length) {
				break;
			}
			
		}
		driver.findElement(By.cssSelector("img[alt='Cart']")).click();
		driver.findElement(By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]")).click();
		webWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("promoCode"))));
		driver.findElement(By.className("promoCode")).sendKeys("rahulshettyacademy");
		driver.findElement(By.className("promoBtn")).click();
		webWait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.className("promoInfo"))));
		System.out.println(driver.findElement(By.className("promoInfo")).getText());
	}
}
