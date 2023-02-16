package core;


import javax.portlet.*;

public class Galois {
    /**
     * @param args
     */
    private int gf_already_setup;
    private int[] B_TO_J;
    private int[] J_TO_B;

    public Galois() {
        gf_already_setup = 0;
    }

    public void gf_modar_setup() {
        int j, b;
        if (gf_already_setup == 1)
            return;

        B_TO_J = new int[256];
        J_TO_B = new int[256];

        for (j = 0; j < 256; j++) {
            B_TO_J[j] = 255;
            J_TO_B[j] = 0;
        }
        b = 1;
        for (j = 0; j < 255; j++) {
            B_TO_J[b] = j;
            J_TO_B[j] = b;
            b = b << 1;
            int a = b & 256;
            if (a != 0)
                b = (b ^ 285) & 255;
        }
    }

    public int gf_single_multiply(int xxx, int yyy) {
        int sum_j;
        gf_modar_setup();
        if (xxx == 0 || yyy == 0)
            return 0;
        sum_j = B_TO_J[xxx] + B_TO_J[yyy];
        if (sum_j >= 255)
            sum_j -= 255;
        return J_TO_B[sum_j];
    }

    public int gf_single_divide(int a, int b) {
        int sum_j;
        gf_modar_setup();
        if (b == 0)
            return -1;
        if (a == 0)
            return 0;
        sum_j = B_TO_J[a] - B_TO_J[b];
        if (sum_j < 0)
            sum_j += 255;
        return J_TO_B[sum_j];

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Galois g = new Galois();
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                System.out.print(g.gf_single_multiply(i, j) + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                System.out.print(g.gf_single_divide(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println(g.gf_single_divide(3, 6));
        System.out.println(g.gf_single_multiply(3, 3));
        for (int i = 0; i < 256; i++) {
            System.out.println(i + "  " + g.B_TO_J[i] + "  " + g.J_TO_B[i]);
        }
        System.out.println(244 ^ 247);
    }
}
