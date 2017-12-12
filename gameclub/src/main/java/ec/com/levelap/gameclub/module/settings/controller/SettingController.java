package ec.com.levelap.gameclub.module.settings.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ec.com.levelap.gameclub.module.settings.entity.Setting;
import ec.com.levelap.gameclub.module.settings.service.SettingService;

import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping(value="api/settings", produces=MediaType.APPLICATION_JSON_VALUE)
public class SettingController {
	@Autowired
	private SettingService settingsService;
	
	@RequestMapping(value="findAll", method=RequestMethod.GET)
	public ResponseEntity<List<Setting>> findAll() throws ServletException{
		List<Setting> settingsList = settingsService.getSettingsRepo().findAll(new Sort("category", "name"));
		return new ResponseEntity<List<Setting>>(settingsList, HttpStatus.OK);
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody List<Setting> settingObj) throws ServletException, IOException{
		return settingsService.save(settingObj);
	}
	
	@RequestMapping(value="findOne/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> findOne(@PathVariable Long id) throws ServletException{
		Setting settingObj = settingsService.getSettingsRepo().findOne(id);
		return new ResponseEntity<Setting>(settingObj, HttpStatus.OK);
	}
	
	@RequestMapping(value="listCategories", method=RequestMethod.GET)
	public ResponseEntity<List<SettingsCategories>> listSettingCategories() {
		List<SettingsCategories> setCat = new ArrayList<SettingsCategories>();
		List<Object> setCatPar = settingsService.getSettingsRepo().findSettingsCategories();
		for(Object obj : setCatPar) {
			setCat.add(new SettingsCategories(obj.toString()));
		}
		return new ResponseEntity<List<SettingsCategories>>(setCat, HttpStatus.OK);
	}
	
	@RequestMapping(value="priceChartings", method=RequestMethod.GET)
	public ResponseEntity<List<Setting>> priceChartings() {
		List<Setting> priceChartingList = settingsService.getSettingsRepo().findPriceCharting();
		return new ResponseEntity<List<Setting>>(priceChartingList, HttpStatus.OK);
	}
	
	@RequestMapping(value="findOneByCode/{code}", method=RequestMethod.GET)
	public ResponseEntity<?> findOne(@PathVariable String code) throws ServletException{
		Setting setting = settingsService.getSettingsRepo().findByCode(code);
		return new ResponseEntity<>(setting, HttpStatus.OK);
	}
	
	private class SettingsCategories{
		@SuppressWarnings("unused")
		public String category;
		
		public SettingsCategories(String categoriesList) {
			this.category = categoriesList;
		}
	}
}
