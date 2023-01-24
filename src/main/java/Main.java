import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import com.github.javafaker.*;


public class Main {
    //public int generateMonth()
    public static void main(String[] args) {

        //String min = LocalDate.now().plusYears(5).format(DateTimeFormatter.ofPattern("YY"));
        Faker fakerInt = new Faker(new Locale("int"));
        Code cvc = fakerInt.code();

        System.out.println("Год " + 1);

        //int max = 28;
        //Random random = new Random();
        //int year = random.nextInt(max - min + 1) + min;

        //System.out.println("Год " + year);
    }

}
