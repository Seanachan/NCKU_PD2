class Engine{
    private String type;
    public Engine(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }
}
class Wheel {
    private int size;
    public Wheel(int size){
        this.size=size;
    }
    public int getSize(){
        return size;
    }
}
public class Car {
    private Engine engine;
    private Wheel[] wheels;
    public Car(String engineType,int wheelSize){
        this.engine=new Engine(engineType);
        this.wheels=new Wheel[4];//4 wheels
        for(int i=0;i<wheels.length;i++){
            wheels[i]=new Wheel(wheelSize);
        }
    }
    public String getEngineType(){
        return engine.getType();
    }
    public int getWheelSize(){
        return wheels[0].getSize();
    }
}
