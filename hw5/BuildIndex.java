/*
 * This will call the Indexer class
 * This is to serialize the search engine
 * save the output as corpus1.ser
 */

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class Calculator{
    static Trie bigTrie=new Trie();
    static int inputFileSize;
    static int[] sizeOfIputFileList;
    public static double tf(int docSize, String term, Trie[] TrieList,int docNum) {
        int numOfTerm=0;
        numOfTerm=TrieList[docNum].getCount(term);

        return  (double) numOfTerm /  docSize;
    }
    public static double idf(int docsSize, String term, Trie[] TrieList) {
        int numOfDocHasTerm=0;
        if(bigTrie.getCount(term)!=0) numOfDocHasTerm=bigTrie.getCount(term);

        else {
            for(int i=0;i<TrieList.length;i++){
                if(TrieList[i].search(term))
                    numOfDocHasTerm++;
            }
            bigTrie.insert(term,numOfDocHasTerm);
        }
        return Math.log((double) docsSize / numOfDocHasTerm);
    }
    
    public static double tfIdfCalculate(String term,Trie[] TrieList,int docNum) {
        return (double) tf(sizeOfIputFileList[docNum], term,TrieList,docNum) * idf(inputFileSize, term,TrieList);
    }
}

public class BuildIndex {
    static ArrayList<HashMap<String,Double>> TFIDFMap = new ArrayList<>();

    static void serialize(String outputFileName, Indexer idx){
        // Serialization
        try {
            FileOutputStream fos = new FileOutputStream(outputFileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(idx);
            
            oos.close();
            fos.close();
            
        } catch (IOException e) {
            e.printStackTrace();	
        }
    }
    static List<List<String>> dataToList(BufferedReader br){
        int lineCount=0;//count what line it's reading now
        List<List<String>> inputFileList=new ArrayList<>();
        List<String> temp=new ArrayList<>();
        String line=null;
        try { 
            while ((line = br.readLine()) != null) {
                line = line.toLowerCase().replaceAll("[^a-z\\s]", " ").trim();//replacing iillegal characters and remove the starting and ending spaces 
                if (!line.isEmpty()) {
                    String[] words = line.split("\\s+");
                    temp.addAll(Arrays.asList(words));
                    lineCount++;
                    if (lineCount % 5 == 0) {
                        inputFileList.add(new ArrayList<>(temp));
                        temp.clear();
                    }
                }
            }
            if (!temp.isEmpty()) {
                inputFileList.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputFileList;
    }
    public static void main(String[] args) {
        String CORPUS_FILE_PATH=args[0],OUTPUT_FILE_NAME;
        OUTPUT_FILE_NAME = CORPUS_FILE_PATH.substring(CORPUS_FILE_PATH.lastIndexOf('/')+1,CORPUS_FILE_PATH.length()-3);
        OUTPUT_FILE_NAME+="ser";
        System.out.println(OUTPUT_FILE_NAME);
        List<List<String>> inputFileList=new ArrayList<>();
        BufferedReader dataReader = null;
        int inputFileSize=0;
        int[] sizeOfIputFileList=null;
        
        try{
            //read file and create bufferedReader
            dataReader = new BufferedReader(new FileReader(CORPUS_FILE_PATH));
            //convert texts in corpus.txt to List<List<String>>
            inputFileList=dataToList(dataReader);
            dataReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Calculator.inputFileSize=inputFileList.size();
        Calculator.sizeOfIputFileList=new int[Calculator.inputFileSize];//store how many data in each article
        Trie[] TrieList=new Trie[Calculator.inputFileSize];
        for(int i=0;i<inputFileList.size();i++){
            TrieList[i]=new Trie();
            for(String str:inputFileList.get(i)){
                TrieList[i].insert(str);
            }
            Calculator.sizeOfIputFileList[i]=inputFileList.get(i).size();
        }
        // System.out.println(inputFileSize);
        for(int i=0;i<Calculator.inputFileSize;i++){
            TFIDFMap.add(new HashMap<>());
            // System.out.println(i);
            for(String str:inputFileList.get(i)){
                double d= Calculator.tfIdfCalculate( str, TrieList, i);
                TFIDFMap.get(i).put(str,d);
                // System.out.println(str+" "+TFIDFMap.get(i).get(str));
            }
        }
        //initialize object of Indexer
        Indexer idx = new Indexer();
        // idx.TrieList=TrieList;
        idx.sizeOfEachElement=Calculator.sizeOfIputFileList;
        idx.TFIDFMap=TFIDFMap;

        //serialize the object
        serialize(OUTPUT_FILE_NAME,idx);
        System.out.println("Serialization Success");
    }
}
