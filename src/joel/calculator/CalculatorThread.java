package joel.calculator;

public final class CalculatorThread implements Runnable {
    private final Thread mThread = new Thread(this);
    private int mThreadPriority;

    public CalculatorThread() {
        mThreadPriority = Thread.NORM_PRIORITY;
    }

    public CalculatorThread(int threadPriority) {
        mThreadPriority = threadPriority;
    }

    @Override
    public void run() {

    }

    public void start() {
        mThread.setPriority(mThreadPriority);
        mThread.start();
    }

    public int getThreadPriority() {
        return mThreadPriority;
    }

    public void setThreadPriority(int threadPriority) {
        mThreadPriority = threadPriority;
    }
}
