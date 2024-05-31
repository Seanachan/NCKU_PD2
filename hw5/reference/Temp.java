package reference;
import java.util.*;

public class Temp {

    public static void main(String[] args) {
        // Example data: document ID and TF-IDF score
        TreeMap<Integer, Double> map = new TreeMap<>();
        map.put(1, 0.5);
        map.put(2, 1.5);
        map.put(3, 0.2);
        map.put(4, 2.0);
        map.put(5, 0.8);

        // Initialize the priority queue with a custom comparator that sorts by value (TF-IDF score)
        PriorityQueue<Map.Entry<Integer, Double>> pq = new PriorityQueue<>(
                Comparator.comparing(Map.Entry<Integer, Double>::getValue).reversed()
        );

        // Add all entries from the map to the priority queue
        pq.addAll(map.entrySet());

        // Print the entries in the priority queue to verify the order
        while (!pq.isEmpty()) {
            Map.Entry<Integer, Double> entry = pq.poll();
            System.out.println("Doc ID: " + entry.getKey() + ", TF-IDF: " + entry.getValue());
        }
    }
}