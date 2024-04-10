import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.IOException;
import java.util.*;

class JsoupExample {
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://pd2-hw3.netdb.csie.ncku.edu.tw/").get();
            System.out.println(Integer.valueOf(doc.title().substring(3)));
            
            Elements stockName = doc.select("th");
            Elements stockPrice = doc.select("td");
            Map<String, String>stock = new LinkedHashMap<>();
            // System.out.println(elements.text());
            for (int i=0;i<stockName.size();i++) {
                stock.put(stockName.get(i).text(),stockPrice.get(i).text());
            }
            for(Map.Entry<String,String> set:stock.entrySet()){
                System.out.println(set.getKey()+" "+set.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}