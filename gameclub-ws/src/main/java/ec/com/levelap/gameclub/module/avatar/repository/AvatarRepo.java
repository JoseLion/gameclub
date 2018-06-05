package ec.com.levelap.gameclub.module.avatar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.avatar.entity.Avatar;

@Repository
public interface AvatarRepo extends JpaRepository<Avatar, Long> {

	public List<Avatar> findByOrderByStatusDescCreationDateAsc();

	public List<Avatar> findByStatusIsTrueOrderByStatusDescCreationDateAsc();

}
