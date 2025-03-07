package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.OrderDao;
import com.sg.flooringmastery.dao.ProductDao;
import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.dto.Order;
import java.util.List;

public class FlooringMasteryServiceImpl implements FlooringMasteryService {
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final TaxDao taxDao;

    public FlooringMasteryServiceImpl(OrderDao orderDao, ProductDao productDao, TaxDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public List<Order> getOrders(String date) {
        return orderDao.getOrders(date);
    }

    @Override
    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }

    @Override
    public void editOrder(int orderNumber, Order order) {
        orderDao.updateOrder(orderNumber, order);
    }

    @Override
    public boolean removeOrder(String date, int orderNumber) {
        orderDao.removeOrder(date,orderNumber);
        return false;
    }

    @Override
    public void exportData() {
        orderDao.exportData();
    }
}
