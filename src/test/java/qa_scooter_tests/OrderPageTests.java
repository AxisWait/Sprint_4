package qa_scooter_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobj_qa_scooter.HomePage;
import pageobj_qa_scooter.OrderPage;

import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class OrderPageTests {
    private WebDriver driver;
    private final String name;
    private final String sName;
    private final String address;
    private final String metro;
    private final String phoneNumber;
    private final String data;
    private final String rentalPeriod;
    private final String color;
    private final String comment;

    public OrderPageTests(String name, String sName, String address, String metro, String phoneNumber, String data, String rentalPeriod, String color, String comment) {
        this.name = name;
        this.sName = sName;
        this.address = address;
        this.metro = metro;
        this.phoneNumber = phoneNumber;
        this.data = data;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {"Антон","Зорин","ул. Тополиная 48, корпус 1, кв 198", "Сокольники", "+79182533232", "17","сутки","чёрный жемчуг", "кек чебурек"},
                {"Петр","Лошкович","ул. Тополиная 47, корпус 2, кв 198", "Сокольники", "+79182533233", "18","сутки","чёрный жемчуг", "кек чебурек"},
                {"Иван","Иванов","ул. Гаврилова 90, кв 50", "Красносельская", "89182533237", "19","двое суток","серая безысходность", "кек чебурек"},
        };
    }

    @Before
    public void setUp(){
        /*WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();*/
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }
    @Test
    public void checkOrderForButtonLogo() {

        HomePage homeFAQPage = new HomePage(driver);
        homeFAQPage.clickOrderButtonLogo();
        OrderPage orderPage = new OrderPage(driver);
        orderPage.checkOrderFields(name,sName,address,metro,phoneNumber,data,rentalPeriod,color,comment);


    }

    @Test
    public void checkOrderForButtonUltraBig() {

        HomePage homeFAQPage = new HomePage(driver);
        homeFAQPage.clickOrderButtonUltraBig();
        OrderPage orderPage = new OrderPage(driver);
        orderPage.checkOrderFields(name,sName,address,metro,phoneNumber,data,rentalPeriod,color,comment);

    }
    @After
    public void tearDown() {
        driver.quit();
    }
}
