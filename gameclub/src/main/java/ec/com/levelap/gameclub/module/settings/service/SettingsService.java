/**
 * 
 */
package ec.com.levelap.gameclub.module.settings.service;


import org.springframework.stereotype.Service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ec.com.levelap.base.entity.ErrorControl;
import org.springframework.http.HttpStatus;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.settings.entity.Settings;
import ec.com.levelap.gameclub.module.settings.repository.SettingsRepo;

// TODO: Auto-generated Javadoc
/**
 * The Class SettingsService.
 *
 * @author Levelap
 */
@Service
public class SettingsService extends BaseService<Settings> {

	/** The settings repo. */
	@Autowired
	private SettingsRepo settingsRepo;
	
	/**
	 * Instantiates a new settings service.
	 */
	public SettingsService() {
		super(Settings.class);
	}
	
	/**
	 * Save.
	 *
	 * @param settings the settings
	 * @return the response entity
	 */
	@Transactional
	public ResponseEntity<?> save(Settings settingObj) throws ServletException, IOException{
		if(settingObj.getId() == null) {
			Settings found = settingsRepo.findByCode(settingObj.getCode());
			if(found != null) {
				return new ResponseEntity<ErrorControl>(new ErrorControl("Parámetro ya existe ", true), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		settingsRepo.save(settingObj);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	

	/**
	 * Gets the settings repo.
	 *
	 * @return the settingsRepo
	 */
	public SettingsRepo getSettingsRepo() {
		return settingsRepo;
	}

	/**
	 * Sets the settings repo.
	 *
	 * @param settingsRepo the settingsRepo to set
	 */
	public void setSettingsRepo(SettingsRepo settingsRepo) {
		this.settingsRepo = settingsRepo;
	}

}
