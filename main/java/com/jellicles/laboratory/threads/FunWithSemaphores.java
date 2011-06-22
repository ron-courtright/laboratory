package com.jellicles.laboratory.threads;

import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author rcourtright
 * Date: 3/25/11
 * Time: 9:52 AM
 */
public class FunWithSemaphores {

    private class Managed implements Runnable {

        private Logger logger = Logger.getLogger(Managed.class);

        final private long count;
        final private Semaphore semaphore;

        public Managed(long count, Semaphore semaphore) {
            this.count = count * 10;
            this.semaphore = semaphore;
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p/>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            int maxLoop = 10;
            for (int i = 0; i < maxLoop; i++) {
                try {
                    semaphore.acquire();
                    Thread.sleep(TimeUnit.MILLISECONDS.convert(count, TimeUnit.MILLISECONDS));
                    logger.info("Managed" + count + " has finished task " + (i + 1) + " of " + maxLoop);
                } catch (InterruptedException ignored) {
                } finally {
                    semaphore.release();
                }
            }
            logger.info(count + " has finished the race");
            try {
            } catch (Throwable t) {
                throw new RuntimeException(t.getLocalizedMessage(), t);
            }
        }
    }

    final private Logger logger;
    final private Semaphore monitor;
    final int permits;

    public FunWithSemaphores() {
        logger = Logger.getLogger(FunWithSemaphores.class);
        this.permits = 10;
        this.monitor = new Semaphore(permits, true);
    }

    public void doIt() {
        try {
            // grab all the permits
            monitor.acquire(permits);
            Set<Thread> threads = new HashSet<Thread>(permits);
            for (int i = 0; i < permits; i++) {
                final String name = "Managed"+ i;
                Thread t = new Thread(
                        new Managed(i, monitor),
                        name
                );
                t.start();
                threads.add(t);
            }
            logger.info("Threads are ready to be unleashed, but pause for a 10 count before unleashing the hounds");
            Thread.sleep(TimeUnit.MILLISECONDS.convert(10l, TimeUnit.SECONDS));
            monitor.release(10);
            logger.info("Threads have been unleashed");
            for (Thread t : threads) {
                t.join();
            }
            logger.info("doIt is done");
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
    }

    public static void main(String...args) {
        FunWithSemaphores fun = new FunWithSemaphores();
        fun.doIt();
    }

}
