package org.example.repository;

import org.example.model.Venues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenuesRepository extends JpaRepository<Venues, Long> {
    List<Venues> findAllByH3IndexIn(List<Long> h3Indexes);
}
