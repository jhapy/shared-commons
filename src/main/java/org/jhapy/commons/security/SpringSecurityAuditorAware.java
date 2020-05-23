package org.jhapy.commons.security;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-07
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

  public Optional<String> getCurrentAuditor() {
    String currentUsername = "Unknown";
    try {
      RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
      if (requestAttributes instanceof ServletRequestAttributes) {
        HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes)
            .getRequest();
        currentUsername = servletRequest.getHeader("X-SecUsername");
      } else {
        currentUsername = SecurityUtils.getUsername();
      }
    } catch (java.lang.IllegalStateException e) {
    }
    return Optional.ofNullable(currentUsername);
  }
}