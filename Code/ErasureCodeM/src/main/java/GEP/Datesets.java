package GEP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Datesets {
    public int getveriablenum(String filepath) throws IOException {// ��ȡ��������
        int num = 0;
        File file = new File(filepath);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, "gbk");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(isr);
            String temp = null;

            temp = reader.readLine();
            String[] str = temp.split(" ");
            num = str.length - 1;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return num;
    }

    public List<List<String>> reader(String txtfile) throws IOException {// ���ļ��ж�ȡ���ݼ�

        File file = new File(txtfile);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, "gbk");
        BufferedReader reader = null;
        List<List<String>> dates = new ArrayList();
        try {
            reader = new BufferedReader(isr);
            String temp = null;

            while ((temp = reader.readLine()) != null)

            {
                String[] str = temp.split(" ");
                ArrayList list = new ArrayList();
                for (int i = 0; i < str.length; i++) {
                    list.add(str[i]);
                }
                // System.out.println(list);
                ArrayList list1 = (ArrayList) list.clone();
                dates.add(list1);
                list.clear();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return dates;

    }

}