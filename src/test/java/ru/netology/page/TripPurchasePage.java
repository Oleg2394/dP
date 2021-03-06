package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.BankCard;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class TripPurchasePage {

    // Создаем переменные с поиском SelenideElement
    private SelenideElement paymentButton = $$("button[type='button'][role='button'] span.button__text").find(exactText("Купить"));
    private SelenideElement creditButton = $$("button[type='button'][role='button'] span.button__text").find(exactText("Купить в кредит"));
    private SelenideElement continueButton = $$("button[type='button'][role='button'] span.button__text").find(text("Продолжить"));
    private SelenideElement paymentTitle = $$("#root>div>h3.heading.heading_size_m.heading_theme_alfa-on-white").find(exactText("Оплата по карте"));
    private SelenideElement creditTitle = $$("#root>div>h3.heading.heading_size_m.heading_theme_alfa-on-white").find(exactText("Кредит по данным карты"));
    private SelenideElement approvedNotification = $("div.notification.notification_status_ok .notification__content");
    private SelenideElement declinedNotification = $("div.notification.notification_status_error .notification__content");

    // Создаем переменные для полей формы
    private SelenideElement cardNumberField;
    private SelenideElement monthField;
    private SelenideElement yearField;
    private SelenideElement holderField;
    private SelenideElement cvcField;

    private SelenideElement cardNumberWarn;
    private SelenideElement monthWarn;
    private SelenideElement yearWarn;
    private SelenideElement holderWarn;
    private SelenideElement cvcWarn;


    public TripPurchasePage() {
        open("http://localhost:8080");
        paymentButton.shouldBe(visible);
        creditButton.shouldBe(visible);
        continueButton.shouldNotBe(visible);

        ElementsCollection formElements = $$("#root>div>form.form.form_size_m.form_theme_alfa-on-white .input__inner>.input__top");

        cardNumberField = formElements.find(exactText("Номер карты")).parent().$(".input__box>input.input__control");
        cardNumberWarn = cardNumberField.parent().parent().$("span.input__sub");
        monthField = formElements.find(exactText("Месяц")).parent().$(".input__box>input.input__control");
        monthWarn = monthField.parent().parent().$("span.input__sub");
        yearField = formElements.find(exactText("Год")).parent().$(".input__box>input.input__control");
        yearWarn = formElements.find(exactText("Год")).parent().$("span.input__sub");
        holderField = formElements.find(exactText("Владелец")).parent().$(".input__box>input.input__control");
        holderWarn = formElements.find(exactText("Владелец")).parent().$("span.input__sub");
        cvcField = formElements.find(exactText("CVC/CVV")).parent().$(".input__box>input.input__control");
        cvcWarn = formElements.find(exactText("CVC/CVV")).parent().$("span.input__sub");
    }


    // Проверяем видимость элементов на странице
    private void ifThereAreElementsOnThePage() {
        cardNumberField.shouldBe(visible);
        monthField.shouldBe(visible);
        yearField.shouldBe(visible);
        holderField.shouldBe(visible);
        cvcField.shouldBe(visible);
        continueButton.shouldBe(visible);
    }

    //Создаем действие нажать кнопку оплаты
    public void clickThePaymentButton() {
        paymentButton.click();
        paymentTitle.shouldBe(visible);
        ifThereAreElementsOnThePage();
    }

    // Создаем действие нажать кнопку оплаты в кредит
    public void clickTheCreditButton() {
        creditButton.click();
        creditTitle.shouldBe(visible);
        ifThereAreElementsOnThePage();
    }

    // Очищаем форму
    public void clearForm() {
        cardNumberField.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.BACK_SPACE);
        monthField.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.BACK_SPACE);
        yearField.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.BACK_SPACE);
        holderField.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.BACK_SPACE);
        cvcField.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.BACK_SPACE);
    }

    // Заполняем форму
    public void completeTheForm(BankCard card) {
        cardNumberField.setValue(card.getCardNumber());
        monthField.setValue(card.getExpireMonth());
        yearField.setValue(card.getExpireYear());
        holderField.setValue(card.getHolderName());
        cvcField.setValue(card.getCvcCode());
        continueButton.click();
        continueButton.waitUntil(enabled, 20000);
    }


    // Предупреждение о неверном номере карты
    public void warningAboutAnInvalidCardNumber(String warnText) {
        cardNumberWarn.shouldBe(visible);
        cardNumberWarn.shouldHave(text(warnText));
    }

    // Предупреждение о неверном номере месяца
    public void warningAboutAnInvalidMounth(String warnText) {
        monthWarn.shouldBe(visible);
        monthWarn.shouldHave(text(warnText));
    }

    // Предупреждение о неверном номере года
    public void warningAboutAnInvalidYear(String warnText) {
        yearWarn.shouldBe(visible);
        yearWarn.shouldHave(text(warnText));
    }

    // Проверяем на истечение строка
    public void expired(String warnText) {
        if (yearWarn.isDisplayed()) {
            yearWarn.shouldHave(visible);
            yearWarn.shouldHave(text(warnText));
        } else {
            monthWarn.shouldHave(visible);
            monthWarn.shouldHave(text(warnText));
        }
    }

    // Предупреждение о неверном вводе в поле владелец
    public void warningAboutAnInvalidHolder(String warnText) {
        holderWarn.shouldBe(visible);
        holderWarn.shouldHave(text(warnText));
    }

    // Предупреждение о неверном вводе cvc кода
    public void warningAboutAnInvalidCvc(String warnText) {
        cvcWarn.shouldBe(visible);
        cvcWarn.shouldHave(text(warnText));
    }

    public void isWarnsInvisible() {
        cardNumberWarn.shouldNotBe(visible);
        monthWarn.shouldNotBe(visible);
        yearWarn.shouldNotBe(visible);
        holderWarn.shouldNotBe(visible);
        cvcWarn.shouldNotBe(visible);
    }

    public void haveApprovedNotification() {
        approvedNotification.shouldBe(visible);
    }

    public void haveDeclinedNotification() {
        declinedNotification.shouldBe(visible);
    }

    public void haveNotApprovedNotification() {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        approvedNotification.shouldNotBe(visible);
    }
}