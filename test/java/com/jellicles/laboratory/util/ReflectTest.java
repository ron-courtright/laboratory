package com.jellicles.laboratory.util;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class ReflectTest {

    Class<?> self = Reflect.class;
    Class<?> clazz = String.class;
    Class<?> interfaceClass = List.class;

    @BeforeTest
    public void setUp() throws Exception {
    }

    @AfterTest
    public void tearDown() throws Exception {
    }

    @Test
    public void testReflect() {
        try {
            new Reflect(null);
        } catch (Throwable t) {
            assertSame(IllegalArgumentException.class, t.getClass());
        }
        Reflect reflect = new Reflect(self);
        assertSame(self, reflect.getClazz());
        assertEquals(self.getName(), reflect.getName());
        assertNotNull(reflect.getConstructors());
        assertNotNull(reflect.getMethods());
        assertNotNull(reflect.getInterfaces());
    }

    @Test
    public void testGetConstructors() {
        Reflect string = new Reflect(clazz);
        assertNotNull(string.getConstructors());
        assertTrue(string.getConstructors().size() == 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFindConstructors() {
        Reflect reflect = new Reflect(clazz);
        reflect.findConstructors();
        Collection<Constructor> ctors = reflect.getConstructors();
        Constructor<?>[] array = clazz.getConstructors();
        assertEquals(ctors.size(), array.length);
        for (Constructor<?> ctor : array) {
            assertTrue(ctors.contains(ctor));
        }
    }

    @Test
    public void testGetMethods() {
        Reflect reflect = new Reflect(clazz);
        assertNotNull(reflect.getMethods());
        assertTrue(reflect.getMethods().size() == 0);
    }

    @Test
    public void testFindMethods() {
        Reflect reflect = new Reflect(clazz);
        reflect.findMethods();
        Collection<Method> methods = reflect.getMethods();
        Method[] array = clazz.getMethods();
        assertEquals(methods.size(), array.length);
        for (Method method : array) {
            assertTrue(reflect.getMethods().contains(method));
        }
    }

    @Test
    public void testGetInterfaces() {
        Reflect reflect = new Reflect(interfaceClass);
        assertNotNull(reflect.getInterfaces());
        assertTrue(reflect.getInterfaces().size() == 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFindInterfaceClasses() {
        Reflect reflect = new Reflect(interfaceClass);
        reflect.findInterfaceClasses();
        Collection<Class> interfaces = reflect.getInterfaces();
        Class<?>[] array = interfaceClass.getInterfaces();
        assertEquals(array.length, interfaces.size());
        for (Class c : array) {
            assertTrue(reflect.getInterfaces().contains(c));
        }
    }

    @Test
    public void testGetClazz() {
        Reflect reflect = new Reflect(self);
        assertSame(self, reflect.getClazz());
    }

    @Test
    public void testGetName() {
        Reflect reflect = new Reflect(self);
        assertEquals(self.getName(), reflect.getName());
    }
}
