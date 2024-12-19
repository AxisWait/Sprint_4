package pageobj_qa_scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class OrderPage {
    private WebDriver driver;
    private final By Header = By.className("Order_Header__BZXOb");
    private final By checkboxField = By.className("Order_Checkboxes__3lWSI");
    private final By inputName = By.xpath("//input[@placeholder = '* Имя']");
    private final By inputSName = By.xpath("//input[@placeholder = '* Фамилия']");
    private final By inputAddress = By.xpath("//input[@placeholder = '* Адрес: куда привезти заказ']");
    private final By comboBoxMetro = By.xpath("//input[@placeholder = '* Станция метро']");
    private final By buttonNext = By.xpath("//button[@class = 'Button_Button__ra12g Button_Middle__1CSJM']");
    private final By inputPhoneNumber = By.xpath("//input[@placeholder = '* Телефон: на него позвонит курьер']");
    private final By datePicker = By.xpath("//input[@placeholder = '* Когда привезти самокат']");
    private final By inputComment = By.xpath("//input[@placeholder = 'Комментарий для курьера']");
    private final By listBox = By.xpath("//div[@aria-haspopup = 'listbox']");
    private final By orderButton = By.xpath("//button[@class = 'Button_Button__ra12g Button_Middle__1CSJM' and contains(text(),'Заказать')]");
    private final By confirmationButton = By.xpath("//button[@class = 'Button_Button__ra12g Button_Middle__1CSJM' and contains(text(),'Да')]");
    private final By orderSuccess = By.xpath("//div[text() = 'Заказ оформлен']");



    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForLoadOrderPage() {
        new WebDriverWait(driver, 20).until(driver -> (driver.findElement(Header).getText() != null
                && !driver.findElement(Header).getText().isEmpty()
        ));
    }

    public void clickButton(By buttonText){
        WebElement element = driver.findElement(buttonText);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        new WebDriverWait(driver, 10)
                .until(visibilityOfElementLocated(buttonText)).click();
    }

    public void inputField(By locator, String text){
        /*WebElement element = new WebDriverWait(driver, 10)
                .until(elementToBeClickable(locator));
        element.sendKeys(text);*/
        assertTrue(driver.findElement(locator).isEnabled());
        driver.findElement(locator).sendKeys(text);
    }
    public void checkboxFieldSelect(By locator, String field){
        new WebDriverWait(driver, 10)
                .until(visibilityOfElementLocated(locator));

        new WebDriverWait(driver, 10).until(
                elementToBeClickable(By.xpath(String.format("//label[text()='%s']", field)))).click();
    }

    public void dateToSelect(String date){
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.visibilityOfElementLocated(datePicker)).click();

        WebElement dateToSelect = new WebDriverWait(driver, 10).until(
                elementToBeClickable(By.xpath(String.format("//div[text()='%s']", date))));
        dateToSelect.click();
    }
    public void listBoxSelect(By locator, String val){
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.visibilityOfElementLocated(locator)).click();

        new WebDriverWait(driver, 10).until(
                elementToBeClickable(By.xpath(String.format("//div[text()='%s']", val)))).click();
    }

    public void checkStatus(){
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.visibilityOfElementLocated(orderSuccess));
        assertTrue(driver.findElement(orderSuccess).isEnabled());
    }

    public void checkOrderFields(String name, String sName, String address, String metro, String phoneNumber, String data, String rentalPeriod, String color, String comment){
        waitForLoadOrderPage();
        inputField(inputName,name);
        inputField(inputSName,sName);
        inputField(inputAddress,address);
        listBoxSelect(comboBoxMetro,metro);
        inputField(inputPhoneNumber,phoneNumber);
        clickButton(buttonNext);
        waitForLoadOrderPage();
        dateToSelect(data);
        listBoxSelect(listBox,rentalPeriod);
        checkboxFieldSelect(checkboxField, color);
        inputField(inputComment,comment);
        clickButton(orderButton);
        clickButton(confirmationButton);
        checkStatus();
    }
}
