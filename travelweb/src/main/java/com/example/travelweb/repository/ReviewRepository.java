package com.example.travelweb.repository;

import com.example.travelweb.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByTourTourID(Long tourId);

    // Tính trung bình sao theo tourID
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.tour.tourID = :tourId")
    Double findAverageRatingByTourId(@Param("tourId") Long tourId);

    // Lấy danh sách tourID có trung bình sao bằng giá trị mong muốn
    @Query("SELECT r.tour.tourID FROM Review r GROUP BY r.tour.tourID HAVING ROUND(AVG(r.rating)) = :star")
    List<Long> findTourIdsByAverageRating(@Param("star") Integer star);

}
