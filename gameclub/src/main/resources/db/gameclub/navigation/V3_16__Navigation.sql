INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent)
VALUES ('Estados de Pago', 'admin.amountRequest', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='admin'));