INSERT INTO gameclub.public_user(name, last_name, username, password, private_key, balance) VALUES
('José Luis', 'León', 'joseluis.levelap@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', DECODE('\256wZu\356\2368\347d1\332\340%^', 'escape'), DECODE('\355C\366\360Ij\307\224+ENV\2244M', 'escape')),
('Jose', 'Lion', 'joseluis.provedatos@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', DECODE('\256wZu\356\2368\347d1\332\340%^', 'escape'), DECODE('\355C\366\360Ij\307\224+ENV\2244M', 'escape')),
('Luis', 'Garcia', 'luis.garcia.provedatos@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', DECODE('\256wZu\356\2368\347d1\332\340%^', 'escape'), DECODE('\355C\366\360Ij\307\224+ENV\2244M', 'escape')),
('Gladys', 'Diaz', 'gladys.provedatos@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', DECODE('\256wZu\356\2368\347d1\332\340%^', 'escape'), DECODE('\355C\366\360Ij\307\224+ENV\2244M', 'escape')),
('Pablo', 'Ponce', 'p.ponce.provedatos@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', DECODE('\256wZu\356\2368\347d1\332\340%^', 'escape'), DECODE('\355C\366\360Ij\307\224+ENV\2244M', 'escape')),
('Fernanda', 'Lima', 'mope09@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', DECODE('\256wZu\356\2368\347d1\332\340%^', 'escape'), DECODE('\355C\366\360Ij\307\224+ENV\2244M', 'escape')),
('Christian', 'Simpson', 'simpsonchristian90@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', DECODE('\256wZu\356\2368\347d1\332\340%^', 'escape'), DECODE('\355C\366\360Ij\307\224+ENV\2244M', 'escape')),
('William', 'Huera', 'williamhuera.levelap@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', DECODE('\256wZu\356\2368\347d1\332\340%^', 'escape'), DECODE('\355C\366\360Ij\307\224+ENV\2244M', 'escape')),
('Victor', 'Cardenas', 'victorcardenas.levelap@gmail.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', DECODE('\256wZu\356\2368\347d1\332\340%^', 'escape'), DECODE('\355C\366\360Ij\307\224+ENV\2244M', 'escape')),
('Luisa', 'Trujillo', 'v.cardenas.levelap@outlook.com', '$2a$12$CrGcBjTb/Yszy5LXuVksae5yPHpSI07QQM8ycknRQqIsAQtDXqpba', DECODE('\256wZu\356\2368\347d1\332\340%^', 'escape'), DECODE('\355C\366\360Ij\307\224+ENV\2244M', 'escape'));

INSERT INTO kushki_subscription(card_finale, email, first_name, last_name, status, subscription_id, public_user) VALUES
('4242','victorcardenas.levelap@gmail.com','victor','cardenas',true,'0000000000008137',(SELECT id FROM gameclub.public_user WHERE username='victorcardenas.levelap@gmail.com')),
('4242','v.cardenas.levelap@outlook.com','luisa','trujillo',true,'0000000000008137',(SELECT id FROM gameclub.public_user WHERE username='v.cardenas.levelap@outlook.com'));