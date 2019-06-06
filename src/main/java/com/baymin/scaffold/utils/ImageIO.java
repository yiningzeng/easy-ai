package com.baymin.scaffold.utils;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageIO {
    public static void byte2image(byte[] data,String path){
        if(data.length<3||path.equals("")) return;
        try{
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch(Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    public static byte[] image2byte(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[2048];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        }
        catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        }
        catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

    public static String byteToString(byte[] buf){
        String str1="";
        StringBuilder sb=new StringBuilder(str1);
        for (byte element: buf )
        {
            sb.append(element);
        }
        str1=sb.toString();
        return str1;
    }
}
