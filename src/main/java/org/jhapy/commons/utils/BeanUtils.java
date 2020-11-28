package org.jhapy.commons.utils;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtilsBean;

/**
 * @author Alexandre Clavaud.
 * @version 1.0
 * @since 27/11/2020
 */
public class BeanUtils {

  public static void nullAwareBeanCopy(Object dest, Object source)
      throws IllegalAccessException, InvocationTargetException {
    new BeanUtilsBean() {
      @Override
      public void copyProperty(Object dest, String name, Object value)
          throws IllegalAccessException, InvocationTargetException {
        if (value != null) {
          super.copyProperty(dest, name, value);
        }
      }
    }.copyProperties(dest, source);
  }
}
