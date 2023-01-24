package ru.netology.travel.data;
import lombok.Value;
import com.github.javafaker.Faker;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DataHelper {
    private DataHelper() {}

    @Value
    public static class NumberCard
    {
        private String card;
        private String status;
    }
    public static NumberCard getInfoCardApproved(){
        return new NumberCard("4444 4444 4444 4441","APPROVED");
    }
    public static NumberCard getInfoCardDeclined(){
        return new NumberCard("4444 4444 4444 4442", "DECLINED");
    }

    @Value
    public static class InfoCard
    {
        private String month;
        private String name;
        private int year;
        private String cvc;
    }
    public static InfoCard getMonth() {
        Faker fakerInt = new Faker(new Locale("int"));
        Faker fakerEn = new Faker(new Locale("en"));
        Faker fakerRu = new Faker(new Locale("ru"));

        int month = ThreadLocalRandom.current().nextInt(13);
        if (month == 0) {
            month = month + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String monthS = decimalFormat.format(month);
        //////////////////////////
        String name = fakerEn.name().fullName();
        /////от 23 до 28 вкл//////
        String minS = LocalDate.now().format(DateTimeFormatter.ofPattern("YY"));
        int min = Integer.parseInt(minS);
        String maхS = LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("YY"));
        int maх = Integer.parseInt(maхS);
        Random random = new Random();
        int year = random.nextInt(maх - min + 1) + min;
        ///////////////////////////
        String cvc = fakerInt.number().digits(3);

        return new InfoCard(monthS,name,year,cvc);
    }

}
