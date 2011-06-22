package com.jellicles.laboratory.threads;

public class Caller implements Runnable {
    String msg;
    CallMe target;
    Thread t;

    public Caller(CallMe targ, String s) {
        target = targ;
        msg = s;
        t = new Thread(this);
        t.start();
    }

    // synchronize calls to call()
    @Override
    public void run() {
        synchronized (target) { // synchronized block
            target.call(msg);
        }
    }

}
