INSERT INTO commons.catalog(name, code, other, parent) VALUES
('Shipping Status', 'SHP', NULL, NULL);
	INSERT INTO commons.catalog(name, code, other, parent) VALUES
	('Sin Tracking', 'SHPSTK', '0', (SELECT id FROM commons.catalog WHERE code='SHP')),
	('Sin Recibir', 'SHPSRC', '1', (SELECT id FROM commons.catalog WHERE code='SHP')),
	('Recibido en Origen', 'SHPRCO', '2', (SELECT id FROM commons.catalog WHERE code='SHP')),
	('En Unidad Contenedora', 'SHPEUC', '3', (SELECT id FROM commons.catalog WHERE code='SHP')),
	('Descargado en Destino', 'SHPDED', '4', (SELECT id FROM commons.catalog WHERE code='SHP')),
	('En Reparto', 'SHPERT', '5', (SELECT id FROM commons.catalog WHERE code='SHP')),
	('Entregada', 'SHPETG', '6', (SELECT id FROM commons.catalog WHERE code='SHP')),
	('Anulada', 'SHPANL', '7', (SELECT id FROM commons.catalog WHERE code='SHP')),
	('Con Novedad', 'SHPCNV', '8', (SELECT id FROM commons.catalog WHERE code='SHP')),
	('Novedad Solucionada', 'SHPNSL', '9', (SELECT id FROM commons.catalog WHERE code='SHP'));