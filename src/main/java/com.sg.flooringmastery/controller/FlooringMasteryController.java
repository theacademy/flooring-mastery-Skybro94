package com.sg.flooringmastery.controller;

import java.math.BigDecimal;
import java.util.List;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.service.FlooringMasteryService;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import org.springframework.stereotype.Component;

@Component
public class FlooringMasteryController {
    private final FlooringMasteryService service;
    private final FlooringMasteryView view;

    public FlooringMasteryController(FlooringMasteryService service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        while (keepGoing) {
            int menuSelection = view.displayMenu();
            switch (menuSelection) {
                case 1 -> displayOrders();
                case 2 -> addOrder();
                case 3 -> editOrder();
                case 4 -> removeOrder();
                case 5 -> exportData();
                case 6 -> keepGoing = false;
                default -> view.displayMessage("Unknown Command");
            }
        }
        view.displayMessage("Goodbye!");
    }

    private void displayOrders() {
        String date = view.getDateInput();
        List<Order> orders = service.getOrders(date);

        if (orders.isEmpty()) {
            view.displayMessage("No orders found for " + date + ". Returning to main menu...");
            return;
        }

        view.displayOrders(orders);
    }

    private void addOrder() {
        String date = view.getFutureDateInput();
        String customerName = view.getValidatedCustomerName();

        List<String> validStates = service.getAllValidStates();
        String state = view.getStateInput(validStates);

        List<Product> productList = service.getAllProducts();
        String productType = view.getProductChoice(productList);

        BigDecimal area = view.getValidatedAreaInput();

        Order newOrder = service.createOrder(date, customerName, state, productType, area);

        view.displayOrder(newOrder);
        boolean confirm = view.getConfirmation("Do you want to place this order?");

        if (!confirm) {
            view.displayMessage("Order cancelled. Returning to main menu...");
            return;
        }

        service.addOrder(newOrder);
        view.displayMessage("Order placed successfully!");
    }

    private void editOrder() {
        String date = view.getDateInput();
        int orderNumber = view.getOrderNumber();

        Order existingOrder = service.getOrder(date, orderNumber);
        if (existingOrder == null) {
            view.displayMessage("Order not found. Returning to main menu...");
            return;
        }

        List<Product> productList = service.getAllProducts();
        Order updatedOrder = view.getUpdatedOrder(existingOrder, productList);

        view.displayOrder(updatedOrder);
        boolean confirm = view.getConfirmation("Do you want to save these changes? ");

        if (!confirm) {
            view.displayMessage("Edit cancelled. Returning to main menu...");
            return;
        }

        service.saveOrder(updatedOrder);
        view.displayMessage("Order updated successfully!");
    }

    private void removeOrder() {
        String date = view.getDateInput();
        int orderNumber = view.getOrderNumber();

        Order orderToRemove = service.getOrder(date, orderNumber);

        if (orderToRemove == null) {
            view.displayMessage("Order #" + orderNumber + " not found on " + date + ". Returning to main menu...");
            return;
        }

        view.displayMessage("Order found:");
        view.displayOrder(orderToRemove);
        boolean confirm = view.getConfirmation("Are you sure you want to remove this order?");

        if (!confirm) {
            view.displayMessage("Order removal cancelled. Returning to main menu...");
            return;
        }

        boolean removed = service.removeOrder(date, orderNumber);

        if (removed) {
            view.displayMessage("Order #" + orderNumber + " successfully removed!");
        } else {
            view.displayMessage("Error: Could not remove order. Returning to main menu...");
        }
    }

    private void exportData() {
        service.exportData();
        view.displayMessage("Data exported successfully.");
    }
}
