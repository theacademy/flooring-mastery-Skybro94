package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        while (true) {
            String input = io.readString("Enter order date (MMDDYYYY): ").trim();
            try {
                LocalDate date = LocalDate.parse(input, formatter);
                return input;
            } catch (DateTimeParseException e) {
                io.print("Invalid date format. Please enter in MMDDYYYY format.");
            }
        }
    }

    public String getFutureDateInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        while (true) {
            String input = io.readString("Enter order date (MMDDYYYY): ").trim();
            try {
                LocalDate date = LocalDate.parse(input, formatter);
                if (date.isAfter(LocalDate.now())) {
                    return input;
                } else {
                    io.print("Error: The date must be in the future.");
                }
            } catch (DateTimeParseException e) {
                io.print("Invalid date format. Please enter in MMDDYYYY format.");
            }
        }
    }

    public String getValidatedCustomerName() {
        while (true) {
            String input = io.readString("Enter customer name: ").trim();
            if (input.matches("[a-zA-Z0-9., ]+")) {
                return input;
            } else {
                io.print("Invalid name. Only letters, numbers, commas, and periods are allowed.");
            }
        }
    }

    public String getStateInput(List<String> validStates) {
        while (true) {
            String state = io.readString("Enter state abbreviation (e.g., CA, TX): ").trim().toUpperCase();
            if (validStates.contains(state)) {
                return state;
            } else {
                io.print("Error: We cannot sell in this state. Please enter a valid state.");
            }
        }
    }

    public BigDecimal getValidatedAreaInput() {
        while (true) {
            BigDecimal area = io.readBigDecimal("Enter area (minimum 100 sqft): ");
            if (area.compareTo(new BigDecimal("100")) >= 0) {
                return area;
            } else {
                io.print("Error: Minimum order size is 100 sqft.");
            }
        }
    }

    public String getProductChoice(List<Product> productList) {
        displayProductList(productList);
        int choice = io.readInt("Enter the number of your chosen product: ", 1, productList.size());
        return productList.get(choice - 1).getProductType();
    }

    public void displayProductList(List<Product> productList) {
        io.print("Available Products:");
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            io.print((i + 1) + ". " + product.getProductType() +
                    " | Cost per sqft: $" + product.getCostPerSquareFoot() +
                    " | Labor cost per sqft: $" + product.getLaborCostPerSquareFoot());
        }
    }

    public Order getUpdatedOrder(Order existingOrder, List<Product> productList) {
        io.print("Editing Order #" + existingOrder.getOrderNumber());
        io.print("Press Enter to keep existing values.");

        String customerName = io.readString("Enter customer name (" + existingOrder.getCustomerName() + "): ");
        if (customerName.trim().isEmpty()) {
            customerName = existingOrder.getCustomerName();
        }

        String state = io.readString("Enter state abbreviation (" + existingOrder.getState() + "): ");
        if (state.trim().isEmpty()) {
            state = existingOrder.getState();
        }

        displayProductList(productList);
        int productChoice = io.readInt("Enter product choice (" + existingOrder.getProductType() + "): ", 1, productList.size());
        String productType = productList.get(productChoice - 1).getProductType();

        String areaInput = io.readString("Enter area (" + existingOrder.getArea() + " sqft): ");
        BigDecimal area = areaInput.trim().isEmpty() ? existingOrder.getArea() : new BigDecimal(areaInput);

        return new Order(
                existingOrder.getOrderNumber(),
                existingOrder.getDate(),
                customerName,
                state,
                existingOrder.getTaxRate(),
                productType,
                area,
                existingOrder.getCostPerSquareFoot(),
                existingOrder.getLaborCostPerSquareFoot(),
                BigDecimal.ZERO, // MaterialCost
                BigDecimal.ZERO, // LaborCost
                BigDecimal.ZERO, // Tax
                BigDecimal.ZERO  // Total
        );
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
