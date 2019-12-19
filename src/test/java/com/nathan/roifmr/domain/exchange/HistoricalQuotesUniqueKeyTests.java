package com.nathan.roifmr.domain.exchange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HistoricalQuotesUniqueKeyTests {
    private HistoricalQuotes testFixture;
    private UUID currentId;

    @BeforeEach
    void setup(){
        currentId = UUID.randomUUID();
        testFixture = new HistoricalQuotes();
        testFixture.setId(currentId);

    }

    @Test
    void getUniqueKeyFormatConfirmation() {
        MarketSymbol security = MarketSymbol.MSFT;
        int duration = 10;
        testFixture.setExchangeSymbol(security);
        testFixture.setQuoteDuration(duration);
        String expectedFormat = String.format("generated_%s_%s_%s",currentId,security,duration);
        assertEquals(expectedFormat,testFixture.getUniqueKey());
    }

    @Test
    void demoPathJoiningResult(){
        Path dir = Path.of("quote_files","mock");
        System.out.println(Path.of(dir.toString(),"some_file_name",".json"));
    }
}
