package ec.com.levelap.gameclub.module.message.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.message.entity.Message;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
	@Query(	"SELECT m FROM Message m " +
				"LEFT JOIN m.sender s " +
			" WHERE " +
				"(m.sender IN NOT NULL AND UPPER(s.name || ' ' || s.lastname) LIKE UPPER('%' || :text || '%')) OR " +
				"UPPER(m.subject) LIKE UPPER('%' || :text || '%') OR " +
				"(m.sender IS NULL AND UPPER('Game Club') LIKE UPPER('%' || :text || '%')) " +
			"ORDER BY m.creationDate DESC")
	public Page<Message> findMessages(@Param("text") String text, Pageable page);
}
