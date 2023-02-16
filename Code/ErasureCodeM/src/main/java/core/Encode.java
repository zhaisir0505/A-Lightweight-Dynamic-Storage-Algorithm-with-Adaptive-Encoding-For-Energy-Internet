package core;


import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Administrator
 */
public class Encode {
    public static int[] le;

    public Encode() throws IOException {
        System.out.println("正在生成校验码,请稍候...");
        Matrix mx = new Matrix();
        Galois g = new Galois();
        RandomAccessFile[] input = new RandomAccessFile[Matrix.n];
        RandomAccessFile[] out = new RandomAccessFile[Matrix.n];
        RandomAccessFile[] output = new RandomAccessFile[Matrix.m];
        int i;
        for (i = 0; i < mx.n; i++) {
            System.out.println(mx.n);
            System.out.println(mx.path[i]);
            input[i] = new RandomAccessFile(mx.path[i], "r");
            out[i] = new RandomAccessFile(mx.path[i], "rw");
        }
        for (; i < mx.m + mx.n; i++)
            output[i - mx.n] = new RandomAccessFile(mx.path[i], "rw");

        int w;
        int length = 0;
        le = FileCut.le;
        for (i = 0; i < mx.n; i++) {
            if (le[i] > length)
                length = le[i];
        }
        byte[][] e = new byte[mx.n][length];
        for (i = 0; i < mx.n; i++) {
            if (le[i] < length) {
                out[i].skipBytes(le[i]);
                for (w = le[i]; w < length; w++)
                    out[i].write(0);
            }
        }
        int[] array = new int[mx.n];
        for (int l = 0; l < length; l++) {
            int[] sum = new int[mx.m];
            for (i = 0; i < mx.m; i++)
                sum[i] = 0;
            for (int k = 0; k < mx.n; k++)
                array[k] = input[k].read();
            for (int j = 0; j < mx.m; j++) {
                for (int k = 0; k < mx.n; k++)
                    sum[j] = sum[j] ^ g.gf_single_multiply(array[k], mx.matrix[mx.n + j][k]);
                e[j][l] = (byte) sum[j];
            }
        }
        for (i = 0; i < mx.m; i++)
            output[i].write(e[i], 0, le[i]);

        for (i = 0; i < mx.n; i++)
            input[i].close();
        for (; i < mx.m + mx.n; i++)
            output[i - mx.n].close();
        System.out.println("校验码已经生成");
    }
}

