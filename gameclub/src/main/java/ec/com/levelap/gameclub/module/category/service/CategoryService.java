package ec.com.levelap.gameclub.module.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.category.entity.Category;
import ec.com.levelap.gameclub.module.category.repository.CategoryRepo;

@Service
public class CategoryService extends BaseService<Category> {
	public CategoryService() {
		super(Category.class);
	}
	
	@Autowired
	private CategoryRepo categoryRepo;

	public CategoryRepo getCategoryRepo() {
		return categoryRepo;
	}
}
