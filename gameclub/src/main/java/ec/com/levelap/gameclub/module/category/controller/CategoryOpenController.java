package ec.com.levelap.gameclub.module.category.controller;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.category.entity.Category;
import ec.com.levelap.gameclub.module.category.service.CategoryService;

@RestController
@RequestMapping(value="open/category", produces=MediaType.APPLICATION_JSON_VALUE)
public class CategoryOpenController {
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="findAll", method=RequestMethod.GET)
	public ResponseEntity<List<Category>> findAll() throws ServletException {
		List<Category> categories = categoryService.getCategoryRepo().findAllByOrderByName();
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
}
