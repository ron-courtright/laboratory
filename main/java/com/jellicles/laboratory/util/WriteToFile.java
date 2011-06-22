package com.jellicles.laboratory.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteToFile {

    /**
     * Write a string to a file on the users desktop.
     * 
     * @param args
     *            No arguments used.
     * @author ron
     */
    public static void main(String[] args) {
        String myPattern = new String("foo");
        byte[] b = myPattern.getBytes();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(b);
            String path = "/Users/ron/Desktop/Output.txt";
            File file = new File(path);
            file.createNewFile();
            FileOutputStream foStream = new FileOutputStream(file);
            baos.writeTo(foStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
