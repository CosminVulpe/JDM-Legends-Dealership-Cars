package com.example.legend.service.repository;

import com.example.legend.service.dto.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM review ORDER BY id DESC LIMIT 5" , nativeQuery = true)
    List<Review> getRecentReviews();

}
