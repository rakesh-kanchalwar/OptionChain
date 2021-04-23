package com.rakeshk.optionchain.model.nifty;
// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */
public class PE{
    public int strikePrice;
    public String expiryDate;
    public String underlying;
    public String identifier;
    public int openInterest;
    public int changeinOpenInterest;
    public double pchangeinOpenInterest;
    public int totalTradedVolume;
    public double impliedVolatility;
    public double lastPrice;
    public double change;
    public double pChange;
    public int totalBuyQuantity;
    public int totalSellQuantity;
    public int bidQty;
    public double bidprice;
    public int askQty;
    public double askPrice;
    public double underlyingValue;
    public int totOI;
    public int totVol;
}

