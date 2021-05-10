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

package org.jhapy.commons.exception;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-06-04
 */
public class ServiceException extends AbstractThrowableProblem {

  private static final long serialVersionUID = 1L;

  private final String serviceName;

  public ServiceException(String defaultMessage, String serviceName) {
    this(ErrorConstants.DEFAULT_TYPE, defaultMessage, serviceName);
  }

  public ServiceException(URI type, String defaultMessage, String serviceName) {
    super(type, defaultMessage, Status.INTERNAL_SERVER_ERROR, null, null, null,
        Collections.singletonMap("serviceName", serviceName));
    this.serviceName = serviceName;
  }

  public String getServiceName() {
    return serviceName;
  }
}