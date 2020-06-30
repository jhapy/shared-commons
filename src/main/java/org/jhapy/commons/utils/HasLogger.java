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

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.jhapy.commons.security.SecurityUtils;
import org.jhapy.dto.utils.AppContextThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-26
 */
public interface HasLogger {

  default String getLoggerPrefix(final String methodName) {
    String username = SecurityUtils.getCurrentUserLogin()
        .orElse(AppContextThread.getCurrentUsername());
    String sessionId = AppContextThread.getCurrentSessionId() == null ? "local"
        : AppContextThread.getCurrentSessionId();
    MDC.put("jhapy.username", username);
    MDC.put("jhapy.sessionId", sessionId);
    String params = "";
    if (StringUtils.isNotBlank(username)) {
      params += username;
    }
    /*
    if (StringUtils.isNotBlank(sessionId)) {
      params += params.length() > 0 ? ", " + sessionId : sessionId;
    }
     */
    return String.format("%-30s", methodName + "(" + params + ")") + " :: ";
  }

  default String getLoggerPrefix(final String methodName, Object... _params) {
    String username = SecurityUtils.getCurrentUserLogin()
        .orElse(AppContextThread.getCurrentUsername());
    String sessionId = AppContextThread.getCurrentSessionId() == null ? "local"
        : AppContextThread.getCurrentSessionId();
    MDC.put("jhapy.username", username);
    MDC.put("jhapy.sessionId", sessionId);
    StringBuilder params = new StringBuilder();
    if (StringUtils.isNotBlank(username)) {
      params.append(username).append(_params.length > 0 ? ", " : "");
    }
    /*
    if (StringUtils.isNotBlank(sessionId)) {
      params.append( params.length() > 0 ? ", " + sessionId : sessionId );
    }
     */
    if (_params.length > 0) {
      for (Object p : _params) {
        if (p.getClass().isArray()) {
          params.append(Arrays.asList((Object[]) p)).append(", ");
        } else {
          params.append(p).append(", ");
        }
      }
      params = new StringBuilder(params.substring(0, params.length() - 2));
    }
    return String.format("%-30s", methodName + "(" + params + ")") + " :: ";
  }

  default Logger logger() {
    return LoggerFactory.getLogger(getClass());
  }

  default Logger logger(Class aClass) {
    return LoggerFactory.getLogger(aClass);
  }
}
