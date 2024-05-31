package reference;

import java.util.HashMap;

class Node{
    Node[] children;
    boolean isEnd;
    int count;
    public Node(){
        this.isEnd=false;
        this.children=new Node[26];
        this.count=0;
    }
}
public class Trie{
    Node root=new Node();
    HashMap<String,Double> TFIDFMap;
    Trie(){
        this.TFIDFMap=new HashMap<>();
    }
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