package GEP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Decode extends Encode {
    private static final List<Character> operators = Arrays.asList('+', '-', '*', '/');
    private static final List<Character> functions = Arrays.asList('s', 'c', 'e', 'Q', 't', 'Y', 'L');

    public List<String> dismantle(String s) {
        List<String> evals = new ArrayList<>();
        List<String> evals1 = new ArrayList<>();
        char[] gene = s.toCharArray();
        int m = 0;
        int n = 1;
        evals1.add(String.valueOf(gene[m]));

        for (int i = 0; i < n; i++) {
            if (operators.contains(evals1.get(i).charAt(0))) {
                evals1.add(String.valueOf(gene[m + 1]));
                evals1.add(String.valueOf(gene[m + 2]));
                m += 2;
                n += 2;
            } else if (functions.contains(evals1.get(i).charAt(0))) {
                evals1.add(String.valueOf(gene[m + 1]));
                m += 1;
                n += 1;
            }
        }
        String evaluation = s.substring(0, m + 1);
        evals.add(evaluation);
        return evals;
    }

    // @Deprecated

    public String generateExpression(String elements) {
        List<String> tree = new ArrayList<>();
        for (int i = 0; i < elements.length(); i++) {
            tree.add(String.valueOf(elements.charAt(i)));
        }
        while (tree.size() > 1) {
            List<String> stacks = new ArrayList<>();
            int variableIndex1 = 0;
            int variableIndex2 = 0;
            int operationIndex = 0;
            int functionIndex = 0;
            for (int i = tree.size() - 1; i >= 0; i--) {
                if (operators.contains(tree.get(i).charAt(0))) {
                    operationIndex = i;
                    stacks.add(tree.get(i));
                    break;
                } else if (functions.contains(tree.get(i).charAt(0)) && tree.get(i).length() == 1) {
                    functionIndex = i;
                    stacks.add(tree.get(i));
                    break;
                }
            }
            for (int i = tree.size() - 1; i >= 0; i--) {
                if (!operators.contains(tree.get(i).charAt(0)) && !functions.contains(tree.get(i).charAt(0))) {
                    variableIndex1 = i;
                    stacks.add(tree.get(i));
                    break;
                }
            }
            if ("s".equals(stacks.get(0)) || "c".equals(stacks.get(0)) || "e".equals(stacks.get(0))
                    || "Q".equals(stacks.get(0)) || "t".equals(stacks.get(0)) || "Y".equals(stacks.get(0))
                    || "L".equals(stacks.get(0))) {
                if ("s".equals(stacks.get(0))) {
                    tree.set(functionIndex, "(Math.sin(" + stacks.get(1) + "))");
                    tree.remove(variableIndex1);
                }
                if ("c".equals(stacks.get(0))) {

                    tree.set(functionIndex, "(Math.cos(" + stacks.get(1) + "))");
                    tree.remove(variableIndex1);
                }
                if ("e".equals(stacks.get(0))) {

                    tree.set(functionIndex, "(Math.exp(" + stacks.get(1) + "))");
                    tree.remove(variableIndex1);
                }
                if ("Q".equals(stacks.get(0))) {

                    tree.set(functionIndex, "(Math.sqrt(" + stacks.get(1) + "))");
                    tree.remove(variableIndex1);
                }
                if ("t".equals(stacks.get(0))) {

                    tree.set(functionIndex, "(Math.tan(" + stacks.get(1) + "))");
                    tree.remove(variableIndex1);
                }

                if ("Y".equals(stacks.get(0))) {

                    tree.set(functionIndex, "(Math.pow(" + stacks.get(1) + ", 1.0/3" + "))");
                    tree.remove(variableIndex1);
                }
                if ("L".equals(stacks.get(0))) {

                    tree.set(functionIndex, "(Math.log(" + stacks.get(1) + "))");
                    tree.remove(variableIndex1);
                }

            } else {
                tree.remove(variableIndex1);
                for (int i = tree.size() - 1; i >= 0; i--) {
                    if (!operators.contains(tree.get(i).charAt(0)) && !functions.contains(tree.get(i).charAt(0))) {
                        variableIndex2 = i;
                        stacks.add(tree.get(i));
                        break;
                    }
                }
                tree.remove(variableIndex2);
                tree.set(operationIndex, "(" + stacks.get(2) + stacks.get(0) + stacks.get(1) + ")");
            }
        }
        return tree.get(0);
    }

    public String getexpress(String gene) {
        Decode seshi = new Decode();
        int L1 = GeneHeadNum + GeneTailNum;
        char[] gene1 = gene.toCharArray();
        char[] gene2;
        gene2 = new char[L1];
        List<String> stringList = new ArrayList<>();
        for (int m = 0; m < Genenum; m++) {
            int u = 0;
            for (int i = m * L1; i < (m + 1) * L1; i++) {
                gene2[u] = gene1[i];
                u++;
            }
            String gene3 = String.valueOf(gene2);

            stringList.addAll(seshi.dismantle(gene3));
        }

        List<String> answers = new ArrayList<>();
        for (String s : stringList) {
            answers.add(seshi.generateExpression(s));
        }
        String result = String.join("+", answers);
        return result;
    }

	/*	public static void main(String[] args) {
			Decode s = new Decode();
			String gene = s.Combination();
			System.out.println(s.getexpress(gene));
		}*/

}
