/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tasks;

import Utils.FileHandler;
import Utils.WarningArray;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michaeldowdle
 */
public class AssignmentInConditionTest {
    
    String testFile, testSourceCode;
    
    public AssignmentInConditionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of checkForSingleAssignment method, of class AssignmentInCondition.
     */
    @Test
    public void testCheckForSingleAssignment() throws Exception {
        System.out.println("checkForSingleAssignment");
        
        //test assignment in condition check
        System.out.println("    Assignment In Condition");
        /**
         * Step	Action
         * 1	Ensure that the system environment is healthy and program is 
         *      running normally
         * 2	Input Source Code containing an ‘Assignment Inside Condition’ 
         *      bug
         * 3	Request to Analyse Source Code
         * 
         * Expected Observation
         * Program accepts source code correctly
         * Program correctly analyses code and accurately identifies and 
         * displays the details of the ‘Assignment Inside Condition’ bug
         */
        testFile = "assignment_in_condition_test.c";
        testSourceCode = "if ( a = 1 )\n";
        testSourceCode += "{ a = b ; }";
        FileHandler.StringToFile(testSourceCode, testFile);
        AssignmentInCondition instance = new AssignmentInCondition(testFile);
        
        int expResult_lineNo = 1;
        String expResult_error = "Assignment In Condition Found";
        String expResult_description = "variable";
        
        WarningArray result = instance.checkForSingleAssignment();
        
        assertEquals(expResult_lineNo, 
                result.getWarnings().get(0).getLineNo());
        assertEquals(expResult_error, 
                result.getWarnings().get(0).getError());
        assertEquals(expResult_description, 
                result.getWarnings().get(0).getDescription());
        
        FileHandler.deleteFile(testFile);
    }

    /**
     * Test of getCondition method, of class AssignmentInCondition.
     * @throws java.io.IOException
     */
    @Test
    public void testGetCondition() throws IOException {
        System.out.println("getCondition");
        
        System.out.println("    single brackets");
        String line = "if ( a = 1 ) { a = b ; }";
        String expResult = " a = 1 ";
        AssignmentInCondition instance = new AssignmentInCondition("temp");
        String result = instance.getCondition(line);
        assertEquals(expResult, result);
        
        System.out.println("    two brackets");
        line = "if ( ( a = 1 ) ) { a = b ; }";
        expResult = " ( a = 1 ) ";
        result = instance.getCondition(line);
        assertEquals(expResult, result);
    }
    
}
