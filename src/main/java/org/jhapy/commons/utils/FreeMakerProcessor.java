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
