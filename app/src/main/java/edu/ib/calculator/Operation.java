package edu.ib.calculator;

/**
 * Enum describing the activity data
 * @author Nikolina Czart
 */

public enum Operation {
    ADD(false,"+", (a,b) -> a+b),
    SUB(false,"-", (a,b) -> a-b),
    MUL(false, "*", (a,b) -> a*b),
    DIV(false,"/", (a,b) -> a/b),
    PER(true, "%", (a, b) -> a/100),
    INV(true, "+/-", (a,b) -> (-1)*a),
    EQU(true, "=", (a,b) -> a);

    private CalculationMethod method;
    private String sign;
    private boolean isOneArgumentOperation;

    private Operation(boolean isOneArgumentOperation, String sign, CalculationMethod method){
        this.method = method;
        this.sign = sign;
        this.isOneArgumentOperation = isOneArgumentOperation;
    }

    public CalculationMethod getCalculationMethod(){
        return method;
    }

    public static Operation fromString(String text) {
        for (Operation operation : Operation.values()) {
            if (operation.sign.equals(text)) {
                return operation;
            }
        }
        return null;
    }

    public boolean isOneArgumentOperation() {
        return isOneArgumentOperation;
    }

    @Override
    public String toString() {
        return sign;
    }
}
