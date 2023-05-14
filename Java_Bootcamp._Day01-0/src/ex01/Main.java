import java.util.*;
public class Main {
    public static void main(String[] args) {
        int number = 479598;
        int res = 0;
        while (number > 0) {
            res += number % 10;
            number = number / 10;
        }
        System.out.println(res);
    }
}