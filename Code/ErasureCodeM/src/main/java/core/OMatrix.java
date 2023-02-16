package core;
import javax.portlet.*;
import java.io.*;
/**
 */
public class OMatrix {
    public int [][]bsmatrix;
    public int num=0;
    public int [][]omatrix;
    public int []flag;
    public OMatrix()throws IOException
    {
        MatrixRe mx=new MatrixRe();
        bsmatrix=new int[mx.n][mx.n];
        omatrix=new int[mx.n][mx.n];
        Galois g=new Galois();
        RandomAccessFile []input=new RandomAccessFile[mx.n+mx.m];//产生标志
        flag=new int[mx.n+mx.m];
        for(int i=0;i<mx.n+mx.m;i++)flag[i]=1;
        for(int i=0;i<mx.n+mx.m;i++)
        {
            try{
                input[i]=new RandomAccessFile(mx.path[i],"r");
                input[i].close();
            }
            catch(IOException e){
                flag[i]=0;
                num++;
            }
        }
        if(num>mx.m) {System.out.println("不可恢复~");return;}
        else{
            int i,j,k=0;
            int [][]newmatrix=new int[mx.n][mx.n];//原矩阵去掉丢失文件所在行形成的方阵
            for(i=0;i<mx.n;i++)
            {
                while(flag[i+k]==0)k++;
                for(j=0;j<mx.n;j++)
                {
                    newmatrix[i][j]=mx.matrix[i+k][j];
                }
            }
            for(i=0;i<Matrix.n;i++)
            {
                for(j=0;j<Matrix.n;j++)
                {
                    System.out.print(newmatrix[i][j]);
                }
                System.out.println();
            }
            int model=model_of_matrix(newmatrix,mx.n);//方阵的模

            int [][]a=new int[mx.n-1][mx.n-1];//a矩阵的模为原矩阵去掉丢失文件所在行的方阵的各余子式
            for(i=0;i<mx.n;i++)
            {
                for(j=0;j<mx.n;j++)
                {
                    for(int m=0;m<mx.n-1;m++)
                    {
                        for(int n=0;n<mx.n-1;n++)
                        {
                            if((m<i)&&(n<j))a[m][n]=newmatrix[m][n];
                            else if((m<i)&&(n>=j))a[m][n]=newmatrix[m][n+1];
                            else if((m>=i)&&(n<j))a[m][n]=newmatrix[m+1][n];
                            else a[m][n]=newmatrix[m+1][n+1];
                        }
                    }
                    int flag=1;
                    if(((i+j)/2)*2!=(i+j))flag=-1;
                    bsmatrix[j][i]=model_of_matrix(a,mx.n-1);//利用a矩阵求出伴随矩阵
                }
            }
            for(i=0;i<mx.n;i++)
            {
                for(j=0;j<mx.n;j++)
                {
                    omatrix[i][j]=g.gf_single_divide(bsmatrix[i][j],model);//求出逆矩阵
                }
            }
        }
        System.out.println(num);
    }
    public int model_of_matrix(int [][]matrix,int n)
    {
        int sum=0;
        int num=1;
        for(int k=0;k<n;k++)num=num*(k+1);
        int []flag=new int[num];
        int []multiply=new int[num];

        int i,j;
        for(i=0;i<n;i++)

            return sum;
        Galois g=new Galois();
        int sum1=0;
        if(n==1)return matrix[0][0];
        for(int i1=0;i1<n;i1++){
            int [][]matrix1=new int[n-1][n-1];
            for(int j1=0;j1<n-1;j1++)
            {
                for(int k=0;k<n-1;k++)
                {
                    if(k<i1)matrix1[j1][k]=matrix[j1+1][k];
                    else matrix1[j1][k]=matrix[j1+1][k+1];
                }
            }
            int flag1=1;
            if((i1/2)*2!=i1)flag1=-1;
            sum1=sum1^g.gf_single_multiply(matrix[0][i1],model_of_matrix(matrix1,n-1));
        }
        return sum1;
    }
    public static void main(String[] args) throws IOException{
        OMatrix o=new OMatrix();
        int i,j;
        for(i=0;i<MatrixRe.n;i++)
        {
            for(j=0;j<MatrixRe.n;j++)
            {
                System.out.print(o.omatrix[i][j]);
            }
            System.out.println();
        }
    }
}