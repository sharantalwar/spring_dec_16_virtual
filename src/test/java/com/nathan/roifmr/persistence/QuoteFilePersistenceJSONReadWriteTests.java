package com.nathan.roifmr.persistence;

import com.nathan.roifmr.domain.exchange.HistoricalQuotes;
import com.nathan.roifmr.domain.exchange.MarketSymbol;
import com.nathan.roifmr.domain.exchange.QuoteRequestDateValidationException;
import com.nathan.roifmr.market.SimpleQuoteAlgorithm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class QuoteFilePersistenceJSONReadWriteTests {
    private final Path MOCK_DIRECTORY = Path.of("quote_files","mock");
    private final String FILEEXT = ".json";

    private HistoricalQuotes currentQuote;

    @Autowired
    private  QuotePersistence fileService;

    @BeforeEach
    void setUp() throws QuoteRequestDateValidationException {
        currentQuote = ((new SimpleQuoteAlgorithm()).generateDailyQuotes(LocalDate.of(2018,1,1),30, MarketSymbol.GOOG)).get();
    }

    @AfterEach
    void tearDown() {
        currentQuote = null;
    }

    @Test
    void debugGetDirectoryForFileWrite() throws FileNotFoundException {

        Path totalPath = Path.of(MOCK_DIRECTORY.toString(),(currentQuote.getUniqueKey()+FILEEXT));
        System.out.println(totalPath);
        File springFile = ResourceUtils.getFile("classpath:quote_files/mock/readme.md");
        System.out.println(String.format("Is Dir: %s, Abs Path:%s, parent:%s",
                springFile.isDirectory(), springFile.getAbsolutePath(),springFile.getParent()));
    }

    @Test
    void saveHistoricalQuoteToMockDirectory() {
        assertTrue(fileService.save(currentQuote.getUniqueKey(),currentQuote));
    }

    @Test
    void findGeneratedHistoricalQuoteFromMockDirectory() {
        String expectedQuoteFileKey = currentQuote.getUniqueKey();
        assertTrue(fileService.save(currentQuote.getUniqueKey(),currentQuote));

        Optional<HistoricalQuotes> rtnValue = fileService.find(expectedQuoteFileKey);
        Assumptions.assumingThat(rtnValue.isPresent(),()->{
            HistoricalQuotes auditValue = rtnValue.get();
            assertAll(
                    ()->assertEquals(currentQuote.getUniqueKey(),auditValue.getUniqueKey())
                    ,()->assertEquals(currentQuote,auditValue)
            );
        });
    }
}
