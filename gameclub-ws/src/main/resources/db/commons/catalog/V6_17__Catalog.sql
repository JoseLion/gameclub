INSERT INTO commons.catalog(name, code, other, parent) VALUES
('Estados de Pago', 'PGS', NULL, NULL);
INSERT INTO commons.catalog(name, code, other, parent) VALUES
('Nueva Solicitud', 'PGSNVS', '15', (SELECT id FROM commons.catalog WHERE code='PGS')),
('En proceso', 'PGSPRS', '16', (SELECT id FROM commons.catalog WHERE code='PGS')),
('Pagado', 'PGSPGD', '17', (SELECT id FROM commons.catalog WHERE code='PGS')),
('Error', 'PGSERR', '18', (SELECT id FROM commons.catalog WHERE code='PGS'));