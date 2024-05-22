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
import java.util.List;

public class BuildIndex {
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

        inputFileSize=inputFileList.size();
        sizeOfIputFileList=new int[inputFileSize];//store how many data in each article

        Trie[] TrieList=new Trie[inputFileSize];
        for(int i=0;i<inputFileSize;i++){
            TrieList[i]=new Trie();
            for(String str:inputFileList.get(i)){
                TrieList[i].insert(str);
            }
            sizeOfIputFileList[i]=inputFileList.get(i).size();
        }

        //initialize object of Indexer
        Indexer idx = new Indexer(inputFileSize);
        idx.TrieList=TrieList;
        idx.sizeOfEachElement=sizeOfIputFileList;

        //serialize the object
        serialize(OUTPUT_FILE_NAME,idx);
        System.out.println("Serialization Success");
    }
}
