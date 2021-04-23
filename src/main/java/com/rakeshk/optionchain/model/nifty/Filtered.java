package com.rakeshk.optionchain.model.nifty;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Filtered{
    public List<Datum> data;
    @JsonProperty("CE") 
    public CE cE;
    @JsonProperty("PE") 
    public PE pE;
}

