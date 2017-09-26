INSERT INTO gameclub.public_user(name, last_name, username, password) VALUES
('José Luis', 'León', 'joseluis.levelap@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba'),
('Jose', 'Lion', 'joseluis.provedatos@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba'),
('Luis', 'Garcia', 'luis.garcia.provedatos@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba'),
('Gladys', 'Diaz', 'gladys.provedatos@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba'),
('Pablo', 'Ponce', 'p.ponce.provedatos@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba'),
('Fernanda', 'Lima', 'mope09@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba'),
('Christian', 'Simpson', 'simpsonchristian90@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba'),
('William', 'Huera', 'williamhuera.levelap@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba'),
('Victor', 'Cardenas', 'victorcardenas.levelap@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba'),
('luisa', 'trujillo', 'v.cardenas.levelap@outlook.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba');

INSERT INTO kushki_subscription(card_finale, creation_date, email, first_name, last_name, 
        status, subscription_id, public_user)
VALUES ('4242',to_date('2017-09-25 09:02:53','yyyy-MM-dd HH24:MI:SS'),'victorcardenas.levelap@gmail.com','victor','cardenas',true,'0000000000008137',9 );
INSERT INTO kushki_subscription(card_finale, creation_date, email, first_name, last_name, 
        status, subscription_id, public_user)
VALUES ('4242',to_date('2017-09-25 09:02:53','yyyy-MM-dd HH24:MI:SS'),'v.cardenas.levelap@outlook.com','luisa','trujillo',true,'0000000000008137',10 );