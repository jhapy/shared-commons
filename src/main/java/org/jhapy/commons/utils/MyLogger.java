package org.jhapy.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandre Clavaud.
 * @version 1.0
 * @since 01/05/2021
 */
public class MyLogger {

  private final Logger logger;

  public MyLogger(Class aClass) {
    logger = LoggerFactory.getLogger(aClass);
  }

}
