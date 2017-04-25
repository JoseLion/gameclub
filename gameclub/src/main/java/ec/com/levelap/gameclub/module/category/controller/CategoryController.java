package ec.com.levelap.gameclub.module.category.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.gameclub.module.category.entity.Category;
import ec.com.levelap.gameclub.module.category.service.CategoryService;

@RestController
@RequestMapping(value="api/category", produces=MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value="findAll", method=RequestMethod.GET)
	public ResponseEntity<List<Category>> findAll() throws ServletException {
		List<Category> categories = categoryService.getCategoryRepo().findAllByOrderByName();
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<Category> findOne(@PathVariable Long id) throws ServletException {
		Category category = categoryService.getCategoryRepo().findOne(id);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> save(@RequestPart Category category, @RequestPart(required=false) MultipartFile whiteVector, @RequestPart(required=false) MultipartFile blackVector) throws ServletException, IOException {
		return categoryService.save(category, whiteVector, blackVector);
	}
	
	@RequestMapping(value="changeStatus/{id}", method=RequestMethod.GET)
	public ResponseEntity<Boolean> changeStatus(@PathVariable Long id) throws ServletException {
		Category category = categoryService.getCategoryRepo().findOne(id);
		category = categoryService.changeStatus(category);
		category = categoryService.getCategoryRepo().save(category);
		
		return new ResponseEntity<Boolean>(category.getStatus(), HttpStatus.OK);
	}
}
