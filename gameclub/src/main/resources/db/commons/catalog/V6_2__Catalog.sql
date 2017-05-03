INSERT INTO commons.catalog(name, code, other, parent) VALUES
('Magazines', 'MGZ', NULL, NULL);
	INSERT INTO commons.catalog(name, code, other, parent) VALUES
	('IGN', 'MGZIGN', 'img/catalog/ign.png', (SELECT id FROM commons.catalog WHERE code='MGZ')),
	('Gamespot', 'MGZGSP', 'img/catalog/gamespot.png', (SELECT id FROM commons.catalog WHERE code='MGZ')),
	('Metacritic', 'MGZMTC', 'img/catalog/metacritic.png', (SELECT id FROM commons.catalog WHERE code='MGZ'));
	
INSERT INTO commons.catalog(name, code, other, parent) VALUES
('Content Rating', 'CRT', NULL, NULL);
	INSERT INTO commons.catalog(name, code, other, parent) VALUES
	('EC', 'CRTECH', 'img/catalog/esrb-ec.png', (SELECT id FROM commons.catalog WHERE code='CRT')),
	('E', 'CRTEVR', 'img/catalog/esrb-e.png', (SELECT id FROM commons.catalog WHERE code='CRT')),
	('E10+', 'CRTE10', 'img/catalog/esrb-e10+.png', (SELECT id FROM commons.catalog WHERE code='CRT')),
	('T', 'CRTTEE', 'img/catalog/esrb-t.png', (SELECT id FROM commons.catalog WHERE code='CRT')),
	('M', 'CRTMTR', 'img/catalog/esrb-m.png', (SELECT id FROM commons.catalog WHERE code='CRT')),
	('AO', 'CRTAOY', 'img/catalog/esrb-ao.png', (SELECT id FROM commons.catalog WHERE code='CRT'));