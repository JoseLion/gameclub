INSERT INTO gameclub.admin_user(full_name, username, password, profile)
VALUES('José Luis León', 'jose.leon@provedatos.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', (SELECT id FROM gameclub.profile WHERE name='Administrador'));