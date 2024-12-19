package pageobj_qa_scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class HomePage {

    private final WebDriver driver;
    //Текстовый заголовок Вопросы о важном
    private final By subHeader = By.xpath("//div[contains(text(), 'Вопросы о важном')]");
    //Выпадающий список
    private final By accordion = By.className("accordion");
    //Выпадающий элемент выпадающего списка
    private final By  accordionPanel = By.className("accordion__panel");
    //Элемент выпадающего списка
    private final By  accordionHeading = By.className("accordion__button");
    //Кнопка Заказать в лого
    private final By orderButtonLogo = By.xpath("//div[@class='Header_Nav__AGCXC']/button[@class='Button_Button__ra12g']");
    //Большая кнопка Заказать на HomePage
    private final By orderButtonUltraBig = By.xpath("(//button[contains(text(), 'Заказать')])[2]");
    private final By homeRoadMap = By.xpath("//div[@class='Home_RoadMap__2tal_']");


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }
    public List<WebElement> listAccordionHeadingElements(){
        return  driver.findElements(accordionHeading);
    }
    public List<WebElement> listAccordionPanelElements(){
        return  driver.findElements(accordionPanel);
    }

    public void scrollToElement(By elem){
        WebElement element = driver.findElement(elem);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
    }
    public void waitForLoadHomeFAQPage() {
        new WebDriverWait(driver, 20).until(driver -> (driver.findElement(subHeader).getText() != null
                && !driver.findElement(subHeader).getText().isEmpty()
        ));
    }
    public void clickAccordionHeadingButtonsAndCheckText(String text, int position){
        assertTrue(driver.findElement(accordion).isEnabled());
        WebElement elementListHeading = listAccordionHeadingElements().get(position);
        WebElement elementListPanel = listAccordionPanelElements().get(position);
        elementsAccordionIsDisplayed(elementListHeading);
        elementListHeading.click();
        elementsAccordionIsDisplayed(elementListPanel);
        assertEquals(String.format("Ошибка! Ожидаемый текст: %s", text), text, elementListPanel.getText());

    }

    public void elementsAccordionIsDisplayed(WebElement element){
        new WebDriverWait(driver, 50)
                .until(visibilityOfElementLocated(accordion));
        assertTrue(element.isDisplayed());
    }

    public void checkFAQText(String text, int position){
        waitForLoadHomeFAQPage();
        scrollToElement(subHeader);
        clickAccordionHeadingButtonsAndCheckText(text, position);
    }
    public void clickButton(By buttonText){
        new WebDriverWait(driver, 10)
                .until(visibilityOfElementLocated(buttonText)).click();
    }

    public void clickOrderButtonLogo(){
        clickButton(orderButtonLogo);
    }
    public void clickOrderButtonUltraBig(){
        scrollToElement(homeRoadMap);
        clickButton(orderButtonUltraBig);
    }

}

