import java.util.Scanner;
// import java.util.Arrays;
// @SuppressWarnings("unused")
public class array {
    public static void main(String[] args){
        //sort() binarySearch()
        Scanner scanner = new Scanner(System.in);
        int[] arr1 = {3,1,4,5,6,2};
        int[] arr2 = {12,43,55,66};
        int[] temp = arr1;//temp points to arr1

        for(int i:arr1) 
            System.out.print(i+" ");
        System.out.println();

        for(int i:arr2)
            System.out.print(i+" ");
            System.out.println();
        for(int i: temp)
            System.out.print(i+" ");
            System.out.println();
        System.arraycopy(arr2, 0, temp, 0, 3);
        for(int i: temp)
            System.out.print(i+" ");
            System.out.println();
        scanner.close();
    }
}
