package com.sg.flooringmastery.dto;

import java.math.BigDecimal;

public class Order {
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;
    private String date;

    public Order(int orderNumber, String date, String customerName, String state, BigDecimal taxRate,
                 String productType, BigDecimal area, BigDecimal costPerSquareFoot,
                 BigDecimal laborCostPerSquareFoot, BigDecimal materialCost, BigDecimal laborCost,
                 BigDecimal tax, BigDecimal total) {
        this.orderNumber = orderNumber;
        this.date = date;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
        this.tax = tax;
        this.total = total;
    }


    public void calculateCosts() {
        this.materialCost = area.multiply(costPerSquareFoot);
        this.laborCost = area.multiply(laborCostPerSquareFoot);
        this.tax = (materialCost.add(laborCost)).multiply(taxRate.divide(new BigDecimal("100")));
        this.total = materialCost.add(laborCost).add(tax);
    }

    public static Order fromCsv(String csv, String date) {
        String[] tokens = csv.split(",");

        return new Order(
                Integer.parseInt(tokens[0]),  // orderNumber
                date,                         // date
                tokens[1],                     // customerName
                tokens[2],                     // state
                new BigDecimal(tokens[3]),     // taxRate
                tokens[4],                     // productType
                new BigDecimal(tokens[5]),     // area
                new BigDecimal(tokens[6]),     // costPerSquareFoot
                new BigDecimal(tokens[7]),     // laborCostPerSquareFoot
                new BigDecimal(tokens[8]),     // materialCost
                new BigDecimal(tokens[9]),     // laborCost
                new BigDecimal(tokens[10]),    // tax
                new BigDecimal(tokens[11])     // total
        );
    }


    public String toCsv() {
        return orderNumber + "," + customerName + "," + state + "," + taxRate + "," + productType + "," +
                area + "," + costPerSquareFoot + "," + laborCostPerSquareFoot + "," + materialCost + "," +
                laborCost + "," + tax + "," + total;
    }

    public String getState() {
        return state;
    }
    public String getDate() {
        return date;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public BigDecimal getArea() {
        return area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }
}
