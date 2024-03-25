import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
public class CodeGenerator {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("請輸入mermaid檔案名稱");
            return;        
        }
        // get input
        String fileName = args[0];
        String mermaidCode = "";

        FileReader mermaidCodeReader = new FileReader();
        mermaidCode = mermaidCodeReader.read(fileName);

        Map<String,List<String>> classMap= new HashMap<>();

        classMap=Parser.splitByClass(mermaidCode);

        for(Map.Entry<String,List<String>> entry:classMap.entrySet()){
            String className=entry.getKey();
            List<String> listOfClass = entry.getValue();
            listOfClass=Parser.parse(listOfClass);
            FileReader.writer(className,listOfClass);
        }
    }
}
class FileReader {
    public String read(String fileName) {
        String mermaidCode="";
        try {
            mermaidCode = Files.readString(Paths.get(fileName));
        }
        catch (IOException e) {
            System.err.println("無法讀取文件 " + fileName);
            e.printStackTrace();
            return null;
        }
        return mermaidCode;
    }
    public static void writer(String fileName,List<String> ls){
        // 寫入文件
        try {
            String output = fileName+".java";
            // String content = "this is going to be written into file";
            File file = new File(output);
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("public class "+fileName+" {\n");
                for (String line : ls) {
                    bw.write(line+"\n");
                }
                // bw.write(content);
                bw.write("}\n");
            }
            // System.out.println("Java class has been generated: " + output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Parser {
		public static Map<String,List<String>>  splitByClass(String input) {
            Map<String,List<String>> mp=new HashMap<>();
            Scanner lineScanner = new Scanner(input);
            String curLine="",cur="";
            String curClass="";
            while(lineScanner.hasNextLine()){
                curLine=lineScanner.nextLine();
                if(curLine.equals("}")) continue;
                if(curLine.equals("classDiagram")||curLine.length()==0||curLine.equals("    "))
                    continue;
                Scanner indiScanner = new Scanner(curLine);
                if(indiScanner.hasNext())cur=indiScanner.next();
                // System.out.println("cur: "+cur);
                // cur=indiScanner.next();
                if(cur.equals("class")){
                    //class Car, append key='Car' to hashmap
                    List<String> list=new ArrayList<>();
                    curClass=indiScanner.next();
                    curClass=curClass.substring(0,curClass.length());
                    if(mp.containsKey(curClass)) continue;
                    mp.put(curClass,list);
                }else{
                    if(!curLine.contains(":")){
                        String toAdd=cur+" ";
                        while(indiScanner.hasNext())
                            toAdd+=indiScanner.next()+" ";
                        mp.get(curClass).add(toAdd);
                    }
                    else if(mp.containsKey(cur)){
                        curClass=cur;
                        //cur='BankAccount' is in mp
                        indiScanner.next();//deal with ':'
                        String toadd="";
                        while(indiScanner.hasNext())
                            toadd+=indiScanner.next()+" ";
                        
                        mp.get(cur).add(toadd);
                    }else{
                        List<String> list=new ArrayList<>();
                        mp.put(cur,list);
                    }

                }
                indiScanner.close();
            }
            lineScanner.close();
            
            return mp;
		}
    public static List<String> parse(List<String> ls){
        String TAB="    ";
        List<String> output = new ArrayList<>();
        for (String str : ls) {
            String cur=TAB;
            if(str.length()<=1) continue;
            if(str.charAt(0)=='+'){
                cur+="public ";
                str=str.substring(1);
            }
            else if(str.charAt(0)=='-'){
                cur+="private ";
                str=str.substring(1);
            }
            Map<String,String> varType = new HashMap<>();
            if(str.contains("(")){
                //is a function
                if(str.contains("get")||str.contains("set")){
                    //is a setter or getter
                    String[] splittedList = new String[5];
                    splittedList=str.split(" ");

                    if(str.contains("get")){
                        //a get function
                        String varString = splittedList[0];
                        varString = varString.substring(3,varString.indexOf("("));
                        varString=varString.substring(0,1).toLowerCase()+varString.substring(1);

                        cur+=splittedList[1]+" "+splittedList[0]+" {\n";
                        cur+=TAB+TAB+"return "+varString+";\n"+TAB+"}";

                    }else{
                        //a set function
                        String varString=splittedList[1].substring(0,splittedList[1].length()-1);

                        cur+=splittedList[2]+" "+splittedList[0]+" "+splittedList[1]+" {\n";

                        cur+=TAB+TAB+"this."+varString+" = "+varString+";\n"+TAB+"}";
                    }

                }else{
                    //normal function, return its designated type
                    String[] typeAndName=new String[10];
                    typeAndName=str.split(" ");
                    String[] types={"void","String","int","char","double","float","byte","long","short","boolean"};
                    boolean hasType=false;
                    String type="";
                    for(int i=0;i<10;i++){
                        if(typeAndName[typeAndName.length-1].contains(types[i])) {
                            hasType=true;
                            break;
                        }
                    }
                    if(hasType) {
                        cur+=typeAndName[typeAndName.length-1];
                        type=typeAndName[typeAndName.length-1];
                        for(int i=0;i<typeAndName.length-1;i++) {
                            cur+=" "+typeAndName[i];
                        }
                    }
                    else{
                        cur+="void";type="void";
                        for(int i=0;i<typeAndName.length-1;i++) {
                            cur+=" "+typeAndName[i];
                        }
                        cur+=" "+typeAndName[typeAndName.length-1];
                    }
                    cur+=" {";
                    switch (type) {
                        case "boolean":
                            cur+="return false;}";
                            break;
                        case "int":
                            cur+="return 0;}";
                            break;
                        case "String":
                            cur+="return \"\";}";
                            break;
                        case "char":
                            cur+="return \'\'';}";
                            break;
                        case "void":
                            cur+=";}";
                            break;
                        case "float":
                            cur+="return 0.0;}";
                            break;
                        case "double":
                            cur+="return 0.0;}";
                            break;
                        default:
                            cur+="return 0;}";
                            break;
                    }
                }
            }else{
                //is a variable
                cur+=str.substring(0, str.length()-1)+";";
                String[] temp= new String[3];
                temp=cur.split(" ");
                varType.put(temp[1],temp[0]);
            }
            
            output.add(cur);
        }
        return output;
    }
}