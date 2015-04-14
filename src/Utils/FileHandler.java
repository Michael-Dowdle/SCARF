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

import Checker.Checker;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * FileHandler class to handle writting and reading from files
 *
 * @author michaeldowdle
 */
public class FileHandler {

    /**
     *
     * @param fileName
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String FileToString(String fileName) throws FileNotFoundException, IOException {
        String contents = "";

        //read in file to string stream
        FileInputStream scfis = new FileInputStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(scfis));

        //get first line of string stream
        int lineNo = 1;
        String line = reader.readLine();
        while (line != null) {

            contents += line;

            line = reader.readLine();
            lineNo++;
        }
        return contents;
    }

    /**
     *
     * @param data
     * @param fileName
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void StringToFile(String data, String fileName) throws FileNotFoundException, IOException {
        //write String to File
        try (FileOutputStream scfos = new FileOutputStream(fileName);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(scfos))) {
            writer.write(data);
        }
    }

    /**
     *
     * @param path
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    /**
     *
     * @param fileName
     * @throws IOException
     */
    public static void deleteFile(String fileName) throws IOException {

        //check file is not orginal source code file
        if (!fileName.equals(Checker.sourceCodeFile)) {
            //delete file
            Files.deleteIfExists(FileSystems.getDefault().getPath(fileName));
        }
    }
}
