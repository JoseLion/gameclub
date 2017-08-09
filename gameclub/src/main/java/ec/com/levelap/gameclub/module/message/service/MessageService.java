package ec.com.levelap.gameclub.module.message.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ec.com.levelap.base.service.BaseService;
import ec.com.levelap.gameclub.module.message.controller.MessageController.ConfirmObj;
import ec.com.levelap.gameclub.module.message.entity.Message;
import ec.com.levelap.gameclub.module.message.entity.WelcomeKit;
import ec.com.levelap.gameclub.module.message.repository.MessageRepo;
import ec.com.levelap.gameclub.module.message.repository.WelcomeKitRepo;
import ec.com.levelap.gameclub.module.user.entity.PublicUser;
import ec.com.levelap.gameclub.module.user.service.PublicUserService;
import ec.com.levelap.gameclub.utils.Const;
import ec.com.levelap.tcc.service.TccService;
import ec.com.levelap.tcc.wsdl.clientes.GrabarDespacho4;
import ec.com.levelap.tcc.wsdl.clientes.GrabarDespacho4Response;
import ec.com.levelap.tcc.wsdl.xsd.TpGrabarRemesaCompleta;
import ec.com.levelap.tcc.wsdl.xsd.TpUnidad;

@Service
public class MessageService extends BaseService<Message> {
	public MessageService() {
		super(Message.class);
	}
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private WelcomeKitRepo welcomeKitRepo;
	
	@Autowired
	private PublicUserService publicUserService;
	
	@Autowired
	private TccService tccService;
	
	@Value("${tcc-configuration.key}")
	private String tccKey;
	
	@Value("${tcc-configuration.account}")
	private String tccAccount;
	
	@Value("${tcc-configuration.nit}")
	private String tccNit;
	
	@Transactional
	public void requestWelcomeKit(PublicUser user, Message message) {
		if (message == null) {
			message = new Message();
			message.setIsPromo(true);
			message.setOwner(user);
			message.setSubject(Const.SBJ_WELCOME_KIT);
			messageRepo.save(message);
		}
		
		
		WelcomeKit welcomeKit = new WelcomeKit();
		welcomeKit.setPublicUser(user);
		welcomeKit.setMessage(message);
		welcomeKitRepo.save(welcomeKit);
	}
	
	@Transactional
	public GrabarDespacho4Response confirmWelcomeKit(ConfirmObj confirmObj) throws ServletException {
		GrabarDespacho4 request = new GrabarDespacho4();
		TpGrabarRemesaCompleta delivery = new TpGrabarRemesaCompleta();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		PublicUser currentUser = publicUserService.getCurrentUser();
		
		delivery.setClave(tccKey);
		delivery.setUnidadnegocio("1");
		delivery.setFechadespacho(df.format(new Date()));
		delivery.setCuentaremitente(tccAccount);
		delivery.setRazonsocialremitente("Tomorrowlabs"); //PREGUNTAR
		delivery.setNaturalezaremitente("J");
		delivery.setTipoidentificacionremitente("NIT");
		delivery.setIdentificacionremitente(tccNit);
		delivery.setTelefonoremitente("0967995408"); //PREGUNTAR
		delivery.setDireccionremitente("Tomorrowlabs EC - Cumbaya"); //PREGUNTAR
		delivery.setCiudadorigen("17001057"); //PREGUNTAR
		
		delivery.setTipoidentificaciondestinatario("CC");
		delivery.setIdentificaciondestinatario(currentUser.getDocument());
		delivery.setPrimernombredestinatario(currentUser.getName());
		delivery.setSegundonombredestinatario("");
		delivery.setPrimerapellidodestinatario(currentUser.getLastName());
		delivery.setSegundoapellidodestinatario("");
		delivery.setRazonsocialdestinatario("");
		delivery.setNaturalezadestinatario("N");
		delivery.setDirecciondestinatario(confirmObj.address);
		delivery.setTelefonodestinatario(confirmObj.phone);
		delivery.setCiudaddestinatario(confirmObj.city);
		delivery.setObservaciones("Entregar a " + confirmObj.receiver);
		
		TpUnidad unit = new TpUnidad();
		unit.setTipounidad("TIPO_UND_PAQ");
		unit.setClaseempaque("CLEM_CAJA");
		unit.setCantidadunidades("1");
		unit.setKilosreales("1"); //PREGUNTAR
		unit.setPesovolumen("1"); //PREGUNTAR
		unit.setValormercancia("20"); //PREGUNTAR
		
		delivery.getUnidad().add(unit);
		delivery.setGenerarDocumentos(false);
		
		request.setObjDespacho(delivery);
		GrabarDespacho4Response response = tccService.saveDelivery(request);
		
		if (response.getRespuesta() == 0) {
			WelcomeKit welcomeKit = welcomeKitRepo.findOne(confirmObj.kitId);
			welcomeKit.setWasConfirmed(true);
			welcomeKit.setDeliveryNumber(response.getRemesa());
			welcomeKitRepo.save(welcomeKit);
		}
		
		return response;
	}
	
	public MessageRepo getMessageRepo() {
		return messageRepo;
	}

	public WelcomeKitRepo getWelcomeKitRepo() {
		return welcomeKitRepo;
	}
}