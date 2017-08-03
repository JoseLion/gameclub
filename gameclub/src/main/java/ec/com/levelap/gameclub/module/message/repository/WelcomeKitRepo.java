package ec.com.levelap.gameclub.module.message.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.message.entity.WelcomeKit;

@Repository
public interface WelcomeKitRepo extends JpaRepository<WelcomeKit, Long> {
	List<WelcomeKit> findByMessageIdOrderByCreationDateDesc(Long messageId);
}
