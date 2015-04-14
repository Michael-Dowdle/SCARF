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

import Utils.ScopeVariableArray;
import Utils.WarningArray;
import Utils.Token;
import Utils.Tokeniser;
import static Utils.Tokeniser.createTokeniser;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * UninitialisedVariable class to handle task of checking for uninitialised
 * variable being used
 *
 * @author michaeldowdle
 */
public class UninitialisedVariable extends Task {

    //set up tokeniser
    private Tokeniser tokeniser;
    private Token token;
    private final String errorDescription = "Uninitialised Variable";

    /**
     * UninitialisedVariable constructor, takes source code file path as
     * parameter
     *
     * @param sourceCodeFile
     */
    public UninitialisedVariable(String sourceCodeFile) {
        this.sourceCodeFile = sourceCodeFile;
    }

    /**
     * checkForUninitialisedVariable method, main method to check for first
     * instance of an open scope bracket
     *
     * @return WarningArray with any warnings found in source code
     * @throws FileNotFoundException
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public WarningArray checkForUninitialisedVariable()
            throws FileNotFoundException, IOException,
            CloneNotSupportedException {

        //get source code file contents
        ScopeVariableArray sva = new ScopeVariableArray();
        WarningArray wa = new WarningArray();
        tokeniser = createTokeniser(sourceCodeFile);
//
//        do { //loop (read each token at a time)
//            token = tokeniser.nextToken();
//
//            //handle global stuff
//            if (Tokeniser.TokenID.OPEN_SCOPE_BRACKET == token.getId()) { // check for open scope bracket
        wa.appendWVA(findEndOfScope(sva));
//            }
//        } while (Tokeniser.TokenID.EOF != token.getId()); // until end of file
        return wa;
    }

    /**
     * find open scope bracket create a childSVA from currentSVA and adding any
     * parameterised variables to it run recursive function (find matching close
     * bracket) pass childSVA and sourceCode as parameters
     *
     * append returnedWVA to currentWVA
     *
     * @param parentSVA
     * @return
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public WarningArray findEndOfScope(ScopeVariableArray parentSVA)
            throws IOException, CloneNotSupportedException {

        ScopeVariableArray currentSVA = parentSVA;
        WarningArray currentWA = new WarningArray();

        do { //loop (read each word at a time)
            token = tokeniser.nextToken();

            switch (token.getId()) {
                case Tokeniser.TokenID.OPEN_SCOPE_BRACKET:   // open scope bracket

                    currentWA.appendWVA(findEndOfScope(currentSVA));
                    break;
                case -3: // keyword

                    if ("int".equals(token.getName())
                            || "bool".equals(token.getName())
                            || "char".equals(token.getName())) { //check for a primitive data type

                        handleDeclarationStatement(currentSVA);

                    } else if (currentSVA.containsVariable(token.getName())) { //check if known variable

                        handleKnownVariableStatement(currentSVA, currentWA);

                    }
                    break;
                case Tokeniser.TokenID.CONST_NUMBER: // constant (number)
                case Tokeniser.TokenID.CONST_CHAR: // constant (char)
                case Tokeniser.TokenID.CONST_STRING: // constant (String)

                    handleConstantStatement(currentSVA, currentWA);

                    break;
                case Tokeniser.TokenID.CLOSE_SCOPE_BRACKET: // close scope bracket
                default:

                    break;
            }
        } while (Tokeniser.TokenID.EOF != token.getId());
        return currentWA;
    }

    /**
     * handleDeclarationStatement 
     * 
     * @param currentSVA
     * @throws IOException 
     */
    public void handleDeclarationStatement(ScopeVariableArray currentSVA) throws IOException {
        //handle declared variable
        handleDeclaredVariable(currentSVA);

        while (Tokeniser.TokenID.SEMICOLON != token.getId()) { // check if not end of statement
            if (Tokeniser.TokenID.COMMA == token.getId()) { // check for another declared variable

                //handle declared variable
                handleDeclaredVariable(currentSVA);
            }
            token = tokeniser.nextToken();
        }
    }

    /**
     * handleKnownVariableStatement
     * 
     * @param currentSVA
     * @param currentWA
     * @throws IOException 
     */
    public void handleKnownVariableStatement(ScopeVariableArray currentSVA, WarningArray currentWA) throws IOException {
        //save variable
        Token varToken = token;

        token = tokeniser.nextToken();

        if (Tokeniser.TokenID.EQUALS == token.getId()) { //check for equals symbol

            token = tokeniser.nextToken();

            if (Tokeniser.TokenID.EQUALS == token.getId()) { // check if variable being used

                if (!currentSVA.isVariableInitialised(varToken.getName())) { // check if variables uninitialised

                    //add variable warning to currentWVA
                    currentWA.addWarning(varToken.getLineNo(), errorDescription, "variable 1 [" + varToken.getName() + " " + varToken.getType() + "  " + varToken.getId() + "]");
                }

                token = tokeniser.nextToken();

                //save variable
                varToken = token;

                if (currentSVA.containsVariable(varToken.getName())) { // check if known variable
                    if (!currentSVA.isVariableInitialised(varToken.getName())) { // check if variables uninitialised
                        //add variable warning to currentWVA
                        currentWA.addWarning(varToken.getLineNo(), errorDescription, "variable 2 [" + varToken.getName() + " " + varToken.getType() + "  " + varToken.getId() + "]");
                    }
                }
            } else { // variable being initialised

                //change variable status to initialised
                currentSVA.initialiseVariable(varToken.getName());
            }
        }
    }

    /**
     * handleConstantStatement
     * 
     * @param currentSVA
     * @param currentWA
     * @throws IOException 
     */
    public void handleConstantStatement(ScopeVariableArray currentSVA, WarningArray currentWA) throws IOException {

        Token varToken;
        token = tokeniser.nextToken();

        if (Tokeniser.TokenID.EQUALS == token.getId()) { //check for equals symbol
            token = tokeniser.nextToken();

            if (Tokeniser.TokenID.EQUALS == token.getId()) { //check for equals symbol
                token = tokeniser.nextToken();

                varToken = token;

                if (currentSVA.containsVariable(varToken.getName())) { // check if known variable
                    if (!currentSVA.isVariableInitialised(varToken.getName())) { // check if variables uninitialised

                        //add variable warning to currentWVA
                        currentWA.addWarning(varToken.getLineNo(), errorDescription, "variable 3 [" + varToken.getName() + " " + varToken.getType() + "  " + varToken.getId() + "]");
                    }
                }
            }
        }
    }

    /**
     * handleDeclaredVariable
     * 
     * @param currentSVA
     * @throws IOException 
     */
    public void handleDeclaredVariable(ScopeVariableArray currentSVA) throws IOException {

        Token typeToken = token;
        token = tokeniser.nextToken();
        Token varToken = token;

        //add token to currentSVA
        currentSVA.addVariable(varToken.getName(), typeToken.getName(), varToken.getLineNo());
        token = tokeniser.nextToken();

        //check if token is initialised
        if (Tokeniser.TokenID.EQUALS == token.getId()) { //check for equals symbol

            //get initialise value
            token = tokeniser.nextToken();

            //change variable status to initialised
            currentSVA.initialiseVariable(varToken.getName());
        }
    }
}
