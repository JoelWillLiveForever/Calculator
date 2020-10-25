package joel.calculator;

import java.util.Stack;
import java.util.StringTokenizer;

public final class Calculator {
    private final Stack<String> mNumbers = new Stack<>();
    private final Stack<String> mOperations = new Stack<>();

    private Token mTokens;
    private String mExpression;
    private Configuration mConfig;

    public Calculator() {
        mExpression = "";
        mTokens = new Token();
        mConfig = new Configuration();
    }

    public Calculator(String expression) {
        mExpression = expression;
        mTokens = new Token();
        mConfig = new Configuration();
    }

    public Calculator(String expression, Token tokens, Configuration config) {
        mExpression = expression;
        mTokens = tokens;
        mConfig = config;
    }

    public Stack<String> getNumbers() {
        return mNumbers;
    }

    public Stack<String> getOperations() {
        return mOperations;
    }

    public Token getTokens() {
        return mTokens;
    }

    public void setTokens(Token tokens) {
        mTokens = tokens;
    }

    public String getExpression() {
        return mExpression;
    }

    public void setExpression(String expression) {
        mExpression = expression;
    }

    public Configuration getConfig() {
        return mConfig;
    }

    public void setConfig(Configuration config) {
        mConfig = config;
    }

    public String getAnswer() {
        if (!mConfig.isErrorCheckerEnabled()) {
            try {
                return start();
            } catch (Exception exc) {
                return exc.getMessage();
            }
        } else {
            return start();
        }
    }

    public String getAnswer(String expression) {
        mExpression = expression;
        return getAnswer();
    }

    public String getAnswer(String expression, Token tokens, Configuration config) {
        mExpression = expression;
        mTokens = tokens;
        mConfig = config;
        return getAnswer();
    }

    private int getPriority(String token) {
        if (token.equals(mTokens.getPlus()) || token.equals(mTokens.getMinus()))
            return 1;
        if (token.equals(mTokens.getMultiply()) || token.equals(mTokens.getDivide()) || token.equals(mTokens.getOldMultiply()) || token.equals(mTokens.getOldDivide()))
            return 2;
        if (token.equals(mTokens.getPower()))
            return 3;
        if (token.equals(mTokens.getUnaryMinus()))
            return 4;
        if (token.equals(mTokens.getFactorial()) || token.equals(mTokens.getPercent()))
            return 5;
        if (token.equals(mTokens.getLg()) || token.equals(mTokens.getLn()) || token.equals(mTokens.getLog()) || token.equals(mTokens.getExp()) || token.equals(mTokens.getRoot()) || token.equals(mTokens.getSin()) || token.equals(mTokens.getCos()) || token.equals(mTokens.getTan()) || token.equals(mTokens.getSinh()) || token.equals(mTokens.getCosh()) || token.equals(mTokens.getTanh()) || token.equals(mTokens.getAsinh()) || token.equals(mTokens.getAcosh()) || token.equals(mTokens.getAtanh()))
            return 6;
        return 0;
    }

    private boolean isFunction(String token) {
        boolean isTrue = false;
        for (String element : mTokens.getFunctions())
            if (element.equals(token)) {
                isTrue = true;
                break;
            }
        return isTrue;
    }

    private boolean isOperation(String token) {
        return mTokens.getOperations().contains(token);
    }

    private boolean isDelimiter(String token) {
        return mTokens.getDelimiters().contains(token);
    }

    private boolean isNumber(String token) {
        if (token.equals(mTokens.getPi()) || token.equals(mTokens.getEuler())) return true;
        return !isDelimiter(token) && !isFunction(token) && !token.equals(mTokens.getUnaryMinus());
    }

    private boolean isUnaryMinus(String previous) {
        return (isDelimiter(previous) && !previous.equals(mTokens.getCloseBracket()) && !previous.equals(mTokens.getFactorial()) && !previous.equals(mTokens.getPercent()) && !previous.equals(mTokens.getPi()) && !previous.equals(mTokens.getEuler())) || previous.equals("") || previous.equals(mTokens.getUnaryMinus());
    }

    private void tryInsertNumericFactor(String previous) {
        if (previous.equals(mTokens.getCloseBracket()) || isNumber(previous) || previous.equals(mTokens.getFactorial()) || previous.equals(mTokens.getPercent())) {
            while (mNumbers.size() >= 2 && !mOperations.isEmpty() && getPriority(mTokens.getMultiply()) <= getPriority(mOperations.peek())) {
                calculate();
            }
            mOperations.add(mTokens.getMultiply());
        }
    }

