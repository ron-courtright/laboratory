package com.jellicles.laboratory.util;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/***
 * Proof in concept of integrating a reasonably current plugin of JMock and a
 * compatible JUnit libraries into a project.
 * 
 * @author ron
 * 
 */
public class MockTest {
    private Synchroniser synchroniser = new Synchroniser();
    private Mockery mockery = new JUnit4Mockery() {
        {
            setThreadingPolicy(synchroniser);
        }
    };

    @Test
    public void testSomething() throws Exception {
        final Callable<?> callable = mockery.mock(Callable.class);
        mockery.checking(new Expectations() {
            {
                oneOf(callable).call();
            }
        });
        Executors.newSingleThreadExecutor().submit(callable).get();
    }
}
