package com.nathan.roifmr.controller;

import com.nathan.roifmr.domain.dto.QuoteRequest;
import com.nathan.roifmr.domain.dto.RequestError;
import com.nathan.roifmr.domain.exchange.Generator;
import com.nathan.roifmr.domain.exchange.MarketSymbol;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class QuotingControllerQuoteRequestValidationAcceptanceTests {

    private final String BASE_URL = "api/exchange/quoting/v0";

    private final String QUOTE_URL = String.format("%s/%s",BASE_URL,"mock");
    private final String REQ_VALIDATION_MSG = "Request validation failure";

    @Test
    void badQuoteRequestMissingOriginationDate(){
        QuoteRequest qRequest = new QuoteRequest(null, MarketSymbol.MSFT, Generator.DEFAULT,1);
        RequestError rtnMessage =
        given().accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(qRequest)
                .log().all()
                .when().post(QUOTE_URL)
                .then().log().all().and().statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                .and().extract().as(RequestError.class);
        assertAll(
                ()->assertEquals(HttpStatus.BAD_REQUEST.value(),rtnMessage.getErrorCode())
                ,()->assertEquals(REQ_VALIDATION_MSG,rtnMessage.getSummary())
                ,()-> rtnMessage.getDetails().forEach(s ->assertTrue(s.contains("originatingDate")))
        );
    }

    @ParameterizedTest
    @MethodSource
    void badQuoteRequestBindingValidation(QuoteRequest currentQuote){
                given().accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(currentQuote)
                        .log().all()
                        .when().post(QUOTE_URL)
                        .then().log().all().and().statusCode(HttpStatus.NOT_ACCEPTABLE.value());
    }
    static Stream<QuoteRequest> badQuoteRequestBindingValidation(){
        return Stream.of(new QuoteRequest(null, MarketSymbol.ORCL, Generator.DEFAULT,1),
                new QuoteRequest(LocalDate.of(2019,2,1),null, Generator.DEFAULT,1),
                new QuoteRequest(LocalDate.of(2019,1,1), MarketSymbol.GOOG, Generator.DEFAULT,0));
    }



    @Test
    void badQuoteRequestMissingMarketSymbol(){
        QuoteRequest qRequest = new QuoteRequest(LocalDate.of(2019,1,1), null, Generator.DEFAULT,1);
        RequestError rtnMessage =
                given().accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(qRequest)
                        .log().all()
                        .when().post(QUOTE_URL)
                        .then().log().all().and().statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                        .and().extract().as(RequestError.class);
    }

    @Test
    void badQuoteRequestMissingDurationDays(){
        QuoteRequest qRequest = new QuoteRequest(LocalDate.of(2019,1,1), MarketSymbol.GOOG, Generator.DEFAULT,0);
        RequestError rtnMessage =
                given().accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(qRequest)
                        .log().all()
                        .when().post(QUOTE_URL)
                        .then().log().all().and().statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                        .and().extract().as(RequestError.class);
    }

    @Test
    void badQuoteRequestMissingAllRequiredFields(){
        QuoteRequest qRequest = new QuoteRequest(null, null, Generator.DEFAULT,0);
        RequestError rtnMessage =
                given().accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(qRequest)
                        .log().all()
                        .when().post(QUOTE_URL)
                        .then().log().all().and().statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                        .and().extract().as(RequestError.class);
    }


}
