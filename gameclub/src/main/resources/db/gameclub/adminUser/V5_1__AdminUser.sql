INSERT INTO gameclub.admin_user(full_name, username, password, profile) VALUES
('José Luis León', 'jose.leon@provedatos.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE name='Administrador')),
('Luis García', 'luis.garcia@provedatos.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE name='Administrador')),
('Fernanda Lima', 'fernanda.lima@provedatos.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE name='Administrador')),
('Christian Simpson', 'christian.simpson@levelapsoftware.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE name='Administrador')),
('Gladys Diaz', 'gladys.diaz@provedatos.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE name='Administrador')),
('Pablo Ponce', 'pablo.ponce@provedatos.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE name='Administrador')),
('William Huera', 'williamhuera.levelap@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE name='Administrador')),
('Alejandro Cordovez', 'acordovez@tomorrowlabs.com.ec', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE name='Administrador'));