package joel;

import joel.calculator.Calculator;
import joel.calculator.Configuration;

public class Main {

    public static void main(String[] args) {
        Configuration.getInstance().setRadian(true);
        String expression = "sin(45)";
        Calculator calculator = new Calculator(expression);

        // speed test
        long time = System.nanoTime();
        System.out.println(calculator.getAnswer());
        time = System.nanoTime() - time;

        System.out.printf("Elapsed %,9.3f ms\n", time / 1_000_000.0);
    }
}
