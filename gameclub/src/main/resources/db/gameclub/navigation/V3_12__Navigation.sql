INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent)
VALUES ('FAQs', 'admin.viewFaqs', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='admin'));