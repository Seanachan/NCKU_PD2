import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
class MyMath{
    public static String round(Double num){
        String ans=String.format("%.2f",num);
        int l=ans.length();
        if(ans.charAt(l-1)=='0') {
            l=ans.length();
            ans=ans.substring(0, l-1);
            l=ans.length();
            if(ans.charAt(l-1)=='0')
                ans=ans.substring(0, l-2);
        }
        return ans;
    }
    public static List<String> SMA(String stockName,Integer start,Integer end,List<List<String>> data){
        List<String> ls = new ArrayList<>();
        int idx=0;
        for(int i=0;i<data.size();i++){
            //get the index of designated stockName
            if(data.get(i).get(0).equals(stockName)) {
                idx=i;
                break;
            }
        }
        Double sum=0.0;
        ls.add(stockName);
        ls.add(start.toString());
        ls.add(end.toString());

        for(int i=start;i<=end-4;i++){
            sum=0.0;
            for(int j=i;j<i+5;j++){
                sum+=Double.parseDouble(data.get(idx).get(j));
            }
            sum=sum/5;
            String str=MyMath.round(sum);
            ls.add(str);
        }
        
        return ls;
    }
    public static Double average(List<String> data,Integer start,Integer end){
        //first element is the name
        Double avg=0.0;
        int length=end-start+1;

        for(int i=start;i<=end;i++){
            avg+=Double.parseDouble(data.get(i));
        }
        avg/=length;
        return avg;
    }
    public static List<String> stdDeviation(String stockName,Integer start,Integer end, List<List<String>> data){
        List<String> ls=new ArrayList<>(); 
        int idx=0;
        for(int i=0;i<data.size();i++){
            if(data.get(i).get(0).equals(stockName)){
                idx=i;
                break;
            }
        }
        Double avg=MyMath.average(data.get(idx),start,end);
        // System.out.println("Average: "+avg.toString());
        
        Double deviation=0.0,cur=0.0;
        for(int i=start;i<=end;i++){
            cur=Double.parseDouble(data.get(idx).get(i));

            deviation+=(cur-avg)*(cur-avg);
        }
        deviation/=(end-start);
        deviation=MyMath.sqrt(deviation);
        ls.add(stockName);ls.add(start.toString());ls.add(end.toString());
        ls.add(MyMath.round(deviation));
        return ls;
    }

    public static List<String> regression(String stockName,Integer start,Integer end,List<List<String>> data){
        List<String> ls=new ArrayList<>();
        Double b0=0.0,b1=0.0;
        int idx=0;
        for(int i=0;i<data.size();i++){
            if(data.get(i).get(0).equals(stockName)) 
                idx=i;
        }
        double time_avg=(end+start)/2.0,avg=MyMath.average(data.get(idx), start, end),cur=0.0;
        double up=0.0,down=0.0;
        for(int i=start;i<=end;i++){
            cur=Double.parseDouble(data.get(idx).get(i));
            up+=(cur-avg)*(i-time_avg);
            down+=(i-time_avg)*(i-time_avg);
        }
        b1=up/down;
        b0=avg-(b1*time_avg);

        ls.add(stockName);ls.add(start.toString());ls.add(end.toString());
        ls.add(MyMath.round(b1));ls.add(MyMath.round(b0));
        return ls;
    }
    
