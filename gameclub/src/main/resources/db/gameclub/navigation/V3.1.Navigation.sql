INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent) VALUES
('Usuarios', 'user', true, 'fa-users', 0, NULL);
	INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent) VALUES
	('Usuarios del Administrador', 'user.viewAdminUsers', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='user')),
	('Perfiles', 'user.viewProfiles', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='user'));