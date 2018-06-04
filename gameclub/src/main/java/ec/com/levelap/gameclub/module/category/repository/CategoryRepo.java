package ec.com.levelap.gameclub.module.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.category.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
	public List<Category> findAllByOrderByName();
	
	public List<Category> findAllByOrderByIdAsc();
	
	public Category findByName(String name);
	
	public Category findByNameAndNameIsNot(String name, String notName);
	
	public List<Category> findByStatusIsTrueOrderByName();
}
