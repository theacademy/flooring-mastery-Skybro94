package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.util.List;

public interface OrderDao {
    List<Order> getOrders(String date);
    void addOrder(Order order);
    Order getOrder(String date, int orderNumber);
    int getNextOrderNumber(String date);
    void updateOrder(int orderNumber, Order order);
    boolean removeOrder(String date, int orderNumber);
    void exportData();
}
