package ec.com.levelap.gameclub.module.message.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
	@Query(	"SELECT m FROM Message m " +
				"LEFT JOIN m.fromUser f " +
				"LEFT JOIN m.toUser t " +
			" WHERE " +
				"m.owner=:owner AND " +
				"((f IS NOT NULL AND UPPER(f.name || ' ' || f.lastName) LIKE UPPER('%' || :text || '%')) OR " +
				"(t IS NOT NULL AND UPPER(t.name || ' ' || t.lastName) LIKE UPPER('%' || :text || '%')) OR " +
				"UPPER(m.subject) LIKE UPPER('%' || :text || '%') OR " +
				"(f IS NULL AND t IS NULL AND UPPER('Game Club') LIKE UPPER('%' || :text || '%'))) " +
			"ORDER BY m.date DESC")
	public Page<Message> findMessages(@Param("owner") PublicUser owner, @Param("text") String text, Pageable page);
}
