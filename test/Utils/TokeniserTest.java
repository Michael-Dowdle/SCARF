/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michaeldowdle
 */
public class TokeniserTest {

    
    String testFile, testSourceCode;
    Tokeniser instance;
    Token token;
        
    public TokeniserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of nextToken method, of class Tokeniser.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testNextToken() throws Exception {
        System.out.println("nextToken");

        
        //test simple singleline statement
        System.out.println("    singleline");
        /**
         * statement    'if ( this == that ) { int variable = 4 ; }'
         *
         * tokens       "if", "\\(", "this", "=", "=", "that", "\\)", "\\{", 
         *              "int", "variable", "=", "4.0", ";", "\\}".
         */
        testFile = "singleline_statement_test.c";
        testSourceCode = "if ( this == that ) { int variable = 4 ; }";
        FileHandler.StringToFile(testSourceCode, testFile);
        instance = Tokeniser.createTokeniser(testFile);

        for (String tok : new String[]{"if", "\\(", "this", "=", "=", "that", 
            "\\)", "\\{", "int", "variable", "=", "4.0", ";", "\\}"}) {
            String expResult = tok;
            token = instance.nextToken();
            String result = token.getName();
            assertEquals(expResult, result);
        }
        FileHandler.deleteFile(testFile);

        
        //test simple multiline statement
        System.out.println("    multiline");
        /**
         * statement    'if ( this == that ) {'
         *              '   int variable = 4 ;'
         *              '}                    '
         * 
         * tokens       "if", "\\(", "this", "=", "=", "that", "\\)", "\\{", 
         *              "int", "variable", "=", "4.0", ";", "\\}".
         */
        testFile = "singleline_statement_test.c";
        testSourceCode = "if ( this == that ) {\n";
        testSourceCode += "int variable = 4 ; }";
        FileHandler.StringToFile(testSourceCode, testFile);
        instance = Tokeniser.createTokeniser(testFile);

        for (String tok : new String[]{"if", "\\(", "this", "=", "=", "that", 
            "\\)", "\\{", "int", "variable", "=", "4.0", ";", "\\}"}) {
            String expResult = tok;
            token = instance.nextToken();
            String result = token.getName();
            assertEquals(expResult, result);
        }
        FileHandler.deleteFile(testFile);
    }
}
