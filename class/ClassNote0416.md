ClassNote0416
===


## Auto-Unboxing

```java
HashMap<String, Integer> countMap = new HashMap<>();

// 假設我們有一個字符串陣列
String[] strings = {"apple", "orange", "banana", "apple", "orange", "apple"};

// 我們遍歷這個陣列，對每個出現的字符串做計數
for(String s: strings){
    if(countMap.containsKey(s)){
        // 如果Map中已經有這個Key，則將其值增加1
        countMap.put(s, countMap.get(s) + 1); //Auto Unboxing
    } else {
        // 否則，向Map中添加這個Key，並將其值設為1
        countMap.put(s, 1);
    }
}
```

發生了以下步驟：

1. Auto-Unboxing: `countMap.get(s)` 從`HashMap`返回一個`Integer`物件，由於進行加法運算，
   **Java會將Integer unboxing 成一個primitive data type**
2. 加法運算：有了primitive data type, 可以直接進行相加
3. Auto-Boxing：加法完成後，結果是一個primitive 的int。將這個值放回`HashMap` 的時候會將int boxing 成一個新的Integer物件

> 過度依賴**Auto-unboxing**和**Auto-boxing**可能會導致性能問題，尤其是在大量計算的場景中，因為每一次boxing/unboxing都可能產生額外的object，從而增加GC的負擔。

## Iterator

> **Iterator** 用於逐一訪問集合中的元素。 Iterator 對象稱為迭代器，它設計的目的是為了提供一種方法來訪問一個聚合對象（如：陣列或集合）的元素，而又不需要暴露該對象的內部表示。

* `hasNext():`  return true if there are more elements in the iterator
* `next():` return the next element in the iterator
* `remove():`remove the last-returned element by iterator

## Revisit Try-Catch-Final statement

```java
try {
    // 可能會產生異常的程式碼
} catch (ExceptionType1 e1) {
    // 處理 ExceptionType1 的程式碼
} catch (ExceptionType2 e2) {
    // 處理 ExceptionType2 的程式碼
} finally {
    // 一定會被執行的程式碼
}
```

也可以用throw拋出可能的異常

```java
public void myMethod() throws Exception1, Exception2{
    //code
}
```


| Exception 的類別               | 描述                                                                                   |
| :----------------------------- | :------------------------------------------------------------------------------------- |
| NullPointerException           | 當應用程式嘗試在需要物件的地方使用 null，這種異常會產生                                |
| ArrayIndexOutOfBoundsException | 當索引值為負或大於等於陣列大小時，這種異常會產生                                       |
| ClassCastException             | 當嘗試將物件強制轉換為不相容的類別時，這種異常會產生                                   |
| IllegalArgumentException       | 當傳遞非法或不適當的引數時，這種異常會產生                                             |
| ArithmeticException            | 當出現異常的算術條件時（如除以零），這種異常會產生                                     |
| IllegalStateException          | 在Java環境或應用程式中不適當的時間點產生的異常                                         |
| NumberFormatException          | 當應用程式嘗試將字串轉換成一種數值型別，但該字串不能轉換為適當的格式時，這種異常會產生 |

## Assert

```java
public class Main {
    public static void main(String[] args) {
        int age = 15;

        assert age >= 18 : "未成年不得入內";
        System.out.println("歡迎光臨");
    }
}
```

**為了啟動assert語句，需要在啟動java程式時加上 "-ea"**
`java -ea Main`

### 環境變數讀取

```java
public class Main {
    public static void main(String[] args) {
        // 獲取所有的環境變數
        Map<String, String> env = System.getenv();

        // 印出每一個環境變數
        for (String envName : env.keySet()) {
            System.out.format("%s=%s\n", envName, env.get(envName));
        }
    }
}
```

> 在Linux系統下，可以使用 **export**命令來設定環境變數

```zsh
export VAR_NAME="value"
```

### 透過assert跟environment value來進行debug技巧

```java
public class Main {
    public static void main(String[] args) {
        // 讀取 "DEBUG" 環境變數
        String debug = System.getenv("DEBUG");

        // 將 "DEBUG" 環境變數轉換為 boolean 值
        boolean isDebug = Boolean.parseBoolean(debug);

        // 如果 DEBUG 環境變數被設定為 "true"，則啟用 assert
        if (isDebug) {
            // 這裡是一個示例，檢查某個條件是否為真
            int x = 10;
            assert x == 10 : "x 應該為 10";
        }
    }
}
```

在這個例子中，我們首先從環境變數讀取 "DEBUG"，並將其轉換為boolean值。如果DEBUG環境變數被設定為 "true"，則我們會啟用assert來檢查某個條件是否為真。

這種策略的好處是，我們可以在debug期間啟用assert，並在客戶環境中關掉它。這可以讓我們在開發和測試期間更容易找到和修復問題，同時在客戶線上環境中避免不必要的time overhead。

## Static

**static**關鍵字表示一個變數或方法屬於class本身，而不是object的instance-level

> **static method**中不能訪問類別中的 **non-static variable**

### Static Initializer

可以使用(Static Initializer Block) 初始化靜態變數。
這個block只會在class被載入到JVM時執行一次。

```java
public class MyClass {
    static int[] array;

    static {
        array = new int[5];
        for (int i = 0; i < array.length; i++) {
            array[i] = i * 10;
        }
    }

    public static void main(String[] args) {
        for (int i : array) {
            System.out.println(i);
        }
    }
}
```

## UML
