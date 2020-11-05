package joel.calculator;

import java.util.Hashtable;

public final class CalculatorHistory {
    private final Hashtable<String, String> mHistory;

    public CalculatorHistory() {
        mHistory = new Hashtable<>();
    }

    public Hashtable<String, String> getHistory() {
        return mHistory;
    }

    public void put(String key, String value) {
        mHistory.put(key, value);
    }

    public boolean containsKey(String key) {
        return mHistory.containsKey(key);
    }

    public String get(String key) {
        return mHistory.get(key);
    }
}
