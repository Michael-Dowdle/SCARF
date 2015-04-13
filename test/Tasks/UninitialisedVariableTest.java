/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tasks;

import Utils.FileHandler;
import Utils.WarningArray;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michaeldowdle
 */
public class UninitialisedVariableTest {
    
    String testFile, testSourceCode;
    
    public UninitialisedVariableTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of checkForUninitialisedVariable method, of class UninitialisedVariable.
     */
    @Test
    public void testCheckForUninitialisedVariable() throws Exception {
        System.out.println("checkForUninitialisedVariable");
        
        //test uninitialised variable check
        /**
         * Step	Action
         * 1	Ensure that the system environment is healthy and program is 
         *      running normally
         * 2	Input Source Code containing an ‘Uninitialised Variable’ bug
         * 3	Request to Analyse Source Code
         * 
         * Expected Observation
         * Program accepts source code correctly
         * Program correctly analyses code and accurately identifies and 
         * displays the details of the ‘Initialised Variable’ bug
         */
        testFile = "uninitialised_variable_test.c";
        testSourceCode = "int variable ;\n";
        testSourceCode += "if ( variable == 2 ) \n";
        testSourceCode += "{ variable = 1 ; }";
        FileHandler.StringToFile(testSourceCode, testFile);
        UninitialisedVariable instance = new UninitialisedVariable(testFile);
        
        int expResult_lineNo = 2;
        String expResult_error = "Uninitialised Variable Found";
        String expResult_description = "variable";
        
        WarningArray result = instance.checkForUninitialisedVariable();
        
        assertEquals(expResult_lineNo, 
                result.getWarnings().get(0).getLineNo());
        assertEquals(expResult_error, 
                result.getWarnings().get(0).getError());
        assertEquals(expResult_description, 
                result.getWarnings().get(0).getDescription());
        
        FileHandler.deleteFile(testFile);
    }

    /**
     * Test of findEndOfScope method, of class UninitialisedVariable.
     */
    @Test
    public void testFindEndOfScope() throws Exception {
//        System.out.println("findEndOfScope");
//        ScopeVariableArray parentSVA = null;
//        UninitialisedVariable instance = null;
//        WarningArray expResult = null;
//        WarningArray result = instance.findEndOfScope(parentSVA);
//        assertEquals(expResult, result);
//        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of handleDeclaredVariable method, of class UninitialisedVariable.
     */
    @Test
    public void testHandleDeclaredVariable() throws Exception {
//        System.out.println("handleDeclaredVariable");
//        ScopeVariableArray currentSVA = null;
//        UninitialisedVariable instance = null;
//        instance.handleDeclaredVariable(currentSVA);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
