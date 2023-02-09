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


    /////1_Валидная оплата по карте Approved/////////
    @Test
    void shouldValidTransferPayApproved() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getValidInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationSuccessfulPay();
        assertEquals("APPROVED", DataHelperSQL.getPaymentEntityLast().getStatus());
    }

    /////!!!! баг2_оплата по карте DECLINED/////////
    @Test
    void shouldValidTransferPayDeclined() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getDeclinedInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationErrorPay();
        assertEquals("DECLINED", DataHelperSQL.getPaymentEntityLast().getStatus());
    }

    //////3_Валидная оплата с помощью кредита Approved/////////
    @Test
    void shouldValidTransferCreditApproved() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getValidInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationSuccessfulPay();
        assertEquals("APPROVED", DataHelperSQL.getCreditRequestEntityLast().getStatus());
    }

    /////!!!! баг4_Валидная оплата с помощью кредита Declined/////////
    @Test
    void shouldValidTransferCreditDeclined() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getDeclinedInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationErrorPay();
        //assertEquals("DECLINED", DataHelperSQL.getPaymentEntityLast().getStatus());
    }

    /////5-1_Пустая отправка данных по карте/////////
    @Test
    void shouldTransferPayApprovedEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getEmptyInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
        paymentPage.notificationObligatoryField();

        //assertEquals("APPROVED", DataHelperSQL.getPaymentEntityLast().getStatus());
    }

    /////5-2_Пустая отправка данных по карте/////////
    @Test
    void shouldTransferCreditApprovedEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getEmptyInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
        creditPage.notificationObligatoryField();
        //assertEquals("DECLINED", DataHelperSQL.getPaymentEntityLast().getStatus());
    }

    /////6-1_Истек срок действия карты/////////
    @Test
    void shouldTransferPayApprovedYearInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getYearMinusCurrentInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationCardExpiryDate();
        //assertEquals("APPROVED", DataHelperSQL.getPaymentEntityLast().getStatus());
    }

    /////6-2_Истек срок действия карты/////////
    @Test
    void shouldTransferCreditApprovedYearMinusCurrent() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getYearMinusCurrentInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationCardExpiryDate();
        //assertEquals("APPROVED", DataHelperSQL.getCreditRequestEntityLast().getStatus());
    }

    /////!!!7-1-баг_Невалидный владелец/////////
    @Test
    void shouldTransferPayApprovedNameInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getNameInvalidInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationObligatoryField();
    }
    /////!!!7-2-баг_Невалидный владелец/////////
    @Test
    void shouldTransferCreditApprovedNameInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getNameInvalidInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationObligatoryField();
    }

    /////8-1_Невалидное значение месяца/////////
    @Test
    void shouldTransferPayApprovedMonthInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getMonthInvalidInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidCardExpirationDate();
        //assertEquals("APPROVED", DataHelperSQL.getPaymentEntityLast().getStatus());
    }
    /////8-2_Невалидное значение месяца/////////
    @Test
    void shouldTransferCreditApprovedMonthInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getMonthInvalidInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidCardExpirationDate();
    }

    /////9-1_Номер карты не по маске/////////
    @Test
    void shouldTransferPayApprovedNumberCardNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getNumberCardNotMaskInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
    }

    /////9-2_Номер карты не по маске/////////
    @Test
    void shouldTransferCreditApprovedNumberCardNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getNumberCardNotMaskInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
    }

    /////10-1_CVC не по маске/////////
    @Test
    void shouldTransferPayApprovedCvcNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getCvcNotMaskInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
    }

    /////10-2_CVC не по маске/////////
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

    ////////!!!!!15-1////////
    @Test
    void shouldTransferPayApprovedCvcEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getCvcEmptyInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
    }

    ////////!!!!!15-2////////
    @Test
    void shouldTransferCreditApprovedCvcEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getCvcEmptyInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
    }

    /////!!!!!!16-баг-1_месяц=00/////////
    @Test
    void shouldTransferPayApprovedMonthNull() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getMonthNullInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidCardExpirationDate();
    }

    /////!!!!!!16-баг-2_месяц=00/////////
    @Test
    void shouldTransferCreditApprovedMonthNull() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getMonthNullInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidCardExpirationDate();
    }

    ////////17-1 месяц на кириллице////////
    @Test
    void shouldTransferPayApprovedMonthRu() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getMonthRuInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
    }

    ////////17-2 месяц на кириллице////////
    @Test
    void shouldTransferCreditApprovedMonthRu() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getMonthRuInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
    }

    ////////18-1 год=00/////
    @Test
    void shouldTransferPayApprovedYearNull() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getYearNullInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationCardExpiryDate();
    }

    ////////18-2 год=00/////
    @Test
    void shouldTransferCreditApprovedYearNull() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getYearNullInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationCardExpiryDate();
    }

    ////////19-1 разделители у номера карты/////
    @Test
    void shouldTransferPayApprovedNumberCardSeparator() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getNumberCardWithSeparatorInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationSuccessfulPay();
    }

    ////////19-1 разделители у номера карты/////
    @Test
    void shouldTransferCreditApprovedNumberCardSeparator() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getNumberCardWithSeparatorInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationSuccessfulPay();
    }

    ////////20-1 месяц не по маске/////
    @Test
    void shouldTransferPayApprovedMonthNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getMonthNotMaskInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidFormat();
    }

    ////////20-2 месяц не по маске/////
    @Test
    void shouldTransferCreditApprovedMonthNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getMonthNotMaskInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidFormat();
    }

    /////21-1_Год +6лет/////////
    @Test
    void shouldTransferPayApprovedYearPlusCurrent() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var infoCard = DataHelper.getYearPlusCurrentInfoCard();
        paymentPage.payWriteInForm(infoCard);
        paymentPage.notificationInvalidCardExpirationDate();
        //assertEquals("APPROVED", DataHelperSQL.getPaymentEntityLast().getStatus());
    }

    /////21-2_Год +6лет/////////
    @Test
    void shouldTransferCreditApprovedYearPlusCurrent() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var infoCard = DataHelper.getYearPlusCurrentInfoCard();
        creditPage.creditWriteInForm(infoCard);
        creditPage.notificationInvalidCardExpirationDate();
    }








































