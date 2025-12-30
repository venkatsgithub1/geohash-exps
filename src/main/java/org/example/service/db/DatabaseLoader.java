package org.example.service.db;

import ch.hsr.geohash.GeoHash;
import com.uber.h3core.H3Core;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Venue;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DatabaseLoader {
    private static final String URL = "jdbc:h2:file:./data/nearby_places_db;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private final ObjectMapper objectMapper;
    private final H3Core h3core;


    public DatabaseLoader(ObjectMapper objectMapper, H3Core h3core) {
        this.objectMapper = objectMapper;
        this.h3core = h3core;
    }

    public void loadDataIfNeeded() {
        loadPlacesData();
        loadVenuesData();
    }

    private void loadVenuesData() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String createTable = "CREATE TABLE IF NOT EXISTS venues " +
                    "(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), latitude DECIMAL(13, 10), " +
                    "longitude DECIMAL(13, 10), rating DECIMAL(13, 10), category VARCHAR(255), geoHash VARCHAR(255), " +
                    "h3Index BIGINT)";
            String createH3Index = "CREATE INDEX IF NOT EXISTS idx_venues_h3 ON venues(h3Index)";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTable);
                stmt.execute(createH3Index);
            }

            // 2. Check if data exists
            boolean dataExists = false;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM venues")) {
                if (rs.next() && rs.getInt(1) > 0) {
                    dataExists = true;
                }
            }

            if (!dataExists) {
                try (Statement stmt = conn.createStatement();
                     BufferedReader bufferedReader = new BufferedReader(
                             new FileReader("./src/main/resources/output/venue_data.json"))) {
                    Map<String, Object> venueMap = objectMapper.readValue(bufferedReader, Map.class);
                    venueMap.keySet().forEach(category -> {

                        Object obj = venueMap.get(category);
                        List<Venue> venues = objectMapper.convertValue(obj, new TypeReference<>() {
                        });
                        venues.forEach(venue -> {
                            try {
                                double latitude = Double.parseDouble(venue.getLatitude());
                                double longitude = Double.parseDouble(venue.getLongitude());
                                String geoHashString = GeoHash.
                                        geoHashStringWithCharacterPrecision(latitude, longitude, 9);
                                long h3Index = h3core.latLngToCell(latitude, longitude, 9);
                                stmt.execute(String.format("INSERT INTO venues (name, latitude, longitude, rating, " +
                                                "category, geoHash, h3Index) VALUES('%s', %f, %f, %f, '%s', '%s', %d)",
                                        venue.getName().replace("'", "''"),
                                        latitude, longitude, Double.parseDouble(venue.getRating()), category,
                                        geoHashString, h3Index));
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadPlacesData() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // 1. Create Table (Safe to run every time with IF NOT EXISTS)
            String createTable = "CREATE TABLE IF NOT EXISTS places " +
                    "(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), latitude DECIMAL(13, 10), " +
                    "longitude DECIMAL(13, 10), geoHash VARCHAR(255), h3Index BIGINT)";
            String createH3Index = "CREATE INDEX IF NOT EXISTS idx_places_h3 ON places(h3Index)";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTable);
                stmt.execute(createH3Index);
            }

            // 2. Check if data exists
            boolean dataExists = false;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM places")) {
                if (rs.next() && rs.getInt(1) > 0) {
                    dataExists = true;
                }
            }

            // 3. Load Initial Data ONLY if table is empty
            if (!dataExists) {
                System.out.println("First time startup: Loading initial data...");
                try (Statement stmt = conn.createStatement()) {
                    try (BufferedReader bufferedReader = new BufferedReader(
                            new FileReader("./src/main/resources/output/place_data.csv"))) {
                        bufferedReader.lines().forEach(line -> {
                            String[] columns = line.split("~");
                            String name = columns[0];
                            double latitude = Double.parseDouble(columns[1]);
                            double longitude = Double.parseDouble(columns[2]);
                            String geoHashString = GeoHash.
                                    geoHashStringWithCharacterPrecision(latitude, longitude, 9);
                            long h3Index = h3core.latLngToCell(latitude, longitude, 9);
                            try {
                                stmt.execute(String.format("INSERT INTO places (name, latitude, longitude, geoHash, " +
                                                "h3Index) " +
                                                "VALUES('%s', %f, %f, '%s', %d)", name.replace("'", "''")
                                        , latitude, longitude, geoHashString, h3Index));
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                }
            } else {
                System.out.println("Data file found. Skipping initialization.");
            }

        } catch (SQLException | IOException e) {
            log.error(e.getMessage());
        }
    }
}