    public static double abs(double x){
        if(x<0){
            return (-1)*x;
        }
        return x;
    }
    public static double sqrt(double c){
        if(c<0) return Double.NaN;
        double err=1e-7;
        double x=c;
        while(MyMath.abs(x-c/x)>err){
            x=(x+c/x)/2.0;
        }
        return x;
    }
}
class Writer{
    public static void writer(List<List<String>> stockInfo,String fileName,boolean append){
        //initialize FileWriter
        FileWriter fileWriter;
        try{
            //for testing, append mode turned off
            fileWriter = new FileWriter(fileName,append);
            BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);

            String content="";
            for(int i=0;i<stockInfo.get(0).size();i++){
                content="";
                //num of columns
                content+=stockInfo.get(0).get(i);
                for(int j=1;j<stockInfo.size();j++){
                    content+=","+stockInfo.get(j).get(i);
                }
                content+="\n";
                bufferedWriter.write(content);
            }

            bufferedWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static void deviationWriter(List<String> stockInfo, String fileName, boolean append){
        //first 3 indexes: stockName, startDate, endDate
        //initialize FileWriter
        FileWriter fileWriter;
        try{
            //for testing, append mode turned off
            fileWriter = new FileWriter(fileName,append);
            BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);

            String content="";
            for(int i=0;i<stockInfo.size();i++){
                if(i==2) content+=stockInfo.get(i)+"\n";
                else
                    if(i!=stockInfo.size()-1)content+=stockInfo.get(i)+",";
                    else content+=stockInfo.get(i)+"\n";
            }
            bufferedWriter.write(content);

            bufferedWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static void topDeviationsWriter(List<List<String>> stockInfo, String fileName, boolean append){
        FileWriter fileWriter;
        try{
            //for testing, append mode turned off
            fileWriter = new FileWriter(fileName,append);
            BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);

            String content="";
            for(int i=0;i<5;i++){
                if(i==4) content+=stockInfo.get(0).get(i)+"\n";
                else content+=stockInfo.get(0).get(i)+",";
            }
            for(int j=0;j<3;j++){
                if(j==2) content+=stockInfo.get(1).get(j)+"\n";
                else content+=stockInfo.get(1).get(j)+",";
            }
            
            bufferedWriter.write(content);

            bufferedWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}


class HtmlParser{
    static List<List<String>> storeList(Document webPage,Elements element){
        List<List<String>> stockInfo=new ArrayList<>();
        
        for(int i=0;i<element.size();i++){
            List<String> temp = new ArrayList<>();
            temp.add(element.get(i).text());
            stockInfo.add(temp);
        }
        return stockInfo;
    }
    
    static List<List<String>> load(String BUFFER_FILE){
        List<List<String>> stockInfo = new ArrayList<>();
        try{
            //scan input data from data.csv
            Scanner line=new Scanner(new File(BUFFER_FILE));
            String curLine = line.nextLine(),cur="";
            String[] nextData=curLine.split(",");
            // cur=nextData.next();
            for(int i=0;i<nextData.length;i++){
                cur=nextData[i];
                List<String> tempList = new ArrayList<String>();
                tempList.add(cur);

                stockInfo.add(tempList);
            }

            /////////////////
            while(line.hasNextLine()){
                curLine=line.nextLine();
                nextData=curLine.split(",");
                int idx=0;
                for(int i=0;i<nextData.length;i++){
                    stockInfo.get(idx++).add(nextData[i]);
                }
            }
            line.close();
        }catch(FileNotFoundException exception){
            exception.printStackTrace();
        }
        return stockInfo;
    }
    public static void main(String[] args) {    
        String mode="",task="",stock="",start="",end=""; //args
        String URL = "https://pd2-hw3.netdb.csie.ncku.edu.tw/",BUFFER_FILE = "data.csv",OUTPUT_FILE="output.csv";
        
        Document webPage = null;
        int day=0;
        mode=args[0];
        if(mode.equals("0")){
            //Scraper Mode: store in 2D List
            //the 0-th element is the stockName
            List<List<String>> stockInfo=new ArrayList<>();
            try{
                webPage=Jsoup.connect(URL).get();
                day=Integer.valueOf(webPage.title().substring(3));
                System.out.printf("day: %d\n",day);
            }catch(IOException e){
                e.printStackTrace();
            }
            File bufFile = new File(BUFFER_FILE);
            File recordFile=new File("record.txt");
            if(!bufFile.exists()){
                if(day!=1){
                    System.out.printf("day: %d, wait for day 1 ...\n",day);
                    return;
                }
                try{
                    boolean success = bufFile.createNewFile();
                    if(success) System.out.println("Successfully create data.csv");
                }catch(IOException exception){
                    exception.printStackTrace();
                    return;
                }
                
                //first initialized, the file has to be given labels(stockName)
                Elements stockName = webPage.select("th");
                stockInfo=storeList(webPage, stockName);
                // System.out.println(stockName.text());
                //write into data.csv
                Writer.writer(stockInfo, BUFFER_FILE,false);
            }else{
                if(day==1){
                    if(recordFile.exists()) return;
                    try{
                        boolean success = recordFile.createNewFile();
                        if(success) {
                            System.out.println("has done looping");
                        }
                    }catch(IOException exception){
                        exception.printStackTrace();
                    }
                    return;
                }
            }
            if(recordFile.exists()){
                System.out.println("has done looping");
                return;
            }
            //has been initialized, could be day>1
            Elements stockPrice = webPage.select("td");
            stockInfo=storeList(webPage,stockPrice);
            // System.out.println(stockPrice.text());
            //write into data.csv
            Writer.writer(stockInfo, BUFFER_FILE,true);
            return;
        }
        
        else if(mode.equals("1")){
            //Analyzing Mode
            List<List<String>> stockInfo= load(BUFFER_FILE);
            task=args[1];
                        
            if(task.equals("0")) {
                //input the collected data
                Writer.writer(stockInfo, OUTPUT_FILE, true);
                return;
            }
            stock=args[2]; start=args[3]; end=args[4];
            switch (task) {
                case "1":
                    //SMA
                    List<String> ListSVM = new ArrayList<>();

                    ListSVM=MyMath.SMA(stock,Integer.parseInt(start),Integer.parseInt(end),stockInfo);
                    Writer.deviationWriter(ListSVM, OUTPUT_FILE, true);//for testing, append==false
                    break;
                case "2":
                    //標準差
                    List<String> ListSTD = new ArrayList<>();
                    ListSTD=MyMath.stdDeviation(stock, Integer.parseInt(start), Integer.parseInt(end), stockInfo);
                    Writer.deviationWriter(ListSTD, OUTPUT_FILE, true);

                    break;
                case "3":
                    //標準差top3
                    List<List<String>> rankSTD = new ArrayList<>();
                    for(int i=0;i<stockInfo.size();i++){
                        List<String> tmpList=MyMath.stdDeviation(stockInfo.get(i).get(0), Integer.parseInt(start), Integer.parseInt(end), stockInfo);
                        rankSTD.add(tmpList);
                    }

                    List<List<String>> ans=new ArrayList<>();
                    Double fDouble=Double.MIN_VALUE,secDouble=Double.MIN_VALUE,thirDouble=Double.MIN_VALUE;
                    String fString="",secString="",thiString="";

                    for(int i=0;i<rankSTD.size();i++){
                        Double cur=Double.parseDouble(rankSTD.get(i).get(3));
                        if(cur>fDouble){
                            thirDouble=secDouble;thiString=secString;
                            secDouble=fDouble;secString=fString;
                            fDouble=cur;fString=rankSTD.get(i).get(0);

                        }else if(cur>secDouble){
                            thirDouble=secDouble;thiString=secString;
                            secDouble=cur;secString=rankSTD.get(i).get(0);
                        }else if(cur>thirDouble){
                            thirDouble=cur;thiString=rankSTD.get(i).get(0);
                        }
                    }
                    List<String> name= new ArrayList<>();
                    List<String> price = new ArrayList<>();
                    name.add(fString);name.add(secString);name.add(thiString);
                    price.add(MyMath.round(fDouble));price.add(MyMath.round(secDouble));price.add(MyMath.round(thirDouble));
                    ans.add(name);ans.add(price);
                    ans.get(0).add(start);ans.get(0).add(end);
                    Writer.topDeviationsWriter(ans, OUTPUT_FILE, true);
                    break;
                case "4":
                    //回歸直線
                    List<String> regLine=MyMath.regression(stock, Integer.parseInt(start), Integer.parseInt(end),stockInfo );
                    Writer.deviationWriter(regLine, OUTPUT_FILE, true);
                    break;
                default:
                System.out.println("Task argument out of range");
                return;
            }

        }else{
            System.out.println("Mode argument out of range");
            return;
        }
    }
}