package com.jellicles.laboratory.threads;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.concurrent.Semaphore;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class PhilosopherTest {

    private Synchroniser synchroniser = new Synchroniser();
    private Mockery mockery = new Mockery() {
        {
            setThreadingPolicy(synchroniser);
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private Semaphore myFork, neighboursFork;
    private int expectedId, expectedServings;
    private Philosopher philosopher;
    private final ByteArrayOutputStream outContent =
            new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent =
            new ByteArrayOutputStream();

    private int counter = 0;

    @BeforeMethod
    public void setUp() throws Exception {
        int index = ++counter;
        myFork = mockery.mock(Semaphore.class, "myFork"+index);
        neighboursFork = mockery.mock(Semaphore.class, "neighboursFork"+index);
        expectedId = 0;
        expectedServings = 10;
        philosopher = new Philosopher(expectedId, expectedServings, myFork,
                neighboursFork);
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @BeforeClass
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterClass
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testPhilosopher() {
        try {
            new Philosopher(expectedId, expectedServings, null, null);
        } catch (Exception ex) {
            fail("Unexpected exception during object creation.");
        }
        try {
            Philosopher p = new Philosopher(expectedId, expectedServings,
                    myFork, neighboursFork);
            assertNotNull(p);
        } catch (Exception ex) {
            fail("Unexpected exception during object creation.");
        }
    }

    @Test
    public void testGetId() {
        final int expected = 6;
        try {
            Field field = Philosopher.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(philosopher, expected);
        } catch (Throwable t) {
            fail(t.getMessage());
        }
        int actual = philosopher.getId();
        assertEquals(expected, actual, "id value mismatch");
    }

    @Test
    public void testGetAvailableMeals() {
        final int expected = 6;
        try {
            Field field = Philosopher.class.getDeclaredField("availableMeals");
            field.setAccessible(true);
            field.set(philosopher, expected);
        } catch (Throwable t) {
            fail(t.getMessage());
        }
        int actual = philosopher.getAvailableMeals();
        assertEquals(expected, actual, "servings value mismatch");
    }

    @Test
    public void testGetMealsConsumed() {
        final int expected = 6;
        try {
            Field field = Philosopher.class.getDeclaredField("mealsConsumed");
            field.setAccessible(true);
            field.set(philosopher, expected);
        } catch (Throwable t) {
            fail(t.getMessage());
        }
        int actual = philosopher.getMealsConsumed();
        assertEquals(expected, actual, "servings value mismatch");
    }

    @Test
    public void testGetName() throws Throwable {
        final int    id       = 6;
        final String expected = "Philosopher " + id;
        try {
            Field field = Philosopher.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(philosopher, id);
        } catch (Throwable t) {
            fail(t.getMessage());
        }
        String actual = philosopher.getName();
        assertEquals(expected, actual, "name mismatch");
    }

    @Test
    public void testGetCurrentState() {
        final DiningState expected = DiningState.HUNGRY;
        try {
            Field field = Philosopher.class.getDeclaredField("currentState");
            field.setAccessible(true);
            field.set(philosopher, expected);
        } catch (Throwable t) {
            fail(t.getMessage());
        }
        DiningState actualState = philosopher.getCurrentState();
        assertEquals(expected, actualState, "state value mismatch");
    }

    // This tests the existence and propagation of the first instances of the
    // "is thinking," "is hungry," and "is eating" messages.
    // This test goes to show that both the messaging feature and the state
    // transition order work.
    private void checkRuntimeMessages() {
        StringBuilder thinking = new StringBuilder(Philosopher.MESSAGE_THINKING);
        assertTrue(outContent.toString().contains(thinking));
        int thinkingIndex = outContent.toString().indexOf(thinking.toString());
        StringBuilder hungry = new StringBuilder(Philosopher.MESSAGE_HUNGRY);
        assertTrue(outContent.toString().contains(hungry));
        int hungryIndex = outContent.toString().indexOf(hungry.toString());
        assertTrue(hungryIndex > thinkingIndex);
        StringBuilder eating = new StringBuilder(Philosopher.MESSAGE_EATING);
        assertTrue(outContent.toString().contains(eating));
        int eatingIndex = outContent.toString().indexOf(eating.toString());
        assertTrue(eatingIndex > hungryIndex);
        // verify empty error output
        assertTrue(errContent.toString().isEmpty());
    }

    @Test
    // allow a minute to run
    public void testRun() throws InterruptedException {
        final States diningState = mockery.states(DiningState.class
                .getSimpleName());
        diningState.startsAs(DiningState.THINKING.toString());
        mockery.checking(new Expectations() {
            {
                // verify setup
                assertEquals(expectedId, philosopher.getId(), "Philosopher id mismatch");
                assertEquals(expectedServings,
                        philosopher.getAvailableMeals(),
                        "Initial meal consumption mismatch");
                assertEquals(DiningState.THINKING,
                        philosopher.getCurrentState(),
                        "Initial state mismatch");
                // carry on with state transition
                exactly(expectedServings).of(myFork).acquire();
                then(diningState.is(DiningState.HUNGRY.toString()));
                /*
                 * assertEquals("State mismatch", DiningState.HUNGRY,
                 * philosopher.getCurrentState());
                 */
                exactly(expectedServings).of(neighboursFork).tryAcquire();
                will(returnValue(true));
                then(diningState.is(DiningState.EATING.toString()));
                /*
                 * assertEquals("State mismatch", DiningState.EATING,
                 * philosopher.getCurrentState());
                 */
                exactly(expectedServings).of(neighboursFork).release();
                exactly(expectedServings).of(myFork).release();
                then(diningState.is(DiningState.THINKING.toString()));
                assertEquals(DiningState.THINKING,
                        philosopher.getCurrentState(),
                        "State mismatch");
            }
        });
        philosopher.run();
        checkRuntimeMessages();
        synchroniser.waitUntil(diningState.is(DiningState.THINKING.toString()));
        assertEquals(philosopher.getAvailableMeals(),
                philosopher.getMealsConsumed(),
                "Serving consumption mismatch.");
    }

}
