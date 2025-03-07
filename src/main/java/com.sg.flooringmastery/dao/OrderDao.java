package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.util.List;

public interface OrderDao {
    List<Order> getOrders(String date);
    void addOrder(Order order);
    void updateOrder(int orderNumber, Order order);
    boolean removeOrder(String date, int orderNumber);
    void exportData();
}
