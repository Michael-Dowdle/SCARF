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
package Tasks;

import Utils.WarningArray;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * class to handling checking for assignment in a condition
 * 
 * @author michaeldowdle
 */
public class AssignmentInCondition extends Task {

    BufferedReader reader;
    int lineNo = 0;
    int bracketDepth = 0;

    /**
     * AssignmentInCondition constructor taking path for source code file
     * as parameters
     * 
     * @param sourceCodeFile 
     */
    public AssignmentInCondition(String sourceCodeFile) {
        this.sourceCodeFile = sourceCodeFile;
    }

    /**
     * 
     * main checking method for the assignment in condition class, 
     * sets up warning array checks line by line of source code for an 'if'
     * 
     * @return WarningArray
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public WarningArray checkForSingleAssignment() throws FileNotFoundException, IOException {

        //read in file to string stream
        FileInputStream scfis = new FileInputStream(sourceCodeFile);
        reader = new BufferedReader(new InputStreamReader(scfis));

        //set up warning array
        WarningArray wa = new WarningArray();
        String errorDescription = "Assignment In Condition";

        //get first line of string stream
        lineNo = 1;
        String line = reader.readLine();
        while (line != null) {
            //System.out.println("checking next line:\t" + lineNo);
            
            //check for an if statement
            if (checkForKeyword("if(", line, lineNo)
                    || checkForKeyword("if (", line, lineNo)) {
                String condition = getCondition(line);

                //check for an assignment in condition
                if (condition.contains("=")) {
                    if (condition.charAt(condition.indexOf("=") - 1) != '!'
                            && condition.charAt(condition.indexOf("=") - 1) != '<'
                            && condition.charAt(condition.indexOf("=") - 1) != '>') {
                        if (condition.charAt(condition.indexOf("=") + 1) != '=') {
                            //create warning for assignment in condition
                            wa.addWarning(lineNo, errorDescription, condition);
                        }
                    }
                }
            }

            //get next line of string stream
            line = reader.readLine();
            lineNo++;
        } //while not end of file
        return wa;
    }

    /**
     * checkForKeyword method to check for a particular String inside the 
     * String source code line of the file
     * 
     * @param keyword
     * @param line
     * @param lineNo
     * @return boolean true if the line contains the String keyword
     */
    public boolean checkForKeyword(String keyword, String line, int lineNo) {

        boolean keywordFound = false;
        if (line.contains(keyword)) {
            keywordFound = true;
        }
        return keywordFound;
    }

    /**
     * 
     * getMatchingCloseBrace recursively check for a matching close bracket
     * 
     * @param line
     * @return lineLocation of the close bracket,
     *          if condition doesn't end of this line, method returns a 0
     * 
     * @throws IOException 
     */
    public int getMatchingCloseBrace(String line) throws IOException {

        int CloseBraceLocation = 1;
        while (!line.isEmpty()) {
            if (line.charAt(0) == '(' && line.length() != 1) {
                //find an open brace, recurse check for matching close brace

                int subCloseBraceLocation = 0;

                //keep checking if statement runs over multiple lines
                while (subCloseBraceLocation == 0) {

                    bracketDepth++;
                    subCloseBraceLocation = getMatchingCloseBrace(line.substring(1));
                    bracketDepth--;
                    if (subCloseBraceLocation == 0) {
                        //end of statement must be on a new line
                        return 0;
                    }
                }

                line = line.substring(subCloseBraceLocation);
                CloseBraceLocation += subCloseBraceLocation;
            } else if (line.charAt(0) == ')') {
                if (bracketDepth != 0 && line.length() == 1) {
                    //end of statement must be on a new line
                    return 0;
                } else {
                    //return location of closing brace
                    return ++CloseBraceLocation;
                }
            } else {
                //remove first char (non brace) from line
                CloseBraceLocation++;
                if (line.length() != 1) {
                    line = line.substring(1);
                } else {
                    //end of statement must be on a new line
                    return 0;
                }
            }
        }

        return -1;
    }

    /**
     * getCondition method to get the entire condition
     * 
     * @param line
     * @return String condition
     * @throws IOException 
     */
    public String getCondition(String line) throws IOException {

        String condition = line.substring(line.indexOf("if"));
        condition = condition.replaceFirst("if", "");

        //strip away blank spaces
        while (condition.charAt(0) == ' ') {
            condition = condition.substring(1);
        }
        condition = condition.substring(1);
        int finalBraceLocation = 0;

        //keep checking if statement runs over multiple lines
        while (finalBraceLocation == 0) {

            finalBraceLocation = getMatchingCloseBrace(condition);
            if (finalBraceLocation == 0) {
                //end of statement must be on a new line
                line = reader.readLine();
                lineNo++;
                condition += line;
            }
        }
        condition = condition.substring(0, finalBraceLocation - 2);

        return condition;
    }
}
