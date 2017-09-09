package ec.com.levelap.gameclub.module.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.review.entity.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
	@Query(	"SELECT r FROM Review r WHERE " +
				"r.loan.gamer.id=:id OR " +
				"r.loan.publicUserGame.publicUser.id=:id " +
			"ORDER BY r.creationDate DESC")
	Page<Review> findReviewsOfUser(@Param("id") Long id, Pageable page);
}
