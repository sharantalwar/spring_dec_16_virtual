package com.nathan.roifmr.domain.exchange;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

public class GeneratedQuote {
    private MarketSymbol exchangeSymbol;
    private LocalDate exchangeDate;
    private BigDecimal exchangeHigh;
    private BigDecimal exchangeLow;
    private BigDecimal exchangeOpen;
    private BigDecimal exchangeClose;
    private int tradingVolume;

    public GeneratedQuote() {
        this.exchangeOpen = BigDecimal.ZERO;
        this.exchangeClose = BigDecimal.ZERO;
        this.exchangeHigh = BigDecimal.ZERO;
        this.exchangeLow = BigDecimal.ZERO;
    }
    public GeneratedQuote(MarketSymbol exchangeSymbol, LocalDate exchangeDate, BigDecimal exchangeHigh, BigDecimal exchangeLow, BigDecimal exchangeOpen, BigDecimal exchangeClose, int tradingVolume) {
        this(exchangeSymbol,exchangeDate);
        this.exchangeHigh = exchangeHigh;
        this.exchangeLow = exchangeLow;
        this.exchangeOpen = exchangeOpen;
        this.exchangeClose = exchangeClose;
        this.tradingVolume = tradingVolume;
    }

    public GeneratedQuote(MarketSymbol exchangeSymbol, LocalDate exchangeDate) {
        this();
        this.exchangeSymbol = exchangeSymbol;
        this.exchangeDate = exchangeDate;
    }

    public MarketSymbol getExchangeSymbol() {
        return exchangeSymbol;
    }

    public void setExchangeSymbol(MarketSymbol exchangeSymbol) {
        this.exchangeSymbol = exchangeSymbol;
    }

    @JsonIgnore
    public LocalDate getExchangeDate() {
        return exchangeDate;
    }
    @JsonGetter("exchangeDate")
    public String getExchangeDateStr() {
        return exchangeDate.toString();
    }
    @JsonSetter("exchangeDate")
    public void setExchangeDate(String exchangeDate) {
        this.exchangeDate = LocalDate.parse(exchangeDate);
    }

    public void setExchangeDate(LocalDate exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public BigDecimal getExchangeHigh() {
        return exchangeHigh;
    }

    public void setExchangeHigh(BigDecimal exchangeHigh) {
        this.exchangeHigh = exchangeHigh;
    }

    public BigDecimal getExchangeLow() {
        return exchangeLow;
    }

    public void setExchangeLow(BigDecimal exchangeLow) {
        this.exchangeLow = exchangeLow;
    }

    public BigDecimal getExchangeOpen() {
        return exchangeOpen;
    }

    public void setExchangeOpen(BigDecimal exchangeOpen) {
        this.exchangeOpen = exchangeOpen;
    }

    public BigDecimal getExchangeClose() {
        return exchangeClose;
    }

    public void setExchangeClose(BigDecimal exchangeClose) {
        this.exchangeClose = exchangeClose;
    }

    public int getTradingVolume() {
        return tradingVolume;
    }

    public void setTradingVolume(int tradingVolume) {
        this.tradingVolume = tradingVolume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneratedQuote that = (GeneratedQuote) o;
        return getTradingVolume() == that.getTradingVolume() &&
                getExchangeSymbol() == that.getExchangeSymbol() &&
                getExchangeDate().equals(that.getExchangeDate()) &&
                getExchangeHigh().equals(that.getExchangeHigh()) &&
                getExchangeLow().equals(that.getExchangeLow()) &&
                getExchangeOpen().equals(that.getExchangeOpen()) &&
                getExchangeClose().equals(that.getExchangeClose());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExchangeSymbol(), getExchangeDate(), getExchangeHigh(), getExchangeLow(), getExchangeOpen(), getExchangeClose(), getTradingVolume());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GeneratedQuote.class.getSimpleName() + "[", "]")
                .add("exchangeSymbol=" + exchangeSymbol)
                .add("exchangeDate=" + exchangeDate)
                .add("exchangeHigh=" + exchangeHigh)
                .add("exchangeLow=" + exchangeLow)
                .add("exchangeOpen=" + exchangeOpen)
                .add("exchangeClose=" + exchangeClose)
                .add("tradingVolume=" + tradingVolume)
                .toString();
    }
}
