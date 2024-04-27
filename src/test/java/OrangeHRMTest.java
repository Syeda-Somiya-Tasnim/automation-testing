import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrangeHRMTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    @Test
    public void runTest() throws InterruptedException {
        login();
        clickPIMMenu();
        clickAddButton();
        toggleLoginDetails();
        addUsernameAndPassword();
        clickPIMMenu();
        searchEmployeeByName();
        assertRecordFound();
        clickContactDetails();
        addMobileNumber();
    }

    public void login() {
        driver.findElement(By.id("txtUsername")).sendKeys("Admin");
        driver.findElement(By.id("txtPassword")).sendKeys("admin123");
        driver.findElement(By.id("btnLogin")).click();
    }

    public void clickPIMMenu() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu_pim_viewPimModule")));
        driver.findElement(By.id("menu_pim_viewPimModule")).click();
    }

    public void clickAddButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnAdd")));
        driver.findElement(By.id("btnAdd")).click();
    }

    public void toggleLoginDetails() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("chkLogin")));
        WebElement toggleButton = driver.findElement(By.id("chkLogin"));
        if (!toggleButton.isSelected()) {
            toggleButton.click();
        }
    }

    public void addUsernameAndPassword() {
        driver.findElement(By.id("user_name")).sendKeys("TestUser");
        driver.findElement(By.id("user_password")).sendKeys("Test123");
        driver.findElement(By.id("re_password")).sendKeys("Test123");
    }

    public void searchEmployeeByName() {
        driver.findElement(By.id("empsearch_employee_name_empName")).sendKeys("John Smith");
        driver.findElement(By.id("searchBtn")).click();
    }

    public void assertRecordFound() {
        WebElement recordFoundMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("empsearch_employee_name_empName")));
        Assertions.assertTrue(recordFoundMessage.isDisplayed(), "Record found message is not displayed");
    }

    public void clickContactDetails() {
        driver.findElement(By.id("sidenav")).findElement(By.linkText("Contact Details")).click();
    }

    public void addMobileNumber() {
        WebElement mobileTextField = driver.findElement(By.id("contact_emp_mobile"));
        mobileTextField.clear();
        mobileTextField.sendKeys("1234567890");
        driver.findElement(By.id("btnSave")).click();
    }

    @AfterAll
    public void tearDown() {
        driver.quit();
    }
}
