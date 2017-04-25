INSERT INTO commons.archive(name, module, type) VALUES
('ps4.svg', 'Console', 'image/svg+xml'),
('xbox-one.svg', 'Console', 'image/svg+xml'),
('nintendo-switch.svg', 'Console', 'image/svg+xml');

INSERT INTO gameclub.console(name, logo) VALUES
('Play Station 4', (SELECT id FROM commons.archive WHERE name='ps4.svg')),
('XBox One', (SELECT id FROM commons.archive WHERE name='xbox-one.svg')),
('Nintendo Switch', (SELECT id FROM commons.archive WHERE name='nintendo-switch.svg'));