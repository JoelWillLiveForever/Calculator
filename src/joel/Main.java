package joel;

import joel.calculator.Calculator;
import joel.calculator.Configuration;

public class Main {

    public static void main(String[] args) {
        Configuration.getInstance().setRadian(true);
        String expression = "150!/54224*231sin(45)(5+5)cos(214e)";
        Calculator calculator = new Calculator(expression);

        // speed test
        long time = System.nanoTime();
        System.out.println("Answer:\t" + calculator.getAnswer());
        time = System.nanoTime() - time;

        System.out.println("Time:\t" + time / 1_000_000.0d + " ms");
    }
}
