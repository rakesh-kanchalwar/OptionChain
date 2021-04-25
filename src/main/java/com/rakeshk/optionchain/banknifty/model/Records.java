package com.rakeshk.optionchain.banknifty.model;

import java.util.List;

public class Records{
    public List<String> expiryDates;
    public List<Datum> data;
    public String timestamp;
    public double underlyingValue;
    public List<Integer> strikePrices;
    public Index index;
}

