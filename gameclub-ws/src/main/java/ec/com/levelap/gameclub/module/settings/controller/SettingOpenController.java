package ec.com.levelap.gameclub.module.settings.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.gameclub.module.settings.entity.Setting;
import ec.com.levelap.gameclub.module.settings.service.SettingService;

@RestController
@RequestMapping(value="open/settings", produces=MediaType.APPLICATION_JSON_VALUE)
public class SettingOpenController {
	@Autowired
	private SettingService settingsService;

	@RequestMapping(value="findAll", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Setting>> findAll() throws ServletException {
		List<Setting> settingList = settingsService.getSettingsRepo().findAll();
		Map<String, Setting> settingsList = new HashMap<>();
		for (Setting setting : settingList) {
			settingsList.put(setting.getCode(), setting);
		}
		return new ResponseEntity<Map<String, Setting>>(settingsList, HttpStatus.OK);
	}
	
	@RequestMapping(value="findByCode/{code}", method=RequestMethod.GET)
	public ResponseEntity<?> findByCode(@PathVariable String code) throws ServletException {
		Setting setting = settingsService.getSettingsRepo().findByCode(code);
		return new ResponseEntity<>(setting, HttpStatus.OK);
	}
}
