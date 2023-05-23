import java.util.*;
public class Main {
    public static void main(String[] args) {
        if(args.length != 1 && !args[0].startsWith("--count=")) {
            System.out.println("Wrong arguments");
            System.exit(-1);
        }
        int count = Integer.parseInt(args[0].substring("--count=".length(), args[0].length()));
        if(count <= 0) {
            System.out.println("Argument is less or equal to zero");
            System.exit(-1);
        }
        Thread eggThread = new Thread(new Egg(count));
        Thread henThread = new Thread(new Hen(count));
        eggThread.start();
        henThread.start();
        for(int i = 0; i < count; i++) {
            System.out.println("Human");
        }
    }
}
