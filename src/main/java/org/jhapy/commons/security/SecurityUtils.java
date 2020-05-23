package org.jhapy.commons.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.jhapy.dto.domain.security.SecurityUser;

/**
 * SecurityUtils takes care of all such static operations that have to do with security and querying
 * rights from different beans of the UI.
 *
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-03-26
 */
public final class SecurityUtils {

  private SecurityUtils() {
    // Util methods only
  }

  /**
   * Gets the user name of the currently signed in user.
   *
   * @return the user name of the current user or <code>null</code> if the user has not signed in
   */
  public static String getUsername() {
    SecurityContext context = SecurityContextHolder.getContext();
    if (context == null || context.getAuthentication() == null) {
      return "Anonymous";
    }
    Object principal = context.getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
      return userDetails.getUsername();
    }
    // Anonymous or no authentication.
    return "Anonymous";
  }

  public static SecurityUser getSecurityUser() {
    SecurityContext context = SecurityContextHolder.getContext();
    if (context == null || context.getAuthentication() == null) {
      return null;
    }
    Object principal = context.getAuthentication().getPrincipal();
    if (principal instanceof SecurityUser) {
      return (SecurityUser) principal;
    }
    // Anonymous or no authentication.
    return null;
  }

  public static boolean hasRole(String role) {
    Authentication userAuthentication = SecurityContextHolder.getContext().getAuthentication();

    // All other views require authentication
    if (!isUserLoggedIn(userAuthentication)) {
      return false;
    }

    return userAuthentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .anyMatch(s -> s.equals(role));
  }

  /**
   * Checks if the user is logged in.
   *
   * @return true if the user is logged in. False otherwise.
   */
  public static boolean isUserLoggedIn() {
    return isUserLoggedIn(SecurityContextHolder.getContext().getAuthentication());
  }

  private static boolean isUserLoggedIn(Authentication authentication) {
    return authentication != null
        && !(authentication instanceof AnonymousAuthenticationToken);
  }
}
