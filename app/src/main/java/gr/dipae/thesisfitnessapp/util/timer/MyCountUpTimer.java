package gr.dipae.thesisfitnessapp.util.timer;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

/**
 * Created by AlexLa
 */
public abstract class MyCountUpTimer {

    private static final int MSG = 1;

    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;
    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;
    private boolean mActive = false;

    // handles counting down
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (MyCountUpTimer.this) {
                if (mCancelled) {
                    return;
                }
                onTick(System.currentTimeMillis());
                sendMessageDelayed(obtainMessage(MSG), mCountdownInterval);
            }
        }
    };

    /**
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyCountUpTimer(long countDownInterval) {
        mCountdownInterval = countDownInterval;
    }

    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mActive = false;
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     */
    public synchronized final MyCountUpTimer start() {
        mCancelled = false;
        mActive = true;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    public boolean isActive() {
        return mActive;
    }

    /**
     * Callback fired on regular interval.
     */
    public abstract void onTick(long timestamp);
}
