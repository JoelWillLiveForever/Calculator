package joel.calculator;

public final class Configuration {
    private byte mPrecision = 7;
    private boolean isRadian = true;
    private boolean isErrorCheckerEnabled = true;

    public Configuration() {
        // void
    }

    public Configuration(byte precision, boolean isRadian, boolean isErrorCheckerEnabled) {
        mPrecision = precision;
        this.isRadian = isRadian;
        this.isErrorCheckerEnabled = isErrorCheckerEnabled;
    }

    public byte getPrecision() {
        return mPrecision;
    }

    public void setPrecision(byte precision) {
        if (precision > 17) {
            precision = 17;
        } else if (precision < 0) {
            precision = 0;
        }
        mPrecision = precision;
    }

    public boolean isRadian() {
        return isRadian;
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
}
