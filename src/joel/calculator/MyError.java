package joel.calculator;

final class MyError {
    public static final byte NO_ERROR = 0x0;
    public static final byte ERROR_DIVISION_BY_ZERO = 0x1;
    public static final byte ERROR_BRACKETS_ARE_NOT_MATCHED = 0x2;
    public static final byte ERROR_NEGATIVE_ARGUMENT_FOR_FACTORIAL = 0x3;
    public static final byte ERROR_TAN_FUNCTION_DEFINITION_AREA = 0x4;
    public static final byte ERROR_ARCSIN_FUNCTION_DEFINITION_AREA = 0x5;
    public static final byte ERROR_ARCCOS_FUNCTION_DEFINITION_AREA = 0x6;
    public static final byte ERROR_ARCCOSH_FUNCTION_DEFINITION_AREA = 0x7;
    public static final byte ERROR_ARCTANH_FUNCTION_DEFINITION_AREA = 0x8;
    public static final byte ERROR_ROOT_FUNCTION_DEFINITION_AREA = 0x9;
    public static final byte ERROR_LOG_FUNCTION_DEFINITION_AREA = 0x10;
    public static final byte ERROR_LG_FUNCTION_DEFINITION_AREA = 0x11;
    public static final byte ERROR_LN_FUNCTION_DEFINITION_AREA = 0x12;
    public static final byte ERROR_NOT_A_NUMBER = 0x13;

    private final CalculatorErrorsDescriptions mCalculatorErrorsDescriptions;
    private final CalculatorTokens mCalculatorTokens;

    private byte mErrorCode = 0;

    // one-time initialization
    public MyError(CalculatorErrorsDescriptions calculatorErrorsDescriptions, CalculatorTokens calculatorTokens) {
        mCalculatorErrorsDescriptions = calculatorErrorsDescriptions;
        mCalculatorTokens = calculatorTokens;
    }

    public byte getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(byte errorCode) {
        mErrorCode = errorCode;
    }

    public String getDescription() {
        switch (mErrorCode) {
            case NO_ERROR: {
                return mCalculatorErrorsDescriptions.getMagicError();
            }
            case ERROR_DIVISION_BY_ZERO: {
                return mCalculatorErrorsDescriptions.getDivisionByZeroError();
            }
            case ERROR_BRACKETS_ARE_NOT_MATCHED: {
                return mCalculatorErrorsDescriptions.getBracketsAreNotMatchedError();
            }
            case ERROR_NEGATIVE_ARGUMENT_FOR_FACTORIAL: {
                return mCalculatorErrorsDescriptions.getNegativeArgumentForFactorialError();
            }
            case ERROR_TAN_FUNCTION_DEFINITION_AREA: {
                return mCalculatorErrorsDescriptions.getDetailedFunctionDefinitionAreaError().replace(CalculatorErrorsDescriptions.MARKER, mCalculatorTokens.getTan());
            }
            case ERROR_ARCSIN_FUNCTION_DEFINITION_AREA: {
                return mCalculatorErrorsDescriptions.getDetailedFunctionDefinitionAreaError().replace(CalculatorErrorsDescriptions.MARKER, mCalculatorTokens.getAsin());
            }
            case ERROR_ARCCOS_FUNCTION_DEFINITION_AREA: {
                return mCalculatorErrorsDescriptions.getDetailedFunctionDefinitionAreaError().replace(CalculatorErrorsDescriptions.MARKER, mCalculatorTokens.getAcos());
            }
            case ERROR_ARCCOSH_FUNCTION_DEFINITION_AREA: {
                return mCalculatorErrorsDescriptions.getDetailedFunctionDefinitionAreaError().replace(CalculatorErrorsDescriptions.MARKER, mCalculatorTokens.getAcosh());
            }
            case ERROR_ARCTANH_FUNCTION_DEFINITION_AREA: {
                return mCalculatorErrorsDescriptions.getDetailedFunctionDefinitionAreaError().replace(CalculatorErrorsDescriptions.MARKER, mCalculatorTokens.getAtanh());
            }
            case ERROR_ROOT_FUNCTION_DEFINITION_AREA: {
                return mCalculatorErrorsDescriptions.getDetailedFunctionDefinitionAreaError().replace(CalculatorErrorsDescriptions.MARKER, mCalculatorTokens.getRoot());
            }
            case ERROR_LOG_FUNCTION_DEFINITION_AREA: {
                return mCalculatorErrorsDescriptions.getDetailedFunctionDefinitionAreaError().replace(CalculatorErrorsDescriptions.MARKER, mCalculatorTokens.getLog());
            }
            case ERROR_LG_FUNCTION_DEFINITION_AREA: {
                return mCalculatorErrorsDescriptions.getDetailedFunctionDefinitionAreaError().replace(CalculatorErrorsDescriptions.MARKER, mCalculatorTokens.getLg());
            }
            case ERROR_LN_FUNCTION_DEFINITION_AREA: {
                return mCalculatorErrorsDescriptions.getDetailedFunctionDefinitionAreaError().replace(CalculatorErrorsDescriptions.MARKER, mCalculatorTokens.getLn());
            }
            case ERROR_NOT_A_NUMBER: {
                return mCalculatorErrorsDescriptions.getNanError();
            }
        }
        return mCalculatorErrorsDescriptions.getMagicError();
    }
}
