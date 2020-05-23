package org.jhapy.commons.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 * Utility class for working with FreeMarker. This bean is only created if and only if FreeMaker
 * {@link Template} is the classPath.
 *
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-11-03
 */
@Component
@ConditionalOnClass(freemarker.template.Template.class)
public class FreeMakerProcessor {

  private final Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);

  /**
   * Process the given sourceCode using the given model and return a string as the result of
   * processing.
   *
   * @param sourceCode the source code to be processed.
   * @param model the model to use
   * @return the result as String
   * @see FreeMarkerTemplateUtils#processTemplateIntoString(Template, Object)
   */
  public String process(String sourceCode, Object model) {
    String result = sourceCode;
    try {
      final Template bodyTemplate = new Template(null, sourceCode, cfg);
      result = FreeMarkerTemplateUtils.processTemplateIntoString(bodyTemplate, model);
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
    return result;
  }
}
