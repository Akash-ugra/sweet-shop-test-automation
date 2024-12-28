import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the Sweet Shop application.
 * This class contains tests for various functionalities of the Sweet Shop website.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static WebDriverManager webDriverManager;

    /**
     * Set up the test environment by instantiating a WebDriver instance.
     * This test suite uses ChromeDriver as its WebDriver implementation.
     * <p>
     * In order to run the tests successfully, the path to the ChromeDriver
     * executable must be specified in the {@code webdriver.chrome.driver}
     * property.  The path can be set in the following ways:
     * <ul>
     * <li>Using the {@code -D} option when running the JUnit test suite:
     *     {@code -Dwebdriver.chrome.driver=/path/to/chromedriver}
     * <li>Setting the {@code webdriver.chrome.driver} environment variable
     * <li>Specifying the path in a {@code junit-platform.properties}
     *     file
     * </ul>
     * <p>
     * If the path is not specified, the test suite will fail with a
     * {@link org.openqa.selenium.WebDriverException}.
     */
    @BeforeAll
    public static void setUp() {
        webDriverManager.chromedriver().setup();
        // Set the path for the WebDriver executable
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1366, 720));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Tests that the home page title is "Sweet Shop".
     * <p>
     * This method navigates to the Sweet Shop website and retrieves the page title.
     * It then asserts that the title matches the expected value.
     */
    @Test
    public void testHomePageTitle() {
        driver.get("https://sweetshop.netlify.app/");
        String title = driver.getTitle();
        assertEquals("Sweet Shop", title);
    }

    /**
     * Tests that clicking a section link navigates to the correct section.
     * <p>
     * This method navigates to the Sweet Shop website and retrieves a link element
     * with a text of "About".  It then clicks the link and asserts that the
     * current URL contains the expected section URL.
     */
    @Test
    public void testNavigationToSection() {
        driver.get("https://sweetshop.netlify.app/");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/nav/div/div/ul/li[1]/a")));
        button.click();
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl != null;
        assertTrue(currentUrl.contains("about"));
        WebElement element = driver.findElement(By.xpath("/html/body/div/header/p[2]"));
        assertEquals("Sweet Shop is a project created to help demonstrate some of the great features of the Chrome DevTools which may be of use to people who help test web applications. Sweet Shop encompasses common issues found in real-world web applications!", element.getText());
    }

    /**
     * Tests that clicking the "Add to Basket" button adds an item to the Basket.
     * <p>
     * This method navigates to the Sweet Shop website, clicks the "Add to Basket"
     * button, and verifies that an item is added to the Basket.
     */
    @Test
    @Order(2)
    public void testAddToBasketAndTotalPrice() {
        driver.get("https://sweetshop.netlify.app/");
        WebElement browseSweetButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/header/a")));
        browseSweetButton.click();
        WebElement basketSection = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/nav/div/div/ul/li[4]/a")));
        basketSection.click();
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div[1]/h4[1]/span[2]")));
        assertEquals("2", webElement.getText());
        WebElement cupsElement = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/ul/li[1]/div/h6"));
        assertEquals("Chocolate Cups", cupsElement.getText());
        WebElement strawsElement = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/ul/li[2]/div/h6"));
        assertEquals("Sherbert Straws", strawsElement.getText());
        WebElement totalPrice = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/ul/li[3]/strong"));
        assertEquals("£1.75", totalPrice.getText());
    }

    /**
     * Tests that removing an item from the Basket updates the total price.
     * <p>
     * This method navigates to the Sweet Shop website, adds three items to the
     * Basket, and verifies that the total price is updated correctly after
     * removing one of the items.
     */
    @Test
    @Order(1)
    public void testRemoveFromBasketAndTotalPrice() {
        driver.get("https://sweetshop.netlify.app/");
        WebElement browseSweetButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/header/a")));
        browseSweetButton.click();
        WebElement addChocolateCups = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div[1]/div[1]/div/div[2]/a")));
        addChocolateCups.click();
        WebElement addSherbertStraws = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div[1]/div[2]/div/div[2]/a")));
        addSherbertStraws.click();
        WebElement addBonBons = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div[1]/div[4]/div/div[2]/a")));
        addBonBons.click();
        WebElement basketSection = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/nav/div/div/ul/li[4]/a")));
        basketSection.click();
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div[1]/h4[1]/span[2]")));
        assertEquals("3", webElement.getText());
        WebElement totalPrice = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/ul/li[4]/strong"));
        assertEquals("£2.75", totalPrice.getText());
        WebElement removeBonBons = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/ul/li[1]/div/a"));
        removeBonBons.click();
        driver.switchTo().alert().accept();
        WebElement newPrice = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/ul/li[3]/strong"));
        assertEquals("£1.75", newPrice.getText());
    }

    /**
     * Tests the login process.
     * <p>
     * This method navigates to the Sweet Shop website, enters login credentials,
     * and verifies that the welcome message is displayed correctly.
     */
    @Test
    public void testLoginProcess() {
        driver.get("https://sweetshop.netlify.app/");
        WebElement LoginSection = driver.findElement(By.xpath("/html/body/nav/div/div/ul/li[2]/a"));
        LoginSection.click();
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div/form/div[1]/input[1]")));
        emailInput.sendKeys("test@example.com");
        WebElement passwordInput = driver.findElement(By.xpath("/html/body/div[1]/div/div/form/div[2]/input"));
        passwordInput.sendKeys("password");
        WebElement loginButton = driver.findElement(By.xpath("/html/body/div[1]/div/div/form/button"));
        loginButton.click();
        WebElement welcomeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/header/p")));
        assertEquals("Welcome back test@user.com", welcomeElement.getText());
    }

    /**
     * Cleans up the test environment by quitting the WebDriver instance.
     * <p>
     * This method is annotated with {@code @AfterAll}, indicating that it
     * will be executed once after all tests in the class have run.
     * If the {@code driver} instance is not null, it will be terminated to
     * close the browser and free up resources.
     */
    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}