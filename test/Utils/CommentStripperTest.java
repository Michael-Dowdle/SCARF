/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author michaeldowdle
 */
public class CommentStripperTest {

    private CommentStripper instance;
    private String testFile, expFile, resultFile, testSourceCode, expSourceCode, expResult, result;

    public CommentStripperTest() {
    }

    @Before
    public void setUp() throws IOException {
        //test singleline comment stripping
        /**
         * Step	Action 
         * 1	Ensure that the system environment is healthy and
         *      program is running normally 
         * 2	Input Source Code containing a ‘single
         *      line comment’ 
         * 3	Request to Analyse Source Code
         *
         * Expected Observation Program accepts source code correctly Program
         * correctly analyses code and ignores ‘single line comment’
         */
        /*---------------- create singleline test file ----------------*/
        testFile = "strip_Singleline_test.c";

        testSourceCode = "";
        testSourceCode += "#include<stdio.h>\n";                  // #include<stdio.h>
        testSourceCode += "\n";                                   //
        testSourceCode += "main()\n";                             // main()
        testSourceCode += "{\n";                                  // {
        testSourceCode += "\t// this is a singleline comment\n";  //    // this is a single line comment
        testSourceCode += "\tprintf(\"Hello World\"\n";           //    printf("Hello World");
        testSourceCode += "}\n";                                  // }

        FileHandler.StringToFile(testSourceCode, testFile);

        /*---------------- create singleline expect file ----------------*/
        expFile = "strip_Singleline_exp.c";

        expSourceCode = "";
        expSourceCode += "#include<stdio.h>\n";                   // #include<stdio.h>
        expSourceCode += "\n";                                    //
        expSourceCode += "main()\n";                              // main()
        expSourceCode += "{\n";                                   // {
        expSourceCode += "\t\n";                                  // 
        expSourceCode += "\tprintf(\"Hello World\"\n";            //    printf("Hello World");
        expSourceCode += "}\n";                                   // }
        FileHandler.StringToFile(expSourceCode, expFile);

        //test multiline comment stripping
        /**
         * Step	Action 
         * 1	Ensure that the system environment is healthy and
         *      program is running normally 
         * 2	Input Source Code containing a ‘multi line comment’ 
         * 3	Request to Analyse Source Code
         *
         * Expected Observation Program accepts source code correctly Program
         * correctly analyses code and ignores ‘multi line comment’
         */
        /*---------------- create multiline test file ----------------*/
        testFile = "strip_Multiline_test.c";

        testSourceCode = "";
        testSourceCode += "/*\n";                                 // /*
        testSourceCode += "* This is a\n";                        // * This is a
        testSourceCode += "* multiline\n";                        // * multiline
        testSourceCode += "* comment\n";                          // * comment
        testSourceCode += "*/\n";                                 // */
        testSourceCode += "\n";                                   //
        testSourceCode += "#include<stdio.h>\n";                  // #include<stdio.h>
        testSourceCode += "\n";                                   //
        testSourceCode += "main()\n";                             // main()
        testSourceCode += "{\n";                                  // {
        testSourceCode += "\tprintf(\"Hello World\"\n";           //    printf("Hello World");
        testSourceCode += "}\n";                                  // }

        FileHandler.StringToFile(testSourceCode, testFile);

        /*---------------- create multiline expect file ----------------*/
        expFile = "strip_Multiline_exp.c";

        expSourceCode = "";
        expSourceCode += "\n";                                    //
        expSourceCode += "\n";                                    //
        expSourceCode += "\n";                                    //
        expSourceCode += "\n";                                    //
        expSourceCode += "\n";                                    //
        expSourceCode += "\n";                                    //
        expSourceCode += "#include<stdio.h>\n";                   // #include<stdio.h>
        expSourceCode += "\n";                                    //
        expSourceCode += "main()\n";                              // main()
        expSourceCode += "{\n";                                   // {
        expSourceCode += "\tprintf(\"Hello World\"\n";            //     printf("Hello World");
        expSourceCode += "}\n";                                   // }

        FileHandler.StringToFile(expSourceCode, expFile);

        //test escape quote ignoring
        /**
         * Step	Action
         * 1	Ensure that the system environment is healthy and program is 
         *      running normally
         * 2	Input Source Code containing an ‘escape char inside a double 
         *      quote string’
         * 3	Request to Analyse Source Code
         * 
         * Expected Observation
         * Program accepts source code correctly
         * Program correctly analyses code including all double quote strings
         */
        /*---------------- create escape quote test file ----------------*/
        testFile = "strip_Escape_Quote_test.c";

        testSourceCode = "";
        testSourceCode += "#include<stdio.h>\n";                  // #include<stdio.h>
        testSourceCode += "\n";                                   //
        testSourceCode += "main()\n";                             // main()
        testSourceCode += "{\n";                                  // {
        testSourceCode += "\tprintf(\"Hello World /* //\"\n";     //    printf("Hello World /* //");
        testSourceCode += "}\n";                                  // }

        FileHandler.StringToFile(testSourceCode, testFile);

        /*---------------- create escape quote expect file ----------------*/
        expFile = "strip_Escape_Quote_exp.c";

        expSourceCode = "";
        expSourceCode += "#include<stdio.h>\n";                   // #include<stdio.h>
        expSourceCode += "\n";                                    //
        expSourceCode += "main()\n";                              // main()
        expSourceCode += "{\n";                                   // {
        expSourceCode += "\tprintf(\"Hello World /* //\"\n";      //    printf("Hello World /* //");
        expSourceCode += "}\n";                                   // }

        FileHandler.StringToFile(expSourceCode, expFile);
    }

    @After
    public void tearDown() throws IOException {
        //clean up (remove files)
        for (String test : new String[]{"Singleline", "Multiline", "Escape_Quote"}) {
            FileHandler.deleteFile("strip_" + test + "_test.c");
            FileHandler.deleteFile("strip_" + test + "_exp.c");
            FileHandler.deleteFile("strip_" + test + "_result.c");
        }
    }

    /**
     * Test of Strip method, of class CommentStripper.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testStrip() throws Exception {

        //test singleline, multiline and escape quotes
        for (String test : new String[]{"Singleline", "Multiline", "Escape_Quote"}) {

            System.out.println("Strip - " + test);
            testFile = "strip_" + test + "_test.c";
            expFile = "strip_" + test + "_exp.c";
            resultFile = "strip_" + test + "_result.c";
            instance = new CommentStripper(testFile, resultFile);
            instance.Strip();
            expResult = FileHandler.readFile(expFile, StandardCharsets.UTF_8);
            result = FileHandler.readFile(resultFile, StandardCharsets.UTF_8);
            assertEquals(expResult, result);
        }
    }
}
