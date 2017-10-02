/**
 * 
 */
package ec.com.levelap.gameclub.module.settings.service;


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
	public ResponseEntity<?> save(List<Settings> settingObj) throws ServletException, IOException{
		if(settingObj.size() > 0) {
			for (Settings settings : settingObj) {
				settingsRepo.save(settings);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<ErrorControl>(new ErrorControl("Parámetro ya existe ", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
