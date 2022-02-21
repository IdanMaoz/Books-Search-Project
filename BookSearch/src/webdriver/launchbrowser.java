
package webdriver;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class launchbrowser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//setting the driver executable
		System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");

		//Initiating your chromedriver
		WebDriver driver=new ChromeDriver();

		//Applied wait time
		
		

		//open browser with desried URL
		driver.get("https://www.amazon.com/");
		
		
		
		WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys("the Lost World by Arthur Conan Doyle");
		
		
		Select books=new Select(driver.findElement(By.xpath("//*[@id=\"searchDropdownBox\"]")));
		books.selectByIndex(5);
		
		NumOfResultsTest(driver);
		
		LongestSearchTest(driver);
		
		NumOfEnglishResultsTest(driver);
		
		
	}
	
	public static void NumOfResultsTest(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(5));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"search\"]/span/div/h1/div/div[1]/div/div/span[1]")));
			WebElement totalEl=driver.findElement(By.xpath("//*[@id=\"search\"]/span/div/h1/div/div[1]/div/div/span[1]"));
			
			String total = totalEl.getText();
			if (total.contains("over")) 
				System.out.println("The number of results is: " + total.split(" ")[2] + " " + total.split(" ")[3]);
			else if(total.contains("-"))
				System.out.println("The number of results is: " + total.split(" ")[2]);
			else
				System.out.println("The number of results is: " + total.split(" ")[0]);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			System.out.println("You need to select a book within 5 minutes");
			driver.close();
			
		}
		
	}
	
	public static void LongestSearchTest(WebDriver driver) {
		String longest = "";
		List<WebElement> next;
		while (true) {
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			List<WebElement> allElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//span[@class='a-size-medium a-color-base a-text-normal']")));
			WebElement compareEl = driver.findElement(By.id("twotabsearchtextbox"));
			String compareText = compareEl.getAttribute("value").toLowerCase();
			
			
			for (WebElement element : allElements)
		    {
				String elText = element.getText().toLowerCase();
				
		        if(elText.length() <= 70 && elText.length() > longest.length() && elText.contains(compareText) ) {
		        	longest = elText;
		        }
		        
		    }

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			next = driver.findElements(By.linkText("Next"));
			
			if (next.isEmpty())
				break;
			next.get(0).click();
		
		
		}
		
		System.out.println("The longest book name with the full search name is: \"" + longest +"\"");
		
	}
	
	public static void NumOfEnglishResultsTest(WebDriver driver) {
		WebElement el=null;
		try {
		 el = driver.findElement(By.xpath("//*[@id=\"p_n_feature_nine_browse-bin/3291437011\"]/span/a/div/label"));
		}
		catch(NoSuchElementException e) {
			System.out.println("This book hasn't English language");
		}
		if (el != null) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(5));
			
				wait.until(ExpectedConditions.invisibilityOf(el));
			
				//check if we choose English
				driver.findElement(By.xpath("//*[@id=\"p_n_feature_nine_browse-bin/3291437011\"]/span/a/span"));
			
		
				WebElement totalEnEl = driver.findElement(By.xpath("//*[@id=\"search\"]/span/div/h1/div/div[1]/div/div"));
				
				
				String totalEn = totalEnEl.getText();
				System.out.println("The number of results in English is: " + totalEn.split(" ")[2]);
			
			}
			catch(TimeoutException ex) {
				System.out.println("You need to select the English language within 5 minutes");
				driver.close();
			}
			catch(NoSuchElementException e){
			
				System.out.println("You selected element that isn't book language-English");
				driver.close();
			
			}
			
			
		}
		
	}

}

