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

package org.jhapy.commons.endpoint;

import ma.glasnost.orika.MappingContext;
import org.jhapy.commons.utils.HasLogger;
import org.jhapy.commons.utils.OrikaBeanMapper;
import org.jhapy.dto.serviceQuery.BaseRemoteQuery;
import org.jhapy.dto.serviceQuery.ServiceResult;
import org.springframework.http.ResponseEntity;

public abstract class BaseEndpoint implements HasLogger {

  protected final OrikaBeanMapper mapperFacade;

  protected BaseEndpoint(OrikaBeanMapper mapperFacade) {
    this.mapperFacade = mapperFacade;
  }

  protected MappingContext getOrikaContext(BaseRemoteQuery query) {
    MappingContext context = new MappingContext.Factory().getContext();

    context.setProperty("username", query.getQueryUsername());
    context.setProperty("userId", query.getQueryUserId());
    context.setProperty("sessionId", query.getQuerySessionId());
    context.setProperty("iso3Language", query.getQueryIso3Language());
    context.setProperty("currentPosition", query.getQueryCurrentPosition());

    return context;
  }

  protected ResponseEntity<ServiceResult> handleResult(String loggerPrefix) {
    return handleResult(loggerPrefix, new ServiceResult<>());
  }

  protected ResponseEntity<ServiceResult> handleResult(String loggerPrefix, Object result) {
    return handleResult(loggerPrefix, new ServiceResult<>(result));
  }

  protected ResponseEntity<ServiceResult> handleResult(String loggerPrefix, String error) {
    return handleResult(loggerPrefix, new ServiceResult<>(error));
  }

  protected ResponseEntity<ServiceResult> handleResult(String loggerPrefix, ServiceResult result) {
    if (result.getIsSuccess()) {
      ResponseEntity<ServiceResult> response = ResponseEntity.ok(result);
      if (logger().isTraceEnabled()) {
        logger().debug(loggerPrefix + "Response OK : " + result);
      }
      return response;
    } else {
      logger().error(loggerPrefix + "Response KO : " + result.getMessage());
      return ResponseEntity.ok(result);
    }
  }

  protected ResponseEntity<ServiceResult> handleResult(String loggerPrefix, Throwable throwable) {
    logger()
        .error(loggerPrefix + "Response KO with Exception : " + throwable.getLocalizedMessage(),
            throwable);
    return ResponseEntity.ok(new ServiceResult<>(throwable));
  }
}
