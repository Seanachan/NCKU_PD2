import java.util.regex.*;
public class test {
    public static void main(String args[]){
        String pattern=".*\\(.*\\)";
        String Input="+setOwner(String owner)";

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(Input);

        System.out.println(m.matches());


    }
    
}
