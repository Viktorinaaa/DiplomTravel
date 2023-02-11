package ru.netology.travel.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.jupiter.api.*;
import lombok.SneakyThrows;
import ru.netology.travel.data.DataHelper;
import ru.netology.travel.page.PaymentPage;
import ru.netology.travel.page.CreditPage;
import ru.netology.travel.data.DataHelperSQL;
import ru.netology.travel.data.TablesSQL.OrderEntity;
import ru.netology.travel.page.MainPage;

import java.sql.DriverManager;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TravelTest {


    @BeforeAll
    static void setupAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    @SneakyThrows
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWindow();
    }


    /////1/////////
    @Test
    void shouldValidTransferPayApproved() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getValidInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationSuccessfulPay();
        assertEquals("APPROVED", DataHelperSQL.getPaymentEntityLast().getStatus());
    }

    /////2/////////
    @Test
    void shouldValidTransferPayDeclined() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getDeclinedInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationErrorPay();
        assertEquals("DECLINED", DataHelperSQL.getPaymentEntityLast().getStatus());
    }

    //////3/////////
    @Test
    void shouldValidTransferCreditApproved() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getValidInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationSuccessfulPay();
        assertEquals("APPROVED", DataHelperSQL.getCreditRequestEntityLast().getStatus());
    }

    /////4/////////
    @Test
    void shouldValidTransferCreditDeclined() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getDeclinedInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationErrorPay();
    }

    /////5-1/////////
    @Test
    void shouldTransferPayApprovedEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getEmptyInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
        paymentPage.notificationObligatoryField();
    }

    /////5-2/////////
    @Test
    void shouldTransferCreditApprovedEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getEmptyInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
        creditPage.notificationObligatoryField();
    }

    /////6-1/////////
    @Test
    void shouldTransferPayApprovedYearInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getYearMinusCurrentInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationCardExpiryDate();
    }

    /////6-2/////////
    @Test
    void shouldTransferCreditApprovedYearMinusCurrent() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getYearMinusCurrentInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationCardExpiryDate();
    }

    /////7-1/////////
    @Test
    void shouldTransferPayApprovedNameInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getNameInvalidInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationObligatoryField();
    }

    /////7-2/////////
    @Test
    void shouldTransferCreditApprovedNameInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getNameInvalidInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationObligatoryField();
    }

    /////8-1/////////
    @Test
    void shouldTransferPayApprovedMonthInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getMonthInvalidInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidCardExpirationDate();
    }

    /////8-2/////////
    @Test
    void shouldTransferCreditApprovedMonthInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getMonthInvalidInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidCardExpirationDate();
    }

    /////9-1/////////
    @Test
    void shouldTransferPayApprovedNumberCardNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getNumberCardNotMaskInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
    }

    /////9-2/////////
    @Test
    void shouldTransferCreditApprovedNumberCardNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getNumberCardNotMaskInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
    }

    /////10-1/////////
    @Test
    void shouldTransferPayApprovedCvcNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getCvcNotMaskInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
    }

    /////10-2/////////
    @Test
    void shouldTransferCreditApprovedCvcNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getCvcNotMaskInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
    }

    /////11-1/////////
    @Test
    void shouldTransferPayApprovedNumberCardEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getNumberCardEmptyInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
    }

    /////11-2/////////
    @Test
    void shouldTransferCreditApprovedNumberCardEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getNumberCardEmptyInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
    }

    /////12-1////////
    @Test
    void shouldTransferPayApprovedMonthEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getMonthEmptyInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
    }

    /////12-2////////
    @Test
    void shouldTransferCreditApprovedMonthEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getMonthEmptyInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
    }

    /////13-1////////
    @Test
    void shouldTransferPayApprovedNameEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getNameEmptyInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationObligatoryField();
    }

    /////13-2////////
    @Test
    void shouldTransferCreditApprovedNameEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getNameEmptyInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationObligatoryField();
    }

    ///////14-1///////
    @Test
    void shouldTransferPayApprovedYearEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getYearEmptyInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
    }

    ///////14-2///////
    @Test
    void shouldTransferCreditApprovedYearEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getYearEmptyInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
    }

    ////////15-1////////
    @Test
    void shouldTransferPayApprovedCvcEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getCvcEmptyInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
    }

    ////////15-2////////
    @Test
    void shouldTransferCreditApprovedCvcEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getCvcEmptyInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
    }

    /////16-1/////////
    @Test
    void shouldTransferPayApprovedMonthNull() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getMonthNullInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidCardExpirationDate();
    }

    /////16-2/////////
    @Test
    void shouldTransferCreditApprovedMonthNull() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getMonthNullInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidCardExpirationDate();
    }

    ////////17-1////////
    @Test
    void shouldTransferPayApprovedMonthRu() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getMonthRuInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
    }

    ////////17-2////////
    @Test
    void shouldTransferCreditApprovedMonthRu() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getMonthRuInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
    }

    ////////18-1/////
    @Test
    void shouldTransferPayApprovedYearNull() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getYearNullInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationCardExpiryDate();
    }

    ////////18-2/////
    @Test
    void shouldTransferCreditApprovedYearNull() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getYearNullInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationCardExpiryDate();
    }

    ////////19-1/////
    @Test
    void shouldTransferPayApprovedNumberCardSeparator() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getNumberCardWithSeparatorInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationSuccessfulPay();
    }

    ////////19-2/////
    @Test
    void shouldTransferCreditApprovedNumberCardSeparator() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getNumberCardWithSeparatorInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationSuccessfulPay();
    }

    ////////20-1/////
    @Test
    void shouldTransferPayApprovedMonthNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getMonthNotMaskInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
    }

    ////////20-2/////
    @Test
    void shouldTransferCreditApprovedMonthNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getMonthNotMaskInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
    }

    /////21-1/////////
    @Test
    void shouldTransferPayApprovedYearPlusCurrent() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getYearPlusCurrentInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidCardExpirationDate();
        //assertEquals("APPROVED", DataHelperSQL.getPaymentEntityLast().getStatus());
    }

    /////21-2/////////
    @Test
    void shouldTransferCreditApprovedYearPlusCurrent() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getYearPlusCurrentInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidCardExpirationDate();
    }

}
