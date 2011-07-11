package com.apress.springrecipes.shop;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;

import java.io.File;

/**
 * Date: 1/24/11
 * Time: 2:24 PM
 */
public class PathCheckingBeanPostProcessor implements BeanPostProcessor,PriorityOrdered {

    private int order;

    /**
     * Apply this BeanPostProcessor to the given new bean instance <i>before</i> any bean
     * initialization callbacks (like InitializingBean's <code>afterPropertiesSet</code>
     * or a custom init-method). The bean will already be populated with property values.
     * The returned bean instance may be a wrapper around the original.
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one
     * @throws org.springframework.beans.BeansException
     *          in case of errors
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof StorageConfig) {
            String path = ((StorageConfig)bean).getPath();
            File file = new File(path);
            if (! file.exists()) {
                file.mkdir();
            }
        }
        return bean;
    }

    /**
     * Apply this BeanPostProcessor to the given new bean instance <i>after</i> any bean
     * initialization callbacks (like InitializingBean's <code>afterPropertiesSet</code>
     * or a custom init-method). The bean will already be populated with property values.
     * The returned bean instance may be a wrapper around the original.
     * <p>In case of a FactoryBean, this callback will be invoked for both the FactoryBean
     * instance and the objects created by the FactoryBean (as of Spring 2.0). The
     * post-processor can decide whether to apply to either the FactoryBean or created
     * objects or both through corresponding <code>bean instanceof FactoryBean</code> checks.
     * <p>This callback will also be invoked after a short-circuiting triggered by a
     * {@link org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation} method,
     * in contrast to all other BeanPostProcessor callbacks.
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one
     * @throws org.springframework.beans.BeansException
     *          in case of errors
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
     * @see org.springframework.beans.factory.FactoryBean
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * Return the order value of this object, with a
     * higher value meaning greater in terms of sorting.
     * <p>Normally starting with 0 or 1, with {@link #LOWEST_PRECEDENCE}
     * indicating greatest. Same order values will result in arbitrary
     * positions for the affected objects.
     * <p>Higher value can be interpreted as lower priority,
     * consequently the first object has highest priority
     * (somewhat analogous to Servlet "load-on-startup" values).
     * <p>Note that order values below 0 are reserved for framework
     * purposes. Application-specified values should always be 0 or
     * greater, with only framework components (internal or third-party)
     * supposed to use lower values.
     *
     * @return the order value
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
