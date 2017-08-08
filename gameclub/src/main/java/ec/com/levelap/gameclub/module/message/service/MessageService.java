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
import ec.com.levelap.tcc.wsdl.xsd.TpDocumentoReferencia;
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
		
		delivery.setClave("BOGLINIO");
		delivery.setCodigolote("");
		delivery.setFechahoralote("");
		delivery.setNumeroremesa("");
		delivery.setNumeroDepacho("");
		delivery.setUnidadnegocio("1");
		delivery.setFechadespacho("2017-08-08");
		delivery.setCuentaremitente("1118100");
		delivery.setSederemitente("");
		delivery.setPrimernombreremitente("PRUEBAS CLIENTES");
		delivery.setSegundonombreremitente("");
		delivery.setPrimerapellidoremitente("");
		delivery.setSegundoapellidoremitente("");
		delivery.setRazonsocialremitente("PRUEBAS CLIENTES");
		delivery.setNaturalezaremitente("J");
		delivery.setTipoidentificacionremitente("NIT");
		delivery.setIdentificacionremitente("900499362");
		delivery.setTelefonoremitente("1111111");
		delivery.setDireccionremitente("ZONA FRANCA BODEGAS 13 Y 14");
		delivery.setCiudadorigen("11001000");
		
		delivery.setTipoidentificaciondestinatario("CC");
		delivery.setIdentificaciondestinatario("80186994");
		delivery.setSededestinatario("");
		delivery.setPrimernombredestinatario("HECTOR");
		delivery.setSegundonombredestinatario("");
		delivery.setPrimerapellidodestinatario("CANO");
		delivery.setSegundoapellidodestinatario("");
		delivery.setRazonsocialdestinatario("");
		delivery.setNaturalezadestinatario("N");
		delivery.setDirecciondestinatario("carrera 112A # 79 B-07");
		delivery.setTelefonodestinatario("3104835685");
		delivery.setCiudaddestinatario("11001000");
		delivery.setBarriodestinatario("");
		
		delivery.setTotalpeso("");
		delivery.setTotalpesovolumen("");
		delivery.setTotalvalormercancia("");
		delivery.setFormapago("");
		delivery.setObservaciones("PEDIDO BP 18664");
		delivery.setLlevabodega("");
		delivery.setRecogebodega("");
		delivery.setCentrocostos("");
		delivery.setTotalvalorproducto("");
		
		TpUnidad unit = new TpUnidad();
		unit.setTipounidad("TIPO_UND_PAQ");
		unit.setTipoempaque("");
		unit.setClaseempaque("CLEM_CAJA");
		unit.setDicecontener("");
		unit.setCantidadunidades("1");
		unit.setKilosreales("3");
		unit.setLargo("0");
		unit.setAlto("0");
		unit.setAncho("0");
		unit.setPesovolumen("3");
		unit.setValormercancia("63900");
		unit.setCodigobarras("");
		unit.setNumerobolsa("");
		unit.setReferencias("");
		
		delivery.getUnidad().add(unit);
		delivery.setNumeroReferenciaCliente("");
		delivery.setFuente("");
		delivery.setGenerarDocumentos(false);
		
		request.setObjDespacho(delivery);
		request.setRemesa("0");
		request.setRespuesta(0);
		request.setMensaje("0");
		GrabarDespacho4Response response = tccService.saveDelivery(request);
		
		/*WelcomeKit welcomeKit = welcomeKitRepo.findOne(confirmObj.kitId);
		welcomeKit.setWasConfirmed(true);
		welcomeKit.setDeliveryNumber(response.getRemesa());
		welcomeKitRepo.save(welcomeKit);*/
		
		return response;
	}
	
	public MessageRepo getMessageRepo() {
		return messageRepo;
	}

	public WelcomeKitRepo getWelcomeKitRepo() {
		return welcomeKitRepo;
	}
}