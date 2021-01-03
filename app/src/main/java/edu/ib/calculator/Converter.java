package edu.ib.calculator;

/**
 * A class that changes double to a string, removing unnecessary 0.
 * @author Nikolina Czart
 */

public class Converter {
    public static String getStringCalculateValue(double calculateValue){
        int temp = (int) calculateValue;

        if(calculateValue - temp == 0)
            return String.valueOf(temp);

        return String.valueOf(calculateValue);
    }
}

