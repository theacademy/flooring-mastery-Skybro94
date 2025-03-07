package com.sg.flooringmastery.ui;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }


    @Override
    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readString(prompt));
            } catch (NumberFormatException e) {
                print("Invalid input. Please enter a valid integer.");
            }
        }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int value;
        do {
            value = readInt(prompt + " (" + min + " - " + max + "): ");
        } while (value < min || value > max);
        return value;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            try {
                return new BigDecimal(readString(prompt));
            } catch (NumberFormatException e) {
                print("Invalid input. Please enter a valid decimal number.");
            }
        }
    }
}
