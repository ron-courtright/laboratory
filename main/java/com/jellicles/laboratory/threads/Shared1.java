package com.jellicles.laboratory.threads;

class Shared1 {
    protected int x = 0, y = 0;

    public synchronized int dif() {
        return x - y;
    }

    public synchronized void bump() throws InterruptedException {
        x++;
        Thread.sleep(9);
        y++;
    }
}