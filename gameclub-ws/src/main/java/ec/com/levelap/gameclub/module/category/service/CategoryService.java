package ec.com.levelap.gameclub.module.category.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ec.com.levelap.archive.Archive;
import ec.com.levelap.archive.ArchiveService;
import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.entity.FileData;
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
	
	@Autowired
	private ArchiveService archiveService;
	
	@Transactional
	public ResponseEntity<?> save(Category category, MultipartFile whiteVector, MultipartFile blackVector) throws ServletException, IOException {
		if (category.getId() == null) {
			Category found = categoryRepo.findByName(category.getName());
			
			if (found != null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("La categoría ingresada ya existe", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			Category original = categoryRepo.findOne(category.getId());
			Category found = categoryRepo.findByNameAndNameIsNot(category.getName(), original.getName());
			
			if (found != null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("La categoría ingresada ya existe", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		if (whiteVector != null) {
			Archive archive = new Archive();
			
			if (category.getId() != null) {
				Category original = categoryRepo.findOne(category.getId());
				archiveService.deleteFile(original.getWhiteVector().getName(), Category.class.getSimpleName());
				archive = original.getWhiteVector();
			}
			
			FileData fileData = archiveService.saveFile(whiteVector, Category.class.getSimpleName());
			
			archive.setModule(Category.class.getSimpleName());
			archive.setName(fileData.getName());
			archive.setType(whiteVector.getContentType());
			category.setWhiteVector(archive);
		}
		
		if (blackVector != null) {
			Archive archive = new Archive();
			
			if (category.getId() != null) {
				Category original = categoryRepo.findOne(category.getId());
				archiveService.deleteFile(original.getBlackVector().getName(), Category.class.getSimpleName());
				archive = original.getBlackVector();
			}
			
			FileData fileData = archiveService.saveFile(blackVector, Category.class.getSimpleName());
			
			archive.setModule(Category.class.getSimpleName());
			archive.setName(fileData.getName());
			archive.setType(blackVector.getContentType());
			category.setBlackVector(archive);
		}
		
		categoryRepo.save(category);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public CategoryRepo getCategoryRepo() {
		return categoryRepo;
	}
}
