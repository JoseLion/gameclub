package ec.com.levelap.gameclub.module.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.entity.PublicUserGame;

@Repository
public interface PublicUserGameRepo extends JpaRepository<PublicUserGame, Long> {
	public Page<PublicUserGame> findByPublicUserOrderByGameName(PublicUser publicUser, Pageable page);
}
