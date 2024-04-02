class ExtendedSampleClass extends SampleClass {   // 繼承 SampleClass
    public String attribute3;  // 新增一個 public 字串屬性 attribute3
    public void printAttribute(){
        System.out.println(attribute2);
    }
    public ExtendedSampleClass() {   // 子類別的建構子
        super();                                          // 呼叫父類別的建構子
        this.attribute3 = "Extended Hello"; // 初始化 attribute3
    }

    @Override
    public void method1() { // 覆寫父類別的 method1 方法
        System.out.println("This is overridden method1 in ExtendedSampleClass.");
    }

    public void method3() {  // 新增一個方法 method3
        System.out.println("This is method3 specific to ExtendedSampleClass.");
    }
}

public class SampleClass {            // 宣告一個 public 類別 SampleClass
    private int attribute1;            // attribute1 是一個 private 整數屬性
    public String attribute2;          // attribute2 是一個 public 字串屬性

    public SampleClass() {             // 這是一個 public 的建構子，建構子的名字需與類別名稱相同
        this.attribute1 = 0;           // 在建構子內初始化 attribute1
        this.attribute2 = "Hello";     // 在建構子內初始化 attribute2
    }

    public void method1() {            // 宣告一個 public 的方法 method1
        System.out.println("This is method1.");
    }

    private void method2() {           // 宣告一個 private 的方法 method2
        System.out.println("This is method2.");
    }

    public static void main(String[] args) {  // main 方法是程式的入口點
        SampleClass sample = new SampleClass();              // 創建 SampleClass 的實例
        sample.method1();                                    // 呼叫 method1
        System.out.println(sample.attribute2);               // 印出 attribute2

        SampleClass extendedSample = new ExtendedSampleClass(); // 使用父類型來參照子類別的實例
        extendedSample.method1();                            // 多型：呼叫被覆寫的 method1
        System.out.println(extendedSample.attribute2);       // 可以訪問從父類別繼承來的 attribute2
    // 注意：extendedSample.attribute3 不可訪問，因為它被宣告為 SampleClass 類型的參考
    // 若要訪問 attribute3 或 method3，需要將 extendedSample 轉型為 ExtendedSampleClass

        if (extendedSample instanceof ExtendedSampleClass) {  // 檢查實例的類型
            ExtendedSampleClass trueExtendedSample = (ExtendedSampleClass) extendedSample;
            System.out.println(trueExtendedSample.attribute3); // 現在可以訪問 attribute3
            trueExtendedSample.method3();                      // 以及 method3
        }  
    }
}