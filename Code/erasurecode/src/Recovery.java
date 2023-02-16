package org.gridsphere.filecut.portlets;

import javax.portlet.*;
import java.io.*;

public class Recovery extends GenericPortlet {
    String VIEW_JSP = "/jsp/view_Recovery.jsp";
    public static String filename;
    public static int filenum;
    public static int jiaoyannum;
    public static String rpath;
    public static String lpath = "E:\\sdfsdfsdfsd" + filename + "length.txt";
    public static String[] path;
    public static String sle;
    public static String over = "0";
    public static String chx;

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        over = "0";
        super.destroy();
    }

    public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        PortletURL url = response.createActionURL();
        request.setAttribute("actionur2", url.toString());
        filename = request.getParameter("filename");
        sle = request.getParameter("sle");
        over = request.getParameter("over");
        chx = request.getParameter("chx");
        System.out.println("sle=" + sle);
        request.setAttribute("over", over);
        super.destroy();
        PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher(VIEW_JSP);
        rd.include(request, response);
    }

    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {

        filename = request.getParameter("filename");
        sle = request.getParameter("sle");
        chx = request.getParameter("chx");
        System.out.println("chx=" + chx);
        long start = System.currentTimeMillis();
        if (filename == null) {
            filename = "";
            super.destroy();
        } else {
            String log = "E:\\sdfsdfsdfsd" + filename + ".log";
            String fg = ".";
            int nn = filename.lastIndexOf(fg);
            rpath = "E:\\sdfsdfsdfsd" + filename.substring(0, nn) + "(recovery)" + filename.substring(nn);
            File file = new File(log);
            FileInputStream logfile = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(logfile);
            BufferedReader br = new BufferedReader(reader);
            br.readLine();
            System.out.println(filename);
            String filenum1 = br.readLine();
            filenum = Integer.valueOf(filenum1);
            System.out.println(filenum);
            String jiaoyannum1 = br.readLine();
            jiaoyannum = Integer.valueOf(jiaoyannum1);
            path = new String[filenum + jiaoyannum];
            System.out.println(jiaoyannum);
            int[] l = new int[filenum];
            for (int lee = 0; lee < filenum; lee++) {
                String sss = br.readLine();
                l[lee] = Integer.valueOf(sss);
            }
            for (int lee = 0; lee < filenum; lee++)
                System.out.println(l[lee]);
            br.close();
            int xh = 0;
            for (xh = 0; xh < filenum; xh++) {
                path[xh] = "E:\\sdfsdfsdfsd" + filename + "." + xh + "tmp";
            }
            for (; xh < filenum + jiaoyannum; xh++) {
                path[xh] = "E:\\sdfsdfsdfsd" + filename + "jiaoyan" + (xh - filenum) + ".txt";
            }
            FileDownload f = new FileDownload();
            f.doView();
            if (chx == null) {
                String nocheckout = "E:\\sdfsdfsdfsd" + filename.substring(0, nn) + "(recovery)(nocheckout)"
                        + filename.substring(nn);
                File nocheckoutfile = new File(nocheckout);
                if (!nocheckoutfile.exists())
                    nocheckoutfile.createNewFile();
                FileOutputStream outnocheck = new FileOutputStream(nocheckoutfile);
                for (int chh = 0; chh < filenum; chh++) {
                    String istr = String.valueOf(chh);
                    if (sle.contains(istr)) {
                        String insun = "E:\\sdfsdfsdfsd" + filename + "." + chh + "tmp";
                        File insunfile = new File(insun);
                        if (!insunfile.exists())
                            System.out.println("子文件" + chh + "不存在");
                        int lengthsunfile = l[chh];
                        byte[] buffercheck = new byte[lengthsunfile];
                        FileInputStream ininsunfile = new FileInputStream(insunfile);
                        int filecount;
                        while ((filecount = ininsunfile.read(buffercheck, 0, lengthsunfile)) != -1) {
                            outnocheck.write(buffercheck, 0, filecount);
                        }
                        ininsunfile.close();
                    }
                }
                outnocheck.close();
                chx = "0";
            } else {
                System.out.println("正在恢复,请稍候....");
                MatrixRe mx = new MatrixRe();
                OMatrix o = new OMatrix();
                Galois g = new Galois();
                RandomAccessFile output = new RandomAccessFile(rpath, "rw");
                RandomAccessFile[] input = new RandomAccessFile[filenum];
                int i = 0, j = 0, k = 0;
                int length = 0;
                for (i = 0; i < filenum; i++) {
                    if (l[i] > length)
                        length = l[i];
                }
                int[][] array = new int[filenum][length];
                byte[][] r = new byte[filenum][length];
                for (i = 0; i < filenum; i++) {
                    while (o.flag[i + k] == 0)
                        k++;
                    input[i] = new RandomAccessFile(mx.path[i + k], "r");
                    for (j = 0; j < length; j++) {
                        array[i][j] = input[i].read();
                    }
                }
                for (i = 0; i < filenum; i++) {
                    for (k = 0; k < length; k++) {
                        int data = g.gf_single_multiply(o.omatrix[i][0], array[0][k]);
                        for (j = 1; j < filenum; j++) {
                            data = data ^ g.gf_single_multiply(o.omatrix[i][j], array[j][k]);
                        }
                        r[i][k] = (byte) data;
                        if (k < l[i])
                            output.write(data);
                    }
                }
                for (i = 0; i < filenum; i++)
                    output.write(r[i], 0, l[i]);
                System.out.println("恢复完毕");
                output.close();
                for (i = 0; i < filenum; i++) {
                    input[i].close();
                }
            }
            over = "1";
            request.setAttribute("over", over);
            long stop = System.currentTimeMillis();
            System.out.println(stop - start);
        }
        response.setRenderParameter("filename", filename);
        response.setRenderParameter("sle", sle);
        response.setRenderParameter("chx", chx);
    }
}