/*
    /////1_Валидная оплата по карте Approved/////////
    @Test
    //@SneakyThrows
    void shouldValidTransferPayApproved() {
        //var conn = DataHelperSQL.getOrderEntity();
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        paymentPage.payApproved(payNumberCardApproved, payValuesCardApproved);
        paymentPage.notificationPay();
    }

    /////!!!! баг2_Валидная оплата по карте DECLINED/////////
    @Test
    void shouldValidTransferPayDeclined() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardDeclined = DataHelper.getInfoCardDeclined();
        var payValuesCardDeclined = DataHelper.getInfoValid();
        paymentPage.payDeclined(payNumberCardDeclined, payValuesCardDeclined);
        paymentPage.notificationPay();
    }

    /////3_Валидная оплата с помощью кредита Approved/////////
    @Test
    void shouldValidTransferCreditApproved() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardApproved = DataHelper.getInfoCardApproved();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        creditPage.creditApproved(creditNumberCardApproved, creditValuesCardApproved);
        creditPage.notificationCredit();
    }

    /////!!!! баг4_Валидная оплата с помощью кредита Declined/////////
    @Test
    void shouldValidTransferCreditDeclined() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardDeclined = DataHelper.getInfoCardDeclined();
        var creditValuesCardDeclined = DataHelper.getInfoValid();
        creditPage.creditDeclined(creditNumberCardDeclined, creditValuesCardDeclined);
        creditPage.notificationCredit();
    }

    /////5-1_Пустая отправка данных по карте/////////
    @Test
    void shouldTransferPayApprovedEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var payPage = mainPage.transferPay();
        payPage.transferPayEmpty();
        payPage.payEmpty();
    }

    /////5-2_Пустая отправка данных по карте/////////
    @Test
    void shouldTransferCreditApprovedEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        creditPage.transferCreditEmpty();
        creditPage.creditEmpty();
    }

    /////6-1_Истек срок действия карты/////////
    @Test
    void shouldTransferPayApprovedYearInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        var payYearInvalidCardApproved = DataHelper.getYearInvalid();
        paymentPage.transferPayApprovedYearInvalid(payNumberCardApproved, payValuesCardApproved, payYearInvalidCardApproved);
        paymentPage.payApprovedYearInvalid();
    }

    /////6-2_Истек срок действия карты/////////
    @Test
    void shouldTransferCreditApprovedYearInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardApproved = DataHelper.getInfoCardApproved();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        var creditYearInvalidCardApproved = DataHelper.getYearInvalid();
        creditPage.transferCreditApprovedYearInvalid(creditNumberCardApproved, creditValuesCardApproved, creditYearInvalidCardApproved);
        creditPage.creditApprovedYearInvalid();
    }

    /////!!!7-1-баг_Невалидный владелец/////////
    @Test
    void shouldTransferPayApprovedNameInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        var payNameInvalidCardApproved = DataHelper.getNameInvalid();
        paymentPage.transferPayApprovedNameInvalid(payNumberCardApproved, payValuesCardApproved, payNameInvalidCardApproved);
        paymentPage.payApprovedNameInvalid();
    }

    /////!!!7-2-баг_Невалидный владелец/////////
    @Test
    void shouldTransferCreditApprovedNameInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardApproved = DataHelper.getInfoCardApproved();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        var creditNameInvalidCardApproved = DataHelper.getNameInvalid();
        creditPage.transferCreditApprovedNameInvalid(creditNumberCardApproved, creditValuesCardApproved, creditNameInvalidCardApproved);
        creditPage.creditApprovedNameInvalid();
    }

    /////8-1_Невалидное значение месяца/////////
    @Test
    void shouldTransferPayApprovedMonthInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        var payMonthInvalidCardApproved = DataHelper.getMonthInvalid();
        paymentPage.transferPayApprovedMonthInvalid(payNumberCardApproved, payValuesCardApproved, payMonthInvalidCardApproved);
        paymentPage.payApprovedMonthInvalid();
    }

    /////8-2_Невалидное значение месяца/////////
    @Test
    void shouldTransferCreditApprovedMonthInvalid() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardApproved = DataHelper.getInfoCardApproved();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        var creditMonthInvalidCardApproved = DataHelper.getMonthInvalid();
        creditPage.transferCreditApprovedMonthInvalid(creditNumberCardApproved, creditValuesCardApproved, creditMonthInvalidCardApproved);
        creditPage.creditApprovedMonthInvalid();
    }

    /////9-1_Номер карты не по маске/////////
    @Test
    void shouldTransferPayApprovedNumberCardNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardNotMaskCardApproved = DataHelper.getNumberCardNotMask();
        var payValuesCardApproved = DataHelper.getInfoValid();
        paymentPage.transferPayApprovedNumberCardNotMask(payNumberCardNotMaskCardApproved, payValuesCardApproved);
        paymentPage.payApprovedNumberCardNotMask();
    }

    /////9-2_Номер карты не по маске/////////
    @Test
    void shouldTransferCreditApprovedNumberCardNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardNotMaskCardApproved = DataHelper.getNumberCardNotMask();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        creditPage.transferCreditApprovedNumberCardNotMask(creditNumberCardNotMaskCardApproved, creditValuesCardApproved);
        creditPage.creditApprovedNumberCardNotMask();
    }

    /////10-1_CVC не по маске/////////
    @Test
    void shouldTransferPayApprovedCvcNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        var payCvcNotMaskCardApproved = DataHelper.getCvcNotMask();
        paymentPage.transferPayApprovedCvcNotMask(payNumberCardApproved, payValuesCardApproved, payCvcNotMaskCardApproved);
        paymentPage.payApprovedCvcNotMask();
    }

    /////10-2_CVC не по маске/////////
    @Test
    //@SneakyThrows
    void shouldTransferCreditApprovedCvcNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardApproved = DataHelper.getInfoCardApproved();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        var creditCvcNotMaskCardApproved = DataHelper.getCvcNotMask();
        creditPage.transferCreditApprovedCvcNotMask(creditNumberCardApproved, creditValuesCardApproved, creditCvcNotMaskCardApproved);
        creditPage.creditApprovedCvcNotMask();
    }

    /////11-1/////////
    @Test
    void shouldTransferPayApprovedNumberCardEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payValuesCardApproved = DataHelper.getInfoValid();
        paymentPage.transferPayApprovedNumberCardEmpty(payValuesCardApproved);
        paymentPage.payApprovedNumberCardEmpty();
    }

    /////11-2/////////
    @Test
    void shouldTransferCreditApprovedNumberCardEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        creditPage.transferCreditApprovedNumberCardEmpty(creditValuesCardApproved);
        creditPage.creditApprovedNumberCardEmpty();
    }


    /////12-1////////
    @Test
    void shouldTransferPayApprovedMonthEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        paymentPage.transferPayApprovedMonthEmpty(payNumberCardApproved, payValuesCardApproved);
        paymentPage.payApprovedCvcNotMask();
    }

    /////12-2////////
    @Test
    void shouldTransferCreditApprovedMonthEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardApproved = DataHelper.getInfoCardApproved();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        creditPage.transferCreditApprovedMonthEmpty(creditNumberCardApproved, creditValuesCardApproved);
        creditPage.creditApprovedCvcNotMask();
    }

    /////13-1////////
    @Test
    void shouldTransferPayApprovedNameEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        paymentPage.transferPayApprovedNameEmpty(payNumberCardApproved, payValuesCardApproved);
        paymentPage.payApprovedNameEmpty();
    }

    /////13-2////////
    @Test
    void shouldTransferCreditApprovedNameEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardApproved = DataHelper.getInfoCardApproved();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        creditPage.transferCreditApprovedNameEmpty(creditNumberCardApproved, creditValuesCardApproved);
        creditPage.creditApprovedNameEmpty();
    }


    ///////14-1///////
    @Test
    void shouldTransferPayApprovedYearEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        paymentPage.transferPayApprovedYearEmpty(payNumberCardApproved, payValuesCardApproved);
        paymentPage.payApprovedYearEmpty();
    }

    ///////14-2///////
    @Test
    void shouldTransferCreditApprovedYearEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardApproved = DataHelper.getInfoCardApproved();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        creditPage.transferCreditApprovedYearEmpty(creditNumberCardApproved, creditValuesCardApproved);
        creditPage.creditApprovedYearEmpty();
    }

    ////////!!!!!15-1////////
    @Test
    void shouldTransferPayApprovedCvcEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        paymentPage.transferPayApprovedCvcEmpty(payNumberCardApproved, payValuesCardApproved);
        paymentPage.payApprovedCvcEmpty();
    }

    ////////15-2////////
    @Test
    void shouldTransferCreditApprovedCvcEmpty() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardApproved = DataHelper.getInfoCardApproved();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        creditPage.transferCreditApprovedCvcEmpty(creditNumberCardApproved, creditValuesCardApproved);
        creditPage.creditApprovedCvcEmpty();
    }

    /////!!!!!!16-баг-1_месяц=00/////////
    @Test
    void shouldTransferPayApprovedMonthNull() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        var payMonthNullCardApproved = DataHelper.getMonthNull();
        paymentPage.transferPayApprovedMonthNull(payNumberCardApproved, payValuesCardApproved, payMonthNullCardApproved);
        paymentPage.payApprovedMonthInvalid();
    }

    /////!!!!!!16-баг-2_месяц=00/////////
    @Test
    void shouldTransferCreditApprovedMonthNull() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardApproved = DataHelper.getInfoCardApproved();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        var creditMonthNullCardApproved = DataHelper.getMonthNull();
        creditPage.transferCreditApprovedMonthNull(creditNumberCardApproved, creditValuesCardApproved, creditMonthNullCardApproved);
        creditPage.creditApprovedMonthInvalid();
    }

    ////////17-1 месяц на кириллице////////
    @Test
    void shouldTransferPayApprovedMonthRu() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        var payMonthRuCardApproved = DataHelper.getMonthRu();
        paymentPage.transferPayApprovedMonthRu(payNumberCardApproved, payValuesCardApproved, payMonthRuCardApproved);
        paymentPage.payApprovedMonthRu();
    }

    ////////17-2 месяц на кириллице////////
    @Test
    void shouldTransferCreditApprovedMonthRu() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardApproved = DataHelper.getInfoCardApproved();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        var creditMonthRuCardApproved = DataHelper.getMonthRu();
        creditPage.transferCreditApprovedMonthRu(creditNumberCardApproved, creditValuesCardApproved, creditMonthRuCardApproved);
        creditPage.creditApprovedMonthRu();
    }

    ////////18-1 год=00/////
    @Test
    void shouldTransferPayApprovedYearNull() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        var payYearNullCardApproved = DataHelper.getYearNull();
        paymentPage.transferPayApprovedYearNull(payNumberCardApproved, payValuesCardApproved, payYearNullCardApproved);
        paymentPage.payApprovedYearNull();
    }

    ////////18-2 год=00/////
    @Test
    void shouldTransferCreditApprovedYearNull() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardApproved = DataHelper.getInfoCardApproved();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        var creditYearNullCardApproved = DataHelper.getYearNull();
        creditPage.transferCreditApprovedYearNull(creditNumberCardApproved, creditValuesCardApproved, creditYearNullCardApproved);
        creditPage.creditApprovedYearNull();
    }

    ////////19-1 разделители у номера карты/////
    @Test
    void shouldTransferPayApprovedNumberCardNSeparator() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardSeparatorCardApproved = DataHelper.getNumberCardSeparator();
        var payValuesCardApproved = DataHelper.getInfoValid();
        paymentPage.transferPayApprovedNumberCardSeparator(payNumberCardSeparatorCardApproved, payValuesCardApproved);
        paymentPage.notificationPay();
    }


    ////////19-2 разделители у номера карты/////
    @Test
    void shouldTransferCreditApprovedNumberCardNSeparator() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardSeparatorCardApproved = DataHelper.getNumberCardSeparator();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        creditPage.transferCreditApprovedNumberCardSeparator(creditNumberCardSeparatorCardApproved, creditValuesCardApproved);
        creditPage.notificationCredit();
    }


    ////////20-1 месяц не по маске/////
    @Test
    void shouldTransferPayApprovedMonthNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        var payMonthNotMaskCardApproved = DataHelper.getMonthNotMask();
        paymentPage.transferPayApprovedMonthNotMask(payNumberCardApproved, payValuesCardApproved, payMonthNotMaskCardApproved);
        paymentPage.payApprovedMonthNotMask();
    }

    ////////20-1 месяц не по маске/////
    @Test
    void shouldTransferCreditApprovedMonthNotMask() {
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var creditPage = mainPage.transferCredit();
        var creditNumberCardApproved = DataHelper.getInfoCardApproved();
        var creditValuesCardApproved = DataHelper.getInfoValid();
        var creditMonthNotMaskCardApproved = DataHelper.getMonthNotMask();
        creditPage.transferCreditApprovedMonthNotMask(creditNumberCardApproved, creditValuesCardApproved, creditMonthNotMaskCardApproved);
        creditPage.creditApprovedMonthNotMask();
    }

*/
}
