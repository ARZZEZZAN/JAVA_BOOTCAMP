import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static int threadsCount;
    private static Object[] arrayLines;
    public static void main(String[] args) throws InterruptedException, IOException {
        if(args.length != 1 || !args[0].startsWith("--threadsCount=")) {
            System.out.println("Wrong arguments");
            System.exit(-1);
        }
        threadsCount = Integer.parseInt(args[0].substring("--threadsCount=".length()));
        if(threadsCount < 1) {
            System.out.println("Wrong arguments");
            System.exit(-1);
        }
        BufferedReader fileReader = new BufferedReader(new FileReader("file_urls.txt"));
        arrayLines = fileReader.lines().toArray();
        if(arrayLines.length == 0) {
            System.out.println("File doesn't have any urls");
            System.exit(-1);
        }
        Consumer consumer = new Consumer(fileReader, arrayLines);
        executeProgram(fileReader, consumer);
        fileReader.close();
    }
    private static void executeProgram(BufferedReader fileReader, Consumer consumer) throws InterruptedException {
        Thread[] threads = new Thread[threadsCount];
        for(int i = 0; i < threadsCount; i++) {
            threads[i] = new Thread(new Producer(consumer, i + 1, arrayLines.length));
        }
        for(Thread thread : threads) {
            thread.start();
        }
    }
}
