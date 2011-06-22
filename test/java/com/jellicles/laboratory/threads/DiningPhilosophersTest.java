package com.jellicles.laboratory.threads;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class DiningPhilosophersTest {

    private Synchroniser synchroniser = new Synchroniser();
    private Mockery mockery = new JUnit4Mockery() {
        {
            setThreadingPolicy(synchroniser);
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private Collection<Philosopher> philosophers;
    private int dinnerGuests = 5;
    private Map<Integer, Semaphore> forks;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    public DiningPhilosophersTest() {
    }

    private PrintStream originalOut = System.out;
    private PrintStream originalErr = System.err;

    @BeforeClass
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterClass
    public void cleanUpStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @BeforeTest
    public void setUp() throws Exception {
        forks = new HashMap<Integer, Semaphore>();
        for (int key = 0; key < dinnerGuests; key++) {
            Semaphore fork = mockery.mock(Semaphore.class, "Fork" + key);
            forks.put(key, fork);
        }
        philosophers = new ArrayList<Philosopher>();
        for (int i = 0; i < dinnerGuests; i++) {
            Integer id = i;
            int nextId = id + 1;
            if (nextId == dinnerGuests) {
                nextId = 0;
            }
            Philosopher p = mockery.mock(Philosopher.class, id.toString());
            Field pId = Philosopher.class.getDeclaredField("id");
            pId.setAccessible(true);
            pId.setInt(p, id);
            Field meals = Philosopher.class.getDeclaredField("availableMeals");
            meals.setAccessible(true);
            int availableMeals = 10;
            meals.setInt(p, availableMeals);
            Field f1 = Philosopher.class.getDeclaredField("myFork");
            f1.setAccessible(true);
            f1.set(p, forks.get(id));
            Field f2 = Philosopher.class.getDeclaredField("neighboursFork");
            f2.setAccessible(true);
            f2.set(p, forks.get(nextId));
            philosophers.add(p);
        }
    }

    @AfterTest
    public void tearDown() throws Exception {
    }

    private class PhilosopherMonitor implements Runnable {

        private Collection<String> expectedThreadNames;

        public PhilosopherMonitor() {
            expectedThreadNames = nameBuilder();
        }

        private Collection<String> nameBuilder() {
            Collection<String> results = new HashSet<String>();
            StringBuilder threadName = new StringBuilder();
            for (int i = 0; i < dinnerGuests; i++) {
                threadName.append("Philosopher ");
                threadName.append(i);
                results.add(threadName.toString());
                threadName.delete(0, threadName.length());
            }
            return results;
        }

        public void run() {
            // pause for 1 second
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ignored) {}
            try {
                // Look for named threads
                ThreadGroup group = Thread.currentThread().getThreadGroup();
                // Wind our way up the stack.
                ThreadGroup parent = group.getParent();
                // Hopefully our parent has the information we need.
                int activeCount = parent.activeCount();
                Thread[] affiliates = new Thread[activeCount];
                group.enumerate(affiliates);
                Set<String> threadNames = new HashSet<String>();
                for (Thread thread : affiliates) {
                    // Okay, so here is the thing: there are transitory
                    // threads that contribute to the activeCount but
                    // vanish by the time the thread affiliates collection
                    // is populated. So check for null before accessing
                    // the object from the thread collection.
                    if (thread != null) {
                        String threadName = thread.getName();
                        threadNames.add(threadName);
                    }
                }
                for (String threadName : expectedThreadNames) {
                    System.out.println(threadName);
                    boolean result = threadNames.contains(threadName);
                    assertTrue(result);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

    }

    @Test
    public void testDiningPhilosophers() throws Exception {
        int guestCount = 5;
        int servings = 10;
        DiningPhilosophers dp = new DiningPhilosophers(guestCount, servings);
        assertNotNull(dp);
        Field gCount = DiningPhilosophers.class.getDeclaredField("guestCount");
        gCount.setAccessible(true);
        assertEquals(guestCount, gCount.getInt(dp));
        Field sCount = DiningPhilosophers.class.getDeclaredField("servings");
        sCount.setAccessible(true);
        assertEquals(servings, sCount.getInt(dp));
        Field dinnerGuests = DiningPhilosophers.class
                .getDeclaredField("dinnerGuests");
        dinnerGuests.setAccessible(true);
        Object dgs = dinnerGuests.get(dp);
        assertNotNull(dgs);
        assertEquals(0, ((Collection<?>) dgs).size());
        Field forks = DiningPhilosophers.class.getDeclaredField("forks");
        forks.setAccessible(true);
        Object f = forks.get(dp);
        assertNotNull(f);
        assertEquals(0, ((Map<?, ?>) f).size());
    }

    // Parse stream
    private boolean parseMessagesForRegex(StringBuilder regex) {
        Pattern pattern = Pattern.compile(regex.toString());
        String messages = outContent.toString();
        Matcher matcher = pattern.matcher(messages);
        return matcher.find();
    }

    // This tests the existence and propagation of the five Philosopher
    // instances.
    private void checkRuntimeMessages() throws Exception {
        // If test bed has changed, check to assure testability.
        assertTrue(dinnerGuests > 0 && dinnerGuests <= 10, "Check test data, undesirable value.");
        // Build regex.
        StringBuilder regex = new StringBuilder();
        for (int i = 0; i < dinnerGuests; i++) {
            regex.append("Philosopher ");
            regex.append(i);
            regex.append(" ");
            regex.append(Philosopher.MESSAGE_THINKING);
            boolean result = parseMessagesForRegex(regex);
            assertTrue(result);
            regex.delete(0, regex.length());
        }
        // If we got this far, then we have what we need.
        // Now to make sure there is no more than we need.
        regex.append("Philosopher");
        regex.append(" ");
        regex.append(dinnerGuests);
        regex.append(" ");
        regex.append(Philosopher.MESSAGE_THINKING);
        boolean result = parseMessagesForRegex(regex);
        assertFalse(result);
    }

    @Test
    public void testRun() throws Exception {
        final int size = 5;
        final int servings = 10;
        DiningPhilosophers dinnerParty = new DiningPhilosophers(size, servings);
        // replace forks and guests with mocks
        Field fs = DiningPhilosophers.class.getDeclaredField("forks");
        fs.setAccessible(true);
        fs.set(dinnerParty, forks);
        Field ps = DiningPhilosophers.class.getDeclaredField("dinnerGuests");
        ps.setAccessible(true);
        ps.set(dinnerParty, philosophers);
        // set expectations during runtime
        mockery.checking(new Expectations() {
            {
                // Do not care about Semaphores and Philosophers,
                // as they are the meat for PhilosopherTest.
                for (Semaphore fork : forks.values()) {
                    ignoring(fork);
                }
                for (Philosopher philosopher : philosophers) {
                    ignoring(philosopher);
                }
            }
        });
        // Start the monitor to verify that the expected threads are running.
        Runnable philosopherMonitor = new PhilosopherMonitor();
        Thread monitor = new Thread(philosopherMonitor,
                PhilosopherMonitor.class.getSimpleName());
        monitor.start();
        // Now run the test.
        dinnerParty.run();
        // Signal the monitor to begin its analysis.
        // Wait for monitor to finish.
        monitor.join();
        // Detect if the Philosopher objects get launched and if
        // their (initial) output is displayed, which proves that
        // the test subject works.
        checkRuntimeMessages();
    }

}
