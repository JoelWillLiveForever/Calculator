package joel;

import joel.calculator.Calculator;
import joel.calculator.Configuration;
import joel.calculator.Token;

public class Main {

    public static void main(String[] args) {
        Token mToken = new Token();
        Configuration mConfig = new Configuration();
        mConfig.setErrorCheckerEnabled(false);
        Calculator calculator = new Calculator();

        // speed test
        long time = System.nanoTime();
        System.out.println(calculator.getAnswer("(2^8)^3", mToken, mConfig));
        time = System.nanoTime() - time;
        System.out.printf("Elapsed %,9.3f ms\n", time/1_000_000.0);
    }
}
