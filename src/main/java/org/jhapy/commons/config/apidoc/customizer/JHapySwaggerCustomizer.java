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

package org.jhapy.commons.config.apidoc.customizer;

import static springfox.documentation.builders.PathSelectors.regex;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import org.jhapy.commons.config.AppProperties;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * A swagger customizer to setup {@link springfox.documentation.spring.web.plugins.Docket} with
 * JHapy settings.
 */
public class JHapySwaggerCustomizer implements SwaggerCustomizer, Ordered {

  /**
   * The default order for the customizer.
   */
  public static final int DEFAULT_ORDER = 0;

  private int order = DEFAULT_ORDER;

  private final AppProperties.Swagger properties;

  /**
   * <p>Constructor for JHapySwaggerCustomizer.</p>
   *
   * @param properties a {@link org.jhapy.commons.config.AppProperties.Swagger} object.
   */
  public JHapySwaggerCustomizer(AppProperties.Swagger properties) {
    this.properties = properties;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void customize(Docket docket) {
    Contact contact = new Contact(
        properties.getContactName(),
        properties.getContactUrl(),
        properties.getContactEmail()
    );

    ApiInfo apiInfo = new ApiInfo(
        properties.getTitle(),
        properties.getDescription(),
        properties.getVersion(),
        properties.getTermsOfServiceUrl(),
        contact,
        properties.getLicense(),
        properties.getLicenseUrl(),
        new ArrayList<>()
    );

    docket.host(properties.getHost())
        .protocols(new HashSet<>(Arrays.asList(properties.getProtocols())))
        .apiInfo(apiInfo)
        .useDefaultResponseMessages(properties.isUseDefaultResponseMessages())
        .forCodeGeneration(true)
        .directModelSubstitute(ByteBuffer.class, String.class)
        .genericModelSubstitutes(ResponseEntity.class)
        .ignoredParameterTypes(Pageable.class)
        .select()
        .paths(regex(properties.getDefaultIncludePattern()))
        .build();
  }

  /**
   * <p>Setter for the field <code>order</code>.</p>
   *
   * @param order a int.
   */
  public void setOrder(int order) {
    this.order = order;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOrder() {
    return order;
  }
}
