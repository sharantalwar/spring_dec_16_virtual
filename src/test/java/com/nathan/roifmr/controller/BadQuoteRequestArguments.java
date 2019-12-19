package com.nathan.roifmr.controller;

import com.nathan.roifmr.domain.dto.QuoteRequest;
import com.nathan.roifmr.domain.exchange.Generator;
import com.nathan.roifmr.domain.exchange.MarketSymbol;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.time.LocalDate;
import java.util.stream.Stream;

public class BadQuoteRequestArguments implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(Arguments.of(new QuoteRequest(null, MarketSymbol.ORCL, Generator.DEFAULT,1),
                new QuoteRequest(LocalDate.of(2019,2,1),null, Generator.DEFAULT,1),
                new QuoteRequest(LocalDate.of(2019,1,1), MarketSymbol.GOOG, Generator.DEFAULT,0) ));
    }
}
