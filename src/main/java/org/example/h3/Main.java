package org.example.h3;

import com.uber.h3core.H3Core;
import com.uber.h3core.LengthUnit;
import com.uber.h3core.util.LatLng;
import org.example.utils.LatLang;
import org.example.utils.LatLangRepository;
import org.example.utils.Places;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            H3Core h3Core = H3Core.newInstance();
            LatLangRepository repository = new LatLangRepository();
            LatLang cubbonLatLang = repository.getLatLangBasedOnPlace(Places.CUBBON_PARK);
            LatLang ulsoorLatLang = repository.getLatLangBasedOnPlace(Places.ULSOOR_LAKE);

            // res 9 is 0.1 km2 manageable resolution for approximation.
            long cellIndex = h3Core.latLngToCell(cubbonLatLang.latitude(), cubbonLatLang.longitude(), 9);
            long cellIndex1 = h3Core.latLngToCell(ulsoorLatLang.latitude(), ulsoorLatLang.longitude(), 9);

            long distanceInSteps = h3Core.gridDistance(cellIndex, cellIndex1);

            LatLng latLng = h3Core.cellToLatLng(cellIndex);
            LatLng latLng1 = h3Core.cellToLatLng(cellIndex1);

            double distanceInMeters = h3Core.greatCircleDistance(latLng, latLng1, LengthUnit.m);

            System.out.println("distance in steps:"+distanceInSteps);
            System.out.println("distance in meters:"+distanceInMeters);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
