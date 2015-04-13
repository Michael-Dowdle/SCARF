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
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author michaeldowdle
 */
public class CommentStripper {

    private String state = "SOURCECODE";
    private String sourceCodeFile = "";
    private String commentFreeSourceCodeFile = "";

    /**
     * 
     * @param sourceCodeFile
     * @param commentFreeSourceCodeFile 
     */
    public CommentStripper(String sourceCodeFile, String commentFreeSourceCodeFile) {
        this.sourceCodeFile = sourceCodeFile;
        this.commentFreeSourceCodeFile = commentFreeSourceCodeFile;
    }

    /**
     * 
     * @return 
     */
    public String getSourceCodeFile() {
        return sourceCodeFile;
    }

    /**
     * 
     * @param sourceCodeFile 
     */
    public void setSourceCodeFile(String sourceCodeFile) {
        this.sourceCodeFile = sourceCodeFile;
    }

    /**
     * 
     * @return 
     */
    public String getCommentFreeSourceCodeFile() {
        return commentFreeSourceCodeFile;
    }

    /**
     * 
     * @param commentFreeSourceCodeFile 
     */
    public void setCommentFreeSourceCodeFile(String commentFreeSourceCodeFile) {
        this.commentFreeSourceCodeFile = commentFreeSourceCodeFile;
    }

    /**
     * 
     * @throws IOException 
     */
    public void Strip() throws IOException {

        String sourceCode;
        try ( //read in file to string stream
                FileInputStream scfis = new FileInputStream(sourceCodeFile);
                BufferedReader reader = new BufferedReader(new InputStreamReader(scfis))) {

            sourceCode = "";
            int charVal;
            while ((charVal = reader.read()) != -1) {
                char c = (char) charVal;
                switch (state) {
                    case "SOURCECODE":
                        if (c == '/') {
                            state = "FORWARDSLASH";
                        } else if (c == '"') {
                            state = "QUOTE";
                            sourceCode += "\"";
                        } else {
                            sourceCode += c;
                        }
                        break;

                    case "FORWARDSLASH":
                        if (c == '*') {
                            state = "MULTICOMMENT";
                        } else if (c == '/') {
                            state = "LINECOMMENT";
                        } else {
                            state = "SOURCECODE";
                            sourceCode += ("/" + c);
                        }
                        break;

                    case "ASTERISK":
                        if (c == '/') {
                            state = "SOURCECODE";
                        } else if (c != '*') {
                            state = "MULTICOMMENT";
                            if (c == '\n') {
                                sourceCode += c;
                            }
                        }
                        break;

                    case "MULTICOMMENT":
                        if (c == '*') {
                            state = "ASTERISK";
                        } else if (c == '\n') {
                            sourceCode += c;
                        }
                        break;

                    case "LINECOMMENT":
                        if (c == '\n') {
                            state = "SOURCECODE";
                            sourceCode += c;
                        }
                        break;

                    case "QUOTE":
                        if (c == '"') {
                            state = "SOURCECODE";
                        } else if (c == '\\') {
                            state = "ESCAPE";
                        }
                        sourceCode += c;
                        break;

                    case "ESCAPE":
                        state = "QUOTE";
                        sourceCode += c;
                        break;
                }
            }
            //write commentless code to file
            FileHandler.StringToFile(sourceCode, commentFreeSourceCodeFile);
        }
    }
}
