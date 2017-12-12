INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent)
VALUES ('Usuarios del Portal', 'admin.viewPublicUsers', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='admin'));

INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent)
VALUES ('Avatares', 'admin.viewAvatars', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='admin'));

INSERT INTO navigation(name, route, is_abstract, icon, level, parent)
VALUES ('Parámetros','admin.viewSettings',false, NULL,1,(SELECT id FROM gameclub.navigation WHERE route='admin'));

INSERT INTO navigation(name, route, is_abstract, icon, level, parent)
VALUES ('Multas','admin.viewFine',false, NULL,1,(SELECT id FROM gameclub.navigation WHERE route='admin'));
