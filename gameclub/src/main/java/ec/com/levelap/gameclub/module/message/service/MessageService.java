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
import ec.com.levelap.tcc.entity.wsDespachos.GrabarDespacho4;
import ec.com.levelap.tcc.entity.wsDespachos.GrabarDespacho4Response;
import ec.com.levelap.tcc.entity.wsDespachos.TpDocumentoReferencia;
import ec.com.levelap.tcc.entity.wsDespachos.TpGrabarRemesaCompleta;
import ec.com.levelap.tcc.entity.wsDespachos.TpUnidad;
import ec.com.levelap.tcc.service.TccService;

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
		request.setRemesa("N/A");
		request.setRespuesta(0);
		request.setMensaje("N/A");
		request.setURLRelacionEnvio("N/A");
		request.setURLRotulos("N/A");
		
		TpGrabarRemesaCompleta delivery = new TpGrabarRemesaCompleta();
		delivery.setClave(tccKey);
		delivery.setUnidadnegocio("1");
		Date today = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		delivery.setFechadespacho(df.format(today)); // PREGUNTAR
		delivery.setCuentaremitente(tccAccount);
		delivery.setSederemitente("1"); // PREGUNTAR
		delivery.setRazonsocialremitente("Tomorrowlabs"); // PREGUNTAR
		delivery.setNaturalezaremitente("J");
		delivery.setTipoidentificacionremitente("NIT");
		delivery.setIdentificacionremitente(tccNit);
		delivery.setTelefonoremitente("0967995408");
		delivery.setDireccionremitente("DIRECCION GAME CLUB"); // PREGUNTAR
		delivery.setCiudadorigen("17001050");
		delivery.setNumeroReferenciaCliente("N/A");
		delivery.setGenerarDocumentos(false);
		
		PublicUser currentUser = publicUserService.getCurrentUser();
		
		delivery.setTipoidentificaciondestinatario("N");
		delivery.setIdentificaciondestinatario(currentUser.getDocument()); // **************** DEL OBJ **************
		delivery.setPrimernombredestinatario(currentUser.getName()); // **************** DEL OBJ **************
		delivery.setSegundonombredestinatario("N/A");
		delivery.setPrimerapellidodestinatario(currentUser.getLastName()); // **************** DEL OBJ **************
		delivery.setSegundoapellidodestinatario("N/A");
		delivery.setRazonsocialdestinatario(currentUser.getName() + " " + currentUser.getLastName()); // **************** DEL OBJ **************
		delivery.setNaturalezadestinatario("N");
		delivery.setDirecciondestinatario(confirmObj.address); // **************** DEL OBJ **************
		delivery.setTelefonodestinatario(confirmObj.phone); // **************** DEL OBJ **************
		delivery.setCiudaddestinatario(confirmObj.city); // **************** DEL OBJ **************
		delivery.setObservaciones("El paquede debe ser entregado a " + confirmObj.receiver);
		
		delivery.setLlevabodega("NO"); // PREGUNTAR
		delivery.setRecogebodega("NO"); // PREGUNTAR
		
		TpUnidad unit = new TpUnidad();
		unit.setTipounidad("TIPO_UND_PAQ");
		unit.setClaseempaque("CLEM_CAJA"); // PREGUNTAR
		unit.setTipoempaque("N/A"); //PREGUNTAR
		unit.setDicecontener("Game Club Welcome Kit"); // PREGUNTAR
		unit.setCantidadunidades("1");
		unit.setKilosreales("0.5"); // PREGUNTAR
		unit.setLargo("20"); // PREGUNTAR
		unit.setAlto("20"); // PREGUNTAR
		unit.setAncho("20"); // PREGUNTAR
		unit.setCodigobarras("N/A"); // PREGUNTAR
		unit.setNumerobolsa("N/A"); // PREGUNTAR
		unit.setReferencias("N/A"); // PREGUNTAR
		unit.setUnidadesinternas("N/A"); // PREGUNTAR
		
		delivery.getUnidad().add(unit);
		/*TpDocumentoReferencia doc = new TpDocumentoReferencia();
		doc.setNumerodocumento("N/A");
		delivery.getDocumentoreferencia().add(doc); // PREGUNTAR*/
		
		request.setObjDespacho(delivery);
		GrabarDespacho4Response response = tccService.saveDelivery(request);
		
		WelcomeKit welcomeKit = welcomeKitRepo.findOne(confirmObj.kitId);
		welcomeKit.setWasConfirmed(true);
		welcomeKit.setDeliveryNumber(response.getRemesa());
		welcomeKitRepo.save(welcomeKit);
		
		return response;
	}
	
	public MessageRepo getMessageRepo() {
		return messageRepo;
	}

	public WelcomeKitRepo getWelcomeKitRepo() {
		return welcomeKitRepo;
	}
}