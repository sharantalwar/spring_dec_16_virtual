package com.nathan.roifmr.domain.exchange;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.LocalDate;
import java.util.*;

/**
 * Represent completed generated quotes
 * Stored on file system
 */
public class HistoricalQuotes {
    //// TODO: 12/18/2019 need file key generation method , id_exchangeSymbol_quoteDuration.txt|json|csv
    private UUID id;
    private LocalDate generatedOn;
    private LocalDate originationDate;
    private List<GeneratedQuote> dailyQuotes;
    private MarketSymbol exchangeSymbol;
    private int quoteDuration;

    public HistoricalQuotes() {
        this.id = UUID.randomUUID();
        this.generatedOn = LocalDate.now();
        this.dailyQuotes = new ArrayList<GeneratedQuote>();
    }

    public HistoricalQuotes(LocalDate originationDate, MarketSymbol exchangeSymbol, int quoteDuration) {
        this();
        this.originationDate = originationDate;
        this.exchangeSymbol = exchangeSymbol;
        this.quoteDuration = quoteDuration;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @JsonIgnore
    public LocalDate getGeneratedOn() {
        return generatedOn;
    }
    @JsonGetter("generatedOn")
    public String getGeneratedOnStr() {
        return generatedOn.toString();
    }
    @JsonSetter("generatedOn")
    public void setGeneratedOnStr(String generatedOn){
        this.generatedOn = LocalDate.parse(generatedOn);
    }

    public void setGeneratedOn(LocalDate generatedOn) {
        this.generatedOn = generatedOn;
    }

    @JsonIgnore
    public LocalDate getOriginationDate() {
        return originationDate;
    }

    @JsonGetter("originationDate")
    public String getOriginationDateStr() {
        return originationDate.toString();
    }
    @JsonSetter("originationDate")
    public void setOriginationDateStr(String originationDate) {
        this.originationDate = LocalDate.parse(originationDate);
    }
    public void setOriginationDate(LocalDate originationDate) {
        this.originationDate = originationDate;
    }

    public List<GeneratedQuote> getDailyQuotes() {
        return dailyQuotes;
    }

    public void setDailyQuotes(List<GeneratedQuote> dailyQuotes) {
        this.dailyQuotes = dailyQuotes;
    }
    public void addDailyQuote(GeneratedQuote dayQuote){
        this.dailyQuotes.add(dayQuote);
    }

    public MarketSymbol getExchangeSymbol() {
        return exchangeSymbol;
    }

    public void setExchangeSymbol(MarketSymbol exchangeSymbol) {
        this.exchangeSymbol = exchangeSymbol;
    }

    public int getQuoteDuration() {
        return quoteDuration;
    }

    public void setQuoteDuration(int quoteDuration) {
        this.quoteDuration = quoteDuration;
    }
    @JsonIgnore
    public String getUniqueKey(){
        /*
        need provide identification of this quote
        id_exchangeSymbol_quoteDuration
         */
        StringJoiner strJoiner = new StringJoiner("_");
        strJoiner.add("generated");
        strJoiner.add(getId().toString());
        strJoiner.add(getExchangeSymbol().name());
        strJoiner.add(Integer.toString(getQuoteDuration()));
        return strJoiner.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricalQuotes that = (HistoricalQuotes) o;
        return getQuoteDuration() == that.getQuoteDuration() &&
                getId().equals(that.getId()) &&
                getGeneratedOn().equals(that.getGeneratedOn()) &&
                getOriginationDate().equals(that.getOriginationDate()) &&
                getExchangeSymbol() == that.getExchangeSymbol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGeneratedOn(), getOriginationDate(), getExchangeSymbol(), getQuoteDuration());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", HistoricalQuotes.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("generatedOn=" + generatedOn)
                .add("originationDate=" + originationDate)
                .add("dailyQuotes=" + dailyQuotes)
                .add("exchangeSymbol=" + exchangeSymbol)
                .add("quoteDuration=" + quoteDuration)
                .toString();
    }
}
