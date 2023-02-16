package GEP;

import java.util.ArrayList;
import java.util.List;

public class Encode {
    int Array = 2;
    int Genenum = 6;
    int GeneHeadNum = 8;
    int GeneTailNum = GeneHeadNum * (Array - 1) + 1;
    char[] geneh = new char[GeneHeadNum];// �������ͷ������
    char[] genetail = new char[GeneTailNum];// �������β������
    char[] gene = new char[geneh.length + genetail.length];
    public List<String> GeneHeadsets = new ArrayList<>();
    public List<String> GeneTailsets = new ArrayList<>();

    public Encode() {

        GeneHeadsets.add("+");
        GeneHeadsets.add("-");
        GeneHeadsets.add("*");
        GeneHeadsets.add("/");
        GeneHeadsets.add("b");
        GeneHeadsets.add("d");
        GeneHeadsets.add("Q");
        GeneHeadsets.add("s");
        GeneHeadsets.add("c");
        GeneHeadsets.add("t");
        GeneHeadsets.add("e");
        GeneHeadsets.add("Y");
        GeneHeadsets.add("L");
        GeneHeadsets.add("f");
        GeneHeadsets.add("j");
        GeneHeadsets.add("k");
        GeneHeadsets.add("u");
        GeneTailsets.add("b");
        GeneTailsets.add("d");
        GeneTailsets.add("f");
        GeneTailsets.add("j");
        GeneTailsets.add("k");
        GeneTailsets.add("u");

    }

    class GeneHead {

        public void GenerateHead() {

            for (int i = 0; i < GeneHeadNum; i++) {
                int index = (int) (Math.random() * GeneHeadsets.size());

                geneh[i] = GeneHeadsets.get(index).charAt(0);

            }

        }
    }

    class GeneTail {

        public void GenerateTail() {

            for (int i = 0; i < GeneTailNum; i++) {
                int index = (int) (Math.random() * GeneTailsets.size());

                genetail[i] = GeneTailsets.get(index).charAt(0);

            }
        }

    }

    public String Combination() {
        StringBuilder sb = new StringBuilder();
        for (int genenumb = 0; genenumb < Genenum; genenumb++) {

            GeneHead A = new GeneHead();
            A.GenerateHead();

            GeneTail B = new GeneTail();
            B.GenerateTail();
            for (int n = 0; n < gene.length; n++) {
                if (n < geneh.length) {
                    gene[n] = geneh[n];
                } else {
                    gene[n] = genetail[n - geneh.length];
                }
            }
            sb.append(gene);

        }

        return String.valueOf(sb);
    }

	/*	public static void main(String[] args) {

			Encode sp = new Encode();
			for (int k = 0; k < 1; k++) {

				String dd = sp.Combination();

				System.out.println(dd);

			}

		}*/
}
