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
    private SelenideElement buttonContinuePay = $("fieldset button");

    private SelenideElement notificationTitle = $(".notification_status_ok");
    private SelenideElement notificationTitleError = $(".notification_status_error");

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
        buttonContinuePay.shouldBe(visible);
    }

    public void creditWriteInForm(DataHelper.InfoCard infoCard) {
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
                "Операция одобрена Банком."), Duration.ofSeconds(30)).shouldBe(visible);
    }

    public void notificationErrorPay() {
        notificationTitle.shouldHave(text("Ошибка\n" +
                "Ошибка! Банк отказал в проведении операции."), Duration.ofSeconds(30)).shouldBe(visible);
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
}



