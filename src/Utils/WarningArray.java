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

import java.util.ArrayList;

/**
 * WarningArray class to handle a collection of warning objects
 * @author michaeldowdle
 */
public class WarningArray {
    
    private ArrayList<Warning> warnings;

    public WarningArray() {
        warnings = new ArrayList();
    }

    /**
     * 
     * @param warnings 
     */
    public WarningArray(ArrayList<Warning> warnings) {
        this.warnings = warnings;
    }
    
    /**
     * 
     * @param WA
     * @throws CloneNotSupportedException 
     */
    public void appendWVA(WarningArray WA) throws CloneNotSupportedException {
        for (Warning warning : WA.warnings) {
            this.warnings.add(warning.clone());
        }
    }
    
    /**
     * 
     * @param lineNo
     * @param error
     * @param description 
     */
    public void addWarning(int lineNo, String error, String description) {
        warnings.add(new Warning(lineNo, error, description));
    }
    
    /**
     * 
     * @param warning
     * @throws CloneNotSupportedException 
     */
    public void addWarning(Warning warning) throws CloneNotSupportedException {
        warnings.add(warning.clone());
    }

    /**
     * 
     * @return 
     */
    public ArrayList<Warning> getWarnings() {
        return warnings;
    }
    
    
}
