import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Temp implements Serializable{
    int a;
    public static void main(String[] args) {
        Temp temp = new Temp();
        // Serialization
        try {
            FileOutputStream fos = new FileOutputStream("temp.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(temp);
            
            oos.close();
            fos.close();
            
        } catch (IOException e) {
            e.printStackTrace();	
        }

        // Deserialization
        try {
            FileInputStream fis = new FileInputStream("temp.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Temp t = (Temp) ois.readObject();
            t.f();
            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
    }
}
