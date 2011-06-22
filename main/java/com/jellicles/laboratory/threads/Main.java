package com.jellicles.laboratory.threads;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Thread t = new Locker("Main.Locker");
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
