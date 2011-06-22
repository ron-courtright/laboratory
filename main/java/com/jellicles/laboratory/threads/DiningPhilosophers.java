/**
 * 
 */
package com.jellicles.laboratory.threads;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * Manage a dinner party of thoughtful, yet hungry, philosophers.
 * 
 * @author ron
 * 
 */
public class DiningPhilosophers implements Runnable {

    private int guestCount;
    private int servings;
    private Map<Integer, Semaphore> forks;
    private Collection<Philosopher> dinnerGuests;

    public DiningPhilosophers(int diners, int maxServings) {
        this.guestCount = diners;
        this.servings = maxServings;
        dinnerGuests = new ArrayList<Philosopher>(diners);
        forks = new HashMap<Integer, Semaphore>();
    }

    // Create the forks, 1 fork per philosopher
    private void initForks() {
        for (int id = 0; id < guestCount; id++) {
            Semaphore fork = new Semaphore(1, true);
            forks.put(new Integer(id), fork);
        }
    }

    // Create the philosophers, pass in their forks
    private void initDinnerGuests() {
        for (int id = 0; id < guestCount; id++) {
            // determine my right-hand neighbor's ID
            int neighboursId = id + 1;
            if (neighboursId == guestCount) {
                // reset id when terminal diner is found
                neighboursId = 0;
            }
            // Initialize each dinner guest
            Philosopher dinnerGuest = new Philosopher(id, servings, forks
                    .get(id), forks.get(neighboursId));
            dinnerGuests.add(dinnerGuest);
        }
    }

    // Have a dinner party where each guest is allowed only one dining utensil.
    // It is a two-step process to assure to effect a closer starting time
    // for all the threads/diners.
    private void soiree() {
        Collection<Thread> threads = new HashSet<Thread>();
        for (Runnable runner : dinnerGuests) {
            Thread t = new Thread(runner, ((Philosopher) runner).getName());
            threads.add(t);
            t.start();
        }
        // Wait for each guest to finish his or her meal.
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                // no-op... don't care
            }
        }
    }

    /**
     * Initialize the forks and dinner guests, then start the dinner party.
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        initForks();
        initDinnerGuests();
        soiree();
    }

    /**
     * @param args
     *            Any arguments will be ignored.
     * 
     */
    public static void main(String[] args) {
        final int size = 5;
        final int servings = 10;
        Runnable dinnerParty = new DiningPhilosophers(size, servings);
        Thread thread = new Thread(dinnerParty);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // All done
        System.out.println("Done");
    }

}
