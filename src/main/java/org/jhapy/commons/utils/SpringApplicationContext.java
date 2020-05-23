package org.jhapy.commons.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-05-10
 */
@Component
public class SpringApplicationContext implements ApplicationContextAware {

  private static ApplicationContext context;

  public static <T> T getBean(Class<T> clazz) {
    return context.getBean(clazz);
  }

  public static Object getBean(String name) {
    return context.getBean(name);
  }

  public void setApplicationContext(final ApplicationContext context)
      throws BeansException {
    SpringApplicationContext.context = context;
  }
}
