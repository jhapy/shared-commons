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

package org.jhapy.commons.security.oauth2;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;

public class AudienceValidator implements OAuth2TokenValidator<Jwt> {

  private final Logger log = LoggerFactory.getLogger(AudienceValidator.class);
  private final OAuth2Error error = new OAuth2Error("invalid_token",
      "The required audience is missing", null);

  private final List<String> allowedAudience;

  public AudienceValidator(List<String> allowedAudience) {
    Assert.notEmpty(allowedAudience, "Allowed audience should not be null or empty.");
    this.allowedAudience = allowedAudience;
  }

  public OAuth2TokenValidatorResult validate(Jwt jwt) {
    List<String> audience = jwt.getAudience();
    if (audience.stream().anyMatch(allowedAudience::contains)) {
      return OAuth2TokenValidatorResult.success();
    } else {
      log.warn("Invalid audience: {}", audience);
      return OAuth2TokenValidatorResult.failure(error);
    }
  }
}
