package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class TaxDaoFileImpl implements TaxDao {
    private static final String TAX_FILE = "src/main/resources/Data/Taxes.txt";
    private final Map<String, Tax> taxes = new HashMap<>();

    public TaxDaoFileImpl() {
        loadTaxes();
    }

    @Override
    public Tax getTaxRate(String state) {
        return taxes.get(state);
    }

    private void loadTaxes() {
        try (Scanner scanner = new Scanner(new File(TAX_FILE))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(",");
                String stateAbbreviation = tokens[0];
                String stateName = tokens[1];
                BigDecimal taxRate = new BigDecimal(tokens[2]);

                Tax tax = new Tax(stateAbbreviation, stateName, taxRate);
                taxes.put(stateAbbreviation, tax);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Tax file not found.");
        }
    }

    @Override
    public List<String> getAllStates() {
        return new ArrayList<>(taxes.keySet());  // return all `stateAbbreviation`
    }


}
