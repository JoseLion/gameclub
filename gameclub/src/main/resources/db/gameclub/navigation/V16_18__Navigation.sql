INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent) VALUES
	('Reportes', 'reports', true, 'fa-line-chart', 0, NULL);
INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent) VALUES
	('Usuario Registrados', 'reports.registeredUsers', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='reports')),
	('Retiro de Saldos', 'reports.amountRequestReport', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='reports'));
	