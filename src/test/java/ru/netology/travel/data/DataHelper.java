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

    public static InfoCard getNumberCardEmpty() {
        return new InfoCard(null, getMonthValid().getMonth(), getNameValid().getName(), getYearValid().getYear(), getCvcValid().getCvc());
    }
    public static InfoCard getMonthEmpty() {
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), null, getNameValid().getName(), getYearValid().getYear(), getCvcValid().getCvc());
    }
    public static InfoCard getNameEmpty() {
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), getMonthValid().getMonth(), null, getYearValid().getYear(), getCvcValid().getCvc());
    }
    public static InfoCard getYearEmpty() {
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), getMonthValid().getMonth(), getNameValid().getName(), null, getCvcValid().getCvc());
    }

    public static InfoCard getCvcEmpty() {
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), getMonthValid().getMonth(), getNameValid().getName(), getYearValid().getYear(), null);
    }

    public static InfoCard getEmpty() {
        return new InfoCard(null, null, null, null, null);
    }


    public static InfoCard getInfoNumberCardApproved() {
    return new InfoCard("4444 4444 4444 4441", getMonthValid().getMonth(), getNameValid().getName(), getYearValid().getYear(), getCvcValid().getCvc());
    }

    public static InfoCard getInfoNumberCardDeclined() {
        return new InfoCard("4444 4444 4444 4442", getMonthValid().getMonth(), getNameValid().getName(), getYearValid().getYear(), getCvcValid().getCvc());
    }

    ///////ВАЛИДНЫЙ МЕСЯЦ/////////////
    public static InfoCard getMonthValid() {
        String month = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), month, getNameValid().getName(), getYearValid().getYear(), getCvcValid().getCvc());
    }

    //////////ВАЛИДНОЕ ИМЯ////////////////
    public static InfoCard getNameValid() {
        Faker fakerEn = new Faker(new Locale("en"));
        String name = fakerEn.name().fullName();
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), getMonthValid().getMonth(), name, getYearValid().getYear(), getCvcValid().getCvc());
    }

    public static InfoCard getYearValid() {
        String year = LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("YY"));
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), getMonthValid().getMonth(), getNameValid().getName(), year, getCvcValid().getCvc());
    }

    /////////ВАЛИДНЫЙ CVC//////////////////
    public static InfoCard getCvcValid() {
        Faker fakerInt = new Faker(new Locale("int"));
        String cvc = fakerInt.number().digits(3);
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), getMonthValid().getMonth(), getNameValid().getName(), getYearValid().getYear(), cvc);
    }

    public static InfoCard getYearInvalidMinusCurrent() {
        String yearInvalidMinusCurrent = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("YY"));
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), getMonthValid().getMonth(), getNameValid().getName(), yearInvalidMinusCurrent, getCvcValid().getCvc());
    }

    public static InfoCard getYearInvalidPlusCurrent() {
        String yearInvalidPlusCurrent = LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("YY"));
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), getMonthValid().getMonth(), getNameValid().getName(), yearInvalidPlusCurrent, getCvcValid().getCvc());
    }

    public static InfoCard getNameInvalid() {
        String nameInvalid = RandomStringUtils.randomAlphabetic(20);
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), getMonthValid().getMonth(), nameInvalid, getYearValid().getYear(), getCvcValid().getCvc());
    }

    public static InfoCard getMonthInvalid() {
        Faker fakerInt = new Faker(new Locale("int"));
        int monthInvalid = fakerInt.number().numberBetween(13, 99);
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), String.valueOf(monthInvalid), getNameValid().getName(), getYearValid().getYear(), getCvcValid().getCvc());
    }

    public static InfoCard getNumberCardNotMask() {
        Faker fakerInt = new Faker(new Locale("int"));
        String numberCardNotMask = fakerInt.number().digits(12);
        return new InfoCard(numberCardNotMask, getMonthValid().getMonth(), getNameValid().getName(), getYearValid().getYear(), getCvcValid().getCvc());
    }

    public static InfoCard getCvcNotMask() {
        Faker fakerInt = new Faker(new Locale("int"));
        String cvcNotMask = fakerInt.number().digits(2);
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), getMonthValid().getMonth(), getNameValid().getName(), getYearValid().getYear(), cvcNotMask);
    }

    public static InfoCard getMonthNull() {
        int monthNullInit = 0;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String monthNull = decimalFormat.format(monthNullInit);
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), monthNull, getNameValid().getName(), getYearValid().getYear(), getCvcValid().getCvc());
    }

    public static InfoCard getMonthRu() {
        String monthRu = RandomStringUtils.randomAlphabetic(8);
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), monthRu, getNameValid().getName(), getYearValid().getYear(), getCvcValid().getCvc());
    }

    public static InfoCard getYearNull() {
        int yearNullInit = 0;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String yearNull = decimalFormat.format(yearNullInit);
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), getMonthValid().getMonth(), getNameValid().getName(), yearNull, getCvcValid().getCvc());
    }

    public static InfoCard getNumberCardSeparator() {
        String numberCardSeparator = "4444-4444-4444-4441";
        return new InfoCard(numberCardSeparator, getMonthValid().getMonth(), getNameValid().getName(), getYearValid().getYear(), getCvcValid().getCvc());
    }

    public static InfoCard getMonthNotMask() {
        int monthNotMask = ThreadLocalRandom.current().nextInt(10);
        if (monthNotMask == 0) {
            monthNotMask = monthNotMask + 1;
        }
        return new InfoCard(getInfoNumberCardApproved().getNumberCard(), Integer.toString(monthNotMask), getNameValid().getName(), getYearValid().getYear(), getCvcValid().getCvc());
    }

}


/*
    public static InfoCard getInfoValid() {
        Faker fakerInt = new Faker(new Locale("int"));
        Faker fakerEn = new Faker(new Locale("en"));
        Faker fakerRu = new Faker(new Locale("ru"));

        ///////ВАЛИДНЫЙ МЕСЯЦ/////////////
        int month = ThreadLocalRandom.current().nextInt(13);
        if (month == 0) {
            month = month + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String monthS = decimalFormat.format(month);


        //////////ВАЛИДНОЕ ИМЯ////////////////
        String name = fakerEn.name().fullName();

        /////ВАЛИДНЫЙ ГОД (от 23 до 28 вкл)//////
        String minS = LocalDate.now().format(DateTimeFormatter.ofPattern("YY"));
        int min = Integer.parseInt(minS);
        String maхS = LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("YY"));
        int maх = Integer.parseInt(maхS);
        Random random = new Random();
        int year = random.nextInt(maх - min + 1) + min;

        /////////ВАЛИДНЫЙ CVC//////////////////
        String cvc = fakerInt.number().digits(3);
        return new InfoCard(getInfoNumberCardApproved(), monthS, name, year, cvc);
    }
*/












///////////////////////////////////////
///////////////////////////////////////
/*
        //НЕВАЛИДНЫЙ ГОД (2022)
        String yearInvalidS = LocalDate.now().format(DateTimeFormatter.ofPattern("YY"));
        int yearInvalid = Integer.parseInt(String.valueOf(yearInvalidS)) - 1;

        //НЕВАЛИДНОЕ ИМЯ (VGHVgvghVGVHJbghv)
        String nameInvalid = RandomStringUtils.randomAlphabetic(20);

        //НЕВАЛИДНЫЙ МЕСЯЦ (>12)
        //Faker fakerInt = new Faker(new Locale("int"));
        int monthInvalid = fakerInt.number().numberBetween(13, 99);

        //НОМЕР КАРТЫ НЕ ПО МАСКЕ (НЕПОЛНЫЙ ВВОД)
        //Faker fakerInt = new Faker(new Locale("int"));
        String numberCardNotMask = fakerInt.number().digits(12);

        //CVC НЕ ПО МАСКЕ (NN)
        //Faker fakerInt = new Faker(new Locale("int"));
        String cvcNotMask = fakerInt.number().digits(2);

        //МЕСЯЦ = 0 (00)
        int monthNullInit = 0;
        //DecimalFormat decimalFormat = new DecimalFormat("00");
        String monthNull = decimalFormat.format(monthNullInit);

        //МЕСЯЦ ru (январь)
        String monthRu = RandomStringUtils.randomAlphabetic(8);

        //ГОД = 0
        int yearNullInit = 0;
        //DecimalFormat decimalFormat = new DecimalFormat("00");
        String yearNull = decimalFormat.format(yearNullInit);

        //НОМЕР КАРТЫ С РАЗДЕЛИТЕЛЕМ
        String numberCardSeparator = "4444-4444-4444-4441";


        //МЕСЯЦ НЕ ПО МАСКЕ
        int monthNotMask = ThreadLocalRandom.current().nextInt(10);
        if (monthNotMask == 0) {
            monthNotMask = monthNotMask + 1;
        }

        return new InfoCard(monthS, name, year, cvc, yearInvalid, nameInvalid, monthInvalid, numberCardNotMask,
                cvcNotMask, monthNull, monthRu, yearNull, numberCardSeparator, monthNotMask);
*/

