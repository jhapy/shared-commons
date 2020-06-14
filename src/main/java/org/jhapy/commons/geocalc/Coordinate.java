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
import static java.lang.Math.floor;
import static java.lang.Math.toRadians;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Abstraction of coordinate systems (degrees, radians, dms and gps)
 *
 * @author rgallet
 */
abstract public class Coordinate implements Serializable {

  public static DegreeCoordinate fromDegrees(double decimalDegrees) {
    return new DegreeCoordinate(decimalDegrees);
  }

  public static DMSCoordinate fromDMS(double wholeDegrees, double minutes, double seconds) {
    return new DMSCoordinate(wholeDegrees, minutes, seconds);
  }

  public static GPSCoordinate fromGPS(double wholeDegrees, double minutes) {
    return new GPSCoordinate(wholeDegrees, minutes);
  }

  public static RadianCoordinate fromRadians(double radians) {
    return new RadianCoordinate(radians);
  }

  abstract double degrees();

  /**
   * @return degree value
   * @deprecated use degrees()
   */
  @Deprecated
  public double getValue() {
    return degrees();
  }

  DMSCoordinate toDMSCoordinate() {
    double _wholeDegrees = (int) degrees();
    double remaining = abs(degrees() - _wholeDegrees);
    double _minutes = (int) (remaining * 60);
    remaining = remaining * 60 - _minutes;
    double _seconds = new BigDecimal(remaining * 60).setScale(4, RoundingMode.HALF_UP)
        .doubleValue();

    return new DMSCoordinate(_wholeDegrees, _minutes, _seconds);
  }

  DegreeCoordinate toDegreeCoordinate() {
    return new DegreeCoordinate(degrees());
  }

  GPSCoordinate toGPSCoordinate() {
    double _wholeDegrees = floor(degrees());
    double remaining = degrees() - _wholeDegrees;
    double _minutes = floor(remaining * 60);

    return new GPSCoordinate(_wholeDegrees, _minutes);
  }

  RadianCoordinate toRadianCoordinate() {
    return new RadianCoordinate(toRadians(degrees()));
  }
}
