package joel;

import joel.calculator.Calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Calculator calculator = new Calculator();
        String expression;
        long time;

        while (true) {
            expression = in.nextLine();
            calculator.setExpression(expression);

            // speed test
            time = System.nanoTime();
            System.out.println("Answer:\t" + calculator.getAnswer());
            time = System.nanoTime() - time;

            System.out.println("Time:\t" + time / 1_000_000.0d + " ms");
        }
    }
}
