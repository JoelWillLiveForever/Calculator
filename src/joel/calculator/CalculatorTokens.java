package joel.calculator;

public final class CalculatorTokens {
    private String mInfinity;

    private String mPlus;
    private String mMinus;

    private String mPower;

    private String mDivide;
    private String mMultiply;

    private String mOldDivide;
    private String mOldMultiply;

    private String mLg;
    private String mLn;

    private String mLog;
    private String mExp;

    private String mSin;
    private String mCos;
    private String mTan;

    private String mSinh;
    private String mCosh;
    private String mTanh;

    private String mAsin;
    private String mAcos;
    private String mAtan;

    private String mAsinh;
    private String mAcosh;
    private String mAtanh;

    private String mEuler;
    private String mPi;

    private String mPercent;
    private String mRoot;

    private String mSeparator;
    private String mFactorial;
    private String mOpenBracket;
    private String mCloseBracket;
    private String mUnaryMinus;

    private String[] mFunctions;

    private String mOperations;
    private String mDelimiters;

    public CalculatorTokens() {
        mInfinity = "∞";
        mPlus = "+";
        mMinus = "-";
        mPower = "^";
        mDivide = "÷";
        mMultiply = "×";
        mOldDivide = "/";
        mOldMultiply = "*";
        mLg = "lg";
        mLn = "ln";
        mLog = "log";
        mExp = "exp";
        mSin = "sin";
        mCos = "cos";
        mTan = "tan";
        mSinh = "sinh";
        mCosh = "cosh";
        mTanh = "tanh";
        mAsin = "asin";
        mAcos = "acos";
        mAtan = "atan";
        mAsinh = "asinh";
        mAcosh = "acosh";
        mAtanh = "atanh";
        mEuler = "e";
        mPi = "π";
        mPercent = "%";
        mRoot = "√";
        mSeparator = ";";
        mFactorial = "!";
        mOpenBracket = "(";
        mCloseBracket = ")";
        mUnaryMinus = "u-";
        mFunctions = new String[]{mSin, mCos, mTan, mLog, mRoot, mFactorial, mLg, mLn, mExp, mAsin, mAcos, mAtan, mSinh, mCosh, mTanh, mAsinh, mAcosh, mAtanh};
        mOperations = mPlus + mMinus + mPower + mDivide + mMultiply + mOldDivide + mOldMultiply + mPercent;
        mDelimiters = mOperations + mSeparator + mRoot + mPi + mEuler + mOpenBracket + mCloseBracket + mFactorial + mInfinity;
    }

    public CalculatorTokens(String infinity, String plus, String minus, String power, String divide, String multiply, String oldDivide, String oldMultiply, String lg, String ln, String log, String exp, String sin, String cos, String tan, String sinh, String cosh, String tanh, String asin, String acos, String atan, String asinh, String acosh, String atanh, String euler, String pi, String percent, String root, String separator, String factorial, String openBracket, String closeBracket, String unaryMinus, String[] functions, String operations, String delimiters) {
        mInfinity = infinity;
        mPlus = plus;
        mMinus = minus;
        mPower = power;
        mDivide = divide;
        mMultiply = multiply;
        mOldDivide = oldDivide;
        mOldMultiply = oldMultiply;
        mLg = lg;
        mLn = ln;
        mLog = log;
        mExp = exp;
        mSin = sin;
        mCos = cos;
        mTan = tan;
        mSinh = sinh;
        mCosh = cosh;
        mTanh = tanh;
        mAsin = asin;
        mAcos = acos;
        mAtan = atan;
        mAsinh = asinh;
        mAcosh = acosh;
        mAtanh = atanh;
        mEuler = euler;
        mPi = pi;
        mPercent = percent;
        mRoot = root;
        mSeparator = separator;
        mFactorial = factorial;
        mOpenBracket = openBracket;
        mCloseBracket = closeBracket;
        mUnaryMinus = unaryMinus;
        mFunctions = functions;
        mOperations = operations;
        mDelimiters = delimiters;
    }

    public String getInfinity() {
        return mInfinity;
    }

    public void setInfinity(String infinity) {
        mInfinity = infinity;
    }

    public String getUnaryMinus() {
        return mUnaryMinus;
    }

    public void setUnaryMinus(String unaryMinus) {
        this.mUnaryMinus = unaryMinus;
    }

    public String getPlus() {
        return mPlus;
    }

    public void setPlus(String plus) {
        this.mPlus = plus;
    }

    public String getMinus() {
        return mMinus;
    }

    public void setMinus(String minus) {
        this.mMinus = minus;
    }

    public String getPower() {
        return mPower;
    }

    public void setPower(String power) {
        this.mPower = power;
    }

