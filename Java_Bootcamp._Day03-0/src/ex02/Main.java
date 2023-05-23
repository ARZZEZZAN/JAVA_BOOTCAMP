import java.util.*;
public class Main {
    private static int arraySize;
    private static int threadsCount;
    public static void main(String[] args) throws InterruptedException {
        if(args.length != 2
                && !args[0].startsWith("--arraySize=")
                && !args[1].startsWith("--threadsCount=")) {
            System.out.println("Wrong arguments");
            System.exit(-1);
        }
        arraySize = Integer.parseInt(args[0].substring("--arraySize=".length()));
        threadsCount = Integer.parseInt(args[1].substring("--threadsCount=".length()));
        if(arraySize <= 0 && threadsCount <= 0 && arraySize <= 2_000_000) {
            System.out.println("Argument is less then 1 or arraySize is bigger then 2_000_000");
            System.exit(-1);
        }
        executeProgram();
    }
    private static void executeProgram() throws InterruptedException {
       int[] mainArray = new int[arraySize];
       int parts = 0;
       for(int i = 0; i < arraySize; i++) {
           mainArray[i] = 1;
//           (int) (Math.random()*(600+1)) - 200;
       }
       int ceilNumber = (int) Math.ceil((double) arraySize/threadsCount) * threadsCount;
       int first = 0, second = 0;
       int forFisrt = 0, forSecond = 1;
       if((first = arraySize - threadsCount) < threadsCount) {
           second = threadsCount - first;
           forFisrt = (arraySize - second) / first;
       } else {
           forFisrt = ceilNumber / threadsCount;
           first = arraySize / forFisrt;
           second = arraySize - (forFisrt * first);
           forSecond = second;
           second = 1;
       }
       Thread[] threads = new Thread[threadsCount];
       Consumer consumer = new Consumer();
       int start = 0, end = 0;
       int i = 0;
       for(i = 0; i < first; i++) {
           end = start + forFisrt;
           threads[i] = new Thread(new Summator(consumer, Arrays.copyOfRange(mainArray, start, end),
                   start, (start + forFisrt), threadsCount));
           start += forFisrt;
       }
       for (int j = 0; j < second; j++, i++) {
           end = start + forSecond;
           threads[i] = new Thread(new Summator(consumer, Arrays.copyOfRange(mainArray, start, end),
                   start, end, threadsCount));
           start += 1;
       }
       for(int j = 0; j < threadsCount; j++) {
           threads[j].start();
       }
        System.out.println(consumer.getSumOfThreads());
    }
    public static int getThreadsCount() {
        return threadsCount;
    }
}
