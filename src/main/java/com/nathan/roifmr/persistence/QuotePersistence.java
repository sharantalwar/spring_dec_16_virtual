package com.nathan.roifmr.persistence;

import com.nathan.roifmr.domain.exchange.HistoricalQuotes;

import java.util.Optional;
import java.util.UUID;

public interface QuotePersistence {
    boolean save(String storageKey, HistoricalQuotes quoteValue);
    Optional<HistoricalQuotes> find(String storageKey);
}
