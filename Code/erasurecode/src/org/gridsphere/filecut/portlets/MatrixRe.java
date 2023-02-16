package org.gridsphere.filecut.portlets;

import javax.portlet.*;
import java.io.*;

/**
 * @author Administrator
 *
 */
public class MatrixRe {
    public static Recovery recovery = new Recovery();
    public static int n;
    public static int m;
    public String[] path = recovery.path;
    public String rpath = recovery.rpath;
    public String lpath = recovery.lpath;
    public int[][] matrix;

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

    public MatrixRe() {
        n = recovery.filenum;
        m = recovery.jiaoyannum;
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
        MatrixRe m = new MatrixRe();
        for (int i = 0; i < MatrixRe.m + MatrixRe.n; i++) {
            for (int j = 0; j < MatrixRe.n; j++) {
                System.out.print(m.matrix[i][j]);
            }
            System.out.println();
        }
    }
}
