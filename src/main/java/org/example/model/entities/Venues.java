package org.example.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name="venues")
@Data
@Entity
public class Venues {
    @Id
    private long id;
    private String name;
    private double latitude;
    private double longitude;
    private double rating;
    private String category;
    @Column(name="geoHash")
    private String geoHash;
    @Column(name="h3Index")
    private long h3Index;
}
