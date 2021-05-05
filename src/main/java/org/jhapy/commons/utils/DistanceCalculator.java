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

package org.jhapy.commons.utils;

import java.util.Objects;
import org.jhapy.dto.utils.LatLng;
import org.jhapy.dto.utils.MapBounds;

/**
 * @author jHapy Lead Dev.
 * @version 1.0
 * @since 2019-08-17
 */

public class DistanceCalculator {

  /**
   * The minimum allowed latitude
   */
  public static final float MIN_LATITUDE = Float.parseFloat("-90.0000");

  /**
   * The maximum allowed latitude
   */
  public static final float MAX_LATITUDE = Float.parseFloat("90.0000");

  /**
   * The minimum allowed longitude
   */
  public static final float MIN_LONGITUDE = Float.parseFloat("-180.0000");

  /**
   * The maximum allowed longitude
   */
  public static final float MAX_LONGITUDE = Float.parseFloat("180.0000");

  /**
   * The diameter of the Earth used in calculations
   */
  public static final float EARTH_DIAMETER = Float.parseFloat("12756.274");

  public static double getRadius(MapBounds mapBounds) {
    double h = distance(mapBounds.getSouthWest().getLat(), mapBounds.getSouthWest().getLng(),
        mapBounds.getSouthWest().getLat(),
        mapBounds.getNorthEast().getLng(), "K");
    double l = distance(mapBounds.getSouthWest().getLat(), mapBounds.getSouthWest().getLng()
        , mapBounds.getNorthEast().getLat(),
        mapBounds.getSouthWest().getLng(), "K");
    return (h > l ? l * 1000 : h * 1000) / 2;
  }

