package org.jhapy.commons.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author Alexandre Clavaud.
 * @version 1.0
 * @since 10/05/2020
 */
@Service
public class BeanUtil implements ApplicationContextAware {
  private static ApplicationContext context;
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }
  public static <T> T getBean(Class<T> beanClass) {
    return context.getBean(beanClass);
  }
}
