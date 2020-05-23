package org.jhapy.commons.geocalc;

/**
 * Represents coordinates given in decimal-degrees (d) format
 *
 * @author rgallet
 */
public class DegreeCoordinate extends Coordinate {

  final double decimalDegrees;

  DegreeCoordinate(double decimalDegrees) {
    this.decimalDegrees = decimalDegrees;
  }

  @Override
  double degrees() {
    return decimalDegrees;
  }

  @Override
  public String toString() {
    return "DegreeCoordinate{" + "decimalDegrees=" + decimalDegrees + " degrees}";
  }
}
