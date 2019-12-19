package com.nathan.roifmr.market;

import com.nathan.roifmr.domain.exchange.GeneratedQuote;
import com.nathan.roifmr.domain.exchange.HistoricalQuotes;
import com.nathan.roifmr.domain.exchange.MarketSymbol;
import com.nathan.roifmr.domain.exchange.QuoteRequestDateValidationException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

public class SimpleQuoteAlgorithm extends AbstractQuoteGenerator {

    private HistoricalQuotes historicalQuotes;

    /*
    algorithm for generation  ‘ sin(x) + 2*sin(x/2) + cos(x)  {high, low , open, close, vol}
    build out Historical quotes
    populate with duration quantity
     */

    @Override
    public Optional<HistoricalQuotes> generateDailyQuotes(LocalDate originDate, int durationInDays, MarketSymbol exchSymbol) throws QuoteRequestDateValidationException {
        if (Objects.nonNull(originDate)&&Objects.nonNull(exchSymbol)){
            if (validateQuoteDates(originDate,durationInDays)){
                setHistoricalQuotes(new HistoricalQuotes(originDate,exchSymbol,durationInDays));
                double seed = 0;
                // price + delta
                for (int i = 0; i < durationInDays ; i++) {
                    seed = Math.random()*1800;
                    getHistoricalQuotes().addDailyQuote(
                            new GeneratedQuote(exchSymbol,originDate.plusDays(i),generateValue(seed),generateValue(seed),generateValue(seed),generateValue(seed),generateValue((seed*1000)).intValue())
                    );
                }
                return Optional.of(getHistoricalQuotes());
            }else {
                throw new QuoteRequestDateValidationException("Quote request failed date validation. Date range must be in past");
            }
        }
        return Optional.empty();
    }
    private BigDecimal generateValue(double value){
        double delta = (Math.sin(value)+(2*Math.sin((value/2))+Math.cos(value)));
        double price = value+ ((delta/100)*value);
        BigDecimal rtnValue = new BigDecimal(price,new MathContext(7,RoundingMode.HALF_DOWN));
        return rtnValue;
    }


    private HistoricalQuotes getHistoricalQuotes() {
        return historicalQuotes;
    }

    private void setHistoricalQuotes(HistoricalQuotes historicalQuotes) {
        this.historicalQuotes = historicalQuotes;
    }
}
