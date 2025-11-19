package algorithms;

import java.util.Scanner;

public class DigitSum {

    public static int sumDigits(String numbersInput) {
        if (numbersInput.length() == 0) return 0;
        int firstNumber = numbersInput.charAt(0) - '0'; // Convert char to int
        return firstNumber + sumDigits(numbersInput.substring(1));
    }

    public static int digitalRoot(String numbersInput) {
        int sum = sumDigits(numbersInput);
        if (sum < 10) return sum;
        return digitalRoot(Integer.toString(sum));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        String input = scanner.nextLine();
        
        System.out.println("Sum of digits: " + sumDigits(input));
        System.out.println("Digital root: " + digitalRoot(input));
        
        scanner.close();
    }
}

