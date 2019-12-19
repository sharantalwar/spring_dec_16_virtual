package com.nathan.roifmr.services;

import com.nathan.roifmr.domain.exchange.HistoricalQuotes;

import java.util.List;
import java.util.Optional;

public interface QuoteStorage {
    String storeGeneratedQuote(HistoricalQuotes currentQuote);
    Optional<HistoricalQuotes> findGeneratedQuote(String quoteKey);
    List<String> getAvailableGeneratedQuotes();
}
