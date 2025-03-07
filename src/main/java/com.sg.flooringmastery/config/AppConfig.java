package com.sg.flooringmastery.config;

import com.sg.flooringmastery.controller.FlooringMasteryController;
import com.sg.flooringmastery.dao.*;
import com.sg.flooringmastery.service.FlooringMasteryService;
import com.sg.flooringmastery.service.FlooringMasteryServiceImpl;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import com.sg.flooringmastery.ui.UserIO;
import com.sg.flooringmastery.ui.UserIOConsoleImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UserIO userIO() {
        return new UserIOConsoleImpl();
    }

    @Bean
    public FlooringMasteryView view(UserIO io) {
        return new FlooringMasteryView(io);
    }

    @Bean
    public OrderDao orderDao() {
        return new OrderDaoFileImpl();
    }

    @Bean
    public ProductDao productDao() {
        return new ProductDaoFileImpl();
    }

    @Bean
    public TaxDao taxDao() {
        return new TaxDaoFileImpl();
    }

    @Bean
    public FlooringMasteryService service(OrderDao orderDao, ProductDao productDao, TaxDao taxDao) {
        return new FlooringMasteryServiceImpl(orderDao, productDao, taxDao);
    }

    @Bean
    public FlooringMasteryController controller(FlooringMasteryService service, FlooringMasteryView view) {
        return new FlooringMasteryController(service, view);
    }
}
