package org.example.service;

import com.uber.h3core.H3Core;
import com.uber.h3core.LengthUnit;
import com.uber.h3core.util.LatLng;
import lombok.extern.slf4j.Slf4j;
import org.example.model.ResultWithDistance;
import org.example.model.entities.Venues;
import org.example.repository.VenuesRepository;
import org.example.utils.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<ResultWithDistance> findNearbyEstablishments(double latitude, double longitude, int precision, int limit) {
        long h3Index = h3core.latLngToCell(latitude, longitude, precision);
        int k = 1;
        int maxK = 13;
        List<Venues> venuesFetched = new ArrayList<>();
        List<ResultWithDistance> results = new ArrayList<>();
        while (venuesFetched.isEmpty() && k <= maxK) {
            List<Long> nearByIndexes = h3core.gridDisk(h3Index, k);
            log.info("nearyByIndexes: {}", nearByIndexes);
            venuesFetched = venuesRepository.findAllByH3IndexIn(nearByIndexes);
            final int kUsed = k;
            venuesFetched.forEach(venueFetched -> results.add(new ResultWithDistance(venueFetched.getName(),
                    CommonUtils.getEstimatedH3Distance(kUsed, 9))));
            k++;
        }
        return results;
    }
}
