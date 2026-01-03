package org.example.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name="places")
@Data
@Entity
public class Places {
    @Id
    private long id;
    private String name;
    private double latitude;
    private double longitude;
    @Column(name="geoHash")
    private String geoHash;
    @Column(name="h3Index")
    private long h3Index;
}
