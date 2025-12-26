package org.example.utils;

import java.util.Map;

public class LatLangRepository {
    private Map<Places, LatLang> placesToLatLangMap;
    public LatLangRepository() {
        placesToLatLangMap = Map.of(Places.CUBBON_PARK, new LatLang(12.9779, 77.5952),
                Places.ULSOOR_LAKE, new LatLang(12.9832, 77.6200));
    }
    public LatLang getLatLangBasedOnPlace(Places place) {
        return placesToLatLangMap.get(place);
    }
}
