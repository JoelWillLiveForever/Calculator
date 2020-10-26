package joel.calculator;

public final class ErrorDescription {
    private static ErrorDescription instance = null;

    public static final String MARKER = "{WORD}";

    private String mMagicError = "Error!";
    private String mDivisionByZeroError = "You cannot divide by zero!";
    private String mBracketsAreNotMatchedError = "Brackets are not matched!";
    private String mNegativeArgumentForFactorialError = "The argument for factorial can only be a natural number!";
    private String mDetailedFunctionDefinitionAreaError = "The argument for the \"" + MARKER + "\" function is not in it's scope!";
    private String mNotANumberError = "Not a number!";

    private ErrorDescription() {
        // void
    }

    public static ErrorDescription getInstance() {
        if (instance == null) {
            instance = new ErrorDescription();
        }
        return instance;
    }

    public String getMagicError() {
        return mMagicError;
    }

    public void setMagicError(String magicError) {
        mMagicError = magicError;
    }

    public String getDivisionByZeroError() {
        return mDivisionByZeroError;
    }

    public void setDivisionByZeroError(String divisionByZeroError) {
        mDivisionByZeroError = divisionByZeroError;
    }

    public String getBracketsAreNotMatchedError() {
        return mBracketsAreNotMatchedError;
    }

    public void setBracketsAreNotMatchedError(String bracketsAreNotMatchedError) {
        mBracketsAreNotMatchedError = bracketsAreNotMatchedError;
    }

    public String getNegativeArgumentForFactorialError() {
        return mNegativeArgumentForFactorialError;
    }

    public void setNegativeArgumentForFactorialError(String negativeArgumentForFactorialError) {
        mNegativeArgumentForFactorialError = negativeArgumentForFactorialError;
    }

    public String getDetailedFunctionDefinitionAreaError() {
        return mDetailedFunctionDefinitionAreaError;
    }

    public void setDetailedFunctionDefinitionAreaError(String detailedFunctionDefinitionAreaError) {
        mDetailedFunctionDefinitionAreaError = detailedFunctionDefinitionAreaError;
    }

    public String getNotANumberError() {
        return mNotANumberError;
    }

    public void setNotANumberError(String notANumberError) {
        mNotANumberError = notANumberError;
    }
}
