public class temp {
    public static void main(String[] args) {
        Double num=19.865;
        String numString="";
        System.out.println(num*1000);
        System.out.println((num*1000)%10);
        numString=String.format("%.2f",num);

        System.out.println(numString);
    }
}
