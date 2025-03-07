package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;

import java.math.BigDecimal;
import java.util.List;

public class FlooringMasteryView {
    private final UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int displayMenu() {
        io.print("*****************************************");
        io.print("* <<Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Quit");
        io.print("*****************************************");
        return io.readInt("Please select an option: ", 1, 6);
    }

    public String getDateInput() {
        return io.readString("Enter order date (MMDDYYYY): ");
    }

    public Order getOrderDetails() {
        String customerName = io.readString("Enter customer name: ");
        String state = io.readString("Enter state abbreviation: ");
        String productType = io.readString("Enter product type: ");
        BigDecimal area = io.readBigDecimal("Enter area (minimum 100 sqft): ");
        while (area.compareTo(new BigDecimal("100")) < 0) {
            io.print("Area must be at least 100 sqft.");
            area = io.readBigDecimal("Enter area: ");
        }
        return new Order(0, customerName, state, BigDecimal.ZERO, productType, area, BigDecimal.ZERO, BigDecimal.ZERO, "");
    }

    public int getOrderNumber() {
        return io.readInt("Enter order number: ");
    }

    public void displayOrders(List<Order> orders) {
        if (orders.isEmpty()) {
            io.print("No orders found for this date.");
            return;
        }
        for (Order order : orders) {
            io.print("Order #" + order.getOrderNumber() + " | Customer: " + order.getCustomerName() + " | Product: " + order.getProductType());
        }
    }

    public void displayMessage(String msg) {
        io.print(msg);
    }

    public boolean getConfirmation(String message) {
        return io.readString(message + " (Y/N): ").trim().equalsIgnoreCase("Y");
    }

    public void displayOrder(Order order) {
        io.print("Order #" + order.getOrderNumber() + " | Customer: " + order.getCustomerName() +
                " | Product: " + order.getProductType() + " | Total: $" + order.getTotal());
    }

}