    public String getDivide() {
        return mDivide;
    }

    public void setDivide(String divide) {
        this.mDivide = divide;
    }

    public String getMultiply() {
        return mMultiply;
    }

    public void setMultiply(String multiply) {
        this.mMultiply = multiply;
    }

    public String getOldDivide() {
        return mOldDivide;
    }

    public void setOldDivide(String oldDivide) {
        this.mOldDivide = oldDivide;
    }

    public String getOldMultiply() {
        return mOldMultiply;
    }

    public void setOldMultiply(String oldMultiply) {
        this.mOldMultiply = oldMultiply;
    }

    public String getLg() {
        return mLg;
    }

    public void setLg(String lg) {
        this.mLg = lg;
    }

    public String getLn() {
        return mLn;
    }

    public void setLn(String ln) {
        this.mLn = ln;
    }

    public String getLog() {
        return mLog;
    }

    public void setLog(String log) {
        this.mLog = log;
    }

    public String getExp() {
        return mExp;
    }

    public void setExp(String exp) {
        this.mExp = exp;
    }

    public String getSin() {
        return mSin;
    }

    public void setSin(String sin) {
        this.mSin = sin;
    }

    public String getCos() {
        return mCos;
    }

    public void setCos(String cos) {
        this.mCos = cos;
    }

    public String getTan() {
        return mTan;
    }

    public void setTan(String tan) {
        this.mTan = tan;
    }

    public String getSinh() {
        return mSinh;
    }

    public void setSinh(String sinh) {
        this.mSinh = sinh;
    }

    public String getCosh() {
        return mCosh;
    }

    public void setCosh(String cosh) {
        this.mCosh = cosh;
    }

    public String getTanh() {
        return mTanh;
    }

    public void setTanh(String tanh) {
        this.mTanh = tanh;
    }

    public String getAsin() {
        return mAsin;
    }

    public void setAsin(String asin) {
        this.mAsin = asin;
    }

    public String getAcos() {
        return mAcos;
    }

    public void setAcos(String acos) {
        this.mAcos = acos;
    }

    public String getAtan() {
        return mAtan;
    }

    public void setAtan(String atan) {
        this.mAtan = atan;
    }

    public String getAsinh() {
        return mAsinh;
    }

    public void setAsinh(String asinh) {
        this.mAsinh = asinh;
    }

    public String getAcosh() {
        return mAcosh;
    }

    public void setAcosh(String acosh) {
        this.mAcosh = acosh;
    }

    public String getAtanh() {
        return mAtanh;
    }

    public void setAtanh(String atanh) {
        this.mAtanh = atanh;
    }

    public String getEuler() {
        return mEuler;
    }

    public void setEuler(String euler) {
        mEuler = euler;
    }

    public String getPi() {
        return mPi;
    }

    public void setPi(String pi) {
        this.mPi = pi;
    }

    public String getPercent() {
        return mPercent;
    }

    public void setPercent(String percent) {
        this.mPercent = percent;
    }

    public String getRoot() {
        return mRoot;
    }

    public void setRoot(String root) {
        this.mRoot = root;
    }

    public String getSeparator() {
        return mSeparator;
    }

    public void setSeparator(String separator) {
        this.mSeparator = separator;
    }

    public String getFactorial() {
        return mFactorial;
    }

    public void setFactorial(String factorial) {
        this.mFactorial = factorial;
    }

    public String getOpenBracket() {
        return mOpenBracket;
    }

    public void setOpenBracket(String openBracket) {
        this.mOpenBracket = openBracket;
    }

    public String getCloseBracket() {
        return mCloseBracket;
    }

    public void setCloseBracket(String closeBracket) {
        this.mCloseBracket = closeBracket;
    }

    public String[] getFunctions() {
        return mFunctions;
    }

    public void setFunctions(String[] functions) {
        this.mFunctions = functions;
    }

    public String getOperations() {
        return mOperations;
    }

    public void setOperations(String operations) {
        this.mOperations = operations;
    }

    public String getDelimiters() {
        return mDelimiters;
    }

    public void setDelimiters(String delimiters) {
        this.mDelimiters = delimiters;
    }

    public void commit() {
        mFunctions = new String[]{mSin, mCos, mTan, mLog, mRoot, mFactorial, mLg, mLn, mExp, mAsin, mAcos, mAtan, mSinh, mCosh, mTanh, mAsinh, mAcosh, mAtanh};
        mOperations = mPlus + mMinus + mPower + mDivide + mMultiply + mOldDivide + mOldMultiply + mPercent;
        mDelimiters = mOperations + mSeparator + mRoot + mPi + mEuler + mOpenBracket + mCloseBracket + mFactorial + mInfinity;
    }
}
