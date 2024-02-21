package JavaLearn;
@SuppressWarnings("unused")
public class string {
    public static void main(String[] args){
        String str = "Seanachan";//String is not basic data type
        // ->String str = new String("Seanachan")

        //convert string to byte/short/integer/long/float
        //Integer.parseInt(str)

        /*
            char charAt(int index)
            int indexOf(int ch)
            int indexOf(String str)
            int lastIndexOf(int ch)
            String substring(int beginIndex,int endIndex)
            char[] toCharArray() -> conver String to char array
         */

        String a = new String(" caterpillar");
        char arr[] = a.toCharArray();
        String name = new String(arr);
        System.out.println(a);
        System.out.println(arr);
        System.out.println(name);
    }
}
