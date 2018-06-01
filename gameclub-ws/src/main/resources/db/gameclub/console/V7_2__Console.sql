INSERT INTO commons.archive(name, module, type) VALUES
('ps4-white.svg', 'Console', 'image/svg+xml'),
('xbox-one-white.svg', 'Console', 'image/svg+xml'),
('nintendo-switch-white.svg', 'Console', 'image/svg+xml'),
('ps4-black.svg', 'Console', 'image/svg+xml'),
('xbox-one-black.svg', 'Console', 'image/svg+xml'),
('nintendo-switch-black.svg', 'Console', 'image/svg+xml');

INSERT INTO gameclub.console(name, white_logo, black_logo) VALUES
('Play Station 4', (SELECT id FROM commons.archive WHERE name='ps4-white.svg'), (SELECT id FROM commons.archive WHERE name='ps4-black.svg')),
('XBox One', (SELECT id FROM commons.archive WHERE name='xbox-one-white.svg'), (SELECT id FROM commons.archive WHERE name='xbox-one-black.svg')),
('Nintendo Switch', (SELECT id FROM commons.archive WHERE name='nintendo-switch-white.svg'), (SELECT id FROM commons.archive WHERE name='nintendo-switch-black.svg'));