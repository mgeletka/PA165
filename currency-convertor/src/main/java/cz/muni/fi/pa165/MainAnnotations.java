package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.ExchangeRateTable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainAnnotations {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("cz.muni.fi.pa165");

        CurrencyConvertor currencyConvertor = context.getBean("CurrencyConvertorAnotation", CurrencyConvertor.class);
        BigDecimal returnedValue = currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.ONE);
        if (!returnedValue.equals(new BigDecimal("27.00"))){
            throw new IllegalStateException("Wrongly returned converted value");
        }
    }

}
