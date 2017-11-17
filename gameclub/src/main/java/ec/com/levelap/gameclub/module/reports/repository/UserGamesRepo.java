package ec.com.levelap.gameclub.module.reports.repository;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.reports.entity.UserGames;

@Repository
public interface UserGamesRepo extends JpaRepository<UserGames, Long> {

//	public List<UserGames> findAllByOrderByName();
}
