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
class Trie{
    Node root=new Node();
    public void insert(String word) {
        Node node = root;
        if(search(word)){
            for(char c:word.toCharArray()){
                node=node.children[c-'a'];
            }
            node.count++;
            return;
        }
        for (char c : word.toCharArray()) {
            if(c-'a'<0||c-'z'>0) System.out.println("exist non-alphabet!");
            if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new Node();
            }
            node = node.children[c - 'a'];
        }
        node.isEnd = true;
        node.count=1;
    }
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
        if(!search(word)) return -1;
        for(char c:word.toCharArray()){
            node = node.children[c - 'a'];
        }
        return node.count;
    }
    public void insert(String str, int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }
    public void insert(String str, boolean flag) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

}
public class temp {
    public static void main(String[] args) {
        Trie myTrie = new Trie();
        myTrie.insert("apple");
        myTrie.insert("apple");
        myTrie.insert("appl");
        System.out.println(myTrie.getCount("apple"));
    }
}
