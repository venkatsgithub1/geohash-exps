package org.example.service;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.util.VincentyGeodesy;
import org.example.model.ResultWithDistance;

import java.util.List;

public class GeoHashService implements GeoService {

    @Override
    public long calculateDistanceInSteps(double latitude, double longitude, double latitude2, double longitude2, int precision) {
        String geoHashString1 = GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, precision);
        String geoHashString2 = GeoHash.geoHashStringWithCharacterPrecision(latitude2, longitude2, precision);
        GeoHash geoHash1 = GeoHash.fromGeohashString(geoHashString1);
        GeoHash geoHash2 = GeoHash.fromGeohashString(geoHashString2);
        return GeoHash.stepsBetween(geoHash1, geoHash2);
    }

    @Override
    public double calculateDistanceInMeters(double latitude, double longitude, double latitude2, double longitude2, int precision) {

        String geoHashString1 = GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, precision);
        String geoHashString2 = GeoHash.geoHashStringWithCharacterPrecision(latitude2, longitude2, precision);
        GeoHash geohash = GeoHash.fromGeohashString(geoHashString1);
        GeoHash geohash2 = GeoHash.fromGeohashString(geoHashString2);
        return VincentyGeodesy.distanceInMeters(geohash.getBoundingBoxCenter(), geohash2.getBoundingBoxCenter());
    }

    @Override
    public List<ResultWithDistance> findNearbyEstablishments(double latitude, double longitude, int precision, int limit) {
        return List.of();
    }
}
