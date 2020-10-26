package joel.calculator;

import java.util.Stack;
import java.util.StringTokenizer;

import static java.lang.Math.*;

public final class Calculator {
    private final Stack<String> mNumbers = new Stack<>();
    private final Stack<String> mOperations = new Stack<>();

    private byte mErrorCode = MyError.NO_ERROR;
    private byte brackets = 0;

    private String mExpression = "";
    private final Token mTokensStrings = Token.getInstance();
    private final Configuration mConfig = Configuration.getInstance();

    public Calculator() {
        // void
    }

    public Calculator(String expression) {
        mExpression = expression;
    }

    public Stack<String> getNumbers() {
        return mNumbers;
    }

    public Stack<String> getOperations() {
        return mOperations;
    }

    public Token getTokensStrings() {
        return mTokensStrings;
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

    public String getAnswer() {
        try {
            return start();
        } catch (Exception exc) {
            if (mConfig.isErrorCheckerEnabled()) {
                if (brackets != 0) {
                    mErrorCode = MyError.ERROR_BRACKETS_ARE_NOT_MATCHED;
                }
                System.out.println(exc.toString());

                MyError myError = MyError.getInstance();
                myError.setErrorCode(mErrorCode);

                return myError.getDescription();
            }
            return "";
        }
    }

    private int getPriority(String token) {
        if (token.equals(mTokensStrings.getPlus()) || token.equals(mTokensStrings.getMinus()))
            return 1;
        if (token.equals(mTokensStrings.getMultiply()) || token.equals(mTokensStrings.getDivide()) || token.equals(mTokensStrings.getOldMultiply()) || token.equals(mTokensStrings.getOldDivide()))
            return 2;
        if (token.equals(mTokensStrings.getPower()))
            return 3;
        if (token.equals(mTokensStrings.getUnaryMinus()))
            return 4;
        if (token.equals(mTokensStrings.getFactorial()) || token.equals(mTokensStrings.getPercent()))
            return 5;
        if (token.equals(mTokensStrings.getLg()) || token.equals(mTokensStrings.getLn()) || token.equals(mTokensStrings.getLog()) || token.equals(mTokensStrings.getExp()) || token.equals(mTokensStrings.getRoot()) || token.equals(mTokensStrings.getSin()) || token.equals(mTokensStrings.getCos()) || token.equals(mTokensStrings.getTan()) || token.equals(mTokensStrings.getSinh()) || token.equals(mTokensStrings.getCosh()) || token.equals(mTokensStrings.getTanh()) || token.equals(mTokensStrings.getAsinh()) || token.equals(mTokensStrings.getAcosh()) || token.equals(mTokensStrings.getAtanh()))
            return 6;
        return 0;
    }

    private boolean isFunction(String token) {
        boolean isTrue = false;
        for (String element : mTokensStrings.getFunctions())
            if (element.equals(token)) {
                isTrue = true;
                break;
            }
        return isTrue;
    }

    private boolean isOperation(String token) {
        return mTokensStrings.getOperations().contains(token);
    }

    private boolean isDelimiter(String token) {
        return mTokensStrings.getDelimiters().contains(token);
    }

    private boolean isNumber(String token) {
        if (token.equals(mTokensStrings.getPi()) || token.equals(mTokensStrings.getEuler())) return true;
        return !isDelimiter(token) && !isFunction(token) && !token.equals(mTokensStrings.getUnaryMinus());
    }

    private boolean isUnaryMinus(String previous) {
        return (isDelimiter(previous) && !previous.equals(mTokensStrings.getCloseBracket()) && !previous.equals(mTokensStrings.getFactorial()) && !previous.equals(mTokensStrings.getPercent()) && !previous.equals(mTokensStrings.getPi()) && !previous.equals(mTokensStrings.getEuler())) || previous.equals("") || previous.equals(mTokensStrings.getUnaryMinus());
    }

    private void tryInsertNumericFactor(String previous) {
        if (previous.equals(mTokensStrings.getCloseBracket()) || isNumber(previous) || previous.equals(mTokensStrings.getFactorial()) || previous.equals(mTokensStrings.getPercent())) {
            while (mNumbers.size() >= 2 && !mOperations.isEmpty() && getPriority(mTokensStrings.getMultiply()) <= getPriority(mOperations.peek())) {
                calculate();
            }
            mOperations.add(mTokensStrings.getMultiply());
        }
    }

    private boolean isContainsFunction(String token) {
        for (String function : mTokensStrings.getFunctions())
            if (token.contains(function)) return true;
        return false;
    }

    private int getFunctionLength(String token) {
        for (String function : mTokensStrings.getFunctions())
            if (token.contains(function)) return function.length();
        return -1;
    }

    private double fastProdTree(double l, double r) {
        if (l > r) return 1.0d;
        if (l == r) return l;
        if (r - l == 1.0d) return l * r;
        double m = floor((l + r) / 2.0d);
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
        if (mOperations.peek().equals(mTokensStrings.getPlus())) {  // plus protected
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());
            result = first + second;
        } else if (mOperations.peek().equals(mTokensStrings.getMinus())) {  // minus protected
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());
            result = first - second;
        } else if (mOperations.peek().equals(mTokensStrings.getMultiply()) || mOperations.peek().equals(mTokensStrings.getOldMultiply())) {
            double second = Double.parseDouble(mNumbers.pop()); // multiply protected
            double first = Double.parseDouble(mNumbers.pop());
            result = first * second;
        } else if (mOperations.peek().equals(mTokensStrings.getDivide()) || mOperations.peek().equals(mTokensStrings.getOldDivide())) {
            double second = Double.parseDouble(mNumbers.pop()); // divide protected

            if (second == 0.0d) {
                mErrorCode = MyError.ERROR_DIVISION_BY_ZERO;
                throw new ArithmeticException();
            }

            double first = Double.parseDouble(mNumbers.pop());
            result = first / second;
        } else if (mOperations.peek().equals(mTokensStrings.getPower())) {  // power protected
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());
            result = pow(first, second);
        } else if (mOperations.peek().equals(mTokensStrings.getLog())) {    // log protected
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());

            if (first == 1.0d || first <= 0.0d || second <= 0.0d) {
                mErrorCode = MyError.ERROR_LOG_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            result = log10(second) / log10(first);
        } else if (mOperations.peek().equals(mTokensStrings.getFactorial())) {  // factorial protected
            double number = Double.parseDouble(mNumbers.pop());

            if (number < 0.0d) {
                mErrorCode = MyError.ERROR_NEGATIVE_ARGUMENT_FOR_FACTORIAL;
                throw new ArithmeticException();
            }

            result = fastFactTree(number);
        } else if (mOperations.peek().equals(mTokensStrings.getPercent())) {    // percent protected
            double number = Double.parseDouble(mNumbers.pop());
            result = number / 100.0d;
        } else if (mOperations.peek().equals(mTokensStrings.getSin())) {    // sin protected
            double number = Double.parseDouble(mNumbers.pop());

            if (mConfig.isRadian()) {
                number = toRadians(number);
            }
            result = sin(number);
        } else if (mOperations.peek().equals(mTokensStrings.getCos())) {    // cos protected
            double number = Double.parseDouble(mNumbers.pop());

            if (mConfig.isRadian()) {
                number = toRadians(number);
            }
            result = cos(number);
        } else if (mOperations.peek().equals(mTokensStrings.getTan())) {    // tan protected
            double number = Double.parseDouble(mNumbers.pop());

            if (number == (PI / 2.0d) || number == (PI / 2.0d + PI)) {
                mErrorCode = MyError.ERROR_TAN_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            if (mConfig.isRadian()) {
                number = toRadians(number);
            }
            result = tan(number);
        } else if (mOperations.peek().equals(mTokensStrings.getAsin())) {   // arcsin protected
            double number = Double.parseDouble(mNumbers.pop());

            if (abs(number) > 1.0d) {
                mErrorCode = MyError.ERROR_ARCSIN_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            result = asin(number);

            if (mConfig.isRadian()) {
                result = toDegrees(result);
            }
        } else if (mOperations.peek().equals(mTokensStrings.getAcos())) {   // arccos protected
            double number = Double.parseDouble(mNumbers.pop());

            if (abs(number) > 1.0d) {
                mErrorCode = MyError.ERROR_ARCCOS_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            result = acos(number);

            if (mConfig.isRadian()) {
                result = toDegrees(result);
            }
        } else if (mOperations.peek().equals(mTokensStrings.getAtan())) {   // arctan protected
            double number = Double.parseDouble(mNumbers.pop());

            result = atan(number);

            if (mConfig.isRadian()) {
                result = toDegrees(result);
            }
        } else if (mOperations.peek().equals(mTokensStrings.getSinh())) {   // sinh protected
            double number = Double.parseDouble(mNumbers.pop());

            if (mConfig.isRadian()) {
                number = toRadians(number);
            }
            result = sinh(number);
        } else if (mOperations.peek().equals(mTokensStrings.getCosh())) {   // cosh protected
            double number = Double.parseDouble(mNumbers.pop());

            if (mConfig.isRadian()) {
                number = toRadians(number);
            }
            result = cosh(number);
        } else if (mOperations.peek().equals(mTokensStrings.getTanh())) {   // tanh protected
            double number = Double.parseDouble(mNumbers.pop());
            if (mConfig.isRadian()) {
                number = toRadians(number);
            }
            result = tanh(number);
        } else if (mOperations.peek().equals(mTokensStrings.getAsinh())) {  // arcsinh protected
            double number = Double.parseDouble(mNumbers.pop());

            result = log(number + sqrt((number * number) + 1.0d));

            if (mConfig.isRadian()) {
                result = toDegrees(result);
            }
        } else if (mOperations.peek().equals(mTokensStrings.getAcosh())) {  // arccosh protected
            double number = Double.parseDouble(mNumbers.pop());

            if (number < 1.0d) {
                mErrorCode = MyError.ERROR_ARCCOSH_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            result = log(number + sqrt((number * number) - 1.0d));

            if (mConfig.isRadian()) {
                result = toDegrees(result);
            }
        } else if (mOperations.peek().equals(mTokensStrings.getAtanh())) {  // arctanh protected
            double number = Double.parseDouble(mNumbers.pop());

            if (abs(number) >= 1.0d) {
                // If argument "number" is "x", then: (it's should be) |x| < 1
                mErrorCode = MyError.ERROR_ARCTANH_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            result = 0.5d * log((1.0d + number) / (1.0d - number));

            if (mConfig.isRadian()) {
                result = toDegrees(result);
            }
        } else if (mOperations.peek().equals(mTokensStrings.getExp())) {    // exp protected
            double number = Double.parseDouble(mNumbers.pop());
            result = exp(number);
        } else if (mOperations.peek().equals(mTokensStrings.getRoot())) {   // root (sqrt) protected
            double number = Double.parseDouble(mNumbers.pop());

            if (number < 0.0d) {
                mErrorCode = MyError.ERROR_ROOT_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            result = sqrt(number);
        } else if (mOperations.peek().equals(mTokensStrings.getLn())) { // ln protected
            double number = Double.parseDouble(mNumbers.pop());

            if (number <= 0.0d) {
                mErrorCode = MyError.ERROR_LN_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            result = log(number);
        } else if (mOperations.peek().equals(mTokensStrings.getLg())) { // lg protected
            double number = Double.parseDouble(mNumbers.pop());

            if (number <= 0.0d) {
                mErrorCode = MyError.ERROR_LG_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            result = log10(number);
        } else if (mOperations.peek().equals(mTokensStrings.getUnaryMinus())) { // unary minus protected
            double number = Double.parseDouble(mNumbers.pop());
            result = -number;
        }

        if (Double.isNaN(result)) {
            mErrorCode = MyError.ERROR_NOT_A_NUMBER;
            throw new ArithmeticException();
        }

        mNumbers.push(Double.toString(result));
        mOperations.pop();
    }

    private String start() {
        if (mExpression.equals("") || mExpression.length() <= 0) return "";
        StringTokenizer tokenizer = new StringTokenizer(mExpression, mTokensStrings.getDelimiters(), true);
        String token, previous = "";

        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();

            System.out.println("\n\nSTART");
            System.out.println("NUMBERS: " + mNumbers.toString());
            System.out.println("OPERATIONS: " + mOperations.toString());

            // is Delimiter
            if (isDelimiter(token)) {
                if (token.equals(mTokensStrings.getOpenBracket())) {
                    brackets++;
                    tryInsertNumericFactor(previous);
                    mOperations.push(mTokensStrings.getOpenBracket());
                } else if (token.equals(mTokensStrings.getCloseBracket())) {
                    brackets--;
                    while (!mOperations.isEmpty() && !mOperations.peek().equals(mTokensStrings.getOpenBracket())) {
                        calculate();
                    }
                    mOperations.pop();
                }
            }

            // is Operation or Function
            if (isOperation(token) || isFunction(token)) {
                if (mOperations.isEmpty()) {
                    if (token.equals(mTokensStrings.getMinus()) && isUnaryMinus(previous)) {
                        token = mTokensStrings.getUnaryMinus();
                    } else if (isFunction(token) && !token.equals(mTokensStrings.getFactorial())) {
                        tryInsertNumericFactor(previous);
                    }
                } else {
                    // operations not empty
                    if (isFunction(token) && !token.equals(mTokensStrings.getFactorial())) {
                        tryInsertNumericFactor(previous);
                    } else if (token.equals(mTokensStrings.getMinus()) && isUnaryMinus(previous)) {
                        token = mTokensStrings.getUnaryMinus();
                    }
                    if (!token.equals(mTokensStrings.getUnaryMinus())) {
                        while (!mOperations.isEmpty() && getPriority(token) <= getPriority(mOperations.peek()))
                            calculate();
                    }
                }
                mOperations.push(token);
            }

            // is Number
            if (isNumber(token)) {
                tryInsertNumericFactor(previous);
                if (token.equals(mTokensStrings.getPi())) {
                    token = String.valueOf(PI);
                } else if (token.equals(mTokensStrings.getEuler())) {
                    token = String.valueOf(E);
                }
                if (isContainsFunction(token)) {
                    int len = getFunctionLength(token);
                    String number = token.substring(0, token.length() - len);
                    String function = token.substring(token.length() - len);
                    mNumbers.push(number);

                    while (!mOperations.isEmpty() && getPriority(mTokensStrings.getMultiply()) <= getPriority(mOperations.peek())) {
                        calculate();
                    }

                    mOperations.push(mTokensStrings.getMultiply());
                    mOperations.push(function);
                    token = function;
                } else {
                    mNumbers.push(token);
                }
            }

            previous = token;

            System.out.println("\n\nEND");
            System.out.println("NUMBERS: " + mNumbers.toString());
            System.out.println("OPERATIONS: " + mOperations.toString());
        }

        while (!mOperations.isEmpty()) {
            calculate();
        }

        System.out.println("\n\nLAST END");
        System.out.println("NUMBERS: " + mNumbers.toString());
        System.out.println("OPERATIONS: " + mOperations.toString());

        if (brackets != 0) {
            mErrorCode = MyError.ERROR_BRACKETS_ARE_NOT_MATCHED;
            throw new ArithmeticException();
        }

        return mNumbers.pop();
    }
}
