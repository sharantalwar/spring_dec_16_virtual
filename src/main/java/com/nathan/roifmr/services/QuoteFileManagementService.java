package com.nathan.roifmr.services;

import com.nathan.roifmr.domain.exchange.HistoricalQuotes;
import com.nathan.roifmr.persistence.QuotePersistence;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuoteFileManagementService implements QuoteStorage {

    private  final QuotePersistence qStore;

    public QuoteFileManagementService(QuotePersistence bRef){
        qStore = bRef;
    }

    @Override
    public String storeGeneratedQuote(HistoricalQuotes currentQuote) {
        if (qStore.save(currentQuote.getUniqueKey(),currentQuote)){
            return currentQuote.getUniqueKey();
        }
        return null;
    }

    @Override
    public Optional<HistoricalQuotes> findGeneratedQuote(String quoteKey) {
        return qStore.find(quoteKey);
    }


    @Override
    public List<String> getAvailableGeneratedQuotes() {
        return null;
    }
}
