package ru.netology.travel.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.netology.travel.data.DataHelper;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {
    private SelenideElement heading = $x("//h3[text()='Оплата по карте']");
    private SelenideElement numberCard = $x("//input[@placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $x("//input[@placeholder='08']");
    private SelenideElement name = $x("//*[text()='Владелец']/following-sibling::span/input");
    private SelenideElement year = $x("//*[text()='Год']/following-sibling::span/input");
    private SelenideElement cvc = $x("//*[@maxlength='3']");
    private SelenideElement buttonContinuePay = $("fieldset button");

    private SelenideElement notificationTitle = $(".notification_status_ok");
    private SelenideElement notificationTitleError = $(".notification_status_error");


    private SelenideElement notificationCardExpiryDate = $x("//*[text()='Истёк срок действия карты']");
    private SelenideElement notificationObligatoryField = $x("//*[text()='Поле обязательно для заполнения']");
    private SelenideElement notificationInvalidCardExpirationDate = $x("//*[text()='Неверно указан срок действия карты']");
    private SelenideElement notificationInvalidFormat = $x("//*[text()='Неверный формат']");


    //////видимость объектов/////////////
    public PaymentPage() {
        heading.shouldBe(visible);
        numberCard.shouldBe(visible);
        month.shouldBe(visible);
        name.shouldBe(visible);
        year.shouldBe(visible);
        cvc.shouldBe(visible);
        buttonContinuePay.shouldBe(visible);
    }

    ///Запись в форму///
    public void payWriteInForm(DataHelper.InfoCard infoCard) {
        numberCard.setValue(infoCard.getNumberCard());
        month.setValue(infoCard.getMonth());
        name.setValue(infoCard.getName());
        year.setValue(infoCard.getYear());
        cvc.setValue(infoCard.getCvc());
        buttonContinuePay.click();
        buttonContinuePay.should(appear, Duration.ofSeconds(10));
    }


    public void notificationSuccessfulPay() {
        notificationTitle.shouldHave(text("Успешно\n" +
                "Операция одобрена Банком."), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void notificationErrorPay() {
        notificationTitle.shouldHave(text("Ошибка\n" +
                "Ошибка! Банк отказал в проведении операции."), Duration.ofSeconds(15)).shouldBe(visible);
    }


    public void notificationCardExpiryDate() {
        notificationCardExpiryDate.should(appear);
    }

    public void notificationObligatoryField() {
        notificationObligatoryField.should(appear);
    }

    public void notificationInvalidCardExpirationDate() {
        notificationInvalidCardExpirationDate.should(appear);
    }

    public void notificationInvalidFormat() {
        notificationInvalidFormat.should(appear);
    }









/*

    ///////////оплата по карте Approved///////////
    public PaymentPage payApproved(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60)); //ofSeconds(100000));
        return new PaymentPage();
    }


    ///////////оплата по карте Declined///////////
    public PaymentPage payDeclined(DataHelper.NumberCard getInfoCardDeclined, DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardDeclined.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }



    ///////////пустая оплата по карте///////////
    public PaymentPage transferPayEmpty() {
        buttonContinuePay.get(2).click();
        return new PaymentPage();
    }

    public PaymentPage payEmpty() {
        return new PaymentPage();
    }


    ////Оплата с истекшим сроком действия///////////
   public PaymentPage transferPayApprovedYearInvalid(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                      DataHelper.YearInvalid getYearInvalid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(getYearInvalid.toString());
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }




    public PaymentPage payApprovedYearInvalid() {
        invalidYear.should(appear);
        return new PaymentPage();
    }

    ///////Невалидный владелец/////////
    public PaymentPage transferPayApprovedNameInvalid(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                      DataHelper.NameInvalid getNameInvalid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getNameInvalid.getNameInvalid());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }


    public PaymentPage payApprovedNameInvalid() {
        invalidName.should(appear);
        return new PaymentPage();
    }

    ///////Невалидный месяц/////////
    public PaymentPage transferPayApprovedMonthInvalid(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                       DataHelper.MonthInvalid getMonthInvalid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(String.valueOf(getMonthInvalid.getMonthInvalid()));
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }



    public PaymentPage payApprovedMonthInvalid() {
        invalidMonth.should(appear);
        return new PaymentPage();
    }

    ///////Номер карты не по маске/////////
    public PaymentPage transferPayApprovedNumberCardNotMask(DataHelper.NumberCardNotMask getNumberCardNotMask,
                                                            DataHelper.InfoCard getInfoValid) {
        number.setValue(getNumberCardNotMask.getNumberCardNotMask());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }



    public PaymentPage payApprovedNumberCardNotMask() {
        numberCardNotMask.should(appear);
        return new PaymentPage();
    }

    ///////Cvc не по маске/////////
    public PaymentPage transferPayApprovedCvcNotMask(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                     DataHelper.CvcNotMask getCvcNotMask) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getCvcNotMask.getCvcNotMask());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }



    public PaymentPage payApprovedCvcNotMask() {
        cvcNotMask.should(appear);
        return new PaymentPage();
    }
/*
    ///////Пустой номер карты/////////
    public PaymentPage transferPayApprovedNumberCardEmpty(DataHelper.InfoCard getInfoValid) {
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }

    public PaymentPage payApprovedNumberCardEmpty() {
        numberCardEmpty.should(appear);
        return new PaymentPage();
    }

    ///////Пустой месяц/////////
    public PaymentPage transferPayApprovedMonthEmpty(DataHelper.NumberCard getInfoCardApproved,
                                                     DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }

    public PaymentPage payApprovedMonthEmpty() {
        monthEmpty.should(appear);
        return new PaymentPage();
    }


    ///////Пустой владелец/////////
    public PaymentPage transferPayApprovedNameEmpty(DataHelper.NumberCard getInfoCardApproved,
                                                    DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }

    public PaymentPage payApprovedNameEmpty() {
        nameEmpty.should(appear);
        return new PaymentPage();
    }

    ///////Пустой год/////////
    public PaymentPage transferPayApprovedYearEmpty(DataHelper.NumberCard getInfoCardApproved,
                                                    DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        name.setValue(getInfoValid.getName());
        month.setValue(getInfoValid.getMonth());
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }

    public PaymentPage payApprovedYearEmpty() {
        yearEmpty.should(appear);
        return new PaymentPage();
    }

    ///////Пустой cvc/////////
    public PaymentPage transferPayApprovedCvcEmpty(DataHelper.NumberCard getInfoCardApproved,
                                                   DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        month.setValue(getInfoValid.getMonth());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }

    public PaymentPage payApprovedCvcEmpty() {
        cvcEmpty.should(appear);
        return new PaymentPage();
    }

    ///////Месяц = 0/////////
    public PaymentPage transferPayApprovedMonthNull(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                    DataHelper.MonthNull getMonthNull) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getMonthNull.getMonthNull());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }
/*
    public PaymentPage transferPayApprovedMonthNull(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonthNull());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }
*/
/*    public PaymentPage payApprovedMonthNull() {
        monthNull.should(appear);
        return new PaymentPage();
    }


    ///////Месяц ru/////////
    public PaymentPage transferPayApprovedMonthRu(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                  DataHelper.MonthRu getMonthRu) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getMonthRu.getMonthRu());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }

    /*
    public PaymentPage transferPayApprovedMonthRu(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getMonthRu.getMonthRu());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }


    public PaymentPage payApprovedMonthRu() {
        monthRu.should(appear);
        return new PaymentPage();
    }

    ////////year = 00///////
    public PaymentPage transferPayApprovedYearNull(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                   DataHelper.YearNull getYearNull) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(getYearNull.toString());
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }

    /*
    public PaymentPage transferPayApprovedYearNull(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(getInfoValid.getYearNull.toString());
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }
     */
/*
    public PaymentPage payApprovedYearNull() {
        yearNull.should(appear);
        return new PaymentPage();
    }

    ////////номер карты с разделителем//////
    public PaymentPage transferPayApprovedNumberCardSeparator(DataHelper.NumberCardSeparator getNumberCardSeparator,
                                                              DataHelper.InfoCard getInfoValid) {
        number.setValue(getNumberCardSeparator.getNumberCardSeparator());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }

    /*
        public PaymentPage transferPayApprovedNumberCardSeparator(DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoValid.getNumberCardSeparator());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }


    ///////Месяц не по маске/////////
    public PaymentPage transferPayApprovedMonthNotMask(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                       DataHelper.MonthNotMask getMonthNotMask) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getMonthNotMask.getMonthNotMask());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }

    /*
     public PaymentPage transferPayApprovedMonthNotMask(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonthNotMask());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofMinutes(60));
        return new PaymentPage();
    }



    public PaymentPage payApprovedMonthNotMask() {
        monthNotMask.should(appear);
        return new PaymentPage();
    }
*/
}