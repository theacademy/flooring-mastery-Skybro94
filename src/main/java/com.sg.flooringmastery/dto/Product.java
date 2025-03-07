package com.sg.flooringmastery.dto;

import java.math.BigDecimal;

public class Product {
    private final String productType;
    private final BigDecimal costPerSquareFoot;
    private final BigDecimal laborCostPerSquareFoot;

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    @Override
    public String toString() {
        return productType + ": $" + costPerSquareFoot + "/sqft, Labor: $" + laborCostPerSquareFoot + "/sqft";
    }
}
