package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;

public interface TaxDao {
    Tax getTaxRate(String state);
}
