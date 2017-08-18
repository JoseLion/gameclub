INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent)
VALUES ('Logística', 'admin.viewLogistics', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='admin'));