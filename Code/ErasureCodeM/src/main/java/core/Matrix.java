package core;


import javax.portlet.*;
import java.io.*;

/**
 * @author Administrator
 */
public class Matrix {
    public static FileCut filecut;
    public static int n;
    public static int m;
    public static String[] path;
    public String rpath;
    public String lpath;
    public static int[][] matrix;

    public int index(int b, int i) {
        if (i == 0)
            return 1;
        else {
            for (int k = 1; k < i; k++) {
                b = b * b;
            }
            return b;
        }
    }

    public Matrix() {
        FileCut filecut = new FileCut();
        n = FileCut.filenum;
        m = FileCut.jiaoyannum;
        path = new String[n + m];
        System.out.println("FileCut.filenum=" + FileCut.filenum);
        System.out.println("n=" + n);
        int k;
        File file = new File(FileCut.filename);
        rpath = (FileCut.filedir + "\\" + file.getName() + ".recovery");
        lpath = (FileCut.filedir + "\\" + file.getName() + "length.txt");
        for (k = 0; k < n; k++) {
            path[k] = "c:\\test\\I Miss You .mp3." + k + "tmp";
            path[k] = FileCut.filenames[k];
            path[k] = FileCut.filenames[k];
        }
        for (; k < n + m; k++) {
            path[k] = (FileCut.filedir + file.getName() + "jiaoyan" + (k - n) + ".txt");
        }
        matrix = new int[m + n][n];
        int i = 0;
        for (; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j)
                    matrix[i][j] = 1;
                else
                    matrix[i][j] = 0;
            }
        }
        for (; i < m + n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = index(j + 1, i - n);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Matrix m = new Matrix();
        for (int i = 0; i < Matrix.m + Matrix.n; i++) {
            for (int j = 0; j < Matrix.n; j++) {
                System.out.print(m.matrix[i][j]);
            }
            System.out.println();
        }
    }
}