package ru.netology.travel.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.netology.travel.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    private SelenideElement numberCard = $x("//input[@placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $x("//input[@placeholder='08']");
    private SelenideElement name = $x("//*[text()='Владелец']/following-sibling::span/input");
    private SelenideElement year = $x("//*[text()='Год']/following-sibling::span/input");
    private SelenideElement cvc = $x("//*[@maxlength='3']");
    private ElementsCollection buttonContinuePay = $$(By.className("button__text"));
    private ElementsCollection notificationTitle = $$(".notification__title");
    private ElementsCollection notificationContent = $$(".notification__content");

    private SelenideElement notificationCardExpiryDate = $x("//*[text()='Истёк срок действия карты']");
    private SelenideElement notificationObligatoryField = $x("//*[text()='Поле обязательно для заполнения']");
    private SelenideElement notificationInvalidCardExpirationDate = $x("//*[text()='Неверно указан срок действия карты']");
    private SelenideElement notificationInvalidFormat = $x("//*[text()='Неверный формат']");

    public CreditPage() {
        numberCard.shouldBe(visible);
        month.shouldBe(visible);
        name.shouldBe(visible);
        year.shouldBe(visible);
        cvc.shouldBe(visible);
        buttonContinuePay.get(2).shouldBe(visible);
    }

    ///Запись в форму///
    public CreditPage creditWriteInForm(DataHelper.InfoCard infoCard) {
        numberCard.setValue(infoCard.getNumberCard());
        month.setValue(infoCard.getMonth());
        name.setValue(infoCard.getName());
        year.setValue(infoCard.getYear());
        cvc.setValue(infoCard.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(10));
        return new CreditPage();
    }


    public CreditPage notificationCredit() {
        notificationTitle.get(0).shouldHave(exactText("Успешно"));
        notificationContent.get(0).shouldHave(exactText("Операция одобрена Банком."));
        return new CreditPage();
    }

    public CreditPage notificationCardExpiryDate() {
        notificationCardExpiryDate.should(appear);
        return new CreditPage();
    }

    public CreditPage notificationObligatoryField() {
        notificationObligatoryField.should(appear);
        return new CreditPage();
    }

    public CreditPage notificationInvalidCardExpirationDate() {
        notificationInvalidCardExpirationDate.should(appear);
        return new CreditPage();
    }

    public CreditPage notificationInvalidFormat() {
        notificationInvalidFormat.should(appear);
        return new CreditPage();
    }



/*
    ///////////оплата по карте Approved///////////
    public CreditPage creditApproved(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        return new CreditPage();
    }

    ///////////оплата по карте Declined///////////
    public CreditPage creditDeclined(DataHelper.NumberCard getInfoCardDeclined, DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardDeclined.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        return new CreditPage();
    }

    ///////////пустая оплата кредит///////////
    public CreditPage transferCreditEmpty() {
        buttonContinuePay.get(2).click();
        return new CreditPage();
    }

    /////////После пустой оплаты кредит/////////
    public CreditPage creditEmpty() {
        return new CreditPage();
    }


    ////Оплата с истекшим сроком действия///////////
    public CreditPage transferCreditApprovedYearInvalid(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                        DataHelper.YearInvalid getYearInvalid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(getYearInvalid.toString());
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedYearInvalid() {
        invalidYear.should(appear);
        return new CreditPage();
    }

    ///////Невалидный владелец/////////
    public CreditPage transferCreditApprovedNameInvalid(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                        DataHelper.NameInvalid getNameInvalid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getNameInvalid.getNameInvalid());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedNameInvalid() {
        invalidName.should(appear);
        return new CreditPage();
    }


    ///////Невалидный месяц/////////
    public CreditPage transferCreditApprovedMonthInvalid(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                         DataHelper.MonthInvalid getMonthInvalid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(String.valueOf(getMonthInvalid.getMonthInvalid()));
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedMonthInvalid() {
        invalidMonth.should(appear);
        return new CreditPage();
    }

    ///////Номер карты не по маске/////////
    public CreditPage transferCreditApprovedNumberCardNotMask(DataHelper.NumberCardNotMask getNumberCardNotMask,
                                                              DataHelper.InfoCard getInfoValid) {
        number.setValue(getNumberCardNotMask.getNumberCardNotMask());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedNumberCardNotMask() {
        numberCardNotMask.should(appear);
        return new CreditPage();
    }

    ///////Cvc не по маске/////////
    public CreditPage transferCreditApprovedCvcNotMask(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                       DataHelper.CvcNotMask getCvcNotMask) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getCvcNotMask.getCvcNotMask());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedCvcNotMask() {
        cvcNotMask.should(appear);
        return new CreditPage();
    }

    ///////Пустой номер карты/////////
    public CreditPage transferCreditApprovedNumberCardEmpty(DataHelper.InfoCard getInfoValid) {
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedNumberCardEmpty() {
        numberCardEmpty.should(appear);
        return new CreditPage();
    }

    ///////Пустой месяц/////////
    public CreditPage transferCreditApprovedMonthEmpty(DataHelper.NumberCard getInfoCardApproved,
                                                       DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedMonthEmpty() {
        monthEmpty.should(appear);
        return new CreditPage();
    }

    ///////Пустой владелец/////////
    public CreditPage transferCreditApprovedNameEmpty(DataHelper.NumberCard getInfoCardApproved,
                                                      DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedNameEmpty() {
        nameEmpty.should(appear);
        return new CreditPage();
    }

    ///////Пустой год/////////
    public CreditPage transferCreditApprovedYearEmpty(DataHelper.NumberCard getInfoCardApproved,
                                                      DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        name.setValue(getInfoValid.getName());
        month.setValue(getInfoValid.getMonth());
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedYearEmpty() {
        yearEmpty.should(appear);
        return new CreditPage();
    }

    ///////Пустой cvc/////////
    public CreditPage transferCreditApprovedCvcEmpty(DataHelper.NumberCard getInfoCardApproved,
                                                     DataHelper.InfoCard getInfoValid) {
        number.setValue(getInfoCardApproved.getCard());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        month.setValue(getInfoValid.getMonth());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedCvcEmpty() {
        cvcEmpty.should(appear);
        return new CreditPage();
    }

    ///////Месяц = 0/////////
    public CreditPage transferCreditApprovedMonthNull(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                      DataHelper.MonthNull getMonthNull) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getMonthNull.getMonthNull());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedMonthNull() {
        monthNull.should(appear);
        return new CreditPage();
    }

    ///////Месяц ru/////////
    public CreditPage transferCreditApprovedMonthRu(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                    DataHelper.MonthRu getMonthRu) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getMonthRu.getMonthRu());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedMonthRu() {
        monthRu.should(appear);
        return new CreditPage();
    }

    ////////year = 00///////
    public CreditPage transferCreditApprovedYearNull(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                     DataHelper.YearNull getYearNull) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(getYearNull.toString());
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedYearNull() {
        yearNull.should(appear);
        return new CreditPage();
    }

    ////////номер карты с разделителем//////
    public CreditPage transferCreditApprovedNumberCardSeparator(DataHelper.NumberCardSeparator getNumberCardSeparator,
                                                                DataHelper.InfoCard getInfoValid) {
        number.setValue(getNumberCardSeparator.getNumberCardSeparator());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }


    ///////Месяц не по маске/////////
    public CreditPage transferCreditApprovedMonthNotMask(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid,
                                                         DataHelper.MonthNotMask getMonthNotMask) {
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getMonthNotMask.getMonthNotMask());
        name.setValue(getInfoValid.getName());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new CreditPage();
    }

    public CreditPage creditApprovedMonthNotMask() {
        monthNotMask.should(appear);
        return new CreditPage();
    }
*/

}
