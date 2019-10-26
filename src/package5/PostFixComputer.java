/*
 * Project: Project 5, post fix calculator
 * Author: Bryce Harmsen                        
 * Creation date: 11/21/2018                                    
 * Completion time: 5 hours
 * Honor Code: I pledge that this program represents my         
 *             own program code.
 */
package package5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.EmptyStackException;
/**
 *
 * @author Bryce Harmsen
 * @since 11-21-2018
 */
public class PostFixComputer {
    //fields
    private Stack<Double> stack;
    private final String DELIM = " ";
    
    //constructors
    /**
     * No-arg constructor initializes the stack.
     */
    public PostFixComputer() {
        stack = new Stack<>();
    }
    
    //methods
    /**
     * Decides which switch statement to perform based on contents of the sTring
     * op. Can read Strings "+", "-", "*", "/", and parseable Doubles. If the
     * op parameter contains an operator symbol, the method will pop the top
     * two values off of the stack, reverse their order, and perform the 
     * designated operation on the operands. If the op parameter contains a 
     * parseable Double, then the method will parse the Double and push it onto
     * the stack.
     * @param op String containing either an operator or an operand (Double).
     * @return Either the result of the operation on the stack values, or the
     *         parsed Double.
     * @throws NumberFormatException If op does not contain either an operator
     *         or a parsable Double
     * @throws EmptyStackException If op contains an operator and the stack
     *         does not contain at least two operands (Doubles).
     * @throws IllegalArgumentException If op = "/" and the 2nd stack value is
     *         0. In other words, this method does not allow to division by
     *         zero.
     */
    public Double switchCalculator(String op)
                        throws NumberFormatException, EmptyStackException,
                               IllegalArgumentException {
        Double operand;
        Double right;
        Double left;
        switch(op) {
            case "+":
                right = stack.pop();
                left = stack.pop();
                operand = left + right;
                break;
            case "-":
                right = stack.pop();
                left = stack.pop();
                operand = left - right;
                break;
            case "*":
                right = stack.pop();
                left = stack.pop();
                operand = left * right;
                break;
            case "/":
                right = stack.pop();
                left = stack.pop();
                if (right == 0)
                    throw new IllegalArgumentException();
                operand = left / right;
                break;
            case "^":
                right = stack.pop();
                left = stack.pop();
                operand = Math.pow(left, right);
                break;
            default:
                operand = Double.valueOf(op);
        }
        return operand;
    }
    
    /**
     * Iteratively passes the Strings from the parameter to the switchCalculator
     * method, then pushes the returned Double onto the stack. After this
     * process is completed for all elements of the parameter, the calculated
     * value is popped off the stack and returned as a Double.
     * @param postFixElements An array of Strings containing operators 
     *        (+, -, *, /) and operands (Doubles) listed in post fix notation.
     *        For example, 1 2 + would be ["1", "2", "+"].
     * @return the solution to the post fix expression as a Double.
     * @throws NumberFormatException If op does not contain either an operator
     *         or a parsable Double
     * @throws EmptyStackException If op contains an operator and the stack
     *         does not contain at least two operands (Doubles).
     * @throws IllegalArgumentException If op = "/" and the 2nd stack value is
     *         0. In other words, this method does not allow to division by
     *         zero.
     */
    public Double postCalculator(String[] postFixElements)
                    throws NumberFormatException, EmptyStackException,
                           IllegalArgumentException {
        for (String element : postFixElements) {
            stack.push(this.switchCalculator(element));
        }
        return stack.pop();
    }
    
    public String[] toPostFix(String[] inFixElements) {
        String[] postFixElements = new String[inFixElements.length];
        //TODO write inFix to postFix conversion here. Handle parenthesis.
        
        return postFixElements;
    }
    
    /**
     * Runs an instance of the class, calling methods and passing the initial
     * file path "expression.txt" to fileReader method. This method also catches
     * the exceptions thrown, effectively driving the object.
     */
    public void run() {
        while(true) {
            try {
                System.out.println("Enter an expression to evaluate, " +
                                    "hit enter to calculate: ");
                Scanner keyboard = new Scanner(System.in);
                String[] inFixElements = keyboard.nextLine().split(DELIM);
                String[] postFixElements = toPostFix(inFixElements);
                Double calculatedValue = this.postCalculator(postFixElements);
                if (!stack.isEmpty())
                    throw new Exception();
                System.out.println(calculatedValue);
            } catch (FileNotFoundException e) {
                System.out.println("File expression.txt should be in same directory "
                         + "as project.");
            } catch (NumberFormatException e) {
                System.out.println("File should only contain "
                                   + "Doubles, operators, and whitespace.");
            } catch (EmptyStackException e) {
                System.out.println("Post Fix notation "
                                   + "contains errors: Too many operators");
            } catch (IllegalArgumentException e) {
                System.out.println("Dividing by zero is not "
                                   + "allowed.");
            } catch (Exception e) {
                System.out.println("Post Fix notation "
                                   + "contains errors: Too many operands.");
            } finally {
                stack.clear();
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PostFixComputer program = new PostFixComputer();
        program.run();
    }
}