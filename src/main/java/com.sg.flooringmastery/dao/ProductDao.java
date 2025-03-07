package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.util.List;

public interface ProductDao {
    Product getProduct(String productType);
    List<Product> getAllProducts();
}
