package com.apress.springrecipes.report;

import java.util.Map;

/**
 * Interface injection of control.
 * Date: 1/18/11
 * Time: 4:36 PM
 */
public interface Injectable {
    public void inject(Map<String, Object> components);
}
