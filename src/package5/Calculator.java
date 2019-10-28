/*
 * Project: CS 480 calculator
 * Author: Bryce Harmsen                        
 * Creation date: 10/26/2019
 */
package package5;

import java.util.Scanner;
import java.util.Stack;
import java.util.EmptyStackException;
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
       
    //methods
    public Double inFixCalculator(String[] inFixElements) {
        for (String elem : inFixElements) {
            try {
                stack.push(Double.valueOf(elem));
            } catch (NumberFormatException nfe) {
                if (elem.equals(")")) {
                    calculateUntil("(");
                } else {
                    String prevOperator = operators.size() > 0 ? operators.peek() : "-";
                    if (PRECEDENCE_ORDER.get(elem) <= PRECEDENCE_ORDER.get(prevOperator)) {
                        calculateUntil(elem);
                    }
                    operators.push(elem);
                }
            }
        }
        
        while (!operators.isEmpty()) {
            Double right = stack.pop();
            Double left = stack.pop();
            String operator = operators.pop();
            stack.push(calculate(left, right, operator));
        }
        
        return stack.pop();
    }
    
    public void calculateUntil(String precedenceFloor) {
        Double left, right;
        String operator = "";
        while(!operators.isEmpty() && PRECEDENCE_ORDER.get(operator = operators.peek())
                >= PRECEDENCE_ORDER.get(precedenceFloor)) {
            operators.pop();
            right = stack.pop();
            left = stack.pop();
            stack.push(calculate(left, right, operator));
        }
    }
    
    public Double calculate(double left, double right, String operator) {
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
    
    /**
     * Runs an instance of the class, calling methods to facilitate the calculator
     * function. This method also catches the exceptions thrown, effectively driving the object.
     */
    public void run() {
        while(true) {
            try {
                System.out.println("Enter an expression to evaluate, " +
                                    "hit enter to calculate: ");
                Scanner keyboard = new Scanner(System.in);
                String[] inFixElements = keyboard.nextLine().split(DELIM);
                Double calculatedValue = inFixCalculator(inFixElements);
                if (!stack.isEmpty())
                    throw new Exception();
                System.out.println(calculatedValue);
            } catch (NumberFormatException e) {
                System.out.println("File should only contain "
                                   + "Doubles, operators, and whitespace.");
            } catch (EmptyStackException e) {
                System.out.println("Post Fix notation "
                                   + "contains errors: Too many operators");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getLocalizedMessage());
            } catch (Exception e) {
                System.out.println("Post Fix notation "
                                   + "contains errors: Exception cause undetermined.\n"
                                   + "Trace:");
                for(StackTraceElement line : e.getStackTrace()) {
                    System.out.println("\t" + line.toString());
                }
            } finally {
                stack.clear();
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Calculator program = new Calculator();
        program.run();
    }
}