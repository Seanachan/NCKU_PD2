// 節點類別
class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

// 連結串列類別
class LinkedList {
    Node head;

    public LinkedList() {
        this.head = null;
    }

    // 在連結串列尾端新增節點
    public void addNode(int data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // 列印連結串列中的節點資料
    public void printList() {
        Node current = head;

        System.out.print("連結串列內容: ");
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}

// 主程式類別
public class myList {
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();

        // 新增節點到連結串列
        linkedList.addNode(1);
        linkedList.addNode(2);
        linkedList.addNode(3);

        // 列印連結串列內容
        linkedList.printList();
    }
}