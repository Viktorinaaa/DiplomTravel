package ru.netology.travel.page;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.travel.data.DataHelper;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    private SelenideElement number = $("[placeholder=\"0000 0000 0000 0000\"] input");
    private SelenideElement month = $("[placeholder=\"08\"] input");
    private SelenideElement name = $x("//input[text()='Владелец]");
    private ElementsCollection year = $$(".input__top input");
    private SelenideElement cvc = $("[maxlength=\"3\"] input");
    private ElementsCollection buttonContinuePay =$$("button__text");

    ///////////оплата по карте Approved///////////
    public CreditPage creditApproved(DataHelper.NumberCard getInfoCardApproved, DataHelper.InfoCard getInfoValid){
        number.setValue(getInfoCardApproved.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.get(getInfoValid.getYear());
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        return new CreditPage();
    }

    ///////////оплата по карте Declined///////////
    public CreditPage creditDeclined(DataHelper.NumberCard getInfoCardDeclined, DataHelper.InfoCard getInfoValid){
        number.setValue(getInfoCardDeclined.getCard());
        month.setValue(getInfoValid.getMonth());
        name.setValue(getInfoValid.getName());
        year.get(getInfoValid.getYear());
        cvc.setValue(getInfoValid.getCvc());
        buttonContinuePay.get(2).click();
        return new CreditPage();
    }

}
