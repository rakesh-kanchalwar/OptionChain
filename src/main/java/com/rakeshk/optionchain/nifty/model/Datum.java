package com.rakeshk.optionchain.nifty.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Datum{
    public int strikePrice;
    public String expiryDate;
    @JsonProperty("PE") 
    public PE pE;
    @JsonProperty("CE") 
    public CE cE;
}

