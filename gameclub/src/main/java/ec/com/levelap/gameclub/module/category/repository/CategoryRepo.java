package ec.com.levelap.gameclub.module.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.com.levelap.gameclub.module.category.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
	
}
