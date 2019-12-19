package com.nathan.roifmr.services.dto;

import com.nathan.roifmr.domain.dto.QuoteRequest;
import com.nathan.roifmr.domain.exchange.HistoricalQuotes;
import com.nathan.roifmr.domain.exchange.QuoteGenerationException;
import com.nathan.roifmr.domain.exchange.QuoteRequestDateValidationException;
import com.nathan.roifmr.market.QuotingProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuoteFulfillmentService implements QuoteRequestService {
    @Override
    public HistoricalQuotes getRequestedQuote(QuoteRequest quoteRequested) throws QuoteRequestDateValidationException, QuoteGenerationException {
        Optional<HistoricalQuotes> rtnValue =
        QuotingProvider.getQuoteGenerator(quoteRequested.getQuotingAlgorithm())
                .generateDailyQuotes(quoteRequested.getOriginatingDate(),quoteRequested.getQuotingDuration(),quoteRequested.getExchangeSymbol());

        if (rtnValue.isPresent()) return rtnValue.get();
        else throw new QuoteGenerationException("Failed to receive historical quote");
    }
}
