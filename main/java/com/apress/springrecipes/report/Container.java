package com.apress.springrecipes.report;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Date: 1/18/11
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class Container {

    private static Logger logger;
    static {
        logger = Logger.getLogger(Container.class);
    }

    // A map for storing the components with their IDs as the keys.
    private Map<String,Object> components;

    private void processEntry(String key, String value) throws Throwable {
        String[] parts = key.split("\\.");
        if (parts.length == 1) {
            // New component definition
            Object component = Class.forName(value).newInstance();
            components.put(parts[0], component);
        } else {
            // Dependency injection
            Object component = components.get(parts[0]);
            Object reference = components.get(value);
            PropertyUtils.setProperty(component, parts[1], reference);
        }
    }

    public Container() {
        components = new HashMap<String,Object>();
        //
        Properties properties = new Properties();
        try {
            InputStream is = Container.class.getResourceAsStream("components.properties");
            properties.load(is);
            for (Map.Entry entry : properties.entrySet()) {
                String key = (String)entry.getKey();
                String value = (String)entry.getValue();
                processEntry(key, value);
            }
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
            throw new RuntimeException(t);
        }
    }

    public Object getComponent(String id) {
        return components.get(id);
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        // so far so good
        Container container = (Container) o;
        if (!components.equals(container.components))
            return false;
        return true;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return components.hashCode();
    }
}
