package joel.calculator;

public final class CalculatorConfiguration {
    public static final byte MAX_PRECISION = 17;
    public static final byte MIN_PRECISION = 0;

    private byte mPrecision;
    private boolean isRadian;
    private boolean isErrorCheckerEnabled;
    private boolean isHistorySupportEnabled;

    public CalculatorConfiguration() {
        mPrecision = 7;
        isRadian = true;
        isErrorCheckerEnabled = true;
        isHistorySupportEnabled = true;
    }

    public CalculatorConfiguration(byte precision, boolean isRadian, boolean isErrorCheckerEnabled, boolean isHistorySupportEnabled) {
        if (precision > MAX_PRECISION) {
            precision = MAX_PRECISION;
        } else if (precision < MIN_PRECISION) {
            precision = MIN_PRECISION;
        }
        mPrecision = precision;
        this.isRadian = isRadian;
        this.isErrorCheckerEnabled = isErrorCheckerEnabled;
        this.isHistorySupportEnabled = isHistorySupportEnabled;
    }

    public byte getPrecision() {
        return mPrecision;
    }

    public void setPrecision(byte precision) {
        if (precision > MAX_PRECISION) {
            precision = MAX_PRECISION;
        } else if (precision < MIN_PRECISION) {
            precision = MIN_PRECISION;
        }
        mPrecision = precision;
    }

    public boolean isRadian() {
        return !isRadian;
    }

    public void setRadian(boolean radian) {
        isRadian = radian;
    }

    public boolean isErrorCheckerEnabled() {
        return isErrorCheckerEnabled;
    }

    public void setErrorCheckerEnabled(boolean errorCheckerEnabled) {
        isErrorCheckerEnabled = errorCheckerEnabled;
    }

    public boolean isHistorySupportEnabled() {
        return isHistorySupportEnabled;
    }

    public void setHistorySupportEnabled(boolean historySupportEnabled) {
        isHistorySupportEnabled = historySupportEnabled;
    }
}
