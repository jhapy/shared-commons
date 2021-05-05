package org.jhapy.commons.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;

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

  public static void copyNonNullProperties(Object destination,
      Map<String, Object> source) {
    var loggerPrefix = HasLoggerStatic.getLoggerPrefix("copyNonNullProperties");
    try {
      source.keySet().forEach(s -> {
        try {
          if (PropertyUtils.describe(destination).containsKey(s)) {
            PropertyUtils.setProperty(destination, s, source.get(s));
          }
        } catch (Exception e22) {
          HasLoggerStatic.logger(BeanUtils.class)
              .error(loggerPrefix + "Error setting properties : {}", e22.getMessage());
        }
      });
    } catch (Exception e1) {
      HasLoggerStatic.logger(BeanUtils.class)
          .error(loggerPrefix + "Error setting properties : {}", e1.getMessage());
    }

  }
}