//}


/*

    //////////Истек срок действия карты////////////////
    @Value
    public static class YearInvalid {
        private int yearInvalid;
    }

    public static YearInvalid getYearInvalid() {
        String year = LocalDate.now().format(DateTimeFormatter.ofPattern("YY"));
        int yearInvalid = Integer.parseInt(String.valueOf(year)) - 1;
        return new YearInvalid(yearInvalid);
    }

    ///////Невалидный владелец/////////
    @Value
    public static class NameInvalid {
        private String nameInvalid;
    }

    public static NameInvalid getNameInvalid() {
        //////////НЕВАЛИДНОЕ ИМЯ//
        String nameInvalid = RandomStringUtils.randomAlphabetic(20);
        return new NameInvalid(nameInvalid);
    }

    //////////Истек срок действия карты////////////////
    @Value
    public static class MonthInvalid {
        private int monthInvalid;
    }

    public static MonthInvalid getMonthInvalid() {
        Faker fakerInt = new Faker(new Locale("int"));
        int monthInvalid = fakerInt.number().numberBetween(13, 99);
        return new MonthInvalid(monthInvalid);
    }

    //////////Номер карты не по маске////////////////
    @Value
    public static class NumberCardNotMask {
        private String numberCardNotMask;
    }

    public static NumberCardNotMask getNumberCardNotMask() {
        Faker fakerInt = new Faker(new Locale("int"));
        String numberCardNotMask = fakerInt.number().digits(12);
        return new NumberCardNotMask(numberCardNotMask);
    }

    //////////CVC не по маске////////////////
    @Value
    public static class CvcNotMask {
        private String cvcNotMask;
    }

    public static CvcNotMask getCvcNotMask() {
        Faker fakerInt = new Faker(new Locale("int"));
        String cvcNotMask = fakerInt.number().digits(2);
        return new CvcNotMask(cvcNotMask);
    }


    //////////месяц = 0////////////////
    @Value
    public static class MonthNull {
        private String monthNull;
    }

    public static MonthNull getMonthNull() {
        int monthNullInit = 0;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String monthNull = decimalFormat.format(monthNullInit);
        return new MonthNull(monthNull);
    }

    //////////месяц ru////////////////
    @Value
    public static class MonthRu {
        private String monthRu;
    }

    public static MonthRu getMonthRu() {
        String monthRu = RandomStringUtils.randomAlphabetic(8);
        return new MonthRu(monthRu);
    }

    //////////год = 0////////////////
    @Value
    public static class YearNull {
        private String yearNull;
    }

    public static YearNull getYearNull() {
        int yearNullInit = 0;
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String yearNull = decimalFormat.format(yearNullInit);
        return new YearNull(yearNull);
    }


    //////////Номер карты с разделителями////////////////
    @Value
    public static class NumberCardSeparator {
        private String numberCardSeparator;
    }

    public static NumberCardSeparator getNumberCardSeparator() {
        String numberCardSeparator = "4444-4444-4444-4441";
        return new NumberCardSeparator(numberCardSeparator);
    }

    //////////месяц не по маске////////////////
    @Value
    public static class MonthNotMask {
        private String monthNotMask;
    }

    public static MonthNotMask getMonthNotMask() {
        int monthNotMask = ThreadLocalRandom.current().nextInt(10);
        if (monthNotMask == 0) {
            monthNotMask = monthNotMask + 1;
        }
        return new MonthNotMask(Integer.toString(monthNotMask));
    }
*/

