package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class ProductDaoFileImpl implements ProductDao {
    private static final String PRODUCT_FILE = "src/main/resources/Data/Products.txt";
    private final Map<String, Product> products = new HashMap<>();

    public ProductDaoFileImpl() {
        loadProducts();
    }

    @Override
    public Product getProduct(String productType) {
        return products.get(productType);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    private void loadProducts() {
        try (Scanner scanner = new Scanner(new File(PRODUCT_FILE))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(",");
                String productType = tokens[0];
                BigDecimal costPerSquareFoot = new BigDecimal(tokens[1]);
                BigDecimal laborCostPerSquareFoot = new BigDecimal(tokens[2]);

                Product product = new Product(productType, costPerSquareFoot, laborCostPerSquareFoot);
                products.put(productType, product);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Product file not found.");
        }
    }
}
