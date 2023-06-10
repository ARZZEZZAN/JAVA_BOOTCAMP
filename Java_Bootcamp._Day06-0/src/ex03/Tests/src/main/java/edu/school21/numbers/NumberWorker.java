package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        if(number <= 1) {
            throw new IllegalArgumentException("Input parameter is less or equal to 1");
        }
        for(int i = 2; i * i <= number; i++) {
            if(number % i == 0) {
                return false;
            }
        }
        return true;
    }
    public int digitsSum(int number) {
        int sum = 0;
        while(number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}
