INSERT INTO navigation(name, route, is_abstract, icon, level, parent)
VALUES ('Reembolsos','admin.viewRefund',false, NULL,1,(SELECT id FROM gameclub.navigation WHERE route='admin'));