import java.util.ArrayList;
import java.util.Arrays;

public class Summator implements Runnable {
    private Consumer consumer;
    private static int count = 0;
    private int start = 0, end = 0;
    private int[] arrays;
    public Summator(Consumer consumer, int[] arrays, int start, int end, int threadsCount) {
        this.consumer = consumer;
        this.arrays = arrays;
        this.start = start;
        this.end = end;
    }
    @Override
    public void run() {
        consumer.sum(arrays, start, end);
    }
}
