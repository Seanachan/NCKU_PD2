# ClassNote 0402

## Dynamic Binding

是一種在運行時確定物件方法調用的機制。

調用一個方法是，被調用的方法**版本是**在程式運行式基於物件的實際類型決定的，而不是**編譯**時。

```java
class Animal {
    void eat() {
        System.out.println("Animal is eating");
    }
}

class Dog extends Animal {
    @Override
    void eat() {
        System.out.println("Dog is eating");
    }
}

public class TestDynamicBinding {
    public static void main(String[] args) {
        Animal myAnimal = new Dog();
        myAnimal.eat(); // Prints "Dog is eating"
    }
}
```

雖然 `myAnimal`的引用類型是 `Animal`，但是實際指向的對象是 `Dog`的一個實例。

調用 `eat()`方法時，JVM在運行時確定 `myAnimal`實際指向對象是 `Dog`，因此調用的是 `Dog`類別中覆寫的 `eat()`方法，而不是 `Animal`類別中的方法。

## Constructor

> Initialize must be written at the first line.

```java
class Box {
    private int width, height, depth;

    // constructor，使用this區分實例變數和constructor參數
    public Box(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    // 設置尺寸的方法，使用this區分實例變數和方法參數
    public void setDimensions(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    // 使用this返回當前類的實例
    public Box getSelf() {
        return this;
    }

    // 顯示尺寸的方法
    public void displayDimensions() {
        System.out.println("Width: " + width + ", Height: " + height + ", Depth: " + depth);
    }
}

public class TestThis {
    public static void main(String[] args) {
        Box myBox = new Box(10, 20, 30);
        myBox.displayDimensions();  // 輸出: Width: 10, Height: 20, Depth: 30

        // 使用setDimensions方法重新設置尺寸
        myBox.setDimensions(50, 60, 70);
        myBox.displayDimensions();  // 輸出: Width: 50, Height: 60, Depth: 70

        // 使用this返回當前類的實例
        Box anotherBox = myBox.getSelf();
        anotherBox.displayDimensions();  // 輸出: Width: 50, Height: 60, Depth: 70
    }
}
```

> 在Box類別的constructor中，我們使用this來區分參數名稱和類別的實例變數名字。

在`Box`類別的constructor中，我們使用 `this`來區分參數名和類別的實例變數名。

在`setDimensions`**方法中，同樣使用** `this`來引用當前object的instance變數。

在`getSelf`方法中，通過this返回當前object的reference

## Package

在java中，每個類別都屬於某個pacakge。如果沒有指定package，屬於`default package`。

```java
package com.example.myapp;
```

當需要用其他package時候，用`import packageName`，就能使用其類別。

> procted 和package-private的差別是存取範疇。
>
> 當變數或方法被定義為procted時，可以被同一個package中的類別及其子方法訪問，即使這些子類別不在同一個package中也是。
>
> 一個變數或方法沒有明確存取修飾子時，稱為 (package-private) ，只能被同一個package中的類別存取，`不能被不同package子類別存取`

## Variable Visibility

* Public：可以被`任何`其他的類別訪問
* Private：僅能被同一類別中的其他方法訪問，其他類別無法。
* Protected: 可以被`同一package`中的其他類別以及`所有子類別`存取。
