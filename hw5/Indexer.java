import java.io.*;
import java.util.HashMap;
import java.util.*;
class Node implements Serializable{
    final Node[] children;
    boolean isEnd;
    int count;
    public Node(){
        this.isEnd=false;
        this.children=new Node[26];
        this.count=0;
    }
}
class Trie implements Serializable{
    final Node root=new Node();
    public void insert(String word) {
        Node node = root;
        if(search(word)){
            for(char c:word.toCharArray()){
                node=node.children[c-'a'];
            }
            node.isEnd=true;
            node.count++;
            return;
        }
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new Node();
            }
            node = node.children[c - 'a'];
        }
        node.isEnd = true;
        node.count+=1;
    }

    //Overload the insert function
    public void insert(String word,int count) {
        Node node = root;
        if(search(word)){
            return;
        }
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new Node();
            }
            node = node.children[c - 'a'];
        }
        node.isEnd = true;
        node.count=count;
    }
    
    // 搜尋 Trie 中是否存在該單詞
    public boolean search(String word) {
        Node node = root;
        for (char c : word.toCharArray()) {
            node = node.children[c - 'a'];
            if (node == null) {
                return false;
            }
        }
        return node.isEnd;
    }
    public int getCount(String word){
        Node node = root;
        if(!search(word)) return 0;
        for(char c:word.toCharArray()){
            node = node.children[c - 'a'];
        }
        return node.count;
    }
}
public class Indexer implements Serializable {
    private static final long serialVersionUID = 1L;
	transient Trie[] TrieList;
    int[] sizeOfEachElement;
    ArrayList<HashMap<String,Double>> TFIDFMap ;
}
/* 
    // BufferedReader dataReader = null;    
    // List<List<String>> inputFileList=new ArrayList<>();
        try{
            dataReader = new BufferedReader(new FileReader("./data/corpus0.txt"));
            inputFileList=dataToList(dataReader);
            dataReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputFileSize=inputFileList.size();
        sizeOfEachElement=new int[inputFileSize];//store how many data in each article

        Trie[] TrieList=new Trie[inputFileSize];
        
        for(int i=0;i<inputFileSize;i++){
            TrieList[i]=new Trie();
            for(String str:inputFileList.get(i)){
                TrieList[i].insert(str);
            }
            sizeOfEachElement[i]=inputFileList.get(i).size();
        }
        long end1 = System.currentTimeMillis();
        System.out.println("plant tree time: "+(end1-start1));
        */