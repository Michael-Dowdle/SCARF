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

import java.util.LinkedList;

/**
 * ScopeVariableArray class to handle variables of particular scopes
 * 
 * @author michaeldowdle
 */
public class ScopeVariableArray {

    private LinkedList<Variable> variableList;

    /**
     * ScopeVariableArray constructor
     */
    public ScopeVariableArray() {
        variableList = new LinkedList();
    }

    /**
     * ScopeVariableArray constructor cloning from a pre existing SVA
     * @param parentList
     * @throws CloneNotSupportedException 
     */
    public ScopeVariableArray(ScopeVariableArray parentList) throws CloneNotSupportedException {
        super();
        //clone the elements of the parentList
        for (Variable variable : parentList.variableList) {
            variableList.add(variable.clone());
        }
    }

    /**
     * 
     * @return 
     */
    public LinkedList<Variable> getVariableList() {
        return variableList;
    }

    /**
     * 
     * @param name
     * @param type
     * @param decLineNo 
     */
    public void addVariable(String name, String type, int decLineNo) {
        variableList.add(new Variable(name, type, decLineNo));
    }
    
    /**
     * 
     * @param name
     * @param type
     * @param decLineNo
     * @param init 
     */
    public void addVariable(String name, String type, int decLineNo, boolean init) {
        variableList.add(new Variable(name, type, decLineNo, init));
    }

    /**
     * 
     * @param name 
     */
    public void initialiseVariable(String name) {
        variableList.stream().filter((variable) 
                -> (variable.getName().equals(name))).forEach((variable) -> {
            variable.setInit(true);
        });
    }

    /**
     * 
     * @param name
     * @return 
     */
    public boolean containsVariable(String name) {
        boolean containsVariable = false;
        for (Variable variable : variableList) {
            if (variable.getName().equals(name)) {
                containsVariable = true;
            }
        }
        return containsVariable;
    }

    /**
     * 
     * @param name
     * @return 
     */
    public boolean isVariableInitialised(String name) {
        boolean init = false;
        for (Variable variable : variableList) {
            if (variable.getName().equals(name) && variable.isInit()) {
                init = true;
            }
        }
        return init;
    }
}
