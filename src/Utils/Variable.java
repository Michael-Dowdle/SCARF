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
 * Variable class to handle variable details as objects
 * @author michaeldowdle
 */
public class Variable implements Cloneable {

    private final String name;
    private final String type;
    private final int decLineNo;
    private boolean init;

    /**
     * 
     * @param name
     * @param type
     * @param decLineNo 
     */
    public Variable(String name, String type, int decLineNo) {
        this.name = name;
        this.type = type;
        this.decLineNo = decLineNo;
        this.init = false;
    }

    /**
     * 
     * @param name
     * @param type
     * @param decLineNo
     * @param init 
     */
    public Variable(String name, String type, int decLineNo, boolean init) {
        this.name = name;
        this.type = type;
        this.decLineNo = decLineNo;
        this.init = init;
    }

    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return 
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @return 
     */
    public int getDecLineNo() {
        return decLineNo;
    }

    /**
     * 
     * @return 
     */
    public boolean isInit() {
        return init;
    }

    /**
     * 
     * @param init 
     */
    public void setInit(boolean init) {
        this.init = init;
    }

    /**
     * 
     * @return
     * @throws CloneNotSupportedException 
     */
    @Override
    public Variable clone() throws CloneNotSupportedException {
        return (Variable) super.clone();
    }
}
