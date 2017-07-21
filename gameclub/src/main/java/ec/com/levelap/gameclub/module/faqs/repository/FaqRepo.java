package ec.com.levelap.gameclub.module.faqs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ec.com.levelap.commons.catalog.Catalog;
import ec.com.levelap.gameclub.module.faqs.entity.Faq;

public interface FaqRepo extends JpaRepository<Faq, Long> {
	@Query(	"SELECT f FROM Faq f WHERE " +
				"(UPPER(f.question) LIKE UPPER('%' || :text || '%') OR " +
				"UPPER(f.answer) LIKE UPPER('%' || :text || '%')) AND " +
				"(:category IS NULL OR f.category=:category) AND " +
				"(:status IS NULL OR f.status=:status) " +
			"ORDER BY f.category.name DESC, f.question DESC")
	public List<Faq> findFaqs(@Param("text") String text, @Param("category") Catalog category, @Param("status") Boolean status);
	
	public Faq findByQuestionIgnoreCase(String question);
	
	public Faq findByQuestionIgnoreCaseAndQuestionIsNotIgnoreCase(String question, String notQuestion);
}
