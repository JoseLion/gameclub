package ec.com.levelap.gameclub.module.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.mail.entity.Mail;

@Repository
public interface MailRepo extends JpaRepository<Mail, Long> {
	public Mail findByAcronym(String acronym);
}
