package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.*;
import ru.netology.page.TripPurchasePage;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

public class TripPurchaseTest {

    private static final String approvedStatus = "APPROVED";
    private static final String declinedStatus = "DECLINED";
    private TripPurchasePage page = new TripPurchasePage();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    // Тест с валидными данными
    @Test
    void successPaymentTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.getApprovalCart_en();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveApprovedNotification();
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertNotEquals(lastOrder.getId(), newOrder.getId());
        PaymentEntity payment = DataHelper.getOrderPayment(newOrder);
        assertEquals(approvedStatus, payment.getStatus());
        assertEquals(45000, payment.getAmount());
    }

    // Тест с неверным месяцев и форматом
    @Test
    void invalidMonthTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.getAnInvalidCardMonth();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidMounth("срок");
        BankCard card2 = DataHelper.GettingAnInvalidCartFormat();
        page.clickThePaymentButton();
        page.clearForm();
        page.completeTheForm(card2);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidMounth("формат");
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест с вводом 00 в поле месяц
    @Test
    void invalidMonth00Test() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.getAnInvalidCardMonth00();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidMounth("формат");
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест с пустым номером месяца
    @Test
    void notMonthTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.GettingAnEmptyCart();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidMounth("обязательно");
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест с неверным именем владельца
    @Test
    void invalidHolderTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.gettingSpecialCharacters();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест с пустым именем владельца
    @Test
    void notHolderTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.gettingAnEmptyHalfCardHolder();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidHolder("обязательно");
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест с не полным номером карты
    @Test
    void invalidCardNumberTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.getInvalidNumberCard_en();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidCardNumber("формат");
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест с пустой картой
    @Test
    void notCardNumberTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.getAnEmptynumberCart_en();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidCardNumber("обязательно");
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест с неполным номеров cvc карты
    @Test
    void invalidCvcTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.GettingInvalidCvcCode();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidCvc("формат");
        BankCard card2 = DataHelper.GettingInvalidCvcCode000();
        page.clickThePaymentButton();
        page.clearForm();
        page.completeTheForm(card2);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidCvc("формат");
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест с вводом 000 в поле cvc кода
    @Test
    void invalidCvc000Test() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.GettingInvalidCvcCode000();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidCvc("формат");
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест с пустым cvc кодом
    @Test
    void notCvcTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.gettingAnEmptyCvcCode();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidCvc("обязательно");
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест с неверным годом и его форматом
    @Test
    void invalidYearTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.gettingTheWrongYear();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidYear("срок");
        BankCard card2 = DataHelper.getInvalidFormatYearCard();
        page.clickThePaymentButton();
        page.clearForm();
        page.completeTheForm(card2);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidYear("формат");
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест с отсутствием года
    @Test
    void notYearTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.gettingAnEmptyYear();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        page.warningAboutAnInvalidYear("обязательно");
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест на срок действия карты
    @Test
    void expiredTermTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.gettingAnExpiredCrd();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        page.expired("срок");
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест на не существующую карту
    @Test
    void notExistCardPaymentTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.getANonExistentNumberCard_en();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveDeclinedNotification();
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertEquals(lastOrder.getId(), newOrder.getId());
    }

    // Тест ввод пустых полей
    @Test
    void clearFieldsErrorsTest() {
        BankCard card = DataHelper.getEmptyCard();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        BankCard card2 = DataHelper.getANonExistentNumberCard_en();
        page.clearForm();
        page.completeTheForm(card2);
        page.isWarnsInvisible();
    }

    // Тест с валидными данными на кредит
    @Test
    void successCreditRequestTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.getApprovalCart_en();
        page.clickTheCreditButton();
        page.completeTheForm(card);
        page.haveApprovedNotification();
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertNotEquals(lastOrder.getId(), newOrder.getId());
        CreditRequestEntity credit = DataHelper.getOrderCreditRegistry(newOrder);
        assertEquals(approvedStatus, credit.getStatus());
    }

    @Test
    void declinedCardCreditRequestTest() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.getNotApprovalCart_en();
        page.clickTheCreditButton();
        page.completeTheForm(card);
        page.haveNotApprovedNotification();
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertNotEquals(lastOrder.getId(), newOrder.getId());
        CreditRequestEntity credit = DataHelper.getOrderCreditRegistry(newOrder);
        assertEquals(declinedStatus, credit.getStatus());
    }

    // Тестирование валидных данных на руссом языке
    @Test
    void successPaymentTest_ru() throws SQLException {
        OrderEntity lastOrder = DataHelper.getLastOrder();
        BankCard card = DataHelper.getApprovalCart_ru();
        page.clickThePaymentButton();
        page.completeTheForm(card);
        page.haveApprovedNotification();
        OrderEntity newOrder = DataHelper.getLastOrder();
        assertNotEquals(lastOrder.getId(), newOrder.getId());
        PaymentEntity payment = DataHelper.getOrderPayment(newOrder);
        assertEquals(approvedStatus, payment.getStatus());
        assertEquals(45000, payment.getAmount());
    }
}
