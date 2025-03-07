package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dto.Order;
import java.util.List;

public interface FlooringMasteryService {
    List<Order> getOrders(String date);
    void addOrder(Order order);
    void editOrder(int orderNumber, Order order);
    boolean removeOrder(String date, int orderNumber);
    void exportData();
}
