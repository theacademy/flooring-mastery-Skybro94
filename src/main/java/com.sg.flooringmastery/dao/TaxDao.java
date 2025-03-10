package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.util.List;

public interface TaxDao {
    Tax getTaxRate(String state);
    List<String> getAllStates();

}

