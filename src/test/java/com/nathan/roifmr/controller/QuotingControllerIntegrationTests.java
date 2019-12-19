package com.nathan.roifmr.controller;

import com.nathan.roifmr.domain.dto.QuoteRequest;
import com.nathan.roifmr.domain.exchange.Generator;
import com.nathan.roifmr.domain.exchange.MarketSymbol;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class QuotingControllerIntegrationTests {
    Logger log = LoggerFactory.getLogger(QuotingControllerIntegrationTests.class);

    @Autowired
    private QuotingController qCtlr;

    
    @Test
    void getAvailableGeneratedQuoteFilesServable(){
        ResponseEntity rtnValue = qCtlr.getAvailableGeneratedQuoteFiles();
        assertAll(
                ()-> assertTrue(rtnValue.getStatusCode()== HttpStatus.OK)

        );
    }
    @ParameterizedTest(name = "Non nullable properties for quote request")
    @MethodSource("quoteMethodSource")
    void getGeneratedQuotesValidationFailureRequest(QuoteRequest arg){
        log.info(arg.toString());
        assertNotNull(arg,"object present");
    }

    static Stream<QuoteRequest> quoteMethodSource(){
        return Stream.of(new QuoteRequest(null, MarketSymbol.ORCL, Generator.DEFAULT,1),
                new QuoteRequest(LocalDate.of(2019,2,1),null, Generator.DEFAULT,1),
                new QuoteRequest(LocalDate.of(2019,1,1), MarketSymbol.GOOG, Generator.DEFAULT,0));

    }

}
