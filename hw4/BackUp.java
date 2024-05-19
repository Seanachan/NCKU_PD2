import java.util.ArrayList;
import java.util.List;
import java.io.*;

class TFIDFCalculator{
    static Trie bigTrie=new Trie();

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
    
    public static double tfIdfCalculate(int docsSize, int docSize, String term,Trie[] TrieList,int docNum) {
        return (double) tf(docSize, term,TrieList,docNum) * idf(docsSize, term,TrieList);
    }
    
    static List<List<String>> dataToList(BufferedReader br){
        int lineCount=0;//count what line it's reading now
        List<List<String>> inputFileList=new ArrayList<>();
        List<String> temp=new ArrayList<>();
        String line=null;
        try { 
            while((line = br.readLine()) != null){
                // String line=lineScanner.nextLine().toLowerCase();
                line=line.toLowerCase();
                String[] arr=line.split("\t")[1].split("\\s+");//abandon the number in the front

                String[] splited;//store the str after replacing non-character with space
                for(String str:arr){
                    str=str.replaceAll("[^a-z]", " ");
                    splited=str.split(" ");
                    for(String s:splited){
                        if(s.length()>0) temp.add(s);
                    }
                }
                lineCount++;
                if(lineCount%5==0) {
                    inputFileList.add(temp);
                    temp=new ArrayList<>();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputFileList;
    }
    public static void main(String[] args) {
        String INPUT_FILE_NAME=args[0],TESTCASE_FILE_PATH=args[1];
        List<List<String>> inputFileList=new ArrayList<>();
        BufferedReader dataReader = null;
        int inputFileSize=0;
        int[] sizeOfIputFileList=null;
        try{
            //read file and create bufferedReader
            dataReader = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        
            //convert texts in docs.txt to List<List<String>>
            inputFileList=dataToList(dataReader);
            dataReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputFileSize=inputFileList.size();
        sizeOfIputFileList=new int[inputFileSize];

        Trie[] TrieList=new Trie[inputFileSize];
        for(int i=0;i<inputFileSize;i++){
            TrieList[i]=new Trie();
            for(String str:inputFileList.get(i)){
                TrieList[i].insert(str);
            }
            sizeOfIputFileList[i]=inputFileList.get(i).size();
        }

        String[] queryTerms=null;
        String[] queryDocNum=null;
        try {
            BufferedReader tcReader = new BufferedReader(new FileReader(TESTCASE_FILE_PATH));
            
            queryTerms= tcReader.readLine().split("\\s+");
            queryDocNum = tcReader.readLine().split("\\s+");
            tcReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < queryDocNum.length; i++) {
            int num = Integer.parseInt(queryDocNum[i]);
            double resultDouble = tfIdfCalculate(inputFileSize, sizeOfIputFileList[num], queryTerms[i], TrieList, num);
            output.append(String.format("%.5f", resultDouble)).append(" ");
        }

        try (FileWriter writer = new FileWriter("output.txt")) {
            writer.write(output.toString().trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}