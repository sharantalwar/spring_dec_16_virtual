package com.nathan.roifmr.market;

import java.time.LocalDate;

public abstract class AbstractQuoteGenerator implements QuoteGenerator{
    private LocalDate currentDay;

    public AbstractQuoteGenerator(){
        currentDay = LocalDate.now();
    }

    protected boolean validateQuoteDates(LocalDate originationDate, int quoteDuration){
        return currentDay.isAfter((originationDate.plusDays(quoteDuration)));
    }
}
