package com.nathan.roifmr.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nathan.roifmr.domain.exchange.Generator;
import com.nathan.roifmr.domain.exchange.MarketSymbol;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class QuoteRequest {

    @JsonIgnore
    @NotNull(message = "Requested Date Time is not allowed to be nullified")
    private LocalDateTime requestedAt;

    @NotNull(message = "Originating date for quotes to start must be present")
    @Past(message = "Originating date required to be in the past. We don't predicate markets in history department")
    private LocalDate originatingDate;

    @NotNull(message = "Exchange symbol required for historical quoting")
    private MarketSymbol exchangeSymbol;
    private Generator quotingAlgorithm;

    @Max(value = 180,message = "maximum quoting duration is 180 days")
    @Min(value = 1,message = "minimum quoting duration is single day")
    private int quotingDuration;

    public QuoteRequest() {
        this.requestedAt = LocalDateTime.now();
        this.quotingAlgorithm = Generator.DEFAULT;
    }

    public QuoteRequest(LocalDate originatingDate, MarketSymbol exchangeSymbol, Generator quotingAlgorithm, int quotingDuration) {
        this();
        this.originatingDate = originatingDate;
        this.exchangeSymbol = exchangeSymbol;
        this.quotingAlgorithm = quotingAlgorithm;
        this.quotingDuration = quotingDuration;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public LocalDate getOriginatingDate() {
        return originatingDate;
    }

    public void setOriginatingDate(LocalDate originatingDate) {
        //// TODO: 12/16/2019 add validation for past
        this.originatingDate = originatingDate;
    }

    public MarketSymbol getExchangeSymbol() {
        return exchangeSymbol;
    }

    public void setExchangeSymbol(MarketSymbol exchangeSymbol) {
        this.exchangeSymbol = exchangeSymbol;
    }

    public Generator getQuotingAlgorithm() {
        return quotingAlgorithm;
    }

    public void setQuotingAlgorithm(Generator quotingAlgorithm) {
        if (quotingAlgorithm !=null){
            this.quotingAlgorithm = quotingAlgorithm;
        }
    }

    public int getQuotingDuration() {
        return quotingDuration;
    }

    public void setQuotingDuration(int quotingDuration) {
        this.quotingDuration = quotingDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuoteRequest that = (QuoteRequest) o;
        return getQuotingDuration() == that.getQuotingDuration() &&
                getRequestedAt().equals(that.getRequestedAt()) &&
                getOriginatingDate().equals(that.getOriginatingDate()) &&
                getExchangeSymbol() == that.getExchangeSymbol() &&
                getQuotingAlgorithm() == that.getQuotingAlgorithm();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRequestedAt(), getOriginatingDate(), getExchangeSymbol(), getQuotingAlgorithm(), getQuotingDuration());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", QuoteRequest.class.getSimpleName() + "[", "]")
                .add("requestedAt=" + requestedAt)
                .add("originatingDate=" + originatingDate)
                .add("exchangeSymbol=" + exchangeSymbol)
                .add("quotingAlgorithum=" + quotingAlgorithm)
                .add("quotingDuration=" + quotingDuration)
                .toString();
    }
}
