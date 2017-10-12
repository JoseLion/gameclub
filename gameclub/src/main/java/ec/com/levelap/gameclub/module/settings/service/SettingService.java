package ec.com.levelap.gameclub.module.settings.service;


import java.util.List;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.entity.ErrorControl;
import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.settings.entity.Setting;
import ec.com.levelap.gameclub.module.settings.repository.SettingRepo;

@Service
public class SettingService extends BaseService<Setting> {
	@Autowired
	private SettingRepo settingsRepo;
	
	public SettingService() {
		super(Setting.class);
	}
	
	@Transactional
	public ResponseEntity<?> save(List<Setting> settingObj) throws ServletException {
		if(settingObj.size() > 0) {
			for (Setting settings : settingObj) {
				settingsRepo.save(settings);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<ErrorControl>(new ErrorControl("Parámetro ya existe ", true), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public String getSettingValue(String code) {
		Setting setting = settingsRepo.findByCode(code);
		return setting.getValue();
	}
	
	public SettingRepo getSettingsRepo() {
		return settingsRepo;
	}
}
