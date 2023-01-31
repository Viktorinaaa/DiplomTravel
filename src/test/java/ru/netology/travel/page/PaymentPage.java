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
    private SelenideElement number = $x("//input[@placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $x("//input[@placeholder='08']");
    private SelenideElement name = $x("//*[text()='Владелец']/following-sibling::span/input");
    private SelenideElement year = $x("//*[text()='Год']/following-sibling::span/input");
    private SelenideElement cvc = $x("//*[@maxlength='3']");

    //private ElementsCollection buttonContinuePay =$$("button__text");
    private ElementsCollection buttonContinuePay = $$(By.className("button__text"));

    private ElementsCollection notificationTitle = $$(".notification__title");
    private ElementsCollection notificationContent = $$(".notification__content");

    private SelenideElement heading = $x("//h3[text()='Оплата по карте']");


    //////видимость объектов/////////////
    public PaymentPage(){
        number.shouldBe(visible);
        month.shouldBe(visible);
        name.shouldBe(visible);
        year.shouldBe(visible);
        cvc.shouldBe(visible);
        buttonContinuePay.get(2).shouldBe(visible);
        heading.shouldBe(visible);
    }

    public PaymentPage notificationPay(){
        notificationTitle.get(0).shouldHave(exactText("Успешно"));
        notificationContent.get(0).shouldHave(exactText("Операция одобрена Банком."));
        return new PaymentPage();
    }

    ///////////оплата по карте Approved///////////
    public PaymentPage payApproved(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid){
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        //year.get(getInfoValid.getYear());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        buttonContinuePay.get(2).should(appear, Duration.ofSeconds(50));
        return new PaymentPage();
    }

    ///////////оплата по карте Declined///////////
    public PaymentPage payDeclined(DataHelper.NumberCard getInfoCardDeclined, DataHelper.InfoCard getInfoValid){
        number.setValue(getInfoCardDeclined.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        //year.get(getInfoValid.getYear());
        year.setValue(String.valueOf(getInfoValid.getYear()));
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        return new PaymentPage();
    }

}
