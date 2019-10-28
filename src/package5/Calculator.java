/*
 * Project: CS 480 calculator
 * Author: Bryce Harmsen                        
 * Creation date: 10/26/2019
 */
package package5;

import java.util.Stack;
import java.util.HashMap;

/**
 *
 * @author Bryce Harmsen
 * @since 10-26-2019
 */
public class Calculator {
    //fields
    private Stack<Double> stack;
    private Stack<String> operators;
    private final String DELIM = " ";
    private final HashMap<String, Integer> PRECEDENCE_ORDER;
    
    //constructors
    /**
     * No-arg constructor initializes the stack.
     */
    public Calculator() {
        stack = new Stack<>();
        operators = new Stack<>();
        PRECEDENCE_ORDER = getPrecedenceOrder();
    }
    
    //methods
    public String getDelim() {
        return DELIM;
    }
    
    public boolean hasOperands() {
        return !stack.isEmpty();
    }
    
    public boolean hasOperators() {
        return !operators.isEmpty();
    }
    
    public void reset() {
        stack.clear();
        operators.clear();
    }
    
    private HashMap<String, Integer> getPrecedenceOrder() {
        HashMap<String, Integer> precedenceOrder = new HashMap<>();
        precedenceOrder.put("(", 4);
        precedenceOrder.put("^", 3);
        precedenceOrder.put("*", 2);
        precedenceOrder.put("/", 2);
        precedenceOrder.put("+", 1);
        precedenceOrder.put("-", 1);
        return precedenceOrder;
    }
       
    public Double calculate(String[] inFixElements) {
        for (String elem : inFixElements) {
            try {
                stack.push(Double.valueOf(elem));
            } catch (NumberFormatException nfe) {
                String prevOperator = operators.size() > 0 ? operators.peek() : "-";
                if (PRECEDENCE_ORDER.get(elem) <= PRECEDENCE_ORDER.get(prevOperator)) {
                    calculateUntil(elem);
                }
                operators.push(elem);
            }
        }
        
        while (!operators.isEmpty()) {
            Double right = stack.pop();
            Double left = stack.pop();
            String operator = operators.pop();
            stack.push(calculateOnce(left, right, operator));
        }
        Double result = stack.pop();

        return result;
    }
    
    private void calculateUntil(String precedenceFloor) {
        Double left, right;
        String operator = "";
        while(!operators.isEmpty() && PRECEDENCE_ORDER.get(operator = operators.peek())
                >= PRECEDENCE_ORDER.get(precedenceFloor)) {
            operators.pop();
            right = stack.pop();
            left = stack.pop();
            stack.push(calculateOnce(left, right, operator));
        }
    }
    
    private Double calculateOnce(double left, double right, String operator) {
        double result;
        switch(operator) {
            case "+":
                result = left + right;
                break;
            case "-":
                result = left - right;
                break;
            case "*":
                result = left * right;
                break;
            case "/":
                if (right == 0)
                    throw new IllegalArgumentException("Dividing by zero is not allowed.");
                result = left / right;
                break;
            case "^":
                result = Math.pow(left, right);
                break;
            default:
                throw new IllegalArgumentException("A bad operator was passed.");
        }
        
        return result;
    }
}