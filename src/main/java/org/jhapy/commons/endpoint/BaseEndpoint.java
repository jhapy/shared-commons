package org.jhapy.commons.endpoint;

import ma.glasnost.orika.MappingContext;
import org.springframework.http.ResponseEntity;
import org.jhapy.commons.utils.HasLogger;
import org.jhapy.commons.utils.OrikaBeanMapper;
import org.jhapy.dto.serviceQuery.BaseRemoteQuery;
import org.jhapy.dto.serviceQuery.ServiceResult;

public abstract class BaseEndpoint implements HasLogger {

  protected final OrikaBeanMapper mapperFacade;

  protected BaseEndpoint(OrikaBeanMapper mapperFacade) {
    this.mapperFacade = mapperFacade;
  }

  protected MappingContext getOrikaContext(BaseRemoteQuery query) {
    MappingContext context = new MappingContext.Factory().getContext();

    context.setProperty("username", query.get_iso3Language());
    context.setProperty("securityUserId", query.get_securityUserId());
    context.setProperty("sessionId", query.get_sessionId());
    context.setProperty("iso3Language", query.get_iso3Language());
    context.setProperty("currentPosition", query.get_currentPosition());

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
      if ( logger().isTraceEnabled() )
        logger().debug(loggerPrefix + "Response OK : " + result);
      return response;
    } else {
      logger().error(loggerPrefix + "Response KO : " + result.getMessage());
      return ResponseEntity.ok(result);
    }
  }

  protected ResponseEntity<ServiceResult> handleResult(String loggerPrefix, Throwable throwable) {
    logger()
        .error(loggerPrefix + "Response KO with Exception : " + throwable.getMessage(), throwable);
    return ResponseEntity.ok(new ServiceResult<>(throwable));
  }
}
