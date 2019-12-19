package com.nathan.roifmr.services.dto;

import com.nathan.roifmr.domain.dto.QuoteRequest;
import com.nathan.roifmr.domain.exchange.HistoricalQuotes;
import com.nathan.roifmr.domain.exchange.QuoteGenerationException;
import com.nathan.roifmr.domain.exchange.QuoteRequestDateValidationException;

/**
 * provide direct support to REST layer
 * convert DTO's to internal domain types
 *
 */

public interface QuoteRequestService {
    HistoricalQuotes getRequestedQuote(QuoteRequest quoteRequested) throws QuoteRequestDateValidationException, QuoteGenerationException;
}
