angular.module('GameClub').constant('Const', {
	authHeader: "authorization",

	publicUser: "publicUser",

	extraHeader: "extra",

	authHeaderPrefix: "Basic ",

	authHeaderSeparator: ":",

	mainState: 'gameclub.home',

	cookieToken: "XSRF-TOKEN",

	ok: "OK",

	statePrefix: {
		gameclub: 'gameclub.',
		account: 'gameclub.account.'
	},

	kushki: {
		publicMerchantId: '10000002423230048673149322144567',
		isTest: true
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
		consoleRequired: 'Elije una consola para poder realizar tu búsqueda'
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

	tooltips: {
		password: 'La contraseña debe tener únicamente letras y números, mínimo 6 caracteres, con al menos una mayúscula y un número'
	},

	shortcurLinks: {
		home: 0,
		sharePlay: 1,
		faq: 2,
		blog: 3,
		login: 4,
	}

});
