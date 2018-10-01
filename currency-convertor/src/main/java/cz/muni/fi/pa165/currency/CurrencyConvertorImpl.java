package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    //private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    private void validateArgumentsForConvert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) throws IllegalArgumentException{
        if(sourceCurrency == null){
            throw new IllegalArgumentException("Source currency cannot be null");
        }

        if(targetCurrency == null){
            throw new IllegalArgumentException("Target currency cannot be null");
        }

        if(sourceAmount == null){
            throw new IllegalArgumentException("Source amount cannot be null");
        }
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) throws  IllegalArgumentException{
        BigDecimal result;
        try {
            validateArgumentsForConvert(sourceCurrency, targetCurrency, sourceAmount);
            result = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency).multiply(sourceAmount).setScale(2, RoundingMode.HALF_EVEN);
        } catch (ExternalServiceFailureException ex){
            throw new IllegalArgumentException("External error occuried");
        }
        return result;
    }

}
