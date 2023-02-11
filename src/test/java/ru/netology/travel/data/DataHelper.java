package ru.netology.travel.data;

import lombok.Value;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class InfoCard {
        private String numberCard;
        private String month;
        private String name;
        private String year;
        private String cvc;
    }

    public static String getNumberCardApproved() {
        return "4444 4444 4444 4441";
    }

    public static String getNumberCardDeclined() {
        return "4444 4444 4444 4442";
    }

    ///////ВАЛИДНЫЙ МЕСЯЦ/////////////
    public static String getMonthValid() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    //////////ВАЛИДНОЕ ИМЯ////////////////
    public static String getNameValid() {
        Faker fakerEn = new Faker(new Locale("en"));
        return fakerEn.name().fullName();
    }

    public static String getYearValid() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("YY"));
    }

    /////////ВАЛИДНЫЙ CVC//////////////////
    public static String getCvcValid() {
        Faker fakerInt = new Faker(new Locale("int"));
        return fakerInt.number().digits(3);
    }


    public static String getNumberCardEmpty() {
        return null;
    }

    public static String getMonthEmpty() {
        return null;
    }

    public static String getNameEmpty() {
        return null;
    }

    public static String getYearEmpty() {
        return null;
    }

    public static String getCvcEmpty() {
        return null;
    }


    public static String getYearInvalidMinusCurrent() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("YY"));
    }

    public static String getYearInvalidPlusCurrent() {
        return LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("YY"));
    }

    public static String getNameInvalid() {
        return RandomStringUtils.randomAlphabetic(20);
    }


    public static String getMonthInvalid() {
        Faker fakerInt = new Faker(new Locale("int"));
        int monthInvalid = fakerInt.number().numberBetween(13, 99);
        return String.valueOf(monthInvalid);
    }

    public static String getNumberCardNotMask() {
        Faker fakerInt = new Faker(new Locale("int"));
        return fakerInt.number().digits(12);
    }

    public static String getCvcNotMask() {
        Faker fakerInt = new Faker(new Locale("int"));
        return fakerInt.number().digits(2);
    }

    public static String getMonthNull() {
        int monthNullInit = 0;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return decimalFormat.format(monthNullInit);
    }

    public static String getMonthRu() {
        return RandomStringUtils.randomAlphabetic(8);
    }

    public static String getYearNull() {
        int yearNullInit = 0;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        return decimalFormat.format(yearNullInit);
    }

    public static String getNumberCardSeparator() {
        return "4444-4444-4444-4441";
    }

    public static String getMonthNotMask() {
        int monthNotMask = ThreadLocalRandom.current().nextInt(10);
        if (monthNotMask == 0) {
            monthNotMask = monthNotMask + 1;
        }
        return String.valueOf(monthNotMask);
    }

    /////валидные данные////
    public static InfoCard getValidInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthValid(), getNameValid(),
                getYearValid(), getCvcValid());
    }

    /////заблокированная карта////
    public static InfoCard getDeclinedInfoCard() {
        return new InfoCard(getNumberCardDeclined(), getMonthValid(), getNameValid(),
                getYearValid(), getCvcValid());
    }

    /////незаполненные поля////
    public static InfoCard getEmptyInfoCard() {
        return new InfoCard(getNumberCardEmpty(), getMonthEmpty(), getNameEmpty(),
                getYearEmpty(), getCvcEmpty());
    }

    ////год с истекшим сроком действия///
    public static InfoCard getYearMinusCurrentInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthValid(), getNameValid(),
                getYearInvalidMinusCurrent(), getCvcValid());
    }

    ////невалидный владелец///
    public static InfoCard getNameInvalidInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthValid(), getNameInvalid(),
                getYearValid(), getCvcValid());
    }

    ////невалидный месяц///
    public static InfoCard getMonthInvalidInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthInvalid(), getNameValid(),
                getYearValid(), getCvcValid());
    }

    ////номер карты не по маске//
    public static InfoCard getNumberCardNotMaskInfoCard() {
        return new InfoCard(getNumberCardNotMask(), getMonthValid(), getNameValid(),
                getYearValid(), getCvcValid());
    }

    ////cvc не по маске//
    public static InfoCard getCvcNotMaskInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthValid(), getNameValid(),
                getYearValid(), getCvcNotMask());
    }

    ////пустой номер карты//
    public static InfoCard getNumberCardEmptyInfoCard() {
        return new InfoCard(getNumberCardEmpty(), getMonthValid(), getNameValid(),
                getYearValid(), getCvcValid());
    }

    ////пустой месяц//
    public static InfoCard getMonthEmptyInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthEmpty(), getNameValid(),
                getYearValid(), getCvcValid());
    }

    ////пустой владелец//
    public static InfoCard getNameEmptyInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthValid(), getNameEmpty(),
                getYearValid(), getCvcValid());
    }

    ////пустой год//
    public static InfoCard getYearEmptyInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthValid(), getNameValid(),
                getYearEmpty(), getCvcValid());
    }

    ////пустой cvc//
    public static InfoCard getCvcEmptyInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthValid(), getNameValid(),
                getYearValid(), getCvcEmpty());
    }

    ////month = 00//
    public static InfoCard getMonthNullInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthNull(), getNameValid(),
                getYearValid(), getCvcValid());
    }

    ////month ru//
    public static InfoCard getMonthRuInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthRu(), getNameValid(),
                getYearValid(), getCvcValid());
    }

    ////year = 00//
    public static InfoCard getYearNullInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthValid(), getNameValid(),
                getYearNull(), getCvcValid());
    }


    ////номер карты с разделителем//
    public static InfoCard getNumberCardWithSeparatorInfoCard() {
        return new InfoCard(getNumberCardSeparator(), getMonthValid(), getNameValid(),
                getYearValid(), getCvcValid());
    }


    ////месяц не по маске//
    public static InfoCard getMonthNotMaskInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthNotMask(), getNameValid(),
                getYearValid(), getCvcValid());
    }

    ////ввод карты, где год > 5 лет///
    public static InfoCard getYearPlusCurrentInfoCard() {
        return new InfoCard(getNumberCardApproved(), getMonthValid(), getNameValid(),
                getYearInvalidPlusCurrent(), getCvcValid());
    }
}


