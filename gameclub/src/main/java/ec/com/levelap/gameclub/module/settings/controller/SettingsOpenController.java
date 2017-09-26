/**
 * 
 */
package ec.com.levelap.gameclub.module.settings.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.settings.entity.Settings;
import ec.com.levelap.gameclub.module.settings.service.SettingsService;


/**
 * @author Levelap
 *
 */
@RestController
@RequestMapping(value="open/settings", produces=MediaType.APPLICATION_JSON_VALUE)
public class SettingsOpenController {
	
	@Autowired
	private SettingsService settingsService;

	@RequestMapping(value="findAll", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Settings>> findAll() throws ServletException {
		List<Settings> settingList = settingsService.getSettingsRepo().findAll();
		Map<String, Settings> settingsList = new HashMap<>();
		for (Settings setting : settingList) {
			settingsList.put(setting.getCode(), setting);
		}
		return new ResponseEntity<Map<String, Settings>>(settingsList, HttpStatus.OK);
	}
	
	
	/**
	 * @return the settingsService
	 */
	public SettingsService getSettingsService() {
		return settingsService;
	}

	/**
	 * @param settingsService the settingsService to set
	 */
	public void setSettingsService(SettingsService settingsService) {
		this.settingsService = settingsService;
	}
	
	
	
	

}
