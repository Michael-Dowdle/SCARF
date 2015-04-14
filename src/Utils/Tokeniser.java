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
package Utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokeniser class to handle reading and organising Tokens using 
 * java.io.StreamTokenizer, incorporating TokenTypes, TokenInfos and TokenIDs.
 * 
 * @author michaeldowdle
 */
public class Tokeniser {

    FileInputStream scfis;
    BufferedReader reader;
    StreamTokenizer st;

    /**
     * TokenType Subclass handling tokenType details
     */
    private class TokenType {

        public final String view;
        public final Pattern regex;
        public final int tokenType, tokenID;

        /**
         * 
         * @param view
         * @param regex
         * @param tokenType
         * @param tokenID 
         */
        public TokenType(String view, Pattern regex, int tokenType, int tokenID) {
            super();
            this.view = view;
            this.regex = regex;
            this.tokenType = tokenType;
            this.tokenID = tokenID;
        }
    }

    /**
     * TokenInfo subclass handling TokenInfo details
     */
    private class TokenInfo {

        /**
         * 
         */
        public static final int KEYWORD = 0,
                IDENTIFIER = 1,
                CONSTANT = 2,
                STRING = 3,
                SPECIAL_SYMBOL = 4,
                OPERATOR = 5;
    }

    /**
     * TokenID subclass handling tokenID details
     */
    public class TokenID {

        /**
         * Global IDs for different tokens
         */
        public static final int EQUALS = '=',
                EOF = StreamTokenizer.TT_EOF,
                EOL = StreamTokenizer.TT_EOL,
                OPEN_SCOPE_BRACKET = '{',
                CLOSE_SCOPE_BRACKET = '}',
                OPEN_BRACKET = '(',
                CLOSE_BRACKET = ')',
                SEMICOLON = ';',
                CONST_NUMBER = -2,
                CONST_CHAR = 34,
                CONST_STRING = 39,
                COMMA = ',',
                MINUS = '-';
    }

    private final LinkedList<TokenType> tokenTypes;

    /**
     * Primary constructor used internally by createTokeniser
     * 
     * @param file
     * @throws FileNotFoundException 
     */
    private Tokeniser(String file) throws FileNotFoundException {
        super();
        tokenTypes = new LinkedList<>();
        scfis = new FileInputStream(file);
        reader = new BufferedReader(new InputStreamReader(scfis));
        st = new StreamTokenizer(reader);
    }

    /**
     * createTokeniser used to create a new Tokeniser object
     * 
     * @param file
     * @return Tokeniser instance
     * @throws FileNotFoundException 
     */
    public static Tokeniser createTokeniser(String file) throws FileNotFoundException {
        Tokeniser tokeniser = new Tokeniser(file);

        //keywords
//        tokeniser.add("auto", TokenInfo.KEYWORD, -3);
//        tokeniser.add("break", TokenInfo.KEYWORD, -3);
//        tokeniser.add("case", TokenInfo.KEYWORD, -3);
//        tokeniser.add("char", TokenInfo.KEYWORD, -3);
//        tokeniser.add("const", TokenInfo.KEYWORD, -3);
//        tokeniser.add("continue", TokenInfo.KEYWORD, -3);
//        tokeniser.add("default", TokenInfo.KEYWORD, -3);
//        tokeniser.add("do", TokenInfo.KEYWORD, -3);
//        tokeniser.add("double", TokenInfo.KEYWORD, -3);
//        tokeniser.add("else", TokenInfo.KEYWORD, -3);
//        tokeniser.add("enum", TokenInfo.KEYWORD, -3);
//        tokeniser.add("extern", TokenInfo.KEYWORD, -3);
//        tokeniser.add("float", TokenInfo.KEYWORD, -3);
//        tokeniser.add("for", TokenInfo.KEYWORD, -3);
//        tokeniser.add("goto", TokenInfo.KEYWORD, -3);
//        tokeniser.add("if", TokenInfo.KEYWORD, -3);
//        tokeniser.add("int", TokenInfo.KEYWORD, -3);
//        tokeniser.add("long", TokenInfo.KEYWORD, -3);
//        tokeniser.add("register", TokenInfo.KEYWORD, -3);
//        tokeniser.add("return", TokenInfo.KEYWORD, -3);
//        tokeniser.add("short", TokenInfo.KEYWORD, -3);
//        tokeniser.add("signed", TokenInfo.KEYWORD, -3);
//        tokeniser.add("sizeof", TokenInfo.KEYWORD, -3);
//        tokeniser.add("static", TokenInfo.KEYWORD, -3);
//        tokeniser.add("struct", TokenInfo.KEYWORD, -3);
//        tokeniser.add("switch", TokenInfo.KEYWORD, -3);
//        tokeniser.add("typedef", TokenInfo.KEYWORD, -3);
//        tokeniser.add("union", TokenInfo.KEYWORD, -3);
//        tokeniser.add("unsigned", TokenInfo.KEYWORD, -3);
//        tokeniser.add("void", TokenInfo.KEYWORD, -3);
//        tokeniser.add("volatile", TokenInfo.KEYWORD, -3);
//        tokeniser.add("while", TokenInfo.KEYWORD, -3);

        tokeniser.add("=", TokenInfo.OPERATOR, TokenID.EQUALS);
        tokeniser.add("\\{", TokenInfo.SPECIAL_SYMBOL, TokenID.OPEN_SCOPE_BRACKET);
        tokeniser.add("\\}", TokenInfo.SPECIAL_SYMBOL, TokenID.CLOSE_SCOPE_BRACKET);
        tokeniser.add("\\(", TokenInfo.SPECIAL_SYMBOL, TokenID.OPEN_BRACKET);
        tokeniser.add("\\)", TokenInfo.SPECIAL_SYMBOL, TokenID.CLOSE_BRACKET);
        
        tokeniser.add(";", TokenInfo.SPECIAL_SYMBOL, TokenID.SEMICOLON);
        tokeniser.add(",", TokenInfo.SPECIAL_SYMBOL, TokenID.COMMA);
        tokeniser.add("-", TokenInfo.OPERATOR, TokenID.MINUS);
        
        return tokeniser;
    }

    /**
     * add a new token type
     * @param regex
     * @param tokenType
     * @param tokenID 
     */
    public void add(String regex, int tokenType, int tokenID) {
        tokenTypes.add(
                new TokenType(
                        regex, Pattern.compile("^(" + regex + ")"), tokenType, tokenID));
    }

    /**
     * get the next token from the Buffered reader
     * 
     * @return
     * @throws IOException 
     */
    public Token nextToken() throws IOException {
        st.nextToken();
        String type = "", name;
        // find out if token value is string or value
        if (st.sval != null) {
            name = st.sval;
        } else {
            name = String.valueOf(st.nval);
        }

        //find out what kind of token this is from ttype id
        boolean match = false;
        for (TokenType tokentype : tokenTypes) {
            Matcher m;
            m = tokentype.regex.matcher(name);

            if (m.find()) {
                match = true;
                type = tokentype.regex.toString();
                break;
            }
        }

        if (!match) {
            //look through the token.type ids for a matching symbol
            for (TokenType tokenType : tokenTypes ) {
                if (st.ttype == tokenType.tokenID) {
                    name = tokenType.view;
                }
            }
        }

        return new Token(type, name, st.ttype, st.lineno());
    }
}
