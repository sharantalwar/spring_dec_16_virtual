package com.nathan.roifmr.market;

public interface HistoricalQuoting {
    //// TODO: 12/16/2019 build quoting file handling at startup
    void getWriteLocation();
    void generateFileName();
    void writeExchSymbolQuotes();


}
