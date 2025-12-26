package org.example.geohashjava;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import ch.hsr.geohash.util.VincentyGeodesy;
import org.example.utils.LatLang;
import org.example.utils.LatLangRepository;
import org.example.utils.Places;

public class Main {
    public static void main(String[] args) {
        LatLangRepository repository = new LatLangRepository();
        LatLang cubbonLatLang = repository.getLatLangBasedOnPlace(Places.CUBBON_PARK);
        LatLang ulsoorLatLang = repository.getLatLangBasedOnPlace(Places.ULSOOR_LAKE);
        int precision = 9;

        String geoHashString = getGeoHashString(cubbonLatLang.latitude(), cubbonLatLang.longitude(), precision);
        System.out.println("geo hash string of cubbon park:"+geoHashString);
        GeoHash geoHash = GeoHash.fromGeohashString(geoHashString);
        WGS84Point boundingBoxCenter = geoHash.getBoundingBoxCenter();

        String geoHashString1 = getGeoHashString(ulsoorLatLang.latitude(), ulsoorLatLang.longitude(), precision);
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
