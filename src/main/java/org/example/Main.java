package org.example;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import ch.hsr.geohash.util.VincentyGeodesy;

public class Main {
    public static void main(String[] args) {
        // cubbon park
        double latitude = 12.9779;
        double longitude = 77.5952;
        int precision = 9;
        String geoHashString = getGeoHashString(latitude, longitude, precision);
        System.out.println("geo hash string of cubbon park:"+geoHashString);
        GeoHash geoHash = GeoHash.fromGeohashString(geoHashString);
        WGS84Point boundingBoxCenter = geoHash.getBoundingBoxCenter();
        // Ulsoor lake
        double latitude1 = 12.9832;
        double longitude1 = 77.6200;
        String geoHashString1 = getGeoHashString(latitude1, longitude1, precision);
        System.out.println("geo hash string of ulsoor lake:"+geoHashString1);
        GeoHash geoHash1 = GeoHash.fromGeohashString(geoHashString1);
        WGS84Point boundingBoxCenter1 = geoHash1.getBoundingBoxCenter();
        // calculation between two places
        long stepsBetween = GeoHash.stepsBetween(geoHash, geoHash1);
        double distanceInMeters = VincentyGeodesy.distanceInMeters(boundingBoxCenter, boundingBoxCenter1);
        System.out.println("Steps between:"+stepsBetween);
        System.out.println("distanceInMeters:"+distanceInMeters);
    }

    private static String getGeoHashString(double latitude, double longitude, int precision) {
        return GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, precision);
    }
}
