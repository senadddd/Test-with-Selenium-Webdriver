package MyStepdefss;



import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;


import static org.junit.Assert.assertEquals;


public class MyStepdefss {
    WebDriver driver;
    WebDriverWait wait;

    @Given("I start a browser {string}")
    public void iStartABrowser(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "C:\\edgedriver\\edgedriver.exe");
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--remote-allow-origins=*", "ignore-certficate-errors");
            driver = new EdgeDriver(edgeOptions);

        }
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://login.mailchimp.com/signup/");


    }

    private String randomOutput(int lengthOfOutput) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String Output = "";
        Random rnd = new Random();
        for (int i = 0; i < lengthOfOutput; i++) {
            int c = (int) (rnd.nextFloat() * chars.length());
            Output += chars.charAt(c);
        }
        return Output;
    }



        @Given("I input valid email {string}")
        public void iInputValidEmail(String Email) {
           String email = "";
           if (Email.equalsIgnoreCase("random")){
               email = randomOutput(20) + "@outlook.com";
           } else if (Email.equalsIgnoreCase("missing")) {
               email = "";

           }
            WebElement emailField = driver.findElement(By.id("email"));
            emailField.click();
            emailField.clear();
            emailField.sendKeys(email);


        }


        @Given("I first input username {string}")
        public void iFirstInputUsername(String username) {
        String usernameText = "";
        if (username.equalsIgnoreCase("Random")){
            usernameText = randomOutput((20));
        } else if (username.equalsIgnoreCase("longUsername")){
            usernameText = randomOutput(100);
        } else if (username.equalsIgnoreCase("exist")) {
            usernameText = "username";

        }


            WebElement usernameField = driver.findElement(By.cssSelector("#new_username"));
            usernameField.click();
            usernameField.clear();
            usernameField.sendKeys(usernameText);


        }

        @Given("I then input a valid {string}")
        public void iThenInputAValid(String password) {
            WebElement passField = driver.findElement(By.cssSelector("#new_password"));
            passField.click();
            passField.clear();
            passField.sendKeys(password);
        }

        @When("I click the signup button")
        public void iClickTheSignupButton() {
            Actions action = new Actions(driver);
            WebElement checkBox = driver.findElement(By.name("marketing_newsletter"));
            checkBox.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("create-account-enabled")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("marketing_newsletter")));
            WebElement button = driver.findElement(By.id("create-account-enabled"));
            action.moveToElement(button).perform();
            button.click();
        }

        @Then("I can connect {string}")
        public void iCanConnect(String connect) {

        String actual = "";
        String expected = connect;

        if (connect.equalsIgnoreCase("Yes")){
            wait.until(ExpectedConditions.urlContains("success"));
            if (driver.getCurrentUrl().contains("success")){
                actual = "Yes";
            }
        } else {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("invalid-error")));
            WebElement errorText = driver.findElement(By.className("invalid-error"));
            if (errorText.getText().contains("An email address must contain a single @.")) {
                actual = "MissingEmail";
            } else if (errorText.getText().contains("Enter a value less than 100 characters long")) {
                actual = "Characters";
            } else if (errorText.getText().contains("Great minds think alike - someone already has this username.")) {
                actual = "Exist";
            }
            assertEquals(actual, expected);
        }
        }





        @After
        public void tearDown() {
            driver.close();


        }
    }













