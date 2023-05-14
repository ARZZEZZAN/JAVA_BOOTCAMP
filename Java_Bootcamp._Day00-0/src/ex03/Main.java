import java.util.*;
public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        String week = new String();
        printResult(inputProcessing(week)/10);
    }
    private static int inputProcessing(String week) {
        int countOrder = 0, mark = 0, mark2 = 0, result = 0;
        boolean error = false;
        while(!(week = scanner.nextLine()).equals("42") && ++countOrder <= 18) {
            if(week.equals("Week " + countOrder)) {
                mark = scanner.nextInt();
                for (int i = 0; i < 4; i++) {
                    mark2 = scanner.nextInt();
                    if(!isValid(mark) || !isValid(mark2)) {
                        error = true;
                        break;
                    }
                    if(mark > mark2) {
                        mark = mark2;
                    }
                }
                result += mark;
                result *= 10;
                scanner.nextLine();
            } else {
                error = true;
            }
            if (error) {
                System.out.println("IllegalArgument");
                System.exit(-1);
            }
        }
        return result;
    }
    private static boolean isValid(int number) {
        if (number <= 9 && number >= 1) {
            return true;
        }
        return false;
    }
    private static void printResult(int number) {
        int reversed = 0, week = 1;
        for(;number != 0; number /= 10) {
            int digit = number % 10;
            reversed = reversed * 10 + digit;
        }
        while (reversed > 0) {
            int iter = reversed % 10;
            reversed /= 10;
            System.out.print("Week " + week + " ");
            for (int i = 0; i < iter; i++) {
                System.out.print("=");
            }
            System.out.println(">");
            week++;
        }
    }
}