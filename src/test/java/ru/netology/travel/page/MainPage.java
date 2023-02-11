package ru.netology.travel.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.visible;


import static com.codeborne.selenide.Selenide.*;

public class MainPage {
    private SelenideElement buttonPay = $x("//*[text()='Купить']");
    private SelenideElement buttonCredit = $x("//*[text()='Купить в кредит']");

    public MainPage() {
        buttonPay.shouldBe(visible);
        buttonCredit.shouldBe(visible);
    }

    public PaymentPage transferPay() {
        buttonPay.click();
        return new PaymentPage();
    }

    public CreditPage transferCredit() {
        buttonCredit.click();
        return new CreditPage();
    }


}
