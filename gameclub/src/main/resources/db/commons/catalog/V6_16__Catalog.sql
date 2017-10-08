INSERT INTO commons.catalog(name, code, other, parent) VALUES
('Prestador no entregó juego a Courier', 'SHPPNE', '10', (SELECT id FROM commons.catalog WHERE code='SHP')),
('Jugador no recibió el juego de Courier', 'SHPJNR', '11', (SELECT id FROM commons.catalog WHERE code='SHP')),
('Jugador no entrega juego a Courier', 'SHPJNE', '12', (SELECT id FROM commons.catalog WHERE code='SHP')),
('Jugador no entrega juego a Courier (2da vez)', 'SHPJNV', '13', (SELECT id FROM commons.catalog WHERE code='SHP')),
('Prestador no recibió el juego de Courier', 'SHPPNR', '14', (SELECT id FROM commons.catalog WHERE code='SHP'));