/*
 * The MIT License
 *
 * Copyright 2015 michaeldowdle.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package Checker;

import Tasks.AssignmentInCondition;
import Tasks.UninitialisedVariable;
import Utils.CommentStripper;
import Utils.FileHandler;
import Utils.WarningArray;
import java.io.IOException;

/**
 *
 * @author michaeldowdle
 */
public class Checker {

    //global sourceCodeFile so commonCommands can use it for validation
    public static String sourceCodeFile = "";

    /**
     *
     * main method takes a single .c source code file as input parameter, run
     * comment stripper against the file, then runs all available checks
     * populating a warning array.
     *
     * Finally displays all details of the warnings found, and cleans up any
     * files left hanging around.
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            WarningArray wa = new WarningArray();

            //get files from user input
            if (args.length != 1) {
                System.out.println("Please enter source code file as argument");
                return;
            }
            sourceCodeFile = args[0];
            String commentlessSourceCode = sourceCodeFile + "_commentless";

            //strip comments from code
            CommentStripper cStripper
                    = new CommentStripper(sourceCodeFile, commentlessSourceCode);
            //System.out.println("strip out comments");
            cStripper.Strip();

            //set up assignment in condition task
            AssignmentInCondition aICCheck
                    = new AssignmentInCondition(commentlessSourceCode);
            //run task
            //System.out.println("check for assignment in condition");
            wa.appendWVA(aICCheck.checkForSingleAssignment());

            //set up uninitialised variable task
            UninitialisedVariable uVCheck
                    = new UninitialisedVariable(commentlessSourceCode);
            //run task
            //System.out.println("check for uninitialised variable");
            wa.appendWVA(uVCheck.checkForUninitialisedVariable());

            //display warnings
            wa.getWarnings().stream().forEach((warning) -> {
                System.out.println("Warning!\n" + warning);
            });

            //clean up temporary files
            FileHandler.deleteFile(commentlessSourceCode);
        } catch (IOException ex) {
            System.err.println("Error: Failed to handle file");
        } catch (CloneNotSupportedException ex) {
            System.err.println("Error: Failed to handle warnings");
        }

    }
}