  /**
   * This routine calculates the distance between two points (given the latitude/longitude of those
   * points). It is being used to calculate the distance between two locations using GeoDataSource
   * (TM) prodducts
   *
   * Definitions: South latitudes are negative, east longitudes are positive
   *
   * Passed to function: lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees) lat2,
   * lon2 = Latitude and Longitude of point 2 (in decimal degrees) unit = the unit you desire for
   * results where: 'M' is statute miles (default) 'K' is kilometers 'N' is nautical miles
   */
  public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
    if ((lat1 == lat2) && (lon1 == lon2)) {
      return 0;
    } else {
      double theta = lon1 - lon2;
      double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
          + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math
          .cos(Math.toRadians(theta));
      dist = Math.acos(dist);
      dist = Math.toDegrees(dist);
      dist = dist * 60 * 1.1515;
      if (Objects.equals(unit, "K")) {
        dist = dist * 1.609344;
      } else if (Objects.equals(unit, "N")) {
        dist = dist * 0.8684;
      }
      return (dist);
    }
  }

  public static LatLng midPoint(LatLng firstCoordinate, LatLng secondCoordinate) {
    double lon1 = Math.toRadians(firstCoordinate.getLng());
    double lat1 = Math.toRadians(firstCoordinate.getLat());

    double lon2 = Math.toRadians(secondCoordinate.getLng());
    double lat2 = Math.toRadians(secondCoordinate.getLat());

    double deltaLong = lon2 - lon1;

    double Bx = Math.cos(lat2) * Math.cos(deltaLong);
    double By = Math.cos(lat2) * Math.sin(deltaLong);
    double lat3 = Math.toDegrees(Math.atan2(Math.sin(lat1) + Math.sin(lat2),
        Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By)));
    double lon3 = Math.toDegrees(lon1 + Math.atan2(By, Math.cos(lat1) + Bx));

    return new LatLng(lat3, lon3);
  }

  /**
   * A method to validate a latitude value
   *
   * @param latitude the latitude to check is valid
   * @return true if, and only if, the latitude is within the MIN and MAX latitude
   */
  public static boolean isValidLatitude(double latitude) {
    return latitude >= MIN_LATITUDE && latitude <= MAX_LATITUDE;
  }

  /**
   * A method to validate a longitude value
   *
   * @param longitude the longitude to check is valid
   * @return true if, and only if, the longitude is between the MIN and MAX longitude
   */
  public static boolean isValidLongitude(double longitude) {
    return longitude >= MIN_LONGITUDE && longitude <= MAX_LONGITUDE;
  }

  /**
   * A private method to calculate the latitude constant
   *
   * @return a double representing the latitude constant
   */
  public static double latitudeConstant() {
    return EARTH_DIAMETER * (Math.PI / Float.parseFloat("360"));
  }

  /**
   * A private method to caluclate the longitude constant
   *
   * @param latitude a latitude coordinate in decimal notation
   * @return a double representing the longitude constant
   */
  public static double longitudeConstant(double latitude) {
    return EARTH_DIAMETER * Math.PI * Math.abs(Math.cos(Math.abs(latitude))) / Float
        .parseFloat("360");

  }

  /**
   * A method to add distance in a northerly direction to a coordinate
   *
   * @param latitude a latitude coordinate in decimal notation
   * @param longitude a longitude coordinate in decimal notation
   * @param distance the distance to add in metres
   * @return the new coordinate
   */
  public static LatLng addDistanceNorth(LatLng source, int distance) {

    // check on the parameters
    if (!isValidLatitude(source.getLat()) || !isValidLongitude(source.getLng())
        || distance <= 0) {
      throw new IllegalArgumentException("All parameters are required and must be valid");
    }

    // convert the distance from metres to kilometers
    float kilometers = distance / 1000f;

    // calculate the new latitude
    double newLat = source.getLat() + (kilometers / latitudeConstant());

    return new LatLng(newLat, source.getLng());

  }

  /**
   * A method to add distance in a southerly direction to a coordinate
   *
   * @param latitude a latitude coordinate in decimal notation
   * @param longitude a longitude coordinate in decimal notation
   * @param distance the distance to add in metres
   * @return the new coordinate
   */
  public static LatLng addDistanceSouth(LatLng source, int distance) {

    // check on the parameters
    if (!isValidLatitude(source.getLat()) || !isValidLongitude(source.getLng())
        || distance <= 0) {
      throw new IllegalArgumentException("All parameters are required and must be valid");
    }

    // convert the distance from metres to kilometers
    float kilometers = distance / 1000f;

    // calculate the new latitude
    double newLat = source.getLat() - (kilometers / latitudeConstant());

    return new LatLng(newLat, source.getLng());

  }

  /**
   * A method to add distance in an easterly direction to a coordinate
   *
   * @param latitude a latitude coordinate in decimal notation
   * @param longitude a longitude coordinate in decimal notation
   * @param distance the distance to add in metres
   * @return the new coordinate
   */
  public static LatLng addDistanceEast(LatLng source, int distance) {

    // check on the parameters
    if (!isValidLatitude(source.getLat()) || !isValidLongitude(source.getLng())
        || distance <= 0) {
      throw new IllegalArgumentException("All parameters are required and must be valid");
    }

    // convert the distance from metres to kilometers
    float kilometers = distance / 1000f;

    // calculate the new longitude
    double newLng = source.getLng() + (kilometers / longitudeConstant(source.getLat()));

    return new LatLng(source.getLat(), newLng);
  }

  /**
   * A method to add distance in an westerly direction to a coordinate
   *
   * @param latitude a latitude coordinate in decimal notation
   * @param longitude a longitude coordinate in decimal notation
   * @param distance the distance to add in metres
   * @return the new coordinate
   */
  public static LatLng addDistanceWest(LatLng source, int distance) {

    // check on the parameters
    if (!isValidLatitude(source.getLat()) || !isValidLongitude(source.getLng())
        || distance <= 0) {
      throw new IllegalArgumentException("All parameters are required and must be valid");
    }

    // convert the distance from metres to kilometers
    float kilometers = distance / 1000f;

    // calculate the new longitude
    double newLng = source.getLng() - (kilometers / longitudeConstant(source.getLat()));

    return new LatLng(source.getLat(), newLng);
  }

  /**
   * A method to build four coordinates representing a bounding box given a start coordinate and a
   * distance
   *
   * @param latitude a latitude coordinate in decimal notation
   * @param longitude a longitude coordinate in decimal notation
   * @param distance the distance to add in metres
   * @return a hashMap representing the bounding box (NE,SE,SW,NW)
   */
  public static java.util.HashMap<String, LatLng> getBoundingBox(LatLng source, int distance) {

    // check on the parameters
    if (!isValidLatitude(source.getLat()) || !isValidLongitude(source.getLng())
        || distance <= 0) {
      throw new IllegalArgumentException("All parameters are required and must be valid");
    }

    // convert the distance from metres to kilometers
    float kilometers = distance / 1000f;

    // declare helper variables
    java.util.HashMap<String, LatLng> boundingBox = new java.util.HashMap<>();

    // calculate the coordinates
    LatLng north = addDistanceNorth(source, distance);
    LatLng south = addDistanceSouth(source, distance);
    LatLng east = addDistanceEast(source, distance);
    LatLng west = addDistanceWest(source, distance);

    // build the bounding box object
    boundingBox.put("NE", new LatLng(north.getLat(), east.getLng()));
    boundingBox.put("SE", new LatLng(south.getLat(), east.getLng()));
    boundingBox.put("SW", new LatLng(south.getLat(), west.getLng()));
    boundingBox.put("NW", new LatLng(north.getLat(), west.getLng()));

    // return the bounding box object
    return boundingBox;
  }

  public static int getRadiusFromZoom(int zoom) {
    return switch (zoom) {
      case 22 -> 4;
      case 21 -> 9;
      case 20 -> 18;
      case 19 -> 36;
      case 18 -> 72;
      case 17 -> 145;
      case 16 -> 290;
      case 15 -> 580;
      case 14 -> 1160;
      case 13 -> 2321;
      case 12 -> 4643;
      case 11 -> 9292;
      case 10 -> 18602;
      case 9 -> 37278;
      case 8 -> 74849;
      case 7 -> 150858;
      case 6 -> 305205;
      case 5 -> 610019;
      case 4 -> 1216870;
      case 3 -> 2407702;
      case 2 -> 3566218;
      case 1 -> 4596052;
      default -> 0;
    };
  }

  public static int getZoomFromMeter(double meter) {
    if (meter < 1128.497220) {
      return 20;
    } else if (meter < 2256.994440) {
      return 19;
    } else if (meter < 4513.988880) {
      return 18;
    } else if (meter < 9027.977761) {
      return 17;
    } else if (meter < 18055.955520) {
      return 16;
    } else if (meter < 36111.911040) {
      return 15;
    } else if (meter < 72223.822090) {
      return 14;
    } else if (meter < 144447.644200) {
      return 13;
    } else if (meter < 288895.288400) {
      return 12;
    } else if (meter < 577790.576700) {
      return 11;
    } else if (meter < 1155581.153000) {
      return 10;
    } else if (meter < 2311162.307000) {
      return 9;
    } else if (meter < 4622324.614000) {
      return 8;
    } else if (meter < 9244649.227000) {
      return 7;
    } else if (meter < 18489298.450000) {
      return 6;
    } else if (meter < 36978596.910000) {
      return 5;
    } else if (meter < 73957193.820000) {
      return 4;
    } else if (meter < 147914387.600000) {
      return 3;
    } else if (meter < 295828775.300000) {
      return 2;
    } else if (meter < 591657550.500000) {
      return 1;
    } else {
      return 0;
    }
  }
}
