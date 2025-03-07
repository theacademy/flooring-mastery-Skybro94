package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.*;

public class OrderDaoFileImpl implements OrderDao {
    private static final String ORDERS_FOLDER = "src/main/resources/Orders/";
    private final Map<String, List<Order>> orders = new HashMap<>();

    public OrderDaoFileImpl() {
        loadAllOrders();
    }

    @Override
    public List<Order> getOrders(String date) {
        return orders.getOrDefault(date, new ArrayList<>());
    }

    @Override
    public void addOrder(Order order) {
        String date = order.getDate();
        List<Order> orderList = orders.getOrDefault(date, new ArrayList<>());

        int newOrderNumber = orderList.stream().mapToInt(Order::getOrderNumber).max().orElse(0) + 1;
        order.setOrderNumber(newOrderNumber);

        orderList.add(order);
        orders.put(date, orderList);
        saveOrders(date);
    }

    @Override
    public void updateOrder(int orderNumber, Order updatedOrder) {
        String date = updatedOrder.getDate();
        List<Order> orderList = orders.getOrDefault(date, new ArrayList<>());

        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNumber() == orderNumber) {
                orderList.set(i, updatedOrder);
                saveOrders(date);
                return;
            }
        }
    }

    @Override
    public boolean removeOrder(String date, int orderNumber) {
        List<Order> orderList = orders.get(date);

        // if no order at this date, let Controller handle
        if (orderList == null || orderList.isEmpty()) {
            return false;
        }

        Order orderToRemove = null;
        for (Order order : orderList) {
            if (order.getOrderNumber() == orderNumber) {
                orderToRemove = order;
                break;
            }
        }

        // if order doesn't exist, let Controller handle
        if (orderToRemove == null) {
            return false;
        }

        orderList.remove(orderToRemove);

        if (orderList.isEmpty()) {
            orders.remove(date);
        }

        saveOrders(date);
        return true;
    }


    @Override
    public void exportData() {
        String exportFile = "src/main/resources/Data/Backup/DataExport.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(exportFile))) {
            writer.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate");
            for (String date : orders.keySet()) {
                for (Order order : orders.get(date)) {
                    writer.println(order.toCsv() + "," + date);
                }
            }
        } catch (IOException e) {
            System.err.println("Error exporting data: " + e.getMessage());
        }
    }

    private void loadAllOrders() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(ORDERS_FOLDER), "Orders_*.txt")) {
            for (Path filePath : stream) {
                String filename = filePath.getFileName().toString();
                String date = filename.substring(7, 15); // MMDDYYYY

                List<Order> orderList = new ArrayList<>();
                try (Scanner scanner = new Scanner(filePath.toFile())) {
                    scanner.nextLine();
                    while (scanner.hasNextLine()) {
                        orderList.add(Order.fromCsv(scanner.nextLine(), date));
                    }
                }
                orders.put(date, orderList);
            }
        } catch (IOException e) {
            System.err.println("Error loading orders: " + e.getMessage());
        }
    }

    private void saveOrders(String date) {
        String filename = ORDERS_FOLDER + "Orders_" + date + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
            for (Order order : orders.get(date)) {
                writer.println(order.toCsv());
            }
        } catch (IOException e) {
            System.err.println("Error saving orders: " + e.getMessage());
        }
    }
}
