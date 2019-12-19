package com.nathan.roifmr.market;

import com.nathan.roifmr.domain.exchange.Generator;

/**
 * implement this as factory provider for quote production
 * based on generator type
 * support with archive manager svc
 */
public class QuotingProvider {
    public static QuoteGenerator getQuoteGenerator(Generator generatorType){

        switch (generatorType){
            case RANDOMIZER:
                return new PsuedoRandomQuoteAlorithm();
            case PERCENTAGE:
                return new MarketPercentageQuoteAlgorithm();
        }
        return new SimpleQuoteAlgorithm();
    }
}
