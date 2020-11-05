package joel.calculator;

import java.util.Hashtable;
import java.util.Stack;
import java.util.StringTokenizer;

import static java.lang.Math.*;

public final class Calculator {
    private final Stack<String> mNumbers = new Stack<>();
    private final Stack<String> mOperations = new Stack<>();

    private final CalculatorTokens mTokens;
    private final CalculatorConfiguration mConfiguration;

    private final MyError mError;

    private byte mErrorCode = MyError.NO_ERROR;
    private byte brackets = 0;

    private String mExpression;
    private Hashtable<String, String> mHistory;

    public Calculator() {
        mTokens = new CalculatorTokens();
        mConfiguration = new CalculatorConfiguration();
        mError = new MyError(new CalculatorErrorsDescriptions(), mTokens);
        mExpression = "";
        if (mConfiguration.isHistorySupportEnabled()) {
            mHistory = new Hashtable<>();
        }
    }

    // one-time initialization
    public Calculator(String expression) {
        mTokens = new CalculatorTokens();
        mConfiguration = new CalculatorConfiguration();
        mError = new MyError(new CalculatorErrorsDescriptions(), mTokens);
        mExpression = expression;
        if (mConfiguration.isHistorySupportEnabled()) {
            mHistory = new Hashtable<>();
        }
    }

    // one-time initialization
    public Calculator(CalculatorTokens tokens, CalculatorConfiguration configuration, CalculatorErrorsDescriptions errorsDescriptions, String expression, Hashtable<String, String> history) {
        mTokens = tokens;
        mConfiguration = configuration;
        mError = new MyError(errorsDescriptions, mTokens);
        mExpression = expression;
        if (mConfiguration.isHistorySupportEnabled()) {
            mHistory = history;
        }
    }

    public String getExpression() {
        return mExpression;
    }

    public void setExpression(String expression) {
        mExpression = expression;
    }

    public Hashtable<String, String> getHistory() {
        if (mConfiguration.isHistorySupportEnabled()) {
            return mHistory;
        }
        return null;
    }

    public void setHistory(Hashtable<String, String> history) {
        if (mConfiguration.isHistorySupportEnabled()) {
            mHistory = history;
        }
    }

    public String getAnswer() {
        if (mExpression.equals("") || mExpression.equals(mTokens.getOpenBracket() + mTokens.getCloseBracket()) || mExpression.length() <= 0) return "";
        if (mHistory.containsKey(mExpression)) return mHistory.get(mExpression);

        System.out.println("Expression: " + mExpression);
        mErrorCode = MyError.NO_ERROR;
        brackets = 0;
        String result;

        try {
            result = start();

            int precision = mConfiguration.getPrecision();
        } catch (Exception exc) {
            if (mConfiguration.isErrorCheckerEnabled()) {
                if (brackets != 0) {
                    mErrorCode = MyError.ERROR_BRACKETS_ARE_NOT_MATCHED;
                }

                mError.setErrorCode(mErrorCode);
                result = mError.getDescription();
            } else {
                result = "";
            }
        }

        if (mConfiguration.isHistorySupportEnabled()) {
            mHistory.put(mExpression, result);
        }

        return result;
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
        if (token.equals(mTokens.getPi()) || token.equals(mTokens.getEuler()) || token.equals(mTokens.getInfinity())) return true;
        return !isDelimiter(token) && !isFunction(token) && !token.equals(mTokens.getUnaryMinus());
    }

    private boolean isUnaryMinus(String previous) {
        return (isDelimiter(previous) && !previous.equals(mTokens.getCloseBracket()) && !previous.equals(mTokens.getFactorial()) && !previous.equals(mTokens.getPercent()) && !previous.equals(mTokens.getPi()) && !previous.equals(mTokens.getEuler())) || previous.equals("") || previous.equals(mTokens.getUnaryMinus());
    }

    private void tryInsertMultiply(String previous) {
        if (previous.equals(mTokens.getCloseBracket()) || isNumber(previous) || previous.equals(mTokens.getFactorial()) || previous.equals(mTokens.getPercent())) {
            while (mNumbers.size() >= 2 && !mOperations.isEmpty() && getPriority(mTokens.getMultiply()) <= getPriority(mOperations.peek())) {
                calculate();
            }
            mOperations.add(mTokens.getMultiply());
        }
    }

    private boolean isTokenContainsFunction(String token) {
        for (String function : mTokens.getFunctions())
            if (token.contains(function)) return true;
        return false;
    }

    private int getFunctionLengthInToken(String token) {
        for (String function : mTokens.getFunctions())
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
        double answer = 0.0d;

        if (mOperations.peek().equals(mTokens.getPlus())) {  // plus protected
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());

            answer = first + second;
        } else if (mOperations.peek().equals(mTokens.getMinus())) {  // minus protected
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());

            answer = first - second;
        } else if (mOperations.peek().equals(mTokens.getMultiply()) || mOperations.peek().equals(mTokens.getOldMultiply())) {
            double second = Double.parseDouble(mNumbers.pop()); // multiply protected
            double first = Double.parseDouble(mNumbers.pop());

            answer = first * second;
        } else if (mOperations.peek().equals(mTokens.getDivide()) || mOperations.peek().equals(mTokens.getOldDivide())) {
            double second = Double.parseDouble(mNumbers.pop()); // divide protected

            if (second == 0.0d) {
                mErrorCode = MyError.ERROR_DIVISION_BY_ZERO;
                throw new ArithmeticException();
            }

            double first = Double.parseDouble(mNumbers.pop());

            answer = first / second;
        } else if (mOperations.peek().equals(mTokens.getPower())) {  // power protected
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());

            answer = pow(first, second);
        } else if (mOperations.peek().equals(mTokens.getLog())) {    // log protected
            double second = Double.parseDouble(mNumbers.pop());
            double first = Double.parseDouble(mNumbers.pop());

            if (first == 1.0d || first <= 0.0d || second <= 0.0d) {
                mErrorCode = MyError.ERROR_LOG_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            answer = log10(second) / log10(first);
        } else if (mOperations.peek().equals(mTokens.getFactorial())) {  // factorial protected
            double number = Double.parseDouble(mNumbers.pop());

            if (number < 0.0d) {
                mErrorCode = MyError.ERROR_NEGATIVE_ARGUMENT_FOR_FACTORIAL;
                throw new ArithmeticException();
            }

            answer = fastFactTree(number);
        } else if (mOperations.peek().equals(mTokens.getPercent())) {    // percent protected
            double number = Double.parseDouble(mNumbers.pop());

            answer = number / 100.0d;
        } else if (mOperations.peek().equals(mTokens.getSin())) {    // sin protected
            double number = Double.parseDouble(mNumbers.pop());

            if (mConfiguration.isRadian()) {
                number = toRadians(number);
            }

            answer = sin(number);
        } else if (mOperations.peek().equals(mTokens.getCos())) {    // cos protected
            double number = Double.parseDouble(mNumbers.pop());

            if (mConfiguration.isRadian()) {
                number = toRadians(number);
            }

            answer = cos(number);
        } else if (mOperations.peek().equals(mTokens.getTan())) {    // tan protected
            double number = Double.parseDouble(mNumbers.pop());

            if (number == (PI / 2.0d) || number == (PI / 2.0d + PI)) {
                mErrorCode = MyError.ERROR_TAN_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            if (mConfiguration.isRadian()) {
                number = toRadians(number);
            }

            answer = tan(number);
        } else if (mOperations.peek().equals(mTokens.getAsin())) {   // arcsin protected
            double number = Double.parseDouble(mNumbers.pop());

            if (abs(number) > 1.0d) {
                mErrorCode = MyError.ERROR_ARCSIN_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            answer = asin(number);

            if (mConfiguration.isRadian()) {
                answer = toDegrees(answer);
            }
        } else if (mOperations.peek().equals(mTokens.getAcos())) {   // arccos protected
            double number = Double.parseDouble(mNumbers.pop());

            if (abs(number) > 1.0d) {
                mErrorCode = MyError.ERROR_ARCCOS_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            answer = acos(number);

            if (mConfiguration.isRadian()) {
                answer = toDegrees(answer);
            }
        } else if (mOperations.peek().equals(mTokens.getAtan())) {   // arctan protected
            double number = Double.parseDouble(mNumbers.pop());

            answer = atan(number);

            if (mConfiguration.isRadian()) {
                answer = toDegrees(answer);
            }
        } else if (mOperations.peek().equals(mTokens.getSinh())) {   // sinh protected
            double number = Double.parseDouble(mNumbers.pop());

            if (mConfiguration.isRadian()) {
                number = toRadians(number);
            }

            answer = sinh(number);
        } else if (mOperations.peek().equals(mTokens.getCosh())) {   // cosh protected
            double number = Double.parseDouble(mNumbers.pop());

            if (mConfiguration.isRadian()) {
                number = toRadians(number);
            }

            answer = cosh(number);
        } else if (mOperations.peek().equals(mTokens.getTanh())) {   // tanh protected
            double number = Double.parseDouble(mNumbers.pop());

            if (mConfiguration.isRadian()) {
                number = toRadians(number);
            }

            answer = tanh(number);
        } else if (mOperations.peek().equals(mTokens.getAsinh())) {  // arcsinh protected
            double number = Double.parseDouble(mNumbers.pop());

            answer = log(number + sqrt((number * number) + 1.0d));

            if (mConfiguration.isRadian()) {
                answer = toDegrees(answer);
            }
        } else if (mOperations.peek().equals(mTokens.getAcosh())) {  // arccosh protected
            double number = Double.parseDouble(mNumbers.pop());

            if (number < 1.0d) {
                mErrorCode = MyError.ERROR_ARCCOSH_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            answer = log(number + sqrt((number * number) - 1.0d));

            if (mConfiguration.isRadian()) {
                answer = toDegrees(answer);
            }
        } else if (mOperations.peek().equals(mTokens.getAtanh())) {  // arctanh protected
            double number = Double.parseDouble(mNumbers.pop());

            if (abs(number) >= 1.0d) {
                // If argument "number" is "x", then: (it's should be) |x| < 1
                mErrorCode = MyError.ERROR_ARCTANH_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            answer = 0.5d * log((1.0d + number) / (1.0d - number));

            if (mConfiguration.isRadian()) {
                answer = toDegrees(answer);
            }
        } else if (mOperations.peek().equals(mTokens.getExp())) {    // exp protected
            double number = Double.parseDouble(mNumbers.pop());

            answer = exp(number);
        } else if (mOperations.peek().equals(mTokens.getRoot())) {   // root (sqrt) protected
            double number = Double.parseDouble(mNumbers.pop());

            if (number < 0.0d) {
                mErrorCode = MyError.ERROR_ROOT_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            answer = sqrt(number);
        } else if (mOperations.peek().equals(mTokens.getLn())) { // ln protected
            double number = Double.parseDouble(mNumbers.pop());

            if (number <= 0.0d) {
                mErrorCode = MyError.ERROR_LN_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            answer = log(number);
        } else if (mOperations.peek().equals(mTokens.getLg())) { // lg protected
            double number = Double.parseDouble(mNumbers.pop());

            if (number <= 0.0d) {
                mErrorCode = MyError.ERROR_LG_FUNCTION_DEFINITION_AREA;
                throw new ArithmeticException();
            }

            answer = log10(number);
        } else if (mOperations.peek().equals(mTokens.getUnaryMinus())) { // unary minus protected
            double number = Double.parseDouble(mNumbers.pop());
            answer = -number;
        }

        if (Double.isNaN(answer)) {
            mErrorCode = MyError.ERROR_NOT_A_NUMBER;
            throw new ArithmeticException();
        }

        mNumbers.push(String.valueOf(answer));
        mOperations.pop();
    }

    private String start() {
        StringTokenizer tokenizer = new StringTokenizer(mExpression, mTokens.getDelimiters(), true);
        String token, previous = "";

        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();

            // is Delimiter
            if (isDelimiter(token)) {
                if (token.equals(mTokens.getOpenBracket())) {
                    brackets++;
                    tryInsertMultiply(previous);
                    mOperations.push(mTokens.getOpenBracket());
                } else if (token.equals(mTokens.getCloseBracket()) || token.equals(mTokens.getSeparator())) {
                    brackets--;
                    while (!mOperations.isEmpty() && !mOperations.peek().equals(mTokens.getOpenBracket())) {
                        calculate();
                    }
                    mOperations.pop();  // delete open bracket
                }
            }

            // is Operation or Function
            if (isOperation(token) || isFunction(token)) {
                if (mOperations.isEmpty()) {
                    if (token.equals(mTokens.getMinus()) && isUnaryMinus(previous)) {
                        token = mTokens.getUnaryMinus();
                    } else if (isFunction(token) && !token.equals(mTokens.getFactorial())) {
                        tryInsertMultiply(previous);
                    }
                } else {
                    // operations not empty
                    if (isFunction(token) && !token.equals(mTokens.getFactorial())) {
                        tryInsertMultiply(previous);
                    } else if (token.equals(mTokens.getMinus()) && isUnaryMinus(previous)) {
                        token = mTokens.getUnaryMinus();
                    }
                    if (!token.equals(mTokens.getUnaryMinus())) {
                        while (!mOperations.isEmpty() && getPriority(token) <= getPriority(mOperations.peek()))
                            calculate();
                    }
                }
                mOperations.push(token);

                if (token.equals(mTokens.getLog())) {
                    mOperations.push(mTokens.getOpenBracket());
                    brackets++;
                }
            }

            // is Number
            if (isNumber(token)) {
                tryInsertMultiply(previous);
                if (token.equals(mTokens.getPi())) {
                    token = String.valueOf(PI);
                } else if (token.equals(mTokens.getEuler())) {
                    token = String.valueOf(E);
                } else if (token.equals(mTokens.getInfinity())) {
                    token = String.valueOf(Double.POSITIVE_INFINITY);
                }

                if (isTokenContainsFunction(token)) {
                    int len = getFunctionLengthInToken(token);
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

        if (brackets != 0) {
            mErrorCode = MyError.ERROR_BRACKETS_ARE_NOT_MATCHED;
            throw new ArithmeticException();
        }

        return mNumbers.pop();
    }
}
