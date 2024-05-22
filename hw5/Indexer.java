import java.io.*;
class Node implements Serializable{
    private static final long serialVersionUID = 1L;
    final Node[] children;
    boolean isEnd;
    int count;
    public Node(){
        this.isEnd=false;
        this.children=new Node[26];
        this.count=0;
    }
}
class Trie implements Serializable {
    private static final long serialVersionUID = 1L;
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
	Trie[] TrieList;
    Trie bigTrie;// mutable value
    int[] sizeOfEachElement;

    Indexer(int trieSize){
        this.TrieList = new Trie[trieSize];
        this.bigTrie = new Trie();
        this.sizeOfEachElement = new int[trieSize];
    }
}