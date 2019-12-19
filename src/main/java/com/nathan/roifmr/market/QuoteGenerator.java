package com.nathan.roifmr.market;

import com.nathan.roifmr.domain.exchange.Generator;
import com.nathan.roifmr.domain.exchange.HistoricalQuotes;
import com.nathan.roifmr.domain.exchange.MarketSymbol;
import com.nathan.roifmr.domain.exchange.QuoteRequestDateValidationException;

import java.time.LocalDate;
import java.util.Optional;

public interface QuoteGenerator {
 //todo make accessible via rest
    Optional<HistoricalQuotes> generateDailyQuotes(LocalDate originDate, int durationInDays, MarketSymbol exchSymbol) throws QuoteRequestDateValidationException;
}
