package com.sg.flooringmastery.dto;

import java.math.BigDecimal;

public class Tax {
    private final String stateAbbreviation;
    private final String stateName;
    private final BigDecimal taxRate;

    public Tax(String stateAbbreviation, String stateName, BigDecimal taxRate) {
        this.stateAbbreviation = stateAbbreviation;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public String getStateName() {
        return stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    @Override
    public String toString() {
        return stateName + " (" + stateAbbreviation + ") - Tax Rate: " + taxRate + "%";
    }
}
