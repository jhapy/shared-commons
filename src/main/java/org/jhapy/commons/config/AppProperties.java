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

package org.jhapy.commons.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@Getter
@ConfigurationProperties(prefix = "jhapy")
public class AppProperties {

  private final Hazelcast hazelcast = new Hazelcast();
  private final RemoteServices remoteServices = new RemoteServices();
  private final Security security = new Security();
  private final CorsConfiguration cors = new CorsConfiguration();
  private final Metrics metrics = new Metrics();
  private final Swagger swagger = new Swagger();
  private final KeycloakAdmin keycloakAdmin = new KeycloakAdmin();

  @Data
  public static class Hazelcast {

    private final ManagementCenter managementCenter = new ManagementCenter();
    private String interfaces;
    private int timeToLiveSeconds = 3600;
    private int backupCount = 1;

    @Data
    public static class ManagementCenter {

      private boolean enabled = false;
      private int updateInterval = 3;
      private String url = "";
    }
  }

  @Data
  public static class RemoteServices {

    private RemoteServer backendServer = new RemoteServer();
    private RemoteServer authorizationServer = new RemoteServer();
    private RemoteServer i18nServer = new RemoteServer();
    private RemoteServer resourceServer = new RemoteServer();
    private RemoteServer auditServer = new RemoteServer();
    private RemoteServer registryServer = new RemoteServer();

    @Data
    public static final class RemoteServer {

      private String url;
      private String name;
    }
  }

  @Data
  public static class Security {

    private final ClientAuthorization clientAuthorization = new ClientAuthorization();
    private final RememberMe rememberMe = new RememberMe();
    private final OAuth2 oauth2 = new OAuth2();

    @Data
    public static class ClientAuthorization {

      private String accessTokenUri = JHapyDefaults.Security.ClientAuthorization.accessTokenUri;
      private String tokenServiceId = JHapyDefaults.Security.ClientAuthorization.tokenServiceId;
      private String clientId = JHapyDefaults.Security.ClientAuthorization.clientId;
      private String clientSecret = JHapyDefaults.Security.ClientAuthorization.clientSecret;
    }

    @Data
    public static class RememberMe {

      @NotNull
      private String key = JHapyDefaults.Security.RememberMe.key;
    }

    @Data
    public static class OAuth2 {

      private List<String> audience = new ArrayList<>();

      public List<String> getAudience() {
        return Collections.unmodifiableList(audience);
      }

      public void setAudience(@NotNull List<String> audience) {
        this.audience.addAll(audience);
      }
    }
  }

  @Data
  public static class Metrics {

    private final Logs logs = new Logs();

    @Data
    public static class Logs {

      private boolean enabled = JHapyDefaults.Metrics.Logs.enabled;
      private long reportFrequency = JHapyDefaults.Metrics.Logs.reportFrequency;
    }
  }

  @Data
  public static class Swagger {

    private String title = JHapyDefaults.Swagger.title;

    private String description = JHapyDefaults.Swagger.description;

    private String version = JHapyDefaults.Swagger.version;

    private String termsOfServiceUrl = JHapyDefaults.Swagger.termsOfServiceUrl;

    private String contactName = JHapyDefaults.Swagger.contactName;

    private String contactUrl = JHapyDefaults.Swagger.contactUrl;

    private String contactEmail = JHapyDefaults.Swagger.contactEmail;

    private String license = JHapyDefaults.Swagger.license;

    private String licenseUrl = JHapyDefaults.Swagger.licenseUrl;

    private String defaultIncludePattern = JHapyDefaults.Swagger.defaultIncludePattern;

    private String host = JHapyDefaults.Swagger.host;

    private String[] protocols = JHapyDefaults.Swagger.protocols;

    private boolean useDefaultResponseMessages = JHapyDefaults.Swagger.useDefaultResponseMessages;

  }

  @Data
  public static class KeycloakAdmin {

    private String serverUrl;
    private String serverAuthUrl;
    private String masterRealm;
    private String applicationRealm;
    private String username;
    private String password;
    private String clientId;
  }
}