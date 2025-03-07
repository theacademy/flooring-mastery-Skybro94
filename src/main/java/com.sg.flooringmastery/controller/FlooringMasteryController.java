package com.sg.flooringmastery.controller;

import java.util.List;
import com.sg.flooringmastery.dto.Order;
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
        view.displayOrders(service.getOrders(date));
    }

    private void addOrder() {
        service.addOrder(view.getOrderDetails());
    }

    private void editOrder() {
        int orderNumber = view.getOrderNumber();
        service.editOrder(orderNumber, view.getOrderDetails());
    }

    private void removeOrder() {
        String date = view.getDateInput();
        int orderNumber = view.getOrderNumber();

        // get orders
        List<Order> orders = service.getOrders(date);
        Order orderToRemove = null;
        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                orderToRemove = order;
                break;
            }
        }

        if (orderToRemove == null) {
            view.displayMessage("Order #" + orderNumber + " not found on " + date + ". Returning to main menu...");
            return;
        }

        view.displayMessage("Order found:");
        view.displayOrder(orderToRemove);
        boolean confirm = view.getConfirmation("Are you sure you want to remove this order? (Y/N)");

        if (!confirm) {
            view.displayMessage("Order removal cancelled. Returning to main menu...");
            return;
        }

        // call DAO to delete the order
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
