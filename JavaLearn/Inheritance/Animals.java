@SuppressWarnings("unused")
public class Animals {
    public static void main(String args[]){
        Animals animal = new Duck();

        Zebra zebra = new Zebra();
        zebra.run();
    }
}
abstract class Animal{
    int age;
    String gender;
    boolean isMammal(){
        //decide according to the detail of object
        return false;
    }
    void mate(){
        System.out.println("Find a partner.");
    }
}
class Duck extends Animals{
    String beakColor;
    public Duck(){}
    void swim(){
        System.out.println("Duck is swimming.");
    }
    void quack(){
        System.out.println("Duck says quack.");
    }
}
class Zebra extends Animals{
    boolean isWild;
    public Zebra(){

    }
    void run(){
        System.out.println("Zebra is running.");
    }
}    