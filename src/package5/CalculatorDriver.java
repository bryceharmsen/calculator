/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package5;

import java.util.EmptyStackException;
import java.util.Scanner;

/**
 *
 * @author bryce
 */
public class CalculatorDriver {
    
    private Calculator calculator;
    private final String EXIT = "x";
    
    public CalculatorDriver() {
        calculator = new Calculator();
    }
    
    /**
     * Runs an instance of the class, calling methods to facilitate the calculator
     * function. This method also catches the exceptions thrown, effectively driving the object.
     */
    public void run() {
        String userInput = "";
        while(!userInput.equals(EXIT)) {
            try {
                System.out.println("Enter an expression to evaluate, " +
                                    "hit enter to calculate (or x to exit): ");
                Scanner keyboard = new Scanner(System.in);
                userInput = keyboard.nextLine();
                if (userInput.equals(EXIT))
                    continue;
                String[] inFixElements = userInput.split(calculator.getDelim());
                Double calculatedValue = calculator.calculate(inFixElements);
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
                calculator.reset();
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CalculatorDriver program = new CalculatorDriver();
        program.run();
    }
}
