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

import java.io.Serializable;

/**
 * Represent a point in spherical system
 *
 * @author rgallet
 */
public class Point implements Serializable {

  //decimal degrees
  public final double latitude, longitude;

  private Point(Coordinate latitude, Coordinate longitude) {
    this.latitude = latitude.degrees();
    this.longitude = longitude.degrees();
  }

  /**
   * Create a new Point.
   *
   * @param latitude latitude
   * @param longitude longitude
   * @return the point
   */
  public static Point at(Coordinate latitude, Coordinate longitude) {
    return new Point(latitude, longitude);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Point other = (Point) obj;
    if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
      return false;
    }
    return Double.doubleToLongBits(this.longitude) == Double.doubleToLongBits(other.longitude);
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 31 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (
        Double.doubleToLongBits(this.latitude) >>> 32));
    hash = 31 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (
        Double.doubleToLongBits(this.longitude) >>> 32));
    return hash;
  }

  @Override
  public String toString() {
    return "Point{" + "latitude=" + latitude + ", longitude=" + longitude + '}';
  }
}
