import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SuppressWarnings("unused")
public class learn_string {
    public static void main(String[] args)throws IOException{
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

        //Using '=' in Java means changing the object referenced by the name
        //Java will check if this object is no longer referenced, and collect the garbage
        String str1 = "flyweight";
        String str2 = "flyweight";
        System.out.println(str1==str2); //see if they reference the same object

        String[] fakeFileData = {
            "justin\t64/5/26\t0939002302\t5433343",
            "momor\t68/7/23\t0939100391\t5432343" }; 
        for(String data:fakeFileData){
            String[] tokens = data.split("\t");
            for(String token:tokens){
                System.out.println(token);
                // System.out.print(tokens+"\t| ");
            }
        }
        System.out.println();

        //Regular Expression
        /*
            X?	X 可出現一次或完全沒有
            X*	X 可出現零次或多次
            X+	X 可出現一次或多次
            X{n}	X 可出現 n 次
            X{n,}	X 可出現至少n次
            X{n, m}	X 可出現至少 n 次，但不超過 m 次
            X?	X 可出現一次或完全沒有
         */
        BufferedReader reader = 
            new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("abcdefgabcabc".replaceAll(".bc", "###")); 

        String phoneEL = "[0-9]{4}-[0-9]{6}";
        String urlEL = "<a.+href*=*['\"]?.*?['\"]?.*?>";
        String emailEL = "^[_a-z0-9-]+(.[_a-z0-9-]+)*" + "@[a-z0-9-]+([.][a-z0-9-]+)*$";

        System.out.print("輸入手機號碼: "); 
        String input = reader.readLine();

        if(input.matches(phoneEL)) 
            System.out.println("格式正確"); 
        else 
            System.out.println("格式錯誤");

        System.out.print("輸入href標籤: "); 
        input = reader.readLine();

        // 驗證href標籤 
        if(input.matches(urlEL))
            System.out.println("格式正確"); 
        else
            System.out.println("格式錯誤");

        System.out.print("輸入電子郵件: "); 
        input = reader.readLine();

        // 驗證電子郵件格式 
        if(input.matches(emailEL))
            System.out.println("格式正確"); 
        else
            System.out.println("格式錯誤"); 
        
    }
}
