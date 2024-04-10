import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;
import java.util.*;
class Math{
    public static String SMA(){

        return "";
    }
    public static String stdDeviation(){
        
        return "";
    }

}
class HtmlParser{
    static void writer(Map<String,List<String>> stockMap){
        //initialize FileWriter
        FileWriter fileWriter;
        final String fileName = "output.csv";
        try{
            //for testing, append mode turned off
            fileWriter = new FileWriter(fileName,false);
            BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);

            // bufferedWriter.write(content);
            String content="";
            for(Map.Entry<String,List<String>> keys: stockMap.entrySet()){
                
            }

            bufferedWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    static Map<String,List<String>> updateMap(Document webPage,Map<String,List<String>> stockMap){
        // Elements stockName = webPage.select("th");
        Elements stockPrice = webPage.select("td");
        int idx=0;
        for(Map.Entry<String,List<String>> set: stockMap.entrySet()){
            set.getValue().add(stockPrice.get(idx++).text());
        }
        return stockMap;
    }

    static Map<String,List<String>> initialMap(Document webPage){
        Map<String,List<String>> stock = new LinkedHashMap<>();
        Elements stockName = webPage.select("th");
        Elements stockPrice = webPage.select("td");
    
        for(int i=0;i<stockName.size();i++){
            List<String> ls=new ArrayList<>();
            ls.add(stockPrice.get(i).text());
            stock.put(stockName.get(i).text(),ls);
        }
        return stock;
    }

    public static void main(String[] args) {    
        String mode="",task="",stock="",start="",end=""; //args
        String URL = "https://pd2-hw3.netdb.csie.ncku.edu.tw/";
        String BUFFER_INPUT = "input.csv";
        Map<String,List<String>> stockMap = new LinkedHashMap<>();
        Document webPage=null;
        int day=0;
        mode=args[0];
        if(mode.equals("0")){
            // 爬蟲 mode
            try{
                webPage = Jsoup.connect(URL).get();
                // System.out.println(webPage.title());
                day=Integer.valueOf(webPage.title().substring(3));
                // if(day!=1) return;
            }catch (IOException e) {
                e.printStackTrace();
                System.out.println("filePathNotFound");
                return;
            }

            File checkEmpty = new File(BUFFER_INPUT);
            if(checkEmpty.length()==0){
                //Gotta intialize the file
                stockMap=initialMap(webPage);

            }else{
                //has been initialized, could be day>1
                stockMap=updateMap(webPage, stockMap);
            }
            //write to input.csv
            writer(stockMap);
            return;
        }
        // else mode equals to 1
        
        
        
        
        task=args[1];
        switch (task) {
            case "0":
            
                return;
            case "1":
                //simple moving average
                
                break;
            case "2":
                //standard deviation

                break;
            case "3":
                //top3 standard deviation

                break;
            case "4":
                //linear regression 

                break;
            default:
                System.out.println("Invalid task num");
                break;
        }
        

        stock=args[2];
        start=args[3];
        end=args[4];
    }

}