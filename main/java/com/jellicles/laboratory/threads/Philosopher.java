package com.jellicles.laboratory.threads;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Dining philosopher problem: How to model limited shared resources
 * http://en.wikipedia.org/wiki/Five_philosophers This is an update of the
 * original version posted on WikipediA JEB 11-04-2010
 */
public class Philosopher implements Runnable {

    // Shared by each instance
    public static final String MESSAGE_PREAMBLE = "%d %d Philosopher %d %s\n";
    public static final String MESSAGE_THINKING = "is thinking.";
    public static final String MESSAGE_HUNGRY = "is hungry and is trying to pick up his forks";
    public static final String MESSAGE_EATING = "picked up his forks and is eating meal #";
    private static final Random rand = new Random();
    private static int event = 0;

    // philosopher's state of being
    private int id; // Who am I
    private int availableMeals; // How much is there to eat?
    private int mealsConsumed; // How much has been eaten?
    private Semaphore myFork; // Resource lock to manage.
    private Semaphore neighboursFork; // Resource lock to acquire.
    private DiningState currentState;
    private int pauseTime;

    /**
     * Constructor: an ID# and two shared resources
     * 
     * @param i
     * @param fork1
     * @param fork2
     */
    public Philosopher(int id, int meals, Semaphore fork1, Semaphore fork2) {
        this.id = id;
        this.availableMeals = meals;
        mealsConsumed = 0;
        myFork = fork1;
        neighboursFork = fork2;
        currentState = DiningState.THINKING;
        pauseTime = 1000;
    }

    /**
     * Get the philospher's id.
     * 
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Get the number of servings the philosopher may take.
     * 
     * @return the maximum number of servings allowed
     */
    public int getAvailableMeals() {
        return availableMeals;
    }

    /**
     * How many servings has the philosopher consumed? This value should decline
     * over the course of the meal.
     * 
     * @return An integer
     */
    public int getMealsConsumed() {
        return mealsConsumed;
    }

    /**
     * Get the name of the philosopher. Useful if a named Thread is desired.
     * 
     * @return A string identifier.
     */
    public String getName() {
        return "Philosopher " + String.valueOf(id);
    }

    /**
     * Get the current dining state of the philosopher.
     * 
     * @return
     */
    public DiningState getCurrentState() {
        return currentState;
    }

    /*
     * Set the current dining state of the philosopher.
     * 
     * @param currentState
     */
    private void setCurrentState(DiningState currentState) {
        this.currentState = currentState;
    }

    /**
     * "Lazy" message queue. Original program used a Vector<String> to queue the
     * events and displayed them at the end. I like having feedback while the
     * program is running, but the messages are sometimes displayed out of order
     * - not a big deal.
     * 
     * @param str
     */
    private void message(String str) {
        System.out.printf(MESSAGE_PREAMBLE, System.currentTimeMillis(),
                ++event, id, str);
    }

    private void message() {
        String message = null;
        switch (getCurrentState()) {
        case THINKING:
            message = MESSAGE_THINKING;
            break;
        case HUNGRY:
            message = MESSAGE_HUNGRY;
            break;
        case EATING:
            message = MESSAGE_EATING + getMealsConsumed();
        }
        System.out.printf(MESSAGE_PREAMBLE, System.currentTimeMillis(),
                ++event, id, message);
    }

    /**
     * Pause - waits a bit (random fraction of a second)
     */
    private void pause() {
        try {
            Thread.sleep(rand.nextInt(pauseTime));
        } catch (InterruptedException e) {
        }
    }

    /**
     * Tell philosopher to think - he waits a bit
     * 
     */
    private void think() {
        setCurrentState(DiningState.THINKING);
        message();
        pause();
    }

    /**
     * Tell the philosopher to be hungry, and then wait to be served a meal.
     */
    private void hungry() {
        setCurrentState(DiningState.HUNGRY);
        message();
        pause();
    }

    /**
     * Tell philosopher to eat. Tries to acquire resources (forks)
     * 
     * Possible modification: Doesn't change a state (hungry, starving, etc.) if
     * they can't get a fork
     * 
     * Possible modification: could return a boolean indicating success
     */
    private void eat() {
        try {
            // Semaphore - waits on his own fork if necessary
            myFork.acquire();
            // He's picked up his own fork, now try and grab his neighbor's
            // fork (does not wait)
            boolean hasBothForks = neighboursFork.tryAcquire();
            if (!hasBothForks) {
                // Unsuccessful, so he must fast for the time being.
                message(">>>> was not able to get his neighbor's fork");
                return;
            }
            // Success! begins to eat
            setCurrentState(DiningState.EATING);
            ++mealsConsumed;
            message();
            pause();
            // Now put down the forks
            neighboursFork.release();
        } catch (InterruptedException e) {
            // In case the thread is interrupted
            message("was interrupted while waiting for his fork");
        } finally { // after meal, always put the own fork back down
            myFork.release();
            setCurrentState(DiningState.THINKING);
            message("puts down his forks after meal #" + getMealsConsumed());
        }
    }

    /**
     * philosophize until all meals are consumed
     */
    @Override
    public void run() {
        while (getMealsConsumed() < getAvailableMeals()) {
            think();
            hungry();
            eat();
        }
    }

}