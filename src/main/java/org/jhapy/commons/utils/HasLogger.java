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

import java.text.MessageFormat;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.jhapy.commons.security.SecurityUtils;
import org.jhapy.dto.utils.AppContextThread;

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
    ThreadContext.put("jhapy.username", username);
    ThreadContext.put("jhapy.sessionId", sessionId);
    var params = "";
    if (StringUtils.isNotBlank(username)) {
      params += username;
    }

    return String.format("%-30s", methodName + "(" + params + ")") + " :: ";
  }

  default String getLoggerPrefix(final String methodName, Object... params) {
    String username = SecurityUtils.getCurrentUserLogin()
        .orElse(AppContextThread.getCurrentUsername());
    String sessionId = AppContextThread.getCurrentSessionId() == null ? "local"
        : AppContextThread.getCurrentSessionId();
    ThreadContext.put("jhapy.username", username);
    ThreadContext.put("jhapy.sessionId", sessionId);
    var paramsStr = new StringBuilder();
    if (StringUtils.isNotBlank(username)) {
      paramsStr.append(username).append(params.length > 0 ? ", " : "");
    }

    if (params.length > 0) {
      for (Object p : params) {
        if (p == null) {
          paramsStr.append("null").append(", ");
        } else if (p.getClass().isArray()) {
          paramsStr.append(Arrays.asList((Object[]) p)).append(", ");
        } else {
          paramsStr.append(p).append(", ");
        }
      }
      paramsStr = new StringBuilder(paramsStr.substring(0, paramsStr.length() - 2));
    }
    return String.format("%-30s", methodName + "(" + paramsStr + ")") + " :: ";
  }

  default Logger logger() {
    return LogManager.getLogger(getClass());
  }

  default void trace(String prefix, String message, Object... params) {
    logger()
        .trace(() -> MessageFormat.format("{0}{1}", prefix, MessageFormat.format(message, params)));
  }

  default void debug(String prefix, String message, Object... params) {
    logger()
        .debug(() -> MessageFormat.format("{0}{1}", prefix, MessageFormat.format(message, params)));
  }

  default void info(String prefix, String message, Object... params) {
    logger()
        .info(() -> MessageFormat.format("{0}{1}", prefix, MessageFormat.format(message, params)));
  }

  default void warn(String prefix, String message, Object... params) {
    logger()
        .warn(() -> MessageFormat.format("{0}{1}", prefix, MessageFormat.format(message, params)));
  }

  default void warn(String prefix, Throwable exception, String message, Object... params) {
    logger()
        .warn(() -> MessageFormat.format("{0}{1}", prefix, MessageFormat.format(message, params)),
            exception);
  }

  default void error(String prefix, String message, Object... params) {
    logger()
        .warn(() -> MessageFormat.format("{0}{1}", prefix, MessageFormat.format(message, params)));
  }

  default void error(String prefix, Throwable exception, String message, Object... params) {
    logger()
        .error(() -> MessageFormat.format("{0}{1}", prefix, MessageFormat.format(message, params)),
            exception);
  }

  default Logger logger(Class aClass) {
    return LogManager.getLogger(aClass);
  }
}
