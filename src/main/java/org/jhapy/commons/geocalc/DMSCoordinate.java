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
