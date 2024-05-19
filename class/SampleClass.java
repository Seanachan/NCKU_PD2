interface Interface1{
    void hello();
}
interface Interface2{
    void hello();
}
interface helloInterface{
    public void hello();
}
abstract class abstractClass{
    public void hello(){
        System.out.println("hello");
    }
}
class myClass{
    public int age;
}
class Test extends abstractClass implements helloInterface{}
public class SampleClass implements Interface1,Interface2{
    final static int a = 12;
    public void hello(){
        System.out.println();
        // a=1; --> CE
    }
    public static void main(String[] args){
        String path = System.getenv("PATH");
        System.out.println("PATH: "+path);
        int[] original = {1,2,3};
        int[] copied = original.clone();

        copied[0]=6;

        for(int i:original) System.out.print(i+" ");
        System.out.println();
        for(int i:copied) System.out.print(i+" ");
        System.out.println("\n");

        int[][] original2D = {{1,2,3},{4,5,6 }};
        int[][] copied2D = new int[original2D.length][];

        copied2D[0]=original2D[0].clone();
        copied2D[1]=original2D[1].clone();

        copied2D[0][0]=12;
        for(int i=0;i<2;i++){
            for(int j=0;j<3;j++) System.out.print(original2D[i][j]+" ");
            System.out.println();
        }
        for(int i=0;i<2;i++){
            for(int j=0;j<3;j++) System.out.print(copied2D[i][j]+" ");
            System.out.println();
        }

        Test test = new Test();
        test.hello();
        Integer i=1;
        Integer j=2;
        System.out.println(i+j);
        
        // a=1;
        return ;
    }
}