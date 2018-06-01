INSERT INTO commons.archive(name, module, type) VALUES
('ps3-black.svg', 'Console', 'image/svg+xml'),
('psvita-black.svg', 'Console', 'image/svg+xml'),
('xbox-360-black.svg', 'Console', 'image/svg+xml'),
('wii-u-black.svg', 'Console', 'image/svg+xml'),
('ps3-white.svg', 'Console', 'image/svg+xml'),
('psvita-white.svg', 'Console', 'image/svg+xml'),
('xbox-360-white.svg', 'Console', 'image/svg+xml'),
('wii-u-white.svg', 'Console', 'image/svg+xml');

INSERT INTO gameclub.console(name, white_logo, black_logo) VALUES
('Play Station 3', (SELECT id FROM commons.archive WHERE name='ps3-white.svg'), (SELECT id FROM commons.archive WHERE name='ps3-black.svg')),
('PS Vita', (SELECT id FROM commons.archive WHERE name='psvita-white.svg'), (SELECT id FROM commons.archive WHERE name='psvita-black.svg')),
('XBox 360', (SELECT id FROM commons.archive WHERE name='xbox-360-white.svg'), (SELECT id FROM commons.archive WHERE name='xbox-360-black.svg')),
('Wii U', (SELECT id FROM commons.archive WHERE name='wii-u-white.svg'), (SELECT id FROM commons.archive WHERE name='wii-u-black.svg'));