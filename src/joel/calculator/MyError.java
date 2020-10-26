package joel.calculator;

final class MyError {
    private static MyError instance = null;

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

    private final ErrorDescription mErrorDescription = ErrorDescription.getInstance();
    private final Token mToken = Token.getInstance();

    private byte mErrorCode = 0;

    private MyError() {
        // void
    }

    public static MyError getInstance() {
        if (instance == null) {
            instance = new MyError();
        }
        return instance;
    }

    public byte getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(byte errorCode) {
        mErrorCode = errorCode;
    }

    public ErrorDescription getErrorDescription() {
        return mErrorDescription;
    }

    public String getDescription() {
        switch (mErrorCode) {
            case NO_ERROR: {
                return mErrorDescription.getMagicError();
            }
            case ERROR_DIVISION_BY_ZERO: {
                return mErrorDescription.getDivisionByZeroError();
            }
            case ERROR_BRACKETS_ARE_NOT_MATCHED: {
                return mErrorDescription.getBracketsAreNotMatchedError();
            }
            case ERROR_NEGATIVE_ARGUMENT_FOR_FACTORIAL: {
                return mErrorDescription.getNegativeArgumentForFactorialError();
            }
            case ERROR_TAN_FUNCTION_DEFINITION_AREA: {
                return mErrorDescription.getDetailedFunctionDefinitionAreaError().replace(ErrorDescription.MARKER, mToken.getTan());
            }
            case ERROR_ARCSIN_FUNCTION_DEFINITION_AREA: {
                return mErrorDescription.getDetailedFunctionDefinitionAreaError().replace(ErrorDescription.MARKER, mToken.getAsin());
            }
            case ERROR_ARCCOS_FUNCTION_DEFINITION_AREA: {
                return mErrorDescription.getDetailedFunctionDefinitionAreaError().replace(ErrorDescription.MARKER, mToken.getAcos());
            }
            case ERROR_ARCCOSH_FUNCTION_DEFINITION_AREA: {
                return mErrorDescription.getDetailedFunctionDefinitionAreaError().replace(ErrorDescription.MARKER, mToken.getAcosh());
            }
            case ERROR_ARCTANH_FUNCTION_DEFINITION_AREA: {
                return mErrorDescription.getDetailedFunctionDefinitionAreaError().replace(ErrorDescription.MARKER, mToken.getAtanh());
            }
            case ERROR_ROOT_FUNCTION_DEFINITION_AREA: {
                return mErrorDescription.getDetailedFunctionDefinitionAreaError().replace(ErrorDescription.MARKER, mToken.getRoot());
            }
            case ERROR_LOG_FUNCTION_DEFINITION_AREA: {
                return mErrorDescription.getDetailedFunctionDefinitionAreaError().replace(ErrorDescription.MARKER, mToken.getLog());
            }
            case ERROR_LG_FUNCTION_DEFINITION_AREA: {
                return mErrorDescription.getDetailedFunctionDefinitionAreaError().replace(ErrorDescription.MARKER, mToken.getLg());
            }
            case ERROR_LN_FUNCTION_DEFINITION_AREA: {
                return mErrorDescription.getDetailedFunctionDefinitionAreaError().replace(ErrorDescription.MARKER, mToken.getLn());
            }
            case ERROR_NOT_A_NUMBER: {
                return mErrorDescription.getNotANumberError();
            }
        }
        return mErrorDescription.getMagicError();
    }
}
