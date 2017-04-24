package ec.com.levelap.gameclub.module.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.category.service.CategoryService;

@RestController
@RequestMapping(value="api/category", produces=MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
}
