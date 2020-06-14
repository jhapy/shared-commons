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

import java.util.Objects;

/**
 * Represents an area (viewed rectangular shaped projected onto Earth), defined by its top left and
 * bottom right coordinates
 *
 * @author rgallet
 */
public class BoundingArea {

  public final Point northEast, southWest;
  public final Point southEast, northWest;

  private BoundingArea(Point northEast, Point southWest) {
    this.northEast = northEast;
    this.southWest = southWest;

    southEast = Point.at(Coordinate.fromDegrees(southWest.latitude),
        Coordinate.fromDegrees(northEast.longitude));
    northWest = Point.at(Coordinate.fromDegrees(northEast.latitude),
        Coordinate.fromDegrees(southWest.longitude));
  }

  public static BoundingArea at(Point northEast, Point southWest) {
    return new BoundingArea(northEast, southWest);
  }

  @Override
  public String toString() {
    return "BoundingArea{" + "northEast=" + northEast + ", southWest=" + southWest + '}';
  }

  /**
   * @param point point to check
   * @return true if Point point is contained withing this bounding area
   * @deprecated use contains(Point point)
   */
  @Deprecated
  public boolean isContainedWithin(Point point) {
    return contains(point);
  }

  /**
   * @param point point to check
   * @return true if Point point is contained withing this bounding area
   */
  public boolean contains(Point point) {
    boolean predicate1 =
        point.latitude >= this.southWest.latitude && point.latitude <= this.northEast.latitude;

    if (!predicate1) {
      return false;
    }

    boolean predicate2;

    if (southWest.longitude
        > northEast.longitude) { //area is going across the max/min longitude boundaries (ie. sort of back of the Earth)
      //we "split" the area in 2, longitude-wise, point only needs to be in one or the other.
      boolean predicate3 = point.longitude <= northEast.longitude && point.longitude >= -180;
      boolean predicate4 = point.longitude >= southWest.longitude && point.longitude <= 180;

      predicate2 = predicate3 || predicate4;
    } else {
      predicate2 = point.longitude >= southWest.longitude && point.longitude <= northEast.longitude;
    }

    return predicate2;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    BoundingArea other = (BoundingArea) obj;

    return Objects.equals(this.northEast, other.northEast) &&
        Objects.equals(this.southWest, other.southWest);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 13 * hash + (this.northEast != null ? this.northEast.hashCode() : 0);
    hash = 13 * hash + (this.southWest != null ? this.southWest.hashCode() : 0);
    return hash;
  }
}