    private boolean isContainsFunction(String token) {
        for (String function : mTokens.getFunctions())
            if (token.contains(function)) return true;
        return false;
    }

    private int getFunctionLength(String token) {
        for (String function : mTokens.getFunctions())
            if (token.contains(function)) return function.length();
        return -1;
    }

    private double fastProdTree(double l, double r) {
        if (l > r) return 1.0d;
        if (l == r) return l;
        if (r - l == 1.0d) return l * r;
        double m = Math.floor((l + r) / 2.0d);
        return fastProdTree(l, m) * fastProdTree(m + 1.0d, r);
    }

    private double fastFactTree(double n) {
        if (n < 0.0d) return 0.0d;
        if (n == 0.0d) return 1.0d;
        if (n == 1.0d || n == 2.0d) return n;
        return fastProdTree(2.0d, n);
    }

    private void calculate() {
        double result = 0.0d;
        if (mOperations.peek().equals(mTokens.getPlus())) {
            // plus
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());
            result = first + second;
        } else if (mOperations.peek().equals(mTokens.getMinus())) {
            // minus
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());
            result = first - second;
        } else if (mOperations.peek().equals(mTokens.getMultiply()) || mOperations.peek().equals(mTokens.getOldMultiply())) {
            // multiply
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());
            result = first * second;
        } else if (mOperations.peek().equals(mTokens.getDivide()) || mOperations.peek().equals(mTokens.getOldDivide())) {
            // divide
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());
            result = first / second;
        } else if (mOperations.peek().equals(mTokens.getPower())) {
            // power
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());
            result = Math.pow(first, second);
        } else if (mOperations.peek().equals(mTokens.getLog())) {
            // log
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());
            result = Math.log10(second) / Math.log10(first);
        } else if (mOperations.peek().equals(mTokens.getFactorial())) {
            // factorial
            double number = Double.parseDouble(mNumbers.pop());
            result = fastFactTree(number);
        } else if (mOperations.peek().equals(mTokens.getPercent())) {
            // percent
            double number = Double.parseDouble(mNumbers.pop());
            result = number / 100.0d;
        } else if (mOperations.peek().equals(mTokens.getSin())) {
            // sin
            double number = Double.parseDouble(mNumbers.pop());

            if (!mConfig.isRadian()) {
                number = Math.toRadians(number);
            }
            result = Math.sin(number);
        } else if (mOperations.peek().equals(mTokens.getCos())) {
            // cos
            double number = Double.parseDouble(mNumbers.pop());

            if (!mConfig.isRadian()) {
                number = Math.toRadians(number);
            }
            result = Math.cos(number);
        } else if (mOperations.peek().equals(mTokens.getTan())) {
            // tan
            double number = Double.parseDouble(mNumbers.pop());

            if (!mConfig.isRadian()) {
                number = Math.toRadians(number);
            }
            result = Math.tan(number);
        } else if (mOperations.peek().equals(mTokens.getAsin())) {
            // asin
            double number = Double.parseDouble(mNumbers.pop());

            if (!mConfig.isRadian()) {
                number = Math.toRadians(number);
            }
            result = Math.asin(number);
        } else if (mOperations.peek().equals(mTokens.getAcos())) {
            // acos
            double number = Double.parseDouble(mNumbers.pop());

            if (!mConfig.isRadian()) {
                number = Math.toRadians(number);
            }
            result = Math.acos(number);
        } else if (mOperations.peek().equals(mTokens.getAtan())) {
            // atan
            double number = Double.parseDouble(mNumbers.pop());

            if (!mConfig.isRadian()) {
                number = Math.toRadians(number);
            }
            result = Math.atan(number);
        } else if (mOperations.peek().equals(mTokens.getSinh())) {
            // sinh
            double number = Double.parseDouble(mNumbers.pop());

            if (!mConfig.isRadian()) {
                number = Math.toRadians(number);
            }
            result = Math.sinh(number);
        } else if (mOperations.peek().equals(mTokens.getCosh())) {
            // cosh
            double number = Double.parseDouble(mNumbers.pop());

            if (!mConfig.isRadian()) {
                number = Math.toRadians(number);
            }
            result = Math.cosh(number);
        } else if (mOperations.peek().equals(mTokens.getTanh())) {
            // tanh
            double number = Double.parseDouble(mNumbers.pop());

            if (!mConfig.isRadian()) {
                number = Math.toRadians(number);
            }
            result = Math.tanh(number);
        } else if (mOperations.peek().equals(mTokens.getAsinh())) {
            // asinh
            double number = Double.parseDouble(mNumbers.pop());

            if (!mConfig.isRadian()) {
                number = Math.toRadians(number);
            }
            result = Math.log(number + Math.sqrt((number * number) + 1));
        } else if (mOperations.peek().equals(mTokens.getAcosh())) {
            // acosh
            double number = Double.parseDouble(mNumbers.pop());

            if (!mConfig.isRadian()) {
                number = Math.toRadians(number);
            }
            result = Math.log(number + Math.sqrt((number * number) - 1));
        } else if (mOperations.peek().equals(mTokens.getAtanh())) {
            // atanh
            double number = Double.parseDouble(mNumbers.pop());

            if (!mConfig.isRadian()) {
                number = Math.toRadians(number);
            }
            result = 0.5d * Math.log((1 + number) / (1 - number));
        } else if (mOperations.peek().equals(mTokens.getExp())) {
            // exp
            double number = Double.parseDouble(mNumbers.pop());
            result = Math.exp(number);
        } else if (mOperations.peek().equals(mTokens.getRoot())) {
            // root (sqrt)
            double number = Double.parseDouble(mNumbers.pop());
            result = Math.sqrt(number);
        } else if (mOperations.peek().equals(mTokens.getLn())) {
            // ln
            double number = Double.parseDouble(mNumbers.pop());
            result = Math.log(number);
        } else if (mOperations.peek().equals(mTokens.getLg())) {
            // lg
            double number = Double.parseDouble(mNumbers.pop());
            result = Math.log10(number);
        } else if (mOperations.peek().equals(mTokens.getUnaryMinus())) {
            // unary minus
            double number = Double.parseDouble(mNumbers.pop());
            result = -number;
        }
        mNumbers.push(Double.toString(result));
        mOperations.pop();
    }

    private String start() {
        if (mExpression.equals("") || mExpression.length() <= 0) return "";
        StringTokenizer tokenizer = new StringTokenizer(mExpression, mTokens.getDelimiters(), true);
        String token, previous = "";

        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();

            // is Delimiter
            if (isDelimiter(token)) {
                if (token.equals(mTokens.getOpenBracket())) {
                    tryInsertNumericFactor(previous);
                    mOperations.push(mTokens.getOpenBracket());
                } else if (token.equals(mTokens.getCloseBracket())) {
                    while (!mOperations.isEmpty() && !mOperations.peek().equals(mTokens.getOpenBracket())) {
                        calculate();
                    }
                    mOperations.pop();
                }
            }

            // is Operation or Function
            if (isOperation(token) || isFunction(token)) {
                if (mOperations.isEmpty()) {
                    if (token.equals(mTokens.getMinus()) && isUnaryMinus(previous)) {
                        token = mTokens.getUnaryMinus();
                    } else if (isFunction(token) && !token.equals(mTokens.getFactorial())) {
                        tryInsertNumericFactor(previous);
                    }
                } else {
                    // operations not empty
                    if (isFunction(token) && !token.equals(mTokens.getFactorial())) {
                        tryInsertNumericFactor(previous);
                    } else if (token.equals(mTokens.getMinus()) && isUnaryMinus(previous)) {
                        token = mTokens.getUnaryMinus();
                    }
                    if (!token.equals(mTokens.getUnaryMinus())) {
                        while (!mOperations.isEmpty() && getPriority(token) <= getPriority(mOperations.peek()))
                            calculate();
                    }
                }
                mOperations.push(token);
            }

            // is Number
            if (isNumber(token)) {
                tryInsertNumericFactor(previous);
                if (token.equals(mTokens.getPi())) {
                    token = String.valueOf(Math.PI);
                } else if (token.equals(mTokens.getEuler())) {
                    token = String.valueOf(Math.E);
                }
                if (isContainsFunction(token)) {
                    int len = getFunctionLength(token);
                    String number = token.substring(0, token.length() - len);
                    String function = token.substring(token.length() - len);
                    mNumbers.push(number);

                    while (!mOperations.isEmpty() && getPriority(mTokens.getMultiply()) <= getPriority(mOperations.peek())) {
                        calculate();
                    }

                    mOperations.push(mTokens.getMultiply());
                    mOperations.push(function);
                    token = function;
                } else {
                    mNumbers.push(token);
                }
            }

            previous = token;
        }

        while (!mOperations.isEmpty()) {
            calculate();
        }

        return mNumbers.pop();
    }
}
