package org.gridsphere.filecut.portlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.portlet.*;

public class FileCut extends GenericPortlet {
    String VIEW_JSP = "/jsp/view_FileCut.jsp";
    public static String[] filenames;
    public static String filename;
    public static String filedir = "E:/sdfsdfsdfsd/";
    public static String cutfilename = "abc.txt";
    public static int filenum;
    public static int jiaoyannum = 1;
    public static File file;
    public static int[] le;
    String outprint = "0";
    String lengths = "";

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        filenum = 0;
        lengths = "";
        outprint = "0";
        super.destroy();
    }

    public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        PortletURL url = response.createActionURL();
        request.setAttribute("actionurl", url.toString());
        String filenums = request.getParameter("filenums");
        String cutfilename = request.getParameter("cutfilename");
        outprint = request.getParameter("outprint");
        super.destroy();
        PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher(VIEW_JSP);
        rd.include(request, response);
    }

    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
        String filenums = request.getParameter("filenums");
        String cutfilename = request.getParameter("cutfilename");
        // String outprint=request.getParameter("outprint");
        long startcut = System.currentTimeMillis();
        if ((filenums == null) || (cutfilename == null)) {
            super.destroy();
        } else {
            filenum = Integer.parseInt(filenums);
            filename = filedir + cutfilename;
            File inputfalseFile = new File(filename);
            if (!inputfalseFile.exists()) {
                outprint = "3";
            } else {
                le = new int[filenum];
                long start = System.currentTimeMillis();
                int filesize = 0;
                filenames = new String[filenum];
                File file = new File(filename.replace("\\", "/"));
                filesize = ((int) (file.length() / filenum)) + 1;
                File[] newFile = new File[filenum];
                try {
                    int count = 0;
                    int i = 0;
                    byte[] buffer = new byte[filesize];
                    FileOutputStream out = null;
                    FileInputStream in = new FileInputStream(file);
                    File f = new File(filedir.replace("\\", "/"));
                    if (!f.exists()) {
                        f.createNewFile();
                    }
                    for (i = 0; i < newFile.length; i++) {
                        newFile[i] = new File(filedir.replace("\\", "/"), file.getName() + "." + i + "tmp");
                        filenames[i] = (filedir.replace("\\", "/") + file.getName() + "." + i + "tmp");

                    }
                    i = 0;
                    while ((count = in.read(buffer, 0, filesize)) != -1) {
                        out = new FileOutputStream(newFile[i]);
                        out.write(buffer, 0, count);
                        le[i] = (int) newFile[i].length();
                        out.close();
                        i++;
                    }
                    in.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                String log = (filedir + file.getName() + ".log");
                File logfilename = new File(log);
                FileOutputStream logfile = new FileOutputStream(logfilename);
                OutputStreamWriter write = new OutputStreamWriter(logfile);
                BufferedWriter bw = new BufferedWriter(write);
                bw.write(file.getName());
                lengths = "";
                lengths = lengths + file.getName() + " ";
                bw.newLine();
                String filenum1 = String.valueOf(filenum);
                bw.write(filenum1);
                lengths = lengths + filenum1 + " ";
                bw.newLine();
                String jiaoyannum1 = String.valueOf(jiaoyannum);
                bw.write(jiaoyannum1);
                lengths = lengths + jiaoyannum1 + " ";
                bw.newLine();
                for (int lee = 0; lee < le.length; lee++) {
                    String sss = String.valueOf(le[lee]);
                    bw.write(sss);
                    lengths = lengths + sss + " ";
                    bw.newLine();
                    // System.out.println(le[lee]);
                }
                for (int i = 0; i < filenum; i++) {
                    String name = filenames[i].substring(8);
                    bw.write(name);
                    lengths = lengths + name + " ";
                    bw.newLine();
                }
                bw.close();
                System.out.println("lenghts=" + lengths);
                Encode e = new Encode();
                FileUpload f = new FileUpload();
                f.doView();
                outprint = "1";
            }
            request.setAttribute("outprint", outprint);
            request.setAttribute("lengths", lengths);
        }
        response.setRenderParameter("filenums", filenums);
        response.setRenderParameter("cutfilename", cutfilename);
    }
}
