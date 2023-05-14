import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = 2;
        if(scanner.hasNextInt()) {
            int num = scanner.nextInt();
            if(num <= 1) {
                System.out.println("IllegalArgument");
                System.exit(-1);
            }
            for (; i * i <= num; i++) {
                if(num % i == 0) {
                    System.out.println("false " + --i);
                    System.exit(0);
                }
            }
            System.out.println("true " + --i);

        }
    }
}