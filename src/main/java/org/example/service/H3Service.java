package org.example.service;

import com.uber.h3core.H3Core;
import com.uber.h3core.LengthUnit;
import com.uber.h3core.util.LatLng;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Venues;
import org.example.repository.VenuesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class H3Service implements GeoService {
    private final H3Core h3core;
    private final VenuesRepository venuesRepository;

    public H3Service(H3Core h3core, VenuesRepository venuesRepository) {
        this.h3core = h3core;
        this.venuesRepository = venuesRepository;
    }

    @Override
    public long calculateDistanceInSteps(double latitude, double longitude, double latitude2, double longitude2, int precision) {
        long latLangCell1 = h3core.latLngToCell(latitude, longitude, precision);
        long latLangCell2 = h3core.latLngToCell(latitude, longitude, precision);
        return h3core.gridDistance(latLangCell1, latLangCell2);
    }

    @Override
    public double calculateDistanceInMeters(double latitude, double longitude, double latitude2, double longitude2, int precision) {
        long latLangCell1 = h3core.latLngToCell(latitude, longitude, precision);
        long latLangCell2 = h3core.latLngToCell(latitude, longitude, precision);
        LatLng latLng1 = h3core.cellToLatLng(latLangCell1);
        LatLng latLng2 = h3core.cellToLatLng(latLangCell2);
        return h3core.greatCircleDistance(latLng1, latLng2, LengthUnit.m);
    }

    @Override
    public List<String> findNearbyEstablishments(double latitude, double longitude, int precision, int limit) {
        long h3Index = h3core.latLngToCell(latitude, longitude, precision);
        List<Long> nearByIndexes = h3core.gridDisk(h3Index, 3);
        log.info("nearyByIndexes: {}", nearByIndexes);
        List<Venues> allById = venuesRepository.findAllByH3IndexIn(nearByIndexes);
        return allById.stream().map(Venues::getName).collect(Collectors.toList());
    }
}
