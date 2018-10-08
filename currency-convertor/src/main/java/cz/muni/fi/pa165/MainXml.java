package cz.muni.fi.pa165;


import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXml {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        CurrencyConvertor currencyConvertor = (CurrencyConvertor)context.getBean("CurrencyConvertor");
        BigDecimal returnedValue = currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.ONE);
        if (!returnedValue.equals(new BigDecimal("27.00"))){
            throw new IllegalStateException("Wrongly returned converted value");
        }
    }


}
