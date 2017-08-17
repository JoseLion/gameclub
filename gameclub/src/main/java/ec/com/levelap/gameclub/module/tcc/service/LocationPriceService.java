package ec.com.levelap.gameclub.module.tcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.levelap.gameclub.module.tcc.repository.LocationPriceRepo;

@Service
public class LocationPriceService {
	@Autowired
	private LocationPriceRepo locationPriceRepo;

	public LocationPriceRepo getLocationPriceRepo() {
		return locationPriceRepo;
	}
}
