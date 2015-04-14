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

/**
 * Warning class to handle warning details as objects
 * @author michaeldowdle
 */
public class Warning implements Cloneable {

    private int lineNo;
    private String error, description;

    /**
     * 
     * @param lineNo
     * @param error
     * @param description 
     */
    public Warning(int lineNo, String error, String description) {
        this.lineNo = lineNo;
        this.error = error;
        this.description = description;
    }

    /**
     * 
     * @return 
     */
    public int getLineNo() {
        return lineNo;
    }

    /**
     * 
     * @param lineNo 
     */
    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    /**
     * 
     * @return 
     */
    public String getError() {
        return error;
    }

    /**
     * 
     * @param error 
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * 
     * @return 
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     * @throws CloneNotSupportedException 
     */
    @Override
    public Warning clone() throws CloneNotSupportedException {
        return (Warning) super.clone();
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "\tError Type:\t" + error + "\n\tDescription:\t" 
                + description + "\n\tLine Number:\t" + lineNo + "\n";
    }

}
