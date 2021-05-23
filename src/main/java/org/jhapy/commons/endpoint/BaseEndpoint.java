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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ma.glasnost.orika.MappingContext;
import org.jhapy.commons.converter.CommonsConverterV2;
import org.jhapy.commons.utils.HasLogger;
import org.jhapy.commons.utils.OrikaBeanMapper;
import org.jhapy.dto.serviceQuery.BaseRemoteQuery;
import org.jhapy.dto.serviceQuery.ServiceResult;
import org.jhapy.dto.utils.Page;
import org.jhapy.dto.utils.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public abstract class BaseEndpoint implements HasLogger {

  protected CommonsConverterV2 converter;

  protected BaseEndpoint(CommonsConverterV2 converter ) {
    this.converter = converter;
  }

  protected Map<String,Object> getContext(BaseRemoteQuery query) {
    Map<String,Object> context = new HashMap<>();

    context.put("username", query.getQueryUsername());
    context.put("userId", query.getQueryUserId());
    context.put("sessionId", query.getQuerySessionId());
    context.put("iso3Language", query.getQueryIso3Language());
    context.put("currentPosition", query.getQueryCurrentPosition());

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

  protected Page toDtoPage(org.springframework.data.domain.Page domain, List data ) {
    Page result = new Page<>();
    result.setTotalPages(domain.getTotalPages());
    result.setSize(domain.getSize());
    result.setTotalElements(domain.getTotalElements());
    result.setNumber(domain.getNumber());
    result.setNumberOfElements(domain.getNumberOfElements());
    result.setPageable(converter.convert(domain.getPageable()));
    result.setContent(data);
    return result;
  }
}
