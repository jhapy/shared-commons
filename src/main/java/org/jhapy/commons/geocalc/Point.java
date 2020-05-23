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
