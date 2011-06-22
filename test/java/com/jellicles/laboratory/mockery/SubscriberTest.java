package com.jellicles.laboratory.mockery;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

//@RunWith(JMock.class)
public class SubscriberTest {
    private Mockery mockery = new Mockery();
    private final Subscriber subscriber = mockery.mock(Subscriber.class);

    @Test
    public void testSubscriber() {
        assertTrue(Subscriber.class.isInterface());
    }

    @Test
    public void testReceiveString() throws Exception {
        final String message = "testReceiveString";
        mockery.checking(new Expectations() {
            {
                oneOf(subscriber).recieve(message);
            }
        });
        // test something
        subscriber.recieve(message);
    }

    @Test
    public void testReceiveCollection() throws Exception {
        final String message = "testReceiveCollection";
        final int expected = 1;
        final Integer integer = new Integer(expected);
        final Collection<String> strings = new ArrayList<String>();
        strings.add(message);
        final Collection<Integer> integers = new ArrayList<Integer>();
        integers.add(integer);
        mockery.checking(new Expectations() {
            {
                oneOf(subscriber).recieve(strings);
                oneOf(subscriber).recieve(integers);
            }
        });
        // test something
        subscriber.recieve(strings);
        subscriber.recieve(integers);
        // verify nothing changed behind our backs,
        // gratuitous since this is an interface...
        assertSame(expected, strings.size(), "String collection count changed");
        for (String string : strings) {
            assertSame(message, string, "String collection mismatch");
        }
        assertSame(expected, integers.size(), "Integer collection count changed");
        for (Integer number : integers) {
            assertSame(integer, number, "Integer collection mismatch");
        }
    }

}
