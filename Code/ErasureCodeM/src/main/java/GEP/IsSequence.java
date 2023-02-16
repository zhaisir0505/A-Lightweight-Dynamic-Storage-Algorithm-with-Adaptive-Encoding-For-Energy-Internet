package GEP;

import java.util.Random;

public class IsSequence extends Serve {
    private String[] popII;
    private double ISRate = 1;
    private Random random = new Random();
    int[] length = { 1, 2, 3 };
    int L = GeneHeadNum + GeneTailNum;

    public void start(String[] popI) {
        popII = new String[size];
        for (int i = 0; i < size; i++) {
            popII[i] = popI[i];

        }
    }

    public void ReSequence() {

        for (int i = 0; i < size; i++) {

            char[] gene = popII[i].toCharArray();
            for (int m = 0; m < Genenum; m++) {
                char[] Buffer;
                Buffer = new char[L];
                int f = 0;
                for (int j = (GeneHeadNum + GeneTailNum) * m; j < (GeneHeadNum + GeneTailNum) * (1 + m); j++) {
                    Buffer[f] = gene[j];
                    f++;

                }
                if (random.nextDouble() < ISRate) {
                    Sequence(gene, m, Buffer);
                }
            }
            popII[i] = String.valueOf(gene);

        }

    }

    public void Sequence(char[] sts, int t, char[] stc) {
        char[] s = stc.clone();
        char[] p = stc.clone();
        int ISlength = length[(int) (Math.random() * length.length)];
        int source = (int) (Math.random() * (L - ISlength));
        int target = (int) (Math.random() * (GeneHeadNum - ISlength));
        int m = target;
        int n = target + ISlength;
        if ((GeneHeadNum - target - ISlength) >= 0) {
            for (int j = 0; j < GeneHeadNum - target - ISlength; j++) {
                s[n++] = p[m++];
            }
        }
        int m1 = source;
        int n1 = target;
        for (int x = 0; x < ISlength; x++) {
            s[n1++] = p[m1++];
        }
        int h = 0;
        for (int j = (GeneHeadNum + GeneTailNum) * t; j < (GeneHeadNum + GeneTailNum) * (1 + t); j++) {
            sts[j] = s[h];
            h++;
        }

    }

}