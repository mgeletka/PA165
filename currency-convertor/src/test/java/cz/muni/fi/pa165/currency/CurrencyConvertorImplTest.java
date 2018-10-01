package cz.muni.fi.pa165.currency;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.*;

public class CurrencyConvertorImplTest {

    private static BigDecimal EXCHANGE_RATE_STANDARD = new BigDecimal("25.5");
    private static BigDecimal AMOUNT_OF_MONEY_STANDARD = new BigDecimal("300");
    private static BigDecimal AMOUNT_OF_MONEY_NEGATIVE = new BigDecimal("300");

    private static Currency CURRENCY_US = Currency.getInstance(Locale.US);
    private static Currency CURRENCY_UK = Currency.getInstance(Locale.UK);
    private static Currency CURRENCY_CANADA = Currency.getInstance(Locale.CANADA);

    private ExchangeRateTable exchangeRateTable = Mockito.mock(ExchangeRateTable.class);
    private CurrencyConvertor currencyConvertor;

    @Before
    public void setUp() throws ExternalServiceFailureException {
        Mockito.when(exchangeRateTable.getExchangeRate(CURRENCY_US, CURRENCY_UK)).thenReturn(EXCHANGE_RATE_STANDARD);
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        BigDecimal expectedResult = EXCHANGE_RATE_STANDARD.multiply(AMOUNT_OF_MONEY_STANDARD);
        BigDecimal actualResult = currencyConvertor.convert(CURRENCY_US, CURRENCY_UK, AMOUNT_OF_MONEY_STANDARD);
        Assert.assertEquals(actualResult, expectedResult);

        actualResult = currencyConvertor.convert(CURRENCY_US, CURRENCY_UK, BigDecimal.ZERO);
        Assert.assertEquals(actualResult, BigDecimal.ZERO);

        expectedResult = EXCHANGE_RATE_STANDARD.multiply(AMOUNT_OF_MONEY_NEGATIVE);
        actualResult = currencyConvertor.convert(CURRENCY_US, CURRENCY_UK, AMOUNT_OF_MONEY_NEGATIVE);
        Assert.assertEquals(actualResult, expectedResult);

        BigDecimal longExchangeRate = new BigDecimal("3.14159");
        Mockito.when(exchangeRateTable.getExchangeRate(CURRENCY_US, CURRENCY_CANADA)).thenReturn(longExchangeRate);
        actualResult = currencyConvertor.convert(CURRENCY_US, CURRENCY_CANADA, AMOUNT_OF_MONEY_STANDARD);
        expectedResult = longExchangeRate.multiply(AMOUNT_OF_MONEY_STANDARD);
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(expected = IllegalAccessException.class)
    public void testConvertWithNullSourceCurrency()
    {
       currencyConvertor.convert(null, CURRENCY_UK, AMOUNT_OF_MONEY_STANDARD);
    }

    @Test(expected = IllegalAccessException.class)
    public void testConvertWithNullTargetCurrency() {
        currencyConvertor.convert(CURRENCY_US, null, AMOUNT_OF_MONEY_STANDARD);
    }

    @Test(expected = IllegalAccessException.class)
    public void testConvertWithNullSourceAmount() {
        currencyConvertor.convert(CURRENCY_US, CURRENCY_UK, null);
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithUnknownCurrency() {
        Currency unknownCurrency = Currency.getInstance(Locale.CHINA);
        currencyConvertor.convert(CURRENCY_US, unknownCurrency, AMOUNT_OF_MONEY_STANDARD);
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException{
        ExchangeRateTable badExchangeRateTable = Mockito.mock(ExchangeRateTable.class);
        Mockito.when(badExchangeRateTable.getExchangeRate(CURRENCY_CANADA, CURRENCY_UK)).thenThrow(ExternalServiceFailureException.class);
        currencyConvertor = new CurrencyConvertorImpl(badExchangeRateTable);
        currencyConvertor.convert(CURRENCY_US, CURRENCY_UK, AMOUNT_OF_MONEY_STANDARD);
    }

}
