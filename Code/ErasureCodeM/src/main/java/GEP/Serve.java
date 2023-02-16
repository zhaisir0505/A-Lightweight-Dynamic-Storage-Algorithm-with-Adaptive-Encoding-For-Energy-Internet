package GEP;

public class Serve extends Encode {
    private String[] pop;
    private String[] popI;
    private double averageFitness;
    private double[] relativeFitness;
    public static double mutateRate = 1;
    int size = 30;
    private double bestIndividual;
    private int bIndex1;

    Serve() {

        pop = new String[size];
        popI = new String[size];
        Encode population = new Encode();
		/*		Decode sp0 = new Decode();
				Fitness sp01 = new Fitness();
		*/
        for (int i = 0; i < size; i++) {
            String chrosome = null;
            chrosome = population.Combination();
			/*			do {
							chrosome = population.Combination();

						} while (sp01.getfitness(sp0.getexpress(chrosome)) == 0);*/

            popI[i] = chrosome;
        }
    }

    private double rand() {
        return Math.random();
    }

    public void gengratepop2() {

        Decode sp1 = new Decode();
        for (int i = 0; i < size; i++) {
            pop[i] = sp1.getexpress(popI[i]);

        }
    }

    public double calAverageFitness1() {
        for (int i = 0; i < size; i++) {
            Fitness sp1 = new Fitness();
            averageFitness = averageFitness + sp1.getfitness(pop[i]);
        }
        averageFitness = averageFitness / size;
        return averageFitness;
    }

    private double calTotalFitness() {
        Fitness sp1 = new Fitness();
        double total = 0.0;
        for (int i = 0; i < size; i++)
            total += sp1.getfitness(pop[i]);
        return total;
    }

    public double[] calRelativeFitness() {
        Fitness sp1 = new Fitness();

        double totalFitness = calTotalFitness();
        relativeFitness = new double[size];// �ص�ע�⣺ע���½�
        for (int i = 0; i < size; i++) {
            relativeFitness[i] = sp1.getfitness(pop[i]) / totalFitness;
        }
        return relativeFitness;
    }

    public void selectbestone() {
        Fitness sp2 = new Fitness();
        bestIndividual = sp2.getfitness(pop[0]);
        bIndex1 = 0;
        for (int i = 1; i < size; i++) {
            if (sp2.getfitness(pop[i]) > bestIndividual) {
                bestIndividual = sp2.getfitness(pop[i]);
                bIndex1 = i;
            }
        }
        popI[0] = popI[bIndex1];

    }

    public void select() {
        calRelativeFitness();
        double[] rouletteWheel; // ����
        String[] childPop = new String[size];
        rouletteWheel = new double[size];
        rouletteWheel[0] = relativeFitness[0];
        for (int i = 1; i < size - 1; i++) {
            rouletteWheel[i] = relativeFitness[i] + rouletteWheel[i - 1];
        }
        rouletteWheel[size - 1] = 1;
        for (int i = 1; i < size; i++) {
            double rnd = rand();
            for (int j = 0; j < size; j++) {
                if (rnd < rouletteWheel[j]) {
                    childPop[i] = popI[j];
                    break;
                }
            }
        }
        for (int i = 1; i < size; i++) {
            popI[i] = childPop[i];

        }
    }

    public void onepointcross() {
        OnepointcrossA s = new OnepointcrossA();
        s.start(popI);
        s.crossover();
    }

    public void mutate() {
        for (int i = 1; i < size; i++) {
            Encode en = new Encode();
            String newchromosome = popI[i];
            char[] chromosome = newchromosome.toCharArray();
            for (int m = 0; m < Genenum; m++) {
                for (int j = (GeneHeadNum + GeneTailNum) * m; j < GeneHeadNum + (GeneHeadNum + GeneTailNum) * m; j++) {
                    if (rand() < mutateRate) {
                        int index = (int) (Math.random() * GeneHeadsets.size());
                        chromosome[j] = GeneHeadsets.get(index).charAt(0);
                    }
                }
                for (int j = GeneHeadNum + (GeneHeadNum + GeneTailNum) * m; j < (m + 1)
                        * (GeneHeadNum + GeneTailNum); j++) {
                    if (rand() < mutateRate) {
                        int index = (int) (Math.random() * GeneTailsets.size());
                        chromosome[j] = GeneTailsets.get(index).charAt(0);
                    }

                }
            }
            popI[i] = String.valueOf(chromosome);

        }
    }

    public void ISsequence() {
        IsSequence s = new IsSequence();
        s.start(popI);
        s.ReSequence();
    }

    public void picture() {
        Decode sp1 = new Decode();
		/*		for (int i = 0; i < size; i++) {
					pop[i] = sp1.getexpress(popI[i]);
				}*/
        Fitness sp2 = new Fitness();
        System.out.println(sp2.getfitness(pop[0]) + ":" + sp1.getexpress(popI[0]));
		/*		for (int i = 0; i < size; i++) {
					System.out.println("No" + (i + 1) + "." + pop[i] + ":" + sp2.getfitness(pop[i]));
				}*/

    }

    public static void main(String[] args) {
        Serve test = new Serve();
        Encode sp = new Encode();
        for (int i = 0; i < 50000; i++) {
            System.out.println("��" + (i + 1) + "th" + ":");
            test.gengratepop2();
            test.selectbestone();
            test.select();
            test.onepointcross();
            test.mutate();
            test.ISsequence();
            test.picture();
        }

    }
}