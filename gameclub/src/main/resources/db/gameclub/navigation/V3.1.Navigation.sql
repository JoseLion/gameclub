INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent) VALUES
('Administración', 'admin', true, 'fa-gears', 0, NULL);
	INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent) VALUES
	('Dashboard', 'admin.dashboard', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='admin')),
	('Usuarios del Administrador', 'admin.viewAdminUsers', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='admin')),
	('Perfiles', 'admin.viewProfiles', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='admin'));
	
INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent) VALUES
('Portal', 'portal', true, 'fa-laptop', 0, NULL);
	INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent) VALUES
	('Consolas', 'portal.viewConsoles', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='portal')),
	('Categorias', 'portal.viewCategories', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='portal')),
	('Juegos', 'portal.viewGames', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='portal'));