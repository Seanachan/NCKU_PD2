import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
@SuppressWarnings("unused")
public class temp {
    public static void main(String[] args) {
        String exp=null;
        String str="ABC to ot CBA";
        for(int m=1;m<10;m++){
            exp=new String(String.format(".*[aA]{%d}.*[bB]{%d}.*",m,2*m));
            System.out.println(exp);
            System.out.println(str.matches(exp));
        }
        
        
        
    }
}
