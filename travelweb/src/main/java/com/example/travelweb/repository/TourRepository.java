package com.example.travelweb.repository;

import com.example.travelweb.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findByAvailabilityTrue();

    List<Tour> findByTourIDIn(List<Long> tourIds);

    @Query("SELECT t FROM Tour t WHERE " +
            "(:minPrice IS NULL OR t.priceAdult >= :minPrice) AND " +
            "(:maxPrice IS NULL OR t.priceAdult <= :maxPrice) AND " +
            "(:domain IS NULL OR t.domain = :domain) AND " +
            "(:duration IS NULL OR t.duration = :duration) AND " +
            "(:tourIds IS NULL OR t.tourID IN :tourIds)")
    List<Tour> filterTours(
            @Param("minPrice") Long minPrice,
            @Param("maxPrice") Long maxPrice,
            @Param("domain") String domain,
            @Param("duration") String duration,
            @Param("tourIds") List<Long> tourIds);

    @Query("SELECT t FROM Tour t WHERE " +
            "(:destination IS NULL OR t.destination = :destination) AND " +
            "(:startDate IS NULL OR t.startDate >= :startDate) AND " +
            "(:endDate IS NULL OR t.endDate <= :endDate)")
    List<Tour> findToursByCriteria(
            @Param("destination") String destination,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

}
