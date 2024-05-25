import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeMap;


public class TFIDFSearch {
    static long runTime = 0;
    static ArrayList<HashMap<String,Double>> TFIDFMap ;
    static Indexer deserialize(String serializedFileName){
        Indexer deserializedIndexer=null;
        // Deserialization
        try {
            FileInputStream fis = new FileInputStream(serializedFileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            final long startTime = System.currentTimeMillis();
            deserializedIndexer = (Indexer) ois.readObject();
            final long endTime = System.currentTimeMillis();
            System.out.println("Deserialize time(sec) : "+(double) (endTime-startTime)/1000);
            ois.close();
            fis.close();
            return deserializedIndexer;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }
    }

    static void write(String output){
        try (FileWriter fw = new FileWriter("output.txt")) {
            fw.write(output.toString().trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully write to output.txt");
    }

    static PriorityQueue<Map.Entry<Integer,Double>> runSingleQuery(String queryWord, int docsSize, int[] sizeOfEachElement){
        TreeMap<Integer,Double> mp = new TreeMap<>(Collections.reverseOrder());
        PriorityQueue<Map.Entry<Integer, Double>> pq = new PriorityQueue<>(
                Comparator.comparing(Map.Entry<Integer, Double>::getValue).reversed().thenComparing(Map.Entry<Integer, Double>::getKey));
                
        for(int i=0;i<docsSize;i++){
            if(TFIDFMap.get(i).containsKey(queryWord)){
                double resultDouble = TFIDFMap.get(i).get(queryWord);
                mp.put(i,resultDouble);
            }
        }
        pq.addAll(mp.entrySet());
        return pq;
    }
    static PriorityQueue<Map.Entry<Integer,Double>> run(String concatWord, String[] testcase,int docsSize,int[] sizeOfEachElement){
        final long startTime = System.currentTimeMillis();
            List<Integer> buffer=null;
            TreeMap<Integer,Double> mp = new TreeMap<>();
            PriorityQueue<Map.Entry<Integer, Double>> pq = new PriorityQueue<>(
                Comparator.comparing(Map.Entry<Integer, Double>::getValue).reversed().thenComparing(Map.Entry<Integer, Double>::getKey));

        if(concatWord.equals("AND")){
            Set<Integer> intersect = null;
            
            for(int i=0;i<testcase.length;i++){
                testcase[i]=testcase[i].toLowerCase().trim();
                buffer=new ArrayList<>();
                for(int j=0;j<docsSize;j++){ 
                    if(TFIDFMap.get(j).containsKey(testcase[i])){
                        //append the doc's num 
                        buffer.add(j);
                    }
                }

                if(intersect!=null)
                    //find intersection
                    intersect.retainAll(new HashSet<>(buffer));
                else{
                    intersect=new HashSet<>(buffer);
                }   
            }
            
            for(int docNum:intersect){
                double resultDouble = 0;
                for(String tc:testcase){
                    if(TFIDFMap.get(docNum).containsKey(tc))
                        resultDouble+=TFIDFMap.get(docNum).get(tc);
                }
                mp.put(docNum, resultDouble);
            }
            pq.addAll(mp.entrySet());
            return pq;
        }else{
            Set<Integer> union = null;
            // HashMap<String,Integer> repStringMap = new HashMap<>();
            
            for(int i=0;i<testcase.length;i++){
                testcase[i]=testcase[i].toLowerCase().trim();
                
                buffer=new ArrayList<>();
                for(int j=0;j<docsSize;j++){ 
                    //append the doc's num 
                    buffer.add(j);
                }

                union=new HashSet<>(buffer);
            }
            
            for(int docNum:union){
                double resultDouble = 0;
                for(String tc:testcase){
                    if(TFIDFMap.get(docNum).containsKey(tc))
                        resultDouble+=TFIDFMap.get(docNum).get(tc);
                }
                mp.put(docNum, resultDouble);
            }
            pq.addAll(mp.entrySet());
            return pq;
        }
    }
    
    public static void main(String[] args) {
        String SERIALIZED_FILE_NAME = args[0]+".ser",TC_PATH = args[1],line=null,concatWord =null, queryWord=null;
        String[] tesetcase=null;
        int[] sizeOfEachElement=null;
        BufferedReader tcReader = null;
        int numOfOutput=0,inputFileSize=0;
        
        Indexer idx = deserialize(SERIALIZED_FILE_NAME);
        sizeOfEachElement=idx.sizeOfEachElement;
        inputFileSize = sizeOfEachElement.length;
        TFIDFMap =idx.TFIDFMap;
        ///////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////

        try { 
            tcReader = new BufferedReader(new FileReader(TC_PATH));
            line=tcReader.readLine();
            numOfOutput=Integer.parseInt(line);
            try (FileWriter writer = new FileWriter("output.txt")) {  
                while ((line = tcReader.readLine()) != null) {
                    StringJoiner output = new StringJoiner(" ");
                    // TreeMap<Integer, Double> map =null;
                    TreeMap<Double,Integer> oneWordMap = null;
                    concatWord=null;
                    if(line.indexOf("AND")>=0) concatWord="AND";
                    else if(line.indexOf("OR")>=0) concatWord="OR";

                    if(concatWord==null){
                        queryWord=line.trim();
                        PriorityQueue<Map.Entry<Integer, Double>> pq =null;
                        pq=runSingleQuery(queryWord, inputFileSize, sizeOfEachElement);
                        int writtenDataNum=0;
                        while(!pq.isEmpty() && writtenDataNum < numOfOutput){
                            Map.Entry<Integer,Double> entry = pq.poll();
                            if(entry.getValue()!=0){
                                output.add(entry.getKey().toString());
                                writtenDataNum++;
                            }
                        }
                        //fill outpdut so that it can print -1 if element is insufficient
                        while(writtenDataNum++ < numOfOutput){
                            output.add("-1");
                        }

                    }else{

                        tesetcase=line.split(concatWord);
                        //save the map as list and sort it by value
                        PriorityQueue<Map.Entry<Integer, Double>> pq =null;
                        pq=run(concatWord, tesetcase,inputFileSize,sizeOfEachElement);
                        
                        int writtenDataNum=0;
                        while(!pq.isEmpty() && writtenDataNum < numOfOutput){
                            Map.Entry<Integer,Double> entry = pq.poll();
                            if(entry.getValue()!=0){
                                output.add(entry.getKey().toString());
                                writtenDataNum++;
                            }
                        }
                        //fill output so that it can print -1 if element is insufficient
                        while(writtenDataNum++ < numOfOutput){
                            output.add("-1");
                        }
                    }
                    writer.write(output.toString()+"\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            tcReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Run time: "+runTime);
        }
        
}
