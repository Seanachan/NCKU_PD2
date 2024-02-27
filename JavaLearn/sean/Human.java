package sean;

public class Human {
    String name;
    int age, height, weight;
    public Human(String str) {
        name = str;
    }// end of constructor(String)
    public void setValue(int a, int h, int w) {
        age = a;
        height = h;
        weight = w;
    }// end of setValue(int,int)
}
