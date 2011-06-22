package com.jellicles.laboratory.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

public class Reflect {

    private Class<?> clazz;
    private String name;
    @SuppressWarnings("unchecked")
    Collection<Constructor> constructors;
    Collection<Method> methods;
    @SuppressWarnings("unchecked")
    Collection<Class> interfaces;

    @SuppressWarnings("unchecked")
    public Reflect(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must not be null.");
        }
        this.clazz = clazz;
        this.name = clazz.getName();
        this.constructors = new HashSet<Constructor>();
        this.methods = new HashSet<Method>();
        this.interfaces = new HashSet<Class>();
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    public Collection<Constructor> getConstructors() {
        return constructors;
    }

    public void findConstructors() {
        Constructor<?>[] ctors = clazz.getConstructors();
        for (Constructor<?> ctor : ctors) {
            this.constructors.add(ctor);
        }
    }

    public Collection<Method> getMethods() {
        return methods;
    }

    public void findMethods() {
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            this.methods.add(m);
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<Class> getInterfaces() {
        return interfaces;
    }

    public void findInterfaceClasses() {
        if (clazz.isInterface()) {
            Class<?>[] cs = clazz.getInterfaces();
            for (Class<?> c : cs) {
                this.interfaces.add(c);
            }
        }
    }

}
