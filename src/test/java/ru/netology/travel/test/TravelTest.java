package ru.netology.travel.test;

import com.codeborne.selenide.Configuration;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.jupiter.api.BeforeEach;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.netology.travel.data.DataHelper;
import ru.netology.travel.data.DataHelperSQL;
import ru.netology.travel.data.TablesSQL.OrderEntity;
import ru.netology.travel.page.MainPage;

import java.sql.DriverManager;
import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TravelTest {

/*
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
        paymentPage.payApproved(payNumberCardApproved,payValuesCardApproved);
        paymentPage.notificationPay();
    }
*/
    @BeforeEach
    @SneakyThrows
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");
    }

    /////валидная оплата по карте Approved/////////
    @Test
    //@SneakyThrows
    void shouldValidTransferPayApproved(){
               //var conn = DataHelperSQL.getOrderEntity();
        var mainPage = open("http://localhost:8080/", MainPage.class);
        var paymentPage = mainPage.transferPay();
        var payNumberCardApproved = DataHelper.getInfoCardApproved();
        var payValuesCardApproved = DataHelper.getInfoValid();
        paymentPage.payApproved(payNumberCardApproved,payValuesCardApproved);
        paymentPage.notificationPay();

    }

}
