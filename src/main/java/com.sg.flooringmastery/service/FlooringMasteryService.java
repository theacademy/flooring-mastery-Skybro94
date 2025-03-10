package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;

import java.math.BigDecimal;
import java.util.List;

public interface FlooringMasteryService {

    List<Order> getOrders(String date);
    List<String> getAllValidStates();
    Order getOrder(String date, int orderNumber);
    List<Product> getAllProducts();
    boolean isValidState(String state);
    Order createOrder(String date, String customerName, String state, String productType, BigDecimal area)
            throws IllegalArgumentException;
    void addOrder(Order order);
    void saveOrder(Order updatedOrder);
    boolean removeOrder(String date, int orderNumber);
    void exportData();
}
