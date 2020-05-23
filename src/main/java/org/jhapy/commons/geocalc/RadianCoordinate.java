package org.jhapy.commons.geocalc;

/**
 * Represents coordinates given in radian-degrees (r) format
 *
 * @author rgallet
 */
public class RadianCoordinate extends Coordinate {

  public final double radians;

  RadianCoordinate(double radians) {
    this.radians = radians;
  }

  @Override
  double degrees() {
    return Math.toDegrees(radians);
  }

  /**
   * @return angle value
   * @deprecated use radians
   */
  @Deprecated
  public double getRadians() {
    return radians;
  }
}
