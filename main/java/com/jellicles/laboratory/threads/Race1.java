package com.jellicles.laboratory.threads;

class Race1 extends Thread {
    static Shared1 s;
    static volatile boolean done = false;

    public static void main(String[] args) {
        Thread lo = new Race1();
        s = new Shared1();
        try {
            lo.start();
            while (!done) {
                s.bump();
                Thread.sleep(30);
            }
            lo.join();
        } catch (InterruptedException e) {
            return;
        }
    }

    public void run() {
        try {
            int i;
            for (i = 0; i < 1000; i++) {
                if (i % 60 == 0)
                    System.out.println();
                String str = ".X";
                System.out.print(str.charAt(s.dif()));
                Thread.sleep(20);
            }
            System.out.println();
            done = true;
        } catch (InterruptedException e) {
            return;
        }
    }
}