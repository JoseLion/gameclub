INSERT INTO gameclub.admin_user(full_name, username, password, profile) VALUES
('José Luis León', 'jose.leon@provedatos.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE wildcard=TRUE LIMIT 1)),
('Luis García', 'luis.garcia@provedatos.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE wildcard=TRUE LIMIT 1)),
('Víctor Cárdenas', 'victorcardenas.levelap@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE wildcard=TRUE LIMIT 1)),
('Fernanda Lima', 'fernanda.lima@provedatos.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE wildcard=TRUE LIMIT 1)),
('Johanna Pérez', 'jperez.levelap@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE wildcard=TRUE LIMIT 1)),
('Gladys Diaz', 'gladys.diaz@provedatos.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE wildcard=TRUE LIMIT 1)),
('Pablo Ponce', 'pablo.ponce@provedatos.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE wildcard=TRUE LIMIT 1)),
('Alejandro Cordovez', 'acordovez@tomorrowlabs.com.ec', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE wildcard=TRUE LIMIT 1));