import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// @SuppressWarnings("unused")
public class RegExp {
    private static boolean palindrome(String str,int l){
        char a,b;
        for(int i=0;i<l/2;i++){
            a=str.charAt(i);
            b=str.charAt(l-1-i);
            if(a!=b){
                if(!Character.isLetter(a) || !Character.isLetter(b)) return false;
                
                String convertedA=String.valueOf(a);
                String convertedB=String.valueOf(b);

                if(!convertedA.equals(convertedB)) return false;
            }
        }
        return true;
    }   
    private static boolean includeString(String str1,String str2,int l1,int l2){
        //str1 is longer
        for(int i=0;i<=l1-l2;i++){
            if(str2.equals(str1.substring(i,i+l2))) 
                return true;
        }
        return false;
    }
    private static boolean countString(String str1,String str2,int l1,int l2,int n){
        int cnt=0;
        for(int i=0;i<=l1-l2;i++){
            if(str2.equals(str1.substring(i,i+l2))) 
                cnt++;
            if(cnt>=n)
                return true;
        }

        return false;
    }
    private static boolean matchPattern(String str,int l){
        /*
        String pattern=null;
        for(int m=1;m<(l/3)+1;m++){
            pattern=new String(String.format(".*[aA]{%d}.*[bB]{%d}.*", 1,2));//no need to iterate}
        }
        */
            ////////////////
            int idx=l-1;
            int b=0;
            //count how many consecutive b are there
            while(idx>=0){
                b=0;
                if(str.charAt(idx)=='b'){
                    while(idx>=0&&str.charAt(idx)=='b'){
                        idx--;
                        b++;
                    }
                    if(b>=2) break;
                }
                idx--;
            }
            idx++;
            int idx2=0;
            while(idx2<idx){
                if(str.charAt(idx2)=='a') return true;
                idx2++;
            }
            ///////////////////
            //".*[aA]{%d}.*[bB]{%d}.*"
        return false;
    }
    private static void printAns(char[] ans){
        for(int i=0;i<3;i++)
            System.out.print(ans[i]+",");
        System.out.println(ans[3]);
    }
    
    public static void main(String[] args) {
        /*
        1.palindrome
        2.include str1 
        3.contians str2 >= n times, answer Y or N
        */
        String str1=new String(args[1]),str2=new String(args[2]);
        str1=str1.toLowerCase();str2=str2.toLowerCase();
        int n = Integer.parseInt(args[3]);
        int l1=str1.length(),l2=str2.length();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line;
            while((line = reader.readLine())!= null){
                String str= new String(line);
                str=str.toLowerCase();
                char[] ans = new char[5];
                int l = str.length();

                ans[0]=(palindrome(str,l))? 'Y':'N';

                if(l>=l1) ans[1]=(includeString(str, str1,l,l1))? 'Y':'N';
                else ans[1]='N';

                if(l>=l2) ans[2]=(countString(str, str2,l,l2,n))? 'Y':'N';
                else ans[2]='N';
                
                ans[3]=(matchPattern(str,l))? 'Y':'N';
                
                printAns(ans);
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        
        /*
        This is a bug, but is fixed.
        ABC to ot CBA
        ABCBA
        ABCCBA
        AA
        aA
        Aaa aBbBbbbb
        Abmxabcbbc 

        N,N,N,N
        Y,Y,N,N
        Y,Y,N,N
        Y,Y,N,N
        Y,N,N,N
        Y,N,N,N
        N,N,Y,Y
        N,Y,Y,Y
        java RegExp ./testcase/tc0 abc b 3 > ./myAns/myAns0 &&
        java RegExp ./testcase/tc1 abc b 3 > ./myAns/myAns1 &&
        java RegExp ./testcase/tc2 abc b 3 > ./myAns/myAns2 &&
        java RegExp ./testcase/tc3 abc b 3 > ./myAns/myAns3 &&
        java RegExp ./testcase/tc4 abc b 3 > ./myAns/myAns4 &&
        java RegExp ./testcase/tc5 abc b 3 > ./myAns/myAns5

        diff  ./testcase/ans0 ./myAns/myAns0 &&
        diff ./testcase/ans1 ./myAns/myAns1 &&
        diff ./testcase/ans2 ./myAns/myAns2 &&
        diff ./testcase/ans3 ./myAns/myAns3 &&
        diff ./testcase/ans4 ./myAns/myAns4 &&
        diff ./testcase/ans5 ./myAns/myAns5 

        */
    }
}