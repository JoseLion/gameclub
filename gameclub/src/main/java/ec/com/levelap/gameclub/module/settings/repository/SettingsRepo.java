package ec.com.levelap.gameclub.module.settings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.settings.entity.Settings;

// TODO: Auto-generated Javadoc
/**
 * The Interface SettingsRepo.
 */
@Repository
public interface SettingsRepo extends JpaRepository<Settings, Long> {

	@Query("SELECT distinct s.category FROM Settings s ")
	public List<Object> findSettingsCategories();
	
	@Query("SELECT s FROM Settings s WHERE s.category='MULTAS' ")
	public List<Settings> findFines();
	
	@Query("SELECT s FROM Settings s WHERE s.category like 'PRICE CHARTING' ")
	public List<Settings> findPriceCharting();
	
	@Query("SELECT s FROM Settings s WHERE s.category='SHIPPING' ")
	public List<Settings> findShipping();
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the settings
	 */
	public Settings findByCode(String code);
	
	/* (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.JpaRepository#findAll()
	 */
	@Query("SELECT s FROM Settings s ORDER BY s.category ")
	public List<Settings> findAll();
	
	
	
}
