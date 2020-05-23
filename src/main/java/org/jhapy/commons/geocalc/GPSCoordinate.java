package org.jhapy.commons.geocalc;

/**
 * Represents coordinates given in Degrees decimal-minutes (D m) format
 *
 * @author rgallet
 */
public class GPSCoordinate extends DMSCoordinate {

  GPSCoordinate(double wholeDegrees, double minutes) {
    super(wholeDegrees, minutes, 0);
  }
}
