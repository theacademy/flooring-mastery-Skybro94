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

    public Order(int orderNumber, String customerName, String state, BigDecimal taxRate,
                 String productType, BigDecimal area, BigDecimal costPerSquareFoot,
                 BigDecimal laborCostPerSquareFoot, String date) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
        this.date = date;
        calculateCosts();
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
                Integer.parseInt(tokens[0]), tokens[1], tokens[2], new BigDecimal(tokens[3]),
                tokens[4], new BigDecimal(tokens[5]), new BigDecimal(tokens[6]),
                new BigDecimal(tokens[7]), date);
    }

    public String toCsv() {
        return orderNumber + "," + customerName + "," + state + "," + taxRate + "," + productType + "," +
                area + "," + costPerSquareFoot + "," + laborCostPerSquareFoot + "," + materialCost + "," +
                laborCost + "," + tax + "," + total;
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
}
