package br.com.cds.connecta.portal.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

/**
 *
 * @author Julio Lemes
 * @date Jul 28, 2015
 */
@Component
public class PortalSpringContext extends ApplicationObjectSupport {

    private static ApplicationContext context;
    
    public PortalSpringContext() {
    }

    @Override
    protected boolean isContextRequired() {
        return true;
    }

    @Override
    protected void initApplicationContext(ApplicationContext context) throws BeansException {
        PortalSpringContext.context = context;
    }
    
    public static ApplicationContext getContext() {
        return context;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static <E> E getBean(Class<E> beanClass) {
        return context.getBean(beanClass);
    }

}
