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

package org.jhapy.commons.config.apidoc;

import static org.jhapy.commons.config.JHapyConstants.SPRING_PROFILE_SWAGGER;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Collections;
import org.jhapy.commons.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

/**
 * Springfox Swagger configuration.
 * <p>
 * Warning! When having a lot of REST endpoints, Springfox can become a performance issue. In that
 * case, you can use the "no-swagger" Spring profile, so that this bean is ignored.
 */
@Configuration
@ConditionalOnWebApplication
@Profile(SPRING_PROFILE_SWAGGER)
@AutoConfigureAfter(AppProperties.class)
public class SwaggerAutoConfiguration {

  static final String STARTING_MESSAGE = "Starting Swagger";
  static final String STARTED_MESSAGE = "Started Swagger in {} ms";
  static final String MANAGEMENT_TITLE_SUFFIX = "Management API";
  static final String MANAGEMENT_GROUP_NAME = "management";
  static final String MANAGEMENT_DESCRIPTION = "Management endpoints documentation";

  private final Logger log = LoggerFactory.getLogger(SwaggerAutoConfiguration.class);

  private final AppProperties.Swagger properties;

  /**
   * <p>Constructor for SwaggerAutoConfiguration.</p>
   *
   * @param appProperties a {@link org.jhapy.commons.config.AppProperties} object.
   */
  public SwaggerAutoConfiguration(AppProperties appProperties) {
    this.properties = appProperties.getSwagger();
  }

  @Bean
  public OpenAPI jHapyOpenAPI(@Value("${spring.application.name:application}") String appName) {
    OAuthFlows authFlows = new OAuthFlows();
    OAuthFlow passwordFlow = new OAuthFlow();
    passwordFlow.setAuthorizationUrl(properties.getOAuthAuthorizationUri());
    passwordFlow.setTokenUrl(properties.getOAuthTokenUri());
    passwordFlow.setScopes(new Scopes().addString("openId", "openid"));
    authFlows.clientCredentials(passwordFlow);

    Server srv = new Server();
    srv.setUrl(properties.getHost());
    return new OpenAPI()
        .components(new Components()
            .addSecuritySchemes("openId", new SecurityScheme().type(Type.OAUTH2).flows(authFlows)))
        .servers(Collections.singletonList(srv))
        .info(new Info().title(StringUtils.capitalize(appName) + " " + MANAGEMENT_TITLE_SUFFIX)
            .description(MANAGEMENT_DESCRIPTION)
            .version(properties.getVersion())
            .termsOfService(properties.getTermsOfServiceUrl())
            .contact(
                new Contact().name(properties.getContactName()).email(properties.getContactEmail())
                    .url(properties.getContactUrl()))
            .license(new License().name(properties.getLicense()).url(properties.getLicenseUrl())));
  }

  @Bean
  @ConditionalOnClass(name = "org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties")
  @ConditionalOnProperty("management.endpoints.web.base-path")
  @ConditionalOnExpression("'${management.endpoints.web.base-path}'.length() > 0")
  @ConditionalOnMissingBean(name = "openApiManagementDocket")
  public GroupedOpenApi openApiManagementDocket(
      @Value("${management.endpoints.web.base-path}") String managementContextPath) {

    return GroupedOpenApi.builder()
        .group(MANAGEMENT_GROUP_NAME)
        .pathsToMatch(managementContextPath + "/**")
        .build();
  }

  @Bean
  @ConditionalOnMissingBean(name = "openApiDefaultDocket")
  public GroupedOpenApi openApiDefaultDocket() {

    return GroupedOpenApi.builder()
        .group("default")
        .pathsToMatch(properties.getDefaultIncludePattern())
        .build();
  }

}
