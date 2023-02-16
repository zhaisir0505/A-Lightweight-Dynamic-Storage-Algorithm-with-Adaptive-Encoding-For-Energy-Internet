package core;

import javax.portlet.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class FileUnion {
    public static void main(String[] args) throws IOException {
        int i = 0;
        String unfile = "E:\\sdfsdfsdfsd\\123.txt.0tmp";
        //String unfile = "E:\\sdfsdfsdfsd\\111.txt";
        String filedir = "E:\\sdfsdfsdfsd\\new.txt";
        String str = unfile.replace("\\", "/");
        List<String> list = new ArrayList<String>();
        while (true) {
            int first = str.lastIndexOf(".");
            String parentName = str.substring(0, first);
            String strfile = parentName + "." + i + "tmp";
            File f = new File(strfile);
            if (!f.exists())
                break;
            list.add(strfile);
            i++;
            str = parentName + "." + i + "tmp";
        }
        try {
            File file = new File(filedir.replace("\\", "/"));
            if (!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            FileInputStream in = null;
            int count = 0;
            byte[] buffer = new byte[1024];
            for (i = 0; i < list.size(); i++) {
                File f = new File(list.get(i));
                in = new FileInputStream(f);
                while ((count = in.read(buffer, 0, 1024)) != -1) {
                    out.write(buffer, 0, count);
                }
                in.close();
            }
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

