package com.jellicles.laboratory.threads;

public class Blocker extends Thread {

    Integer share = new Integer(42);

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        // dangerous as is!
        this.share = share;
    }

    public Blocker(String arg0) {
        super(arg0);
    }

}
