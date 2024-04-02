# Call By ??

## All Objects are 'reference'(參照)

在Java中，所有物件變數實際上都是以參照（reference）的形式存在。當創建一個物件變數時，實際上是創建了一個參照，該參照指向物件在記憶體中的位置，而不是物件本身。

因此，你可以將這個參照賦值給另一個物件變數，或者將其作為參數傳遞給方法，這些操作都不會創建新的物件，而只是增加了 **指向** 原始物件的參照數量。

## Primitive Type

基本數據類型（如int、double等）的行為不同，基本數據類型的變數在賦值或傳遞時會創建新的資料實體的copy。

因此，在使用物件變數時，我們必須注意這種參照行為，以避免因不正確的操作而產生意外的結果。

## Call by Value

將一個變數的值傳遞給一個方法。這個方法可能會修改這個值，但是這個改變不會影響原來的變數，因為我們只是將值傳遞給方法，而不是變數本身。

```java
void updateValue(int value) {
    value = 55;
}

public static void main(String[] args) {
    int value = 22;
    System.out.println("Before: " + value);  // Output: Before: 22
    updateValue(value);
    System.out.println("After: " + value);  // Output: After: 22
}

```

## Call by Reference

將一個變數的Reference傳遞給方法。因此，如果方法修改了這個參照參照的物件，那麼這個改變會反映到原來的物件上。這種方式適用於物件。

```java
class MyObject {
    int value;
}

void updateValue(MyObject obj) {
    obj.value = 55;
}

public static void main(String[] args) {
    MyObject obj = new MyObject();
    obj.value = 22;
    System.out.println("Before: " + obj.value);  // Output: Before: 22
    updateValue(obj);
    System.out.println("After: " + obj.value);  // Output: After: 55
}
```

> 但是，當嘗試改變reference本身（例如，使它參照到另一個物件）時，這個改變不會反映到原來的物件上。

```java
public class Main {
    public static void main(String[] args) {
        Dog aDog = new Dog("Max");
        foo(aDog);

        // 當 foo 返回後，aDog 的名字仍然是 "Max"
        System.out.println(aDog.getName()); // 輸出 "Max"
    }
    public static void foo(Dog d) {
        d.getName().equals("Max"); // true
        // 改變 d 內的屬性
        d.setName("Fifi");
        d.getName().equals("Fifi"); // true
        // 但是，當我們嘗試將 d 參照到另一個物件時，這個改變不會反映到原來的物件上
        d = new Dog("Boxer");
        d.getName().equals("Boxer"); // true
    }
}
```

> 即便 foo前面有改變名字成 FiFi， 但因為後面改變reference了，所以此改變不會發生，main裡面print出來的依然是Max

## Dynamic Binding

調用一個物件的方法時，被調用的方法版本（子類別或父類別中的方法）是**在程序運行時基於物件的實際類型決定的，而不是在編譯時。**這是Java多型的核心。**

## this的用法

## Interface

* 將方法聲明和implementation分開
* 無需改變現有的類別，可以更改或增加類別的行為

```java
// 定義一個 Flyable Interface
interface Flyable {
    // Interface中的抽象方法 fly
    void fly();
}

// 定義一個 Bird 類別
class Bird {
    // Bird的行為
    void eat() {
        System.out.println("Bird is eating");
    }
}

// Penguin類別繼承Bird類別，並實現Flyable接口
class Penguin extends Bird implements Flyable {
    // 實現 fly 方法
    public void fly() {
        System.out.println("Penguin can fly");
    }
}

public class TestInterface {
    public static void main(String[] args) {
        // 創建一隻Penguin
        Penguin penguin = new Penguin();
        penguin.eat();  // 輸出: Bird is eating
        penguin.fly();  // 輸出: Penguin can fly
    }
}
```

### 多重繼承

一個類別可以實現多個Interface，類似於多重繼承。

```java
// 定義一個 Runnable Interface
interface Runnable {
    // Interface中的抽象方法 run
    void run();
}
// 定義一個 Swimmable Interface
interface Swimmable {
    // Interface中的抽象方法 swim
    void swim();
}
// 定義一個 Robot 類別，同時實現 Runnable 和 Swimmable 兩個 Interface
class Robot implements Runnable, Swimmable {
    // 實現 run 方法
    public void run() {
        System.out.println("Robot is running");
    }

    // 實現 swim 方法
    public void swim() {
        System.out.println("Robot is swimming");
    }
}

public class TestInterface {
    public static void main(String[] args) {
        // 創建一個 Robot
        Robot myRobot = new Robot();
        myRobot.run();  // 輸出: Robot is running
        myRobot.swim();  // 輸出: Robot is swimming
    }
}
```

> What if it implements 2 Interfaces with the same-named methods?
>
> --> if the return type is the same, then it doesn't matter. But if it's different, CompileError happens.

## Abstract Class

抽象類別（Abstract Class）是一種不能**直接實例化**（不可透過**new**來生成物件）的特殊類別。我們可以通過繼承抽象類別並實現其抽象方法來創建新的類別。抽象類別通常被用來作為所有具有共同特性的類別的基礎。是一種介於Interface（不能提供任何實作內容）與class之間的物件機制。 

```java
// 定義一個抽象類別 Animal
abstract class Animal {
    // 抽象方法 sound
    abstract void sound();
}

// Dog 類別繼承 Animal 抽象類別
class Dog extends Animal {
    // 實現 sound 方法
    public void sound() {
        System.out.println("Dog says: Woof Woof!");
    }
}

public class TestAbstractClass {
    public static void main(String[] args) {
        // 創建一個 Dog
        Dog myDog = new Dog();
        myDog.sound();  // 輸出: Dog says: Woof Woof!
    }
}
```
