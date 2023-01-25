package ru.netology.travel.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.travel.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {
    private SelenideElement number = $("[placeholder=\"0000 0000 0000 0000\"] input");
    private SelenideElement month = $("[placeholder=\"08\"] input");
    private SelenideElement name = $x("//input[text()='Владелец]");
    private ElementsCollection year = $$(".input__top input");
    private SelenideElement cvc = $("[maxlength=\"3\"] input");
    private ElementsCollection buttonContinuePay =$$("button__text");

    private ElementsCollection notificationTitle = $$(".notification__title");
    private ElementsCollection notificationContent = $$(".notification__content");


    //////видимость объектов/////////////
    public PaymentPage(){
        number.shouldBe(visible);
        month.shouldBe(visible);
        name.shouldBe(visible);
        year.get(2).shouldBe(visible);
        cvc.shouldBe(visible);
        buttonContinuePay.get(2).shouldBe(visible);
    }
/*
    public NotificationPay(){
        notificationTitle.shouldHave(text("Успешно"));//text("Успешно"));
                //(exactText("Успешно"));
    }*/

    ///////////оплата по карте Approved///////////
    public PaymentPage payApproved(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid){
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.get(getInfoValid.getYear());
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        return new PaymentPage();
    }

    ///////////оплата по карте Declined///////////
    public PaymentPage payDeclined(DataHelper.NumberCard getInfoCardDeclined, DataHelper.InfoCard getInfoValid){
        number.setValue(getInfoCardDeclined.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.get(getInfoValid.getYear());
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        return new PaymentPage();
    }


}
