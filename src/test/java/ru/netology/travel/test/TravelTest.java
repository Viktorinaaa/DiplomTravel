package ru.netology.travel.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.travel.data.DataHelper;
import ru.netology.travel.page.MainPage;
import ru.netology.travel.page.PaymentPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TravelTest {



    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");
    }

    /////валидная оплата по карте Approved/////////
    @Test
    void shouldValidTransferPayApproved(){
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        var payInfoApproved = paymentPage.payApproved(payNumberCardApproved,payValuesCardApproved);
        var payNotification = paymentPage.notificationPay();
    }



}
