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

package org.jhapy.commons.geocalc;

import static java.lang.Math.abs;

/**
 * Represents coordinates given in Degrees Minutes decimal-seconds (D M s) format
 *
 * @author rgallet
 */
public class DMSCoordinate extends Coordinate {

  public final double wholeDegrees, minutes, seconds;

  DMSCoordinate(double wholeDegrees, double minutes, double seconds) {
    this.wholeDegrees = wholeDegrees;
    this.minutes = minutes;
    this.seconds = seconds;
  }

  @Override
  double degrees() {
    double decimalDegrees = abs(wholeDegrees) + minutes / 60 + seconds / 3600;

    if (wholeDegrees < 0) {
      decimalDegrees = -decimalDegrees;
    }

    return decimalDegrees;
  }

  /**
   * @return minutes
   * @deprecated use minutes
   */
  @Deprecated
  public double getMinutes() {
    return minutes;
  }

  /**
   * @return wholeDegrees
   * @deprecated use wholeDegrees
   */
  @Deprecated
  public double getWholeDegrees() {
    return wholeDegrees;
  }

  /**
   * @return seconds
   * @deprecated use seconds
   */
  @Deprecated
  public double getSeconds() {
    return seconds;
  }

  @Override
  public String toString() {
    return "DMSCoordinate{" +
        "wholeDegrees=" + wholeDegrees +
        ", minutes=" + minutes +
        ", seconds=" + seconds +
        '}';
  }
}
