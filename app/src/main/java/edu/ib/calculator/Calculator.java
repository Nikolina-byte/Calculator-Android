package edu.ib.calculator;

import java.util.ArrayList;

/**
 * Class that calculates and stores the last calculations.
 * @author Nikolina Czart
 */

public class Calculator {
    private ArrayList<Double> numbers;
    private ArrayList<Operation> operations;
    private Operation lastOperation;
    private Double lastNumber;
    private Double lastResult;
    private String progressNumber;


    public Calculator() {
        this.numbers = new ArrayList<>();
        this.operations = new ArrayList<>();
        this.progressNumber = "";
    }

    public double calculate() {
        Operation firstOperation = operations.get(0);
        this.operations.remove(0);
        double result = calculateFirstResult(firstOperation);
        int numberIndex = firstOperation.isOneArgumentOperation() ? 1 : 2;
        for(Operation operation : operations){
            if(operation.equals(Operation.EQU)){
                break;
            }
            result = operation.getCalculationMethod().calculate(result, this.numbers.get(numberIndex));
            numberIndex++;
        }
        this.lastResult = result;
        return result;
    }

    public void clearCalculation(){
        this.operations.clear();
        this.numbers.clear();
        this.progressNumber = "";
    }

    private double calculateFirstResult(Operation operation) {
        if(operation == null){
            return 0;
        }
        else if(operation.isOneArgumentOperation()){
            return operation.getCalculationMethod().calculate(this.numbers.get(0), 0);
        }
        return operation.getCalculationMethod().calculate(this.numbers.get(0), this.numbers.get(1));
    }

    public String getCalculationInfo(){
        StringBuilder result = new StringBuilder();
        int numberIndex = 0;
        for(Operation operation : operations){
            result.append(Converter.getStringCalculateValue(this.numbers.get(numberIndex)));
            result.append(operation);
            if(operation.equals(Operation.EQU)){
                break;
            }
            numberIndex++;
        }

        return result.toString();
    }

    public void addOperations(Operation operation){
        if(!operation.isOneArgumentOperation()){
            lastOperation=operation;
        }
        this.operations.add(operation);
    }

    public void addProgressNumber(String alternative){
        if(this.progressNumber.isEmpty()){
            this.lastNumber = Double.parseDouble(alternative);
            this.numbers.add(lastNumber);
        }
        else{
            this.lastNumber = Double.parseDouble(this.progressNumber);
            this.numbers.add(lastNumber);
            this.progressNumber = "";
        }
    }

    public void setProgressNumber(String progressNumber) {
        this.progressNumber = progressNumber;
    }

    public String getProgressNumber() {
        return progressNumber.isEmpty() ? "0" : progressNumber;
    }

    public void concatProgressNumber(String digit){
        if(validateDigit(digit)){
            this.progressNumber += digit;
        }
    }

    public void initialWithLastOperation(){
        this.numbers.add(lastResult);
        this.numbers.add(lastNumber);
        if(lastOperation == null){
            this.operations.add(Operation.EQU);
        }
        else {
            this.operations.add(lastOperation);
        }
    }

    public void executeOperation(Operation operation){
        double number = 0;
        if(!this.progressNumber.isEmpty()){
            number = Double.parseDouble(this.progressNumber);
        }
        else if(lastResult != null){
            number = lastResult;
        }
        double result = operation.getCalculationMethod().calculate(number, 1);
        this.progressNumber = Converter.getStringCalculateValue(result);
    }

    private boolean validateDigit(String digit){
        if(".".equals(digit)){
            if(this.progressNumber.isEmpty()){
                this.progressNumber += "0";
                return true;
            }
            else if(this.progressNumber.contains(digit)){
                return false;
            }
        }
        if("0".equals(digit) && this.progressNumber.isEmpty()){
            return false;
        }
        return true;
    }
}