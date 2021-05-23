package org.jhapy.commons.exception;

import java.net.URI;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.zalando.problem.StatusType;
import org.zalando.problem.violations.Violation;

/**
 * @author Alexandre Clavaud.
 * @version 1.0
 * @since 22/05/2021
 */
@Data
public class JHapyProblem {
  private URI type;
  private String title;
  private StatusType status;
  private String detail;
  private URI instance;
  private String serviceName;
  private String message;
  private String param;
  private String fieldErrors;
  private String path;
  private List<Violation> violations;
  private String[] stacktrace;
}