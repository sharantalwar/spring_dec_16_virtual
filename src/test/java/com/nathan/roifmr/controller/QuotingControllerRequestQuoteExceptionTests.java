package com.nathan.roifmr.controller;

import com.nathan.roifmr.domain.dto.QuoteRequest;
import com.nathan.roifmr.domain.dto.RequestError;
import com.nathan.roifmr.domain.exchange.Generator;
import com.nathan.roifmr.domain.exchange.MarketSymbol;
import com.nathan.roifmr.domain.exchange.QuoteGenerationException;
import com.nathan.roifmr.domain.exchange.QuoteRequestDateValidationException;
import com.nathan.roifmr.services.dto.QuoteRequestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class QuotingControllerRequestQuoteExceptionTests {

    private final String BASE_URL = "api/exchange/quoting/v0";
    private final String QUOTE_URL = String.format("%s/%s",BASE_URL,"mock");
    private final String BAD_DATE_VALIDATION_EXCEPTION_MESSAGE = "Requested quote date range invalid";
    private final String FAILED_TO_FETCH_QUOTE = "Failed to retrieve generated data";
    private QuoteRequest currentQuote;

    @MockBean
    private QuoteRequestService qSvc;

    @BeforeEach
    void setUp() throws QuoteRequestDateValidationException, QuoteGenerationException {

    }

    @AfterEach
    void tearDown() {
        currentQuote = null;
    }

    @Test
    void badQuoteRequestWithMockedServiceThrowsException() throws QuoteRequestDateValidationException, QuoteGenerationException {
        currentQuote = new QuoteRequest(LocalDate.now().minusDays(10),MarketSymbol.AMZN,Generator.DEFAULT,2);

        BDDMockito.given(qSvc.getRequestedQuote(any(QuoteRequest.class))).willThrow(QuoteGenerationException.class);

        RequestError rtnMessage =
        given().accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(currentQuote)
                .when().post(QUOTE_URL)
                .then().statusCode(HttpStatus.I_AM_A_TEAPOT.value())
                .and().extract().as(RequestError.class);
        assertAll(()->assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),rtnMessage.getErrorCode()),
                ()->assertEquals(FAILED_TO_FETCH_QUOTE,rtnMessage.getSummary()));
    }

    @Test
    void badQuoteRequestWithDateRangeValidationException() {
        currentQuote = new QuoteRequest(LocalDate.now().minusDays(2),MarketSymbol.GOOG, Generator.DEFAULT,2);
        RequestError rtnMessage =
        given().accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(currentQuote)
                .when().post(QUOTE_URL)
                .then().statusCode(HttpStatus.BAD_REQUEST.value())
                .and().extract().as(RequestError.class);

        assertAll(()->assertEquals(HttpStatus.NOT_ACCEPTABLE.value(),rtnMessage.getErrorCode()),
                ()->assertEquals(BAD_DATE_VALIDATION_EXCEPTION_MESSAGE,rtnMessage.getSummary()));
    }
    @Test
    void getQuoteRequestFailureException(){
        currentQuote = new QuoteRequest(LocalDate.now().minusDays(7),MarketSymbol.MSFT,Generator.PERCENTAGE,5);
        RequestError rtnMessage =
        given().accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(currentQuote)
                .when().post(QUOTE_URL)
                .then().statusCode(HttpStatus.I_AM_A_TEAPOT.value())
                .and().extract().as(RequestError.class);

        assertAll(()->assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),rtnMessage.getErrorCode()),
                ()->assertEquals(FAILED_TO_FETCH_QUOTE,rtnMessage.getSummary()));

    }
}
