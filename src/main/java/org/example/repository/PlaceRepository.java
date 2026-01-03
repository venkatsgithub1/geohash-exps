package org.example.repository;

import org.example.model.entities.Places;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Places, Long> {
}
