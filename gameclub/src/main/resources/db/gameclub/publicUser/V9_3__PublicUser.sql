INSERT INTO gameclub.public_user(name, last_name, username, password, balance) VALUES
('José Luis', 'León', 'joseluis.levelap@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', NULL),
('Jose', 'Lion', 'joseluis.provedatos@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', NULL),
('Luis', 'Garcia', 'luis.garcia.provedatos@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', NULL),
('Gladys', 'Diaz', 'gladys.provedatos@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', NULL),
('Pablo', 'Ponce', 'p.ponce.provedatos@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', NULL),
('Fernanda', 'Lima', 'mope09@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', NULL),
('Christian', 'Simpson', 'simpsonchristian90@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', NULL),
('William', 'Huera', 'williamhuera.levelap@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', NULL),
('Victor', 'Cardenas', 'victorcardenas.levelap@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', NULL),
('Luisa', 'Trujillo', 'v.cardenas.levelap@outlook.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', NULL);

INSERT INTO kushki_subscription(card_finale, email, first_name, last_name, status, subscription_id, public_user) VALUES
('4242','victorcardenas.levelap@gmail.com','victor','cardenas',true,'0000000000008137',(SELECT id FROM gameclub.public_user WHERE username='victorcardenas.levelap@gmail.com')),
('4242','v.cardenas.levelap@outlook.com','luisa','trujillo',true,'0000000000008137',(SELECT id FROM gameclub.public_user WHERE username='v.cardenas.levelap@outlook.com'));