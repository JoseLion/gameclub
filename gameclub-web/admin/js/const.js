angular.module("Gameclub").constant('Const', {
	// ------------------------------------------------------
	// ---------------------- SECURITY ----------------------
	// ------------------------------------------------------

	adminUser: "adminUser",

	authHeader: "authorization",

	extraHeader: "extra",

	authHeaderPrefix: "Basic ",

	authHeaderSeparator: ":",

	cookieToken: "XSRF-TOKEN",

	ok: "OK",

	loginState: "login",

	mainState: "admin.dashboard",

	// ------------------------------------------------------
	// ------------------------- END ------------------------
	// ------------------------------------------------------

	tableSize: 20,

	errorMessages: {
		required: "El dato es requerido.",
		number: "El dato debe ser numérico.",
		email: "Correo eletrónico inválido.",
		pattern: "Formato inválido.",
		ciRequired: "Número de cédula inválido.",
		rucRequired: "Número de RUC inválido.",
		date: "Formato de fecha inválido.",
		min: "Valor por debajo del minímo establecido.",
		max: "Valor por encima del máximo establecido.",
		url: "Formato de URL inválido.",
		maxlength: "El texto sobrepasa la cantidad máxima de caracteres",
		minlength: "El texto no cumple con la cantidad mínima de caracteres",
		tableRequired: "La tabla debe contener al menos un elemento.",
		incompleteSchedule: "Horario incompleto.",
		incorrectRange: "Rango no válido."
	},

	messages: {
		sure: "¿Está seguro?",
		confirmation: "Se guardará la información.",
		success: "Información guardada exitosamente.",
		statusChanged: "El estado se cambió con éxito.",
		expired: "La sesión ha expirado.",
		unauthorized: "No autorizado.",
		unableToConnect: "No se pudo conectar al servidor."
	},

	code: {
		contentRatings: "CRT"
	}
});