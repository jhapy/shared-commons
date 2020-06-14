/*
 * Copyright 2020-2020 the original author or authors from the JHapy project.
 *
 * This file is part of the JHapy project, see https://www.jhapy.org/ for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jhapy.commons.utils;

import java.util.Map;
import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.OrikaSystemProperties;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-06
 */
@Component
@Primary
public class OrikaBeanMapper extends ConfigurableMapper implements ApplicationContextAware,
    HasLogger {

  private MapperFactory factory;
  private ApplicationContext applicationContext;

  public OrikaBeanMapper() {
    super(false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configure(MapperFactory factory) {
    this.factory = factory;
    addAllSpringBeans(applicationContext);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configureFactoryBuilder(final DefaultMapperFactory.Builder factoryBuilder) {
    // Nothing to configure for now
  }

  @SuppressWarnings("rawtypes")
  public void addMapper(Mapper<?, ?> mapper) {
    String loggerString = getLoggerPrefix("addMapper");
    logger().debug(
        loggerString + "Add : " + mapper.getAType().getName() + " -> " + mapper.getBType()
            .getName());

    factory.classMap(mapper.getAType(), mapper.getBType())
        .byDefault()
        .customize((Mapper) mapper)
        .register();
  }

  public void addMapper(Class aType, Class bType) {
    String loggerString = getLoggerPrefix("addMapper");
    logger().debug(loggerString + "Add : " + aType.getName() + " <-> " + bType.getName());

    ClassMapBuilder classMapBuilder = factory.classMap(aType, bType);

    Class aParent = getParentClass(aType);
    Class bParent = getParentClass(bType);

    if (aParent != null && bParent != null) {
      classMapBuilder = classMapBuilder.use(aParent, bParent);
    }
    classMapBuilder = classMapBuilder.byDefault();

    classMapBuilder.register();
  }

  private Class getParentClass(Class aClass) {
    if (aClass.getSuperclass() == null) {
      return null;
    }

    if (aClass.getSuperclass().equals(Object.class)) {
      return aClass;
    } else {
      return getParentClass(aClass.getSuperclass());
    }
  }

  public ClassMapBuilder getClassMapBuilder(Class aType, Class bType) {
    return factory.classMap(aType, bType);
  }

  /**
   * Registers a {@link Converter} into the {@link ConverterFactory}.
   */
  public void addConverter(Converter<?, ?> converter) {
    String loggerString = getLoggerPrefix("addConverter");
    logger().debug(
        loggerString + "Add : " + converter.getAType().getName() + " <-> " + converter.getBType()
            .getName());
    factory.getConverterFactory().registerConverter(converter);
  }

  /**
   * Scans the appliaction context and registers all Mappers and Converters found in it.
   */
  @SuppressWarnings("rawtypes")
  private void addAllSpringBeans(final ApplicationContext applicationContext) {
    Map<String, Mapper> mappers = applicationContext.getBeansOfType(Mapper.class);
    for (Mapper mapper : mappers.values()) {
      addMapper(mapper);
    }
    Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class);
    for (Converter converter : converters.values()) {
      addConverter(converter);
    }
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
    System.setProperty(OrikaSystemProperties.WRITE_SOURCE_FILES, "true");
    System.setProperty(OrikaSystemProperties.WRITE_CLASS_FILES, "true");
    init();
  }
}
