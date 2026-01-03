package org.example.service;

import org.example.model.ResultWithDistance;

import java.util.List;

public interface GeoService {
    long calculateDistanceInSteps(double latitude, double longitude, double latitude2, double longitude2, int precision);

    double calculateDistanceInMeters(double latitude, double longitude, double latitude2, double longitude2, int precision);

    List<ResultWithDistance> findNearbyEstablishments(double latitude, double longitude, int precision, int limit);
}
