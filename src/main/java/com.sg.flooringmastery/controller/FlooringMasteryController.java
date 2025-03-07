package com.sg.flooringmastery.controller;

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
        int orderNumber = view.getOrderNumber();
        service.removeOrder(orderNumber);
    }

    private void exportData() {
        service.exportData();
        view.displayMessage("Data exported successfully.");
    }
}
