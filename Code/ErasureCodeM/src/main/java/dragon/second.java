package dragon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class second {
    static  public int r;

    public static boolean isPrime(int a) {
        boolean flag = true;
        if (a < 2) {// 素数不小于2
            return false;
        } else {
            for (int i = 2; i <= Math.sqrt(a); i++) {
                if (a % i == 0) {// 若能被整除，则说明不是素数，返回false
                    flag = false;
                    break;// 跳出循环
                }
            }
        }
        return flag;
    }

    public static boolean checkRelativelyPrime(int arr[]) {
        boolean flag = true;
        //1.先判断数组中是否有相同元素
        if(checkSame(arr)) {
            //2.两两比较数组中的元素的公因数
            for(int i = 0;i < arr.length-1;i++) {
                List <Integer>list1 = calcfactors(arr[i]);
                for(int j = i+1;j < arr.length;j++) {
                    List <Integer>list2 = calcfactors(arr[j]);
                    if(isRepeat(list1,list2)) {
                        flag = true;
                    }
                    else {
                        flag = false;
                        return flag;
                    }
                }
            }
        }
        return flag;
    }
    //计算一个数的公因数
    public static List<Integer> calcfactors(int number) {
        List<Integer> listFactors = new ArrayList<Integer>();
        //不包括1，从二开始
        for(int i = 2;i<=number;i++) {
            if(number%i==0) {
                listFactors.add(i);

            }
        }
        return listFactors;
    }
    //判断数组中是否有相同的数
    public static boolean checkSame(int arr[]) {
        for(int i = 0;i<arr.length-1;i++) {
            if(i+1<arr.length&&arr[i]==arr[i+1])
                return false;
        }
        return true;
    }
    public static boolean isRepeat(List<Integer> List1,List<Integer> List2) {
        for (int i = 0; i < List1.size(); i++) {
            for (int j = 0; j < List2.size(); j++) {
                if(List1.get(i)==List2.get(j))
                    return false;
            }
        }
        return true;
    }

    public static int[] encode(int k_,int n_,int d_)
    {
        int p_=d_+1;
        //int p_=7;
        int[] d=new int [n_+1];
        d[0]=p_;
        //d[1]=9;d[2]=11;d[3]=13;
        int temp=p_+1;

        //求出一系列的d
        for(int i=1;i<=n_;i++)
        {
            while(isPrime(temp)==false)
            {
                temp++;
            }
            d[i]=temp;
            temp++;
            //System.out.println(d[i]);
        }

        //最小的 i d 的乘积大于 p 和k -1个最大的 i d 的乘积。
        double m=1;
        for(int i=1;i<=k_;i++)
        {
            m=m*d[i];
        }
        //System.out.println("m="+m);

        double m_=p_;
        for(int i=0;i<(k_-1);i++)
        {
            m_=m_*d[n_-i];
        }
        //System.out.println("m_="+m_);

        //判断这些整数是否符合条件(如果不符合，则做出一些操作(未完成))
        if(m<=m_&&checkRelativelyPrime(d)==false)
        {
//			for(int i=0;i<(k_-1);i++)
//			{
//				d[n_-i]=d[n_-i]-1;
//			}
            System.out.println("生成的一组整数不符合要求！");

        }
        for(int i=0;i<=n_;i++)
        {
            //System.out.println(d[i]);
        }

        //开始随机选取一个r
        Random df = new Random();
        int ram=(int) ((m/p_)-1);
        r=df.nextInt(ram);
        //int r=10;
        System.out.println("r="+r);

        //开始计算d'
        int d1=d_+r*p_;
        int[] D=new int[n_+1];
        System.out.println("加密结果为：");
        for(int i=1;i<=n_;i++)
        {
            D[i]=d1%d[i];
            System.out.println("D"+i+"="+d1+"mod"+d[i]+"="+D[i]);
        }
        System.out.println("其中,D'="+d1);
        return d;
    }

    public static void decode(int k_,int n_,int d_,int r,int d[])
    {
        System.out.println("加密完成，下面开始解密过程");
        System.out.println("你至少需要输入"+k_+"块数据才可以进行解密");

        int[] innum=new int[k_+1];
        double[] input=new double[k_+1];
        Scanner scan = new Scanner(System.in);
        for(int i=1;i<=k_;i++)
        {
            System.out.println("请输入你获得的D的下标(范围为1-"+n_+")");
            innum[i]=scan.nextInt();
            System.out.println("请输入你获得的D的数值");
            input[i]=scan.nextInt();
            if(i==k_)
            {
                System.out.println("输入完成！");
                break;
            }
            System.out.println("请继续输入！");
        }
        double m1=1;
        for(int i=1;i<=k_;i++)
        {
            int temp=innum[i];
            m1=m1*d[temp];
        }
        //System.out.println(m1);
        //求出y[]
        int[] y=new int[k_+1];
        for(int i=1;i<=k_;i++)
        {
            int t=innum[i];
            double temp=m1/d[t];
            //System.out.println(temp);
            //inv操作求解(可能有问题)
            for(int k=1;k<d[t];k++)
            {
                if((temp*k)%d[t]==1)
                {
                    y[i]=k;
                    break;
                }
            }
            System.out.println("y"+i+"="+y[i]);
        }

        double d1=0,sum=0;
        for(int i=1;i<=k_;i++)
        {
            int t=innum[i];
            sum=m1/d[t]*y[i]*input[i];
            d1=d1+sum;
        }
        d1=d1%m1;
        System.out.println("解密后D'为:"+d1);
        double answer;
        answer=d1-r*d[0];
        System.out.println("解密后原始数据为:"+answer);
    }

    public static void main(String args[])
    {
        System.out.println("请输入k,n,D(需要加密的数据)");
        //int k_=2,n_=3,d_=4,p_;
        Scanner scan = new Scanner(System.in);
        int k_=scan.nextInt();
        int n_=scan.nextInt();
        int d_=scan.nextInt();
        int d[]=encode(k_,n_,d_);
        decode(k_,n_,d_,r,d);

    }
}

