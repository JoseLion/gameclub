angular.module('GameClub').constant('Const', {
	authHeader: "authorization",

	publicUser: "publicUser",

	extraHeader: "extra",

	authHeaderPrefix: "Basic ",

	authHeaderSeparator: ":",

	mainState: 'gameclub.home',

	cookieToken: "XSRF-TOKEN",

	ok: "OK",

	iva: 0.12,

	statePrefix: {
		gameclub: 'gameclub.',
		account: 'gameclub.account.'
	},

	errorMessages: {
		required: 'El dato es requerido',
		number: 'El dato debe ser numérico',
		email: 'Correo eletrónico inválido',
		pattern: 'Formato inválido',
		ciRequired: 'Número de cédula inválido',
		rucRequired: 'Número de RUC inválido',
		date: 'Formato de fecha inválido',
		min: 'Valor por debajo del minímo establecido',
		max: 'Valor por encima del máximo establecido',
		url: 'Formato de URL inválido',
		maxlength: 'El texto sobrepasa la cantidad máxima de caracteres',
		minlength: 'El texto no cumple con la cantidad mínima de caracteres',
		tableRequired: 'La tabla debe contener al menos un elemento',
		consoleRequired: 'Elije una consola para poder realizar tu búsqueda',
		cardRequired: 'Debe seleccionar una tarjeta para completar realizar el pedido'
	},

	messages: {
		sure: '¿Está seguro?',
		confirmation: 'Se guardará la información.',
		success: 'Información guardada exitosamente.',
		successSent: 'Información enviada exitosamente.',
		statusChanged: 'El estado se cambió con éxito.',
		expired: 'La sesión ha expirado.',
		unauthorized: 'No autorizado.',
		unableToConnect: 'No se pudo conectar al servidor.',
		error: 'Oops, algo salió mal. Por favor intente más tarde.',
		kushkiError: 'Su tarjeta no superó la validación satisfactoriamente.',
		acceptTerms: 'Debes aceptar los términos y condiciones para continuar'
	},

	code: {
		country: 'EC',
		faq: "FAQ",
		shippingDelivered: "SHPETG",
		integrity: 'ITG'
	},

	settings: {
		priceChartingGames: 'STGPCHNAP',
		priceChartingMax: 'STGPCHMAX',
		priceChartingMin: 'STGPCHMIN',
		weekShippingCost: 'STGRETWEK',
		shippingKitValue: 'STGSHPKIT',
		feeLoanGamer: 'STGFEEPLY',
		feeLoanLender: 'STGFEELND',
		costReferred: 'STGREFREW'
	},

	tooltips: {
		password: 'La contraseña debe tener únicamente letras y números, mínimo 6 caracteres, con al menos una mayúscula y un número'
	},

	shortcurLinks: {
		home: 0,
		sharePlay: 1,
		faq: 2,
		blog: 3,
		login: 4,
	},

	google: {
		maps: "AIzaSyBiUXqwBImDoqoRBlvtAAVgnkP5yjMVwyY",
		geolocation: "AIzaSyBC8pBwfSjl2ZTdN3dgByX__iOYGu8tlgU"
	},

	paymentez: {
		baseUrl: "https://ccapi-stg.paymentez.com",
		appCode: "TOMO-EC",
		appKey: "LeqWG1P48IC8Cfj99DPW6rLGnh5wSC"
	},

	seoImg: "-GameClub-Alquila-Juegos-Gana-Dinero-Playstation-Nintendo-Xbox"
});
