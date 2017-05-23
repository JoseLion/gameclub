INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent) VALUES
('Blog', 'levelapBlog', true, 'fa-quote-left', 0, NULL);
	INSERT INTO gameclub.navigation(name, route, is_abstract, icon, level, parent) VALUES
	('Artículos', 'levelapBlog.viewArticles', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='levelapBlog')),
	('Tags', 'levelapBlog.viewTags', false, NULL, 1, (SELECT id FROM gameclub.navigation WHERE route='levelapBlog'));