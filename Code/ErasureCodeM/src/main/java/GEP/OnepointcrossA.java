package GEP;

public class OnepointcrossA extends Serve {
    private String[] popII;
    private double crossoverRate =1;

    public void start(String[] popI) {
        popII = new String[size];
        for (int i = 0; i < size; i++) {
            popII[i] = popI[i];

        }
    }

    public void crossover() {
        if (size % 2 != 0) {
            for (int i = 1; i < size; i += 2) {

                double random = Rand();
                if (random < crossoverRate) {
                    cross(i, i + 1);
                }
            }
        } else {
            for (int i = 1; i < size - 1; i += 2) {

                double random = Rand();
                if (random < crossoverRate) {
                    cross(i, i + 1);
                }
            }
        }
    }

    public int rand(int s, int e) {
        return (int) (Rand() * (e - s) + s);// [start , end)

    }

    private double Rand() {
        return Math.random();
    }

    public void cross(int str, int stc) {
        String chromFragment1, chromFragment2;
        int rnda = rand(0, (GeneHeadNum + GeneTailNum) * Genenum - 1);

        chromFragment1 = getChrom(popII[str], rnda, (GeneHeadNum + GeneTailNum) * Genenum - 1);
        chromFragment2 = getChrom(popII[stc], rnda, (GeneHeadNum + GeneTailNum) * Genenum - 1);

        popII[str] = setChrom(popII[str], rnda, (GeneHeadNum + GeneTailNum) * Genenum - 1, chromFragment2);
        popII[stc] = setChrom(popII[stc], rnda, (GeneHeadNum + GeneTailNum) * Genenum - 1, chromFragment1);
    }

    public String getChrom(String strs, int begin, int end) {
        char[] dest = new char[end - begin + 1];
        strs.getChars(begin, end + 1, dest, 0);
        return new String(dest);// ********import
    }

    public String setChrom(String strs1, int begin, int end, String strs2) {

        StringBuffer buffer = new StringBuffer();
        buffer.append(strs1);

        for (int index = begin, idx = 0; index <= end; index++, idx++) {
            buffer.setCharAt(index, strs2.charAt(idx));

        }

        return buffer.toString();
    }

    public void end() {
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.println(popII[i]);
        }
    }

}