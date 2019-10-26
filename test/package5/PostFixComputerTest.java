package package5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.EmptyStackException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bryce Harmsen
 * @since 11-21-2018
 */
public class PostFixComputerTest {

    /**
     * Test of postCalculator method, of class PostFixComputer.
     */
    @Test
    public void testPostCalculator() {
        System.out.println("postCalculator");
        Double result;
        PostFixComputer instance = new PostFixComputer();
        
        String[] postFixElements = {"3.0", "2.0", "+"};
        result = instance.postCalculator(postFixElements);
        Double expResult = 5.0;
        assertEquals(expResult, result);
        
        String[] postFixElements2 = {"1.0", "1.0", "-"};
        result = instance.postCalculator(postFixElements2);
        expResult = 0.0;
        assertEquals(expResult, result);
        
        String[] postFixElements3 = {"2.0", "1.0", "*"};
        result = instance.postCalculator(postFixElements3);
        expResult = 2.0;
        assertEquals(expResult, result);
        
        String[] postFixElements4 = {"4.0", "1.0", "/"};
        result = instance.postCalculator(postFixElements4);
        expResult = 4.0;
        assertEquals(expResult, result);
        
        String[] postFixElements5 = {"0.0"};
        result = instance.postCalculator(postFixElements5);
        expResult = 0.0;
        assertEquals(expResult, result);
    }
    
     /**
     * Test of methods, of class PostFixComputer.
     * @throws java.io.FileNotFoundException
     */
    @Test(expected = NumberFormatException.class)
    public void testNumberFormatException() throws FileNotFoundException {
        System.out.println("NumberFormatException");
        File expressionFile = new File("badNumberFormat.txt");
        PostFixComputer instance = new PostFixComputer();
        String[] result = instance.fileReader(expressionFile);
        instance.postCalculator(result);
    }
    
     /**
     * Test of methods, of class PostFixComputer.
     * @throws java.io.FileNotFoundException
     */
    @Test(expected = EmptyStackException.class)
    public void testEmptyStackException() throws FileNotFoundException {
        System.out.println("EmptyStackException");
        File expressionFile = new File("tooManyOperators.txt");
        PostFixComputer instance = new PostFixComputer();
        String[] result = instance.fileReader(expressionFile);
        instance.postCalculator(result);
    }
    
     /**
     * Test of methods, of class PostFixComputer.
     * @throws java.io.FileNotFoundException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() throws FileNotFoundException {
        System.out.println("IllegalArgumentException");
        File expressionFile = new File("divideByZero.txt");
        PostFixComputer instance = new PostFixComputer();
        String[] result = instance.fileReader(expressionFile);
        instance.postCalculator(result);
    }
    
         /**
     * Test of fileReader method, of class PostFixComputer.
     * @throws java.io.FileNotFoundException
     */
    @Test
    public void testFileReader() throws FileNotFoundException  {
        System.out.println("fileReader");
        File expressionFile = new File("testExpression.txt");
        PostFixComputer instance = new PostFixComputer();
        String[] result = instance.fileReader(expressionFile);
        String[] expResult = {"this", "is", "a", "test"};
        assertArrayEquals(expResult, result);
    }
}
