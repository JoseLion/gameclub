package ec.com.levelap.gameclub.module.settings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.settings.entity.Setting;

@Repository
public interface SettingRepo extends JpaRepository<Setting, Long> {
	@Query("SELECT DISTINCT s.category FROM Setting s")
	public List<Object> findSettingsCategories();
	
	@Query("SELECT s FROM Setting s WHERE s.category='MULTAS'")
	public List<Setting> findFines();
	
	@Query("SELECT s FROM Setting s WHERE s.category like 'PRICE CHARTING'")
	public List<Setting> findPriceCharting();
	
	@Query("SELECT s FROM Setting s WHERE s.category='SHIPPING'")
	public List<Setting> findShipping();
	
	public Setting findByCode(String code);
	
	@Query("SELECT s FROM Setting s ORDER BY s.category")
	public List<Setting> findAll();
}
