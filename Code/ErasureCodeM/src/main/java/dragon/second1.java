package dragon;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class second1 {
    public static int r;

    public second1() {
    }

    public static boolean isPrime(int a) {
        boolean flag = true;
        if (a < 2) {
            return false;
        } else {
            for(int i = 2; (double)i <= Math.sqrt((double)a); ++i) {
                if (a % i == 0) {
                    flag = false;
                    break;
                }
            }

            return flag;
        }
    }

    public static boolean checkRelativelyPrime(int[] arr) {
        boolean flag = true;
        if (checkSame(arr)) {
            for(int i = 0; i < arr.length - 1; ++i) {
                List<Integer> list1 = calcfactors(arr[i]);

                for(int j = i + 1; j < arr.length; ++j) {
                    List<Integer> list2 = calcfactors(arr[j]);
                    if (!isRepeat(list1, list2)) {
                        flag = false;
                        return flag;
                    }

                    flag = true;
                }
            }
        }

        return flag;
    }

    public static List<Integer> calcfactors(int number) {
        List<Integer> listFactors = new ArrayList();

        for(int i = 2; i <= number; ++i) {
            if (number % i == 0) {
                listFactors.add(i);
            }
        }

        return listFactors;
    }

    public static boolean checkSame(int[] arr) {
        for(int i = 0; i < arr.length - 1; ++i) {
            if (i + 1 < arr.length && arr[i] == arr[i + 1]) {
                return false;
            }
        }

        return true;
    }

    public static boolean isRepeat(List<Integer> List1, List<Integer> List2) {
        for(int i = 0; i < List1.size(); ++i) {
            for(int j = 0; j < List2.size(); ++j) {
                if (List1.get(i) == List2.get(j)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static int[] encode(int k_, int n_, int d_) {
        int p_ = d_ + 1;
        int[] d = new int[n_ + 1];
        d[0] = p_;
        int temp = p_ + 1;

        for(int i = 1; i <= n_; ++i) {
            while(!isPrime(temp)) {
                ++temp;
            }

            d[i] = temp++;
        }

        double m = 1.0D;

        for(int i = 1; i <= k_; ++i) {
            m *= (double)d[i];
        }

        double m_ = (double)p_;

        int i;
        for(i = 0; i < k_ - 1; ++i) {
            m_ *= (double)d[n_ - i];
        }

        if (m <= m_ && !checkRelativelyPrime(d)) {
            System.out.println("生成的一组整数不符合要求！");
        }

        for(i = 0; i <= n_; ++i) {
        }

        Random df = new Random();
        int ram = (int)(m / (double)p_ - 1.0D);
        r = df.nextInt(ram);
        System.out.println("r=" + r);
        int d1 = d_ + r * p_;
        int[] D = new int[n_ + 1];
        System.out.println("加密结果为：");

        for(i = 1; i <= n_; ++i) {
            D[i] = d1 % d[i];
            System.out.println("D" + i + "=" + d1 + "mod" + d[i] + "=" + D[i]);
        }

        System.out.println("其中,D'=" + d1);
        return d;
    }

    public static void decode(int k_, int n_, int d_, int r, int[] d) {
        System.out.println("加密完成，下面开始解密过程");
        System.out.println("你至少需要输入" + k_ + "块数据才可以进行解密");
        int[] innum = new int[k_ + 1];
        double[] input = new double[k_ + 1];
        Scanner scan = new Scanner(System.in);

        for(int i = 1; i <= k_; ++i) {
            System.out.println("请输入你获得的D的下标(范围为1-" + n_ + ")");
            innum[i] = scan.nextInt();
            System.out.println("请输入你获得的D的数值");
            input[i] = (double)scan.nextInt();
            if (i == k_) {
                System.out.println("输入完成！");
                break;
            }

            System.out.println("请继续输入！");
        }

        double m1 = 1.0D;

        int i;
        for( i = 1; i <= k_; ++i) {
            i = innum[i];
            m1 *= (double)d[i];
        }

        int[] y = new int[k_ + 1];

        double temp;
        int k;
        for(i = 1; i <= k_; ++i) {
            int t = innum[i];
            temp = m1 / (double)d[t];

            for(k = 1; k < d[t]; ++k) {
                if (temp * (double)k % (double)d[t] == 1.0D) {
                    y[i] = k;
                    break;
                }
            }

            System.out.println("y" + i + "=" + y[i]);
        }

        double d1 = 0.0D;
        temp = 0.0D;

        for(k = 1; k <= k_; ++k) {
            int t = innum[k];
            temp = m1 / (double)d[t] * (double)y[k] * input[k];
            d1 += temp;
        }

        d1 %= m1;
        System.out.println("解密后D'为:" + d1);
        double answer = d1 - (double)(r * d[0]);
        System.out.println("解密后原始数据为:" + answer);
    }

    public static void main(String[] args) {
        System.out.println("请输入k,n,D(需要加密的数据)");

        Scanner scan = new Scanner(System.in);
        int k_ = scan.nextInt();
        int n_ = scan.nextInt();
        int d_ = scan.nextInt();
        int[] d = encode(k_, n_, d_);
        decode(k_, n_, d_, r, d);
    }
}
