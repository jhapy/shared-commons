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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>JHapyDefaults interface.</p>
 */
public interface JHapyDefaults {

  interface Async {

    int corePoolSize = 2;
    int maxPoolSize = 50;
    int queueCapacity = 10000;
  }

  interface Http {

    interface Cache {

      int timeToLiveInDays = 1461; // 4 years (including leap day)
    }
  }

  interface Cache {

    interface Hazelcast {

      int timeToLiveSeconds = 3600; // 1 hour
      int backupCount = 1;

      interface ManagementCenter {

        boolean enabled = false;
        int updateInterval = 3;
        String url = "";
      }
    }
  }

  interface Mail {

    boolean enabled = false;
    String from = "";
    String baseUrl = "";
  }

  interface Security {

    interface ClientAuthorization {

      String accessTokenUri = null;
      String tokenServiceId = null;
      String clientId = null;
      String clientSecret = null;
    }

    interface Authentication {

      interface Jwt {

        String secret = null;
        String base64Secret = null;
        long tokenValidityInSeconds = 1800; // 30 minutes
        long tokenValidityInSecondsForRememberMe = 2592000; // 30 days
      }
    }

    interface RememberMe {

      String key = null;
    }
  }

  interface Swagger {

    String title = "Application API";
    String description = "API documentation";
    String version = "0.0.1";
    String termsOfServiceUrl = null;
    String contactName = null;
    String contactUrl = null;
    String contactEmail = null;
    String license = null;
    String licenseUrl = null;
    String defaultIncludePattern = "/api/**";
    String host = null;
    String[] protocols = {};
    boolean useDefaultResponseMessages = true;
  }

  interface Metrics {

    interface Jmx {

      boolean enabled = false;
    }

    interface Logs {

      boolean enabled = false;
      long reportFrequency = 60;

    }

    interface Prometheus {

      boolean enabled = false;
      String endpoint = "/prometheusMetrics";
    }
  }

  interface Logging {

    boolean useJsonFormat = false;

    interface Logstash {

      boolean enabled = false;
      String host = "localhost";
      int port = 5000;
      int queueSize = 512;
    }
  }

  interface Social {

    String redirectAfterSignIn = "/#/home";
  }

  interface Gateway {

    Map<String, List<String>> authorizedMicroservicesEndpoints = new LinkedHashMap<>();

    interface RateLimiting {

      boolean enabled = false;
      long limit = 100_000L;
      int durationInSeconds = 3_600;

    }
  }

  interface Ribbon {

    String[] displayOnActiveProfiles = null;
  }

  interface Registry {

    String password = null;
  }

  interface ClientApp {

    String name = "jHapyApp";
  }

  interface AuditEvents {

    int retentionPeriod = 30;
  }
}
