package joel.calculator;

public final class CalculatorErrorsDescriptions {
    public static final String MARKER = "{WORD}";

    private String mMagicError;
    private String mDivisionByZeroError;
    private String mBracketsAreNotMatchedError;
    private String mNegativeArgumentForFactorialError;
    private String mDetailedFunctionDefinitionAreaError;
    private String mNanError;

    public CalculatorErrorsDescriptions() {
        mMagicError = "Error!";
        mDivisionByZeroError = "You cannot divide by zero!";
        mBracketsAreNotMatchedError = "Brackets are not matched!";
        mNegativeArgumentForFactorialError = "The argument for factorial can only be a natural number!";
        mDetailedFunctionDefinitionAreaError = "The argument for the \"" + MARKER + "\" function is not in it's scope!";
        mNanError = "Not a number!";
    }

    public CalculatorErrorsDescriptions(String magicError, String divisionByZeroError, String bracketsAreNotMatchedError, String negativeArgumentForFactorialError, String detailedFunctionDefinitionAreaError, String nanError) {
        mMagicError = magicError;
        mDivisionByZeroError = divisionByZeroError;
        mBracketsAreNotMatchedError = bracketsAreNotMatchedError;
        mNegativeArgumentForFactorialError = negativeArgumentForFactorialError;
        mDetailedFunctionDefinitionAreaError = detailedFunctionDefinitionAreaError;
        mNanError = nanError;
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

    public String getNanError() {
        return mNanError;
    }

    public void setNanError(String nanError) {
        mNanError = nanError;
    }
}
