package ec.com.levelap.gameclub.module.messaging.controller;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.levelap.tcc.entity.wsrecogidas.ArrayOfClsRecogida;
import ec.com.levelap.tcc.entity.wsrecogidas.ClsRecogida;
import ec.com.levelap.tcc.entity.wsrecogidas.EstadoRecogidaResponse;
import ec.com.levelap.tcc.service.TccService;

@RestController
@RequestMapping(value="open/messaging", produces=MediaType.APPLICATION_JSON_VALUE)
public class MessagingOpenController {
	@Autowired
	private TccService tccService;
	
	@RequestMapping(value="pickupStatus/{id}/{office}", method=RequestMethod.GET)
	public ResponseEntity<EstadoRecogidaResponse> pickupStatus(@PathVariable Integer id, @PathVariable Integer office) throws ServletException {
		ArrayOfClsRecogida pickups = new ArrayOfClsRecogida();
		ClsRecogida pick = new ClsRecogida();
		pick.setIdRecogida(id.intValue());
		pick.setOficina(office.intValue());
		pickups.getClsRecogida().add(pick);
		
		//List<ClsRecogida> array = Arrays.asList(pick);
		//pickups.setClsRecogida(array);
		
		EstadoRecogidaResponse response = tccService.getPickupStatus(pickups);
		
		return new ResponseEntity<EstadoRecogidaResponse>(response, HttpStatus.OK);
	}
}
