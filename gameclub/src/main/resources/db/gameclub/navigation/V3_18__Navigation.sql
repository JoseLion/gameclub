INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent) VALUES
	('Reportes', 'reports', true, 'fa-line-chart', 0, NULL);
INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent) VALUES
	('Usuario Registrados', 'reports.registeredUsers', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='reports')),
	('Retiro de Saldos', 'reports.amountRequestReport', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='reports')),
	('Facturación', 'reports.billing', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='reports')),
	('Juegos de Plataforma', 'reports.platformGames', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='reports')),
	('Log Tracking', 'reports.logTracking', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='reports')),
	('Logistica Kits', 'reports.logisticsKits', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='reports'));
	