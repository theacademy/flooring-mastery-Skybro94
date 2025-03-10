package com.sg.flooringmastery;
import static org.mockito.Mockito.*;


import com.sg.flooringmastery.dao.OrderDao;
import com.sg.flooringmastery.dao.ProductDao;
import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import com.sg.flooringmastery.service.FlooringMasteryService;
import com.sg.flooringmastery.service.FlooringMasteryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringMasteryServiceImplTest {
    private FlooringMasteryService service;
    private OrderDao orderDao;
    private ProductDao productDao;
    private TaxDao taxDao;

    @BeforeEach
    void setUp() {
        orderDao = mock(OrderDao.class);
        productDao = mock(ProductDao.class);
        taxDao = mock(TaxDao.class);
        service = new FlooringMasteryServiceImpl(orderDao, productDao, taxDao);
    }

    @Test
    void testGetOrders() {
        Order mockOrder = new Order(
                1, "06032023",  // orderNumber, date
                "Alice", "TX", new BigDecimal("6.25"),  // customerName, state, taxRate
                "Tile", new BigDecimal("200"),  // productType, area
                new BigDecimal("3.50"), new BigDecimal("4.15"), // costPerSquareFoot, laborCostPerSquareFoot
                new BigDecimal("700"), new BigDecimal("500"),   // materialCost, laborCost
                new BigDecimal("75"), new BigDecimal("1275")    // tax, total
        );

        when(orderDao.getOrders("06032023")).thenReturn(List.of(mockOrder));

        List<Order> orders = service.getOrders("06032023");

        assertFalse(orders.isEmpty(), "Orders list should not be empty");
        assertEquals(1, orders.get(0).getOrderNumber(), "Order number mismatch");
        assertEquals("Alice", orders.get(0).getCustomerName(), "Customer name mismatch");
        assertEquals("TX", orders.get(0).getState(), "State mismatch");
        assertEquals(new BigDecimal("6.25"), orders.get(0).getTaxRate(), "Tax rate mismatch");
        assertEquals("Tile", orders.get(0).getProductType(), "Product type mismatch");
        assertEquals(new BigDecimal("1275"), orders.get(0).getTotal(), "Total price mismatch");
    }



    @Test
    void testCreateOrder() {
        when(taxDao.getTaxRate("TX")).thenReturn(new Tax("TX", "Texas", new BigDecimal("6.25")));
        when(productDao.getProduct("Tile")).thenReturn(new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15")));
        when(orderDao.getNextOrderNumber("06042023")).thenReturn(1);

        Order newOrder = service.createOrder("06042023", "Bob", "TX", "Tile", new BigDecimal("200"));

        assertNotNull(newOrder);
        assertEquals(1, newOrder.getOrderNumber());
        assertEquals("Bob", newOrder.getCustomerName());
    }

    @Test
    void testRemoveOrder() {
        when(orderDao.getOrders("06052023")).thenReturn(Arrays.asList(
                new Order(1, "06052023",  // orderNumber, date
                        "Charlie", "CA", new BigDecimal("7.50"),  // customerName, state, taxRate
                        "Wood", new BigDecimal("150"),  // productType, area
                        new BigDecimal("5.50"), new BigDecimal("6.00"),  // costPerSquareFoot, laborCostPerSquareFoot
                        new BigDecimal("825"), new BigDecimal("900"),  // materialCost, laborCost
                        new BigDecimal("127.50"), new BigDecimal("1852.50")) // tax, total
        ));

        service.removeOrder("06052023", 1);
        verify(orderDao, times(1)).removeOrder("06052023", 1);
    }

}
