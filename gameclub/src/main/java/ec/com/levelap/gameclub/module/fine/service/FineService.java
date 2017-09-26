/**
 * 
 */
package ec.com.levelap.gameclub.module.fine.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ec.com.levelap.base.entity.ErrorControl;
import org.springframework.http.HttpStatus;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.fine.entity.Fine;
import ec.com.levelap.gameclub.module.fine.repository.FineRepo;

/**
 * @author Levelap
 *
 */
@Service
public class FineService extends BaseService<Fine> {
	
	public FineService() {
		super(Fine.class);
	}

	@Autowired
	private FineRepo fineRepo;
	
	@Transactional
	public ResponseEntity<?> save(Fine fineObj) throws ServletException, IOException{
		if(fineObj.getId() == null) {
			Fine found = fineRepo.findOne(fineObj.getId());
			if(found != null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("Parámetro ya existe ", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		fineRepo.save(fineObj);
		return new ResponseEntity<>(HttpStatus.OK);
	}

//	@Transactional
//	public ResponseEntity<?> saveList(List<Fine> fines) throws ServletException, IOException{
//		if(fines.size() == 0) {
//			List<Fine> founds = fineRepo.findOne(fineObj.getId());
//			if(found != null) {
//				return new ResponseEntity<ErrorControl>(new ErrorControl("Parámetro ya existe ", true), HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		}
//		fineRepo.save(fineObj);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
	/**
	 * @return the fineRepo
	 */
	public FineRepo getFineRepo() {
		return fineRepo;
	}

	/**
	 * @param fineRepo the fineRepo to set
	 */
	public void setFineRepo(FineRepo fineRepo) {
		this.fineRepo = fineRepo;
	}
	
	
}
