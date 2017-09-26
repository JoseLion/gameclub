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
	public Page<Review> findReviewsOfUser(@Param("id") Long id, Pageable page);
	
	public Long countByGamerAcceptedIsTrueAndLoanGamerId(Long gamerId);
	
	public Long countByLenderAcceptedIsTrueAndLoanPublicUserGamePublicUserId(Long lenderId);
	
	@Query(value="SELECT AVG(r.gamer_score) FROM gameclub.review r LEFT JOIN gameclub.loan l ON r.loan=l.id WHERE r.gamer_accepted=TRUE AND l.gamer=?1", nativeQuery=true)
	public Double getGamerAverageScore(Long gamerId);
	
	@Query(value="SELECT AVG(r.lender_score) FROM gameclub.review r LEFT JOIN gameclub.loan l ON r.loan=l.id LEFT JOIN gameclub.public_user_game pg ON l.public_user_game=pg.id WHERE r.lender_accepted=TRUE AND pg.public_user=?1", nativeQuery=true)
	public Double getLenderAverageScore(Long lenderId);
}
