package joel.calculator;

public final class Configuration {
    private static Configuration instance = null;

    public static final byte MAX_PRECISION = 17;
    public static final byte MIN_PRECISION = 0;

    private byte mPrecision = 7;
    private boolean isRadian = true;
    private boolean isErrorCheckerEnabled = true;

    private Configuration() {
        // void
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
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
}
