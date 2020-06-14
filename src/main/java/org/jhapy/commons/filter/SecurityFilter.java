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

package org.jhapy.commons.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.jhapy.commons.utils.HasLogger;
import org.jhapy.dto.utils.AppContextThread;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Component
@Order(1)
public class SecurityFilter implements Filter, HasLogger {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    SpringBeanAutowiringSupport
        .processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String loggerPrefix = getLoggerPrefix("doFilter");
    HttpServletRequest req = (HttpServletRequest) request;

    String username = req.getHeader("X-SecUsername");
    if (StringUtils.isNotBlank(username)) {
      logger().trace(loggerPrefix + "Username = " + username);
      AppContextThread.setCurrentUsername(username);
    }
    String securityUserId = req.getHeader("X-SecSecurityUserId");
    if (StringUtils.isNotBlank(securityUserId)) {
      logger().trace(loggerPrefix + "SecurityUserId = " + securityUserId);
      AppContextThread.setCurrentSecurityUserId(securityUserId);
    }
    String sessionId = req.getHeader("X-SecSessionId");
    if (StringUtils.isNotBlank(sessionId)) {
      logger().trace(loggerPrefix + "SessionId = " + sessionId);
      AppContextThread.setCurrentSessionId(sessionId);
    }
    String iso3Language = req.getHeader("X-Iso3Language");
    if (StringUtils.isNotBlank(iso3Language)) {
      logger().trace(loggerPrefix + "Iso3Language = " + iso3Language);
      AppContextThread.setCurrentIso3Language(iso3Language);
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }
}