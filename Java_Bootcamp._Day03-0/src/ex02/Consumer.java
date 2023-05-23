import java.util.ArrayList;

public class Consumer {
    private static int result = 0;
    private static int count = 1;
    public synchronized void sum(int[] arrays, int start, int end) {
        int sum = 0;
        for(int i : arrays) {
            sum += i;
        }
        System.out.println("Thread " + count + ": from " +
                start + " to " + end + " sum is " + sum);
        result += sum;
        count++;
        notify();
    }
    public synchronized int getSumOfThreads() throws InterruptedException {
        int threadsCount = Main.getThreadsCount();
        while(count < threadsCount) {
            wait();
        }
        return result;
    }

}
