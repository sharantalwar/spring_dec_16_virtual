package com.nathan.roifmr.market;

import com.nathan.roifmr.domain.exchange.HistoricalQuotes;
import com.nathan.roifmr.domain.exchange.MarketSymbol;
import com.nathan.roifmr.domain.exchange.QuoteRequestDateValidationException;

import java.time.LocalDate;
import java.util.Optional;

public class MarketPercentageQuoteAlgorithm extends AbstractQuoteGenerator {
    @Override
    public Optional<HistoricalQuotes> generateDailyQuotes(LocalDate originDate, int durationInDays, MarketSymbol exchSymbol) throws QuoteRequestDateValidationException {
        return Optional.empty();
    }
}
