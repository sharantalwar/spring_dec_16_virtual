package com.nathan.roifmr.market;

import com.nathan.roifmr.domain.exchange.HistoricalQuotes;
import com.nathan.roifmr.domain.exchange.MarketSymbol;
import com.nathan.roifmr.domain.exchange.QuoteRequestDateValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SimpleQuoteAlgorithmTest {

    private SimpleQuoteAlgorithm testFixture;

    @BeforeEach
    void setUp() {
        this.testFixture = new SimpleQuoteAlgorithm();
    }

    @AfterEach
    void tearDown() {
        testFixture = null;
    }

    @Test
    void generationOfHistoricalQuotesWithGoodSeedValues() throws QuoteRequestDateValidationException {
        LocalDate expectedOrigination = LocalDate.of(2019,1,1);
        int expectedDuration=30;
        MarketSymbol expectedExchangeSymbol = MarketSymbol.ORCL;
        Optional<HistoricalQuotes> rtnValue = testFixture.generateDailyQuotes(expectedOrigination,expectedDuration,expectedExchangeSymbol);

        assertAll(()->assertEquals(expectedDuration,rtnValue.get().getQuoteDuration())
        ,()->assertEquals(expectedExchangeSymbol,rtnValue.get().getExchangeSymbol())
        ,()->assertEquals(expectedOrigination,rtnValue.get().getOriginationDate())
        ,()->assertTrue(rtnValue.get().getDailyQuotes().size()==expectedDuration)
        );
//        rtnValue.getDailyQuotes().stream().forEach(c->System.out.println(c));
    }

    @Test
    void badGenerationOfHistoricalquotesWithNullOriginDate() throws QuoteRequestDateValidationException {
        assertFalse(testFixture.generateDailyQuotes(null,1,MarketSymbol.ORCL).isPresent());
    }
    @Test
    void badGenerationOfHistoricalquotesWithNullMarketSymbol() throws QuoteRequestDateValidationException {
        assertTrue(testFixture.generateDailyQuotes(LocalDate.of(2019,1,1),1,null).isEmpty());
    }
    @Test
    void badGenerationOfHistoricalquotesThrowsExceptionWithBadDateRange(){
        LocalDate expectedOriginationDate = LocalDate.now().minusDays(2);
        int quoteDuration = 2;
        assertThrows(QuoteRequestDateValidationException.class,()->testFixture.generateDailyQuotes(expectedOriginationDate,quoteDuration,MarketSymbol.ORCL));
    }

    @Test
    void generationOfHistoricalQuotesUpToPreviousDayDate() throws QuoteRequestDateValidationException {
        LocalDate expectedOriginDate = LocalDate.now().minusDays(3);
        int quoteDuration = 2;
        assertEquals(true, testFixture.generateDailyQuotes(expectedOriginDate, quoteDuration, MarketSymbol.GOOG).isPresent());
    }

}
