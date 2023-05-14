import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        int[] counts = new int[65536];
        for (char c : input.toCharArray()) {
            counts[c]++;
        }

        int[] topCounts = new int[10];
        char[] topChars = new char[10];

        for (int i = 0; i < counts.length; i++) {
            int count = counts[i];

            for (int j = 0; j < topCounts.length; j++) {
                if (count > topCounts[j]) {
                    for (int k = topCounts.length - 1; k > j; k--) {
                        topCounts[k] = topCounts[k - 1];
                        topChars[k] = topChars[k - 1];
                    }
                    topCounts[j] = count;
                    topChars[j] = (char)i;
                    break;
                } else if (count == topCounts[j] && i < topChars[j]) {
                    for (int k = topCounts.length - 1; k > j; k--) {
                        topChars[k] = topChars[k - 1];
                    }
                    topChars[j] = (char)i;
                    break;
                }
            }
        }
        int maxCount = topCounts[0];
        System.out.println();
        System.out.println();
        for (int i = 0; i < 10; i++) {
            if (topCounts[i] == maxCount)
                System.out.print(topCounts[i] + "\t");
        }
        System.out.println();
        for (int i = 10; i > 0; i--) {
            for (int j = 0; j < 10; j++) {
                if (topCounts[j] * 10 / maxCount >= i)
                    System.out.print("#\t");
                if (topCounts[j] * 10 / maxCount == i - 1) {
                    if (topCounts[j] != 0) {
                        System.out.print(topCounts[j] + "\t");
                    }
                }
            }
            System.out.println();
        }
        for (int i = 0; i < 10; i++) {
            System.out.print(topChars[i] + "\t");
        }
    }
}
