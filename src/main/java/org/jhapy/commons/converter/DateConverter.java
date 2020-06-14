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

package org.jhapy.commons.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;
import org.jhapy.commons.utils.HasLogger;

public class DateConverter {

  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  private static final SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_FORMAT);
  private static final SimpleDateFormat sdfIso8601 = new SimpleDateFormat(ISO8601_DATE_FORMAT);

  public static class Serialize extends JsonSerializer<Instant> implements HasLogger {

    @Override
    public void serialize(Instant value, JsonGenerator jgen, SerializerProvider provider) {
      String loggerPrefix = getLoggerPrefix("serialize");
      try {
        if (value == null) {
          jgen.writeNull();
        } else {
          jgen.writeString(DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneId.systemDefault())
              .format(value));
        }
      } catch (Exception e) {
        logger().error(
            loggerPrefix + "Unexpected error while serializing date : '" + value + "' : " + e
                .getMessage(), e);
      }
    }
  }

  public static class Deserialize extends JsonDeserializer<Instant> implements HasLogger {

    @Override
    public Instant deserialize(com.fasterxml.jackson.core.JsonParser jp,
        DeserializationContext ctxt) throws IOException {
      String loggerPrefix = getLoggerPrefix("deserialize");
      String dateAsString = "";
      try {
        dateAsString = jp.getText();
        if (StringUtils.isBlank(dateAsString)) {
          return null;
        } else {
          try {
            return Instant.ofEpochMilli(sdf1.parse(dateAsString).getTime());
          } catch (NumberFormatException | ParseException pe) {
            return Instant.ofEpochMilli(sdfIso8601.parse(dateAsString).getTime());
          }
        }
      } catch (Exception e) {
        logger().error(
            loggerPrefix + "Unexpected error while deserializing date : '" + dateAsString + "' : "
                + e.getMessage(), e);
      }
      return null;
    }
  }
}