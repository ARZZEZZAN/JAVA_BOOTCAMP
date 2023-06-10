package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberWorkerTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79})
    void isPrimeForPrimes(int input) {
        NumberWorker numberWorker = new NumberWorker();
        assertTrue(numberWorker.isPrime(input));
    }
    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 12, 14, 20})
    void isPrimeForNotPrimes(int input) {
        NumberWorker numberWorker = new NumberWorker();
        assertFalse(numberWorker.isPrime(input));
    }
    @ParameterizedTest
    @ValueSource(ints = {0, 1, -1, -300})
    void isPrimeForIncorrectNumbers(int input) {
        NumberWorker numberWorker = new NumberWorker();
        assertThrows(IllegalArgumentException.class, () -> {
            numberWorker.isPrime(input);
        });
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 2)
    void digitSum(int input, int expected) {
        NumberWorker numberWorker = new NumberWorker();
        assertEquals(expected, numberWorker.digitsSum(input));
    }
}