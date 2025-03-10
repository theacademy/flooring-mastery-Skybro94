package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.OrderDao;
import com.sg.flooringmastery.dao.ProductDao;
import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
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
    public List<String> getAllValidStates() {
        return taxDao.getAllStates();
    }

    @Override
    public Order getOrder(String date, int orderNumber) {
        return orderDao.getOrder(date, orderNumber);
    }


    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public boolean isValidState(String state) {
        return taxDao.getTaxRate(state) != null;
    }

    @Override
    public Order createOrder(String date, String customerName, String state, String productType, BigDecimal area) {

        Tax taxData = taxDao.getTaxRate(state);
        if (taxData == null) {
            throw new IllegalArgumentException("Error: We cannot sell in " + state + ".");
        }
        BigDecimal taxRate = taxData.getTaxRate();

        Product product = productDao.getProduct(productType);
        if (product == null) {
            throw new IllegalArgumentException("Error: Product type '" + productType + "' does not exist.");
        }

        BigDecimal costPerSquareFoot = product.getCostPerSquareFoot();
        BigDecimal laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();
        BigDecimal materialCost = area.multiply(costPerSquareFoot);
        BigDecimal laborCost = area.multiply(laborCostPerSquareFoot);
        BigDecimal tax = (materialCost.add(laborCost)).multiply(taxRate).divide(new BigDecimal("100"));
        BigDecimal total = materialCost.add(laborCost).add(tax);

        int newOrderNumber = orderDao.getNextOrderNumber(date);

        return new Order(newOrderNumber, date, customerName, state, taxRate, productType, area,
                costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, total);
    }

    @Override
    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }

    @Override
    public void saveOrder(Order updatedOrder) {
        updatedOrder.calculateCosts();
        orderDao.updateOrder(updatedOrder.getOrderNumber(), updatedOrder);
    }

    @Override
    public boolean removeOrder(String date, int orderNumber) {
        return orderDao.removeOrder(date, orderNumber);
    }

    @Override
    public void exportData() {
        orderDao.exportData();
    }
}
