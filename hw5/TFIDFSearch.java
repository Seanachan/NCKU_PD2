/**
 * TFIDFSearch
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Calculator{
    public static double tf(int docSize, String term, Trie[] TrieList,int docNum, Trie bigTrie) {
        int numOfTerm=0;
        numOfTerm=TrieList[docNum].getCount(term);
        return  (double) numOfTerm /  docSize;
    }
    public static double idf(int docsSize, String term, Trie[] TrieList, Trie bigTrie) {
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
    
    public static double tfIdfCalculate(int docsSize, int docSize, String term,Trie[] TrieList,int docNum, Trie bigTrie) {
        return (double) tf(docSize, term,TrieList,docNum,bigTrie) * idf(docsSize, term,TrieList, bigTrie);
    }
}
public class TFIDFSearch {
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
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }
        return deserializedIndexer;
    }
    static void write(String output){
        try (FileWriter fw = new FileWriter("output.txt")) {
            fw.write(output.toString().trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully write to output.txt");
    }
    public static void main(String[] args) {
        String SERIALIZED_FILE_NAME = args[0]+".ser",TC_PATH = args[1],line=null,concatWord =null;
        String[] tesetcase=null;
        BufferedReader tcReader = null;
        int numOfOutput=0;
        System.out.println("Start Deserializing...");
        Indexer idx=deserialize(SERIALIZED_FILE_NAME);
        List<Boolean> ls = new ArrayList<>();
        
        try { 
            tcReader = new BufferedReader(new FileReader(TC_PATH));
            line=tcReader.readLine();
            numOfOutput=Integer.parseInt(line);
            System.out.println(numOfOutput);
            while ((line = tcReader.readLine()) != null) {
                if(concatWord==null){
                    if(line.indexOf("AND")>=0) concatWord="AND";
                    else if(line.indexOf("OR")>=0) concatWord="OR";
                    
                }
                if(concatWord==null){
                    //query words
                    
                }
                if(concatWord.equals("AND")) {
                    //split words from line
                    tesetcase = line.split("AND");
                    for(String str:tesetcase) str=str.toLowerCase();

                    
                    // TODO: intersection of terms which are to be found --> try HashMap

                }
                else {
                    //split words from line
                    tesetcase = line.split("OR");
                    for(String str:tesetcase) str=str.toLowerCase();

                    // TODO: union of terms which are to be found --> also HashMap

                }

                // TODO: calculate the tf-idfs and sum them up

                // TODO : store the sum results to Lists

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //TODO: write answer to output.txt
        // write();
    }
    
}