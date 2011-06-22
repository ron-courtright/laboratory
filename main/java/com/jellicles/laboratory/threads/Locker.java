package com.jellicles.laboratory.threads;

public class Locker extends Thread {

    public Locker(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    private Integer key = new Integer(6);
    private Blocker blocker = new Blocker("Blocker");

    public void run() {
        synchronized (key) {
            int value = blocker.getShare();
            // naughty
            blocker.setShare(++value);
        }
    }
}
