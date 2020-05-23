package org.jhapy.commons.utils;

import java.text.DecimalFormat;
import org.jhapy.dto.utils.LatLng;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 10/10/19
 */
public class LatLngUtils {

  private final static DecimalFormat decimalFormat = new DecimalFormat("#.00000");

  private LatLngUtils() {
  }

  public static String getDisplayValue(LatLng latLng) {
    if (latLng != null) {
      return String.format("(l:%s, L:%s)", decimalFormat.format(latLng.getLat()),
          decimalFormat.format(latLng.getLng()));
    } else {
      return "";
    }
  }
}
