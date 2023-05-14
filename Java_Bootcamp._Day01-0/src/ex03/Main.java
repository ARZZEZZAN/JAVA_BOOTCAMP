import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        int number = 0;
        while (number != 42) {
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                if(isPrimeNumber(sumOfDigits(number))) {
                    count++;
                }
            } else {
                scanner.close();
                break;
            }
        }
        System.out.println("Count of coffee-request – " + count);
    }
    private static int sumOfDigits(int number) {
        int res = 0;
        while (number > 0) {
            res += number % 10;
            number = number / 10;
        }
        return res;

    }

    private static boolean isPrimeNumber(int number) {
        int i = 2;
        if(number > 1) {
            for (; i * i <= number; i++) {
                if(number % i == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}