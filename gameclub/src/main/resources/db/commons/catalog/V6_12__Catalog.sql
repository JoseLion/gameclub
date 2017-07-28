INSERT INTO commons.catalog(name, code, other, parent) VALUES
('Integrity', 'ITG', NULL, NULL);
	INSERT INTO commons.catalog(name, code, other, parent) VALUES
	('Nuevo', 'ITGNVO', 'Aún no has sacado ni el envoltorio de este juego y ya lo quieres compartir ¡Eres genial!', (SELECT id FROM commons.catalog WHERE code='ITG')),
	('Como nuevo', 'ITGCNV', 'Probaste el juego, te gustó y quieres que más gente sepa lo bueno que es. No tiene ninguna cicatriz a la vista', (SELECT id FROM commons.catalog WHERE code='ITG')),
	('Muy bueno', 'ITGMBN', 'Jugaste la campaña y algunas partidas online, pero tienes más cosas que jugar. Ya se pueden ver algunas marcas de las batallas que tuviste con este disco', (SELECT id FROM commons.catalog WHERE code='ITG')),
	('Bueno', 'ITGBNO', 'Jugaste la campaña y bastantes partidas online, es lo que más juegas. Ya se ve las marcas de las varias batallas que tuviste con este disco', (SELECT id FROM commons.catalog WHERE code='ITG')),
	('Aceptable', 'ITGACP', 'Haz pasado cientos de horas con este juego. Las marcas de toda esta aventura son evidentes en el disco pero aún funciona como en el primer día', (SELECT id FROM commons.catalog WHERE code='ITG'));
	
INSERT INTO commons.catalog(name, code, other, parent) VALUES
('Faqs Categories', 'FAQ', NULL, NULL);
	INSERT INTO commons.catalog(name, code, other, parent) VALUES
	('GameClub', 'FAQGMC', null, (SELECT id FROM commons.catalog WHERE code='FAQ')),
	('Comunidad', 'FAQCOM', null, (SELECT id FROM commons.catalog WHERE code='FAQ')),
	('Cuenta', 'FAQCNT', null, (SELECT id FROM commons.catalog WHERE code='FAQ')),
	('Pagos', 'FAQPGS', null, (SELECT id FROM commons.catalog WHERE code='FAQ')),
	('Juegos', 'FAQJGS', null, (SELECT id FROM commons.catalog WHERE code='FAQ')),
	('Dueño', 'FAQDNO', null, (SELECT id FROM commons.catalog WHERE code='FAQ')),
	('Jugador', 'FAQJGR', null, (SELECT id FROM commons.catalog WHERE code='FAQ')),
	('Entrega y Recepción', 'FAQEYR', null, (SELECT id FROM commons.catalog WHERE code='FAQ')),
	('Embalaje de Juegos', 'FAQEDJ', null, (SELECT id FROM commons.catalog WHERE code='FAQ')),
	('Juego Dañado/Perdido', 'FAQJDP', null, (SELECT id FROM commons.catalog WHERE code='FAQ')),
	('Garantías', 'FAQGRT', null, (SELECT id FROM commons.catalog WHERE code='FAQ')),
	('Multas', 'FAQMLT', null, (SELECT id FROM commons.catalog WHERE code='FAQ')),
	('Coins', 'FAQCNS', null, (SELECT id FROM commons.catalog WHERE code='FAQ')),
	('Otros', 'FAQOTR', null, (SELECT id FROM commons.catalog WHERE code='FAQ'));