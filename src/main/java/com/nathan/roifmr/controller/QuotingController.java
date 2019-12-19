package com.nathan.roifmr.controller;

import com.nathan.roifmr.domain.dto.QuoteRequest;
import com.nathan.roifmr.domain.exchange.Generator;
import com.nathan.roifmr.domain.exchange.HistoricalQuotes;
import com.nathan.roifmr.domain.exchange.QuoteGenerationException;
import com.nathan.roifmr.domain.exchange.QuoteRequestDateValidationException;
import com.nathan.roifmr.services.dto.QuoteRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/exchange/quoting/v0")
public class QuotingController {
    Logger log = LoggerFactory.getLogger(QuotingController.class);

    private final QuoteRequestService quoteSvcRef;

    public QuotingController(QuoteRequestService svcQRef){
        this.quoteSvcRef = svcQRef;
    }


    @PostMapping(path = "mock",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getGeneratedQuotes(@Valid @RequestBody QuoteRequest generationRequest) throws QuoteRequestDateValidationException, QuoteGenerationException {
        log.info("Generate quote request",generationRequest);
        return new ResponseEntity(quoteSvcRef.getRequestedQuote(generationRequest), HttpStatus.CREATED);
    }

    public ResponseEntity getAvailableGeneratedQuoteFiles() {
        return null;
    }
}
