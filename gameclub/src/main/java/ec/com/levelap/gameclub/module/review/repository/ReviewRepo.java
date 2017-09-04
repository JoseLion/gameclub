package ec.com.levelap.gameclub.module.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.review.entity.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
	
}
