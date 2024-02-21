package JavaLearn;
public class object {
    public static void main(String[] args){
        int myInt = 10;
        Integer myInteger = 10;//autoboxing: it's a comiler sugar
        // Integer myInteger = new Integer(12);

        System.out.println(myInt/3);//3
        System.out.println((myInteger.doubleValue()/3));//3.3333333333333335

        //unbox first, then add, and boxing
        Integer i = 10;
        System.out.println(i+10);
        System.out.println(i++);

        //careful using boxing
        Integer i1 = 200;
        Integer i2 = 200;
        //while autoboxing, value that's not between -128, 127 will create an object everytime, so i1 and i2 reference different object
        if(i1==i2)
            System.out.println("i1==i2");
        else
            System.out.println("i1!=i2");
        
        i1=100;i2=100;
        if(i1==i2)
            System.out.println("i1==i2");
        else
            System.out.println("i1!=i2");
        
        //correct way
        if(i1.equals(i2))
            System.out.println("i1==i2");
        else
            System.out.println("i1!=i2");
        

    }    
}
