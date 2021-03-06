INSERT INTO commons.archive(module, name, type) VALUES
('Game', 'call-of-duty-modern-warfare-3-cover.png', 'image/png'),
('Game', 'call-of-duty-modern-warfare-3-banner.jpg', 'image/jpeg'),
('Game', 'gear-of-war-4-cover.jpg', 'image/jpeg'),
('Game', 'gear-of-war-4-banner.jpg', 'image/jpeg'),
('Game', 'resident-evil-7-biohazard-cover.jpg', 'image/jpeg'),
('Game', 'resident-evil-7-biohazard-banner.jpg', 'image/jpeg'),
('Game', 'batman-arkham-knight-cover.jpg', 'image/jpeg'),
('Game', 'batman-arkham-knight-banner.jpg', 'image/jpeg'),
('Game', 'zelda-breath-of-the-wild-cover.jpg', 'image/jpeg'),
('Game', 'zelda-breath-of-the-wild-banner.jpg', 'image/jpeg'),
('Game', 'diamond-call-of-duty-modern-warfare-3-cover.png', 'image/png'),
('Game', 'diamond-gear-of-war-4-cover.jpg', 'image/jpeg'),
('Game', 'diamond-resident-evil-7-biohazard-cover.jpg', 'image/jpeg'),
('Game', 'diamond-batman-arkham-knight-cover.jpg', 'image/jpeg'),
('Game', 'diamond-zelda-breath-of-the-wild-cover.jpg', 'image/jpeg');

INSERT INTO gameclub.game(name, price_charting_id, release_date, rating, trailer_url, upload_payment, banner, content_rating, cover, diamond, description) VALUES
('Call Of Duty: Modern Warfare 3', 32091, '2011-11-08 00:00:00.0', 74, 'http://www.ign.com/games/call-of-duty-modern-warfare-3/xbox-360-69475', 500, (SELECT id FROM commons.archive WHERE module='Game' AND name='call-of-duty-modern-warfare-3-banner.jpg'), 10, (SELECT id FROM commons.archive WHERE module='Game' AND name='call-of-duty-modern-warfare-3-cover.png'), (SELECT id FROM commons.archive WHERE module='Game' AND name='diamond-call-of-duty-modern-warfare-3-cover.png'), 'The world stands on the brink, and Makarov is intent on bringing civilization to its knees. In this darkest hour, are you willing to do what is necessary? Co-developed by Infinity Ward and Sledgehammer Games, Modern Warfare 3 delivers a full assault on gamers'' senses, including an intense single-player campaign that picks up immediately following the events of Call of Duty: Modern Warfare 2 and escalates into World War 3, as well as groundbreaking upgrades to the beloved Special Ops like the all-new Survival Mode. Special Ops serves as the perfect springboard to Modern Warfare 3''s re-imagined multiplayer, featuring the series'' trademark visceral gun-on-gun combat, a revamped pointstreak system with the introduction of "Strike Packages," devastating new modes like "Kill Confirmed" and other gameplay enhancements, new features and a bevy of new weapons, equipment, and perks.'),
('Gears Of War 4', 36989, '2016-10-11 00:00:00.0', 92, 'http://www.ign.com/games/gears-of-war-ultimate-edition/xbox-one-20026389', 300, (SELECT id FROM commons.archive WHERE module='Game' AND name='gear-of-war-4-banner.jpg'), 10, (SELECT id FROM commons.archive WHERE module='Game' AND name='gear-of-war-4-cover.jpg'), (SELECT id FROM commons.archive WHERE module='Game' AND name='diamond-gear-of-war-4-cover.jpg'), 'A new generation of soldiers battles the enemy in Gears of War 4, the next game in the blockbuster third-person shooter series. After narrowly escaping an attack on their village, JD Fenix and his friends, Kait and Del, must rescue the ones they love and discover the source of a monstrous new enemy. Fans can expect a familiar, yet distinct evolution of gameplay, including brutal and intimate action, a heart-pounding campaign, genre-defining multiplayer and stunning visuals powered by Unreal Engine 4.'),
('Resident Evil 7 Biohazard', 37226, '2017-01-24 00:00:00.0', 77, 'http://www.ign.com/games/resident-evil-7/xbox-one-20054863', 400, (SELECT id FROM commons.archive WHERE module='Game' AND name='resident-evil-7-biohazard-banner.jpg'), 10, (SELECT id FROM commons.archive WHERE module='Game' AND name='resident-evil-7-biohazard-cover.jpg'), (SELECT id FROM commons.archive WHERE module='Game' AND name='diamond-resident-evil-7-biohazard-cover.jpg'), 'Resident Evil 7 Biohazard is the the harrowing homecoming of this survivor horror franchise. While Resident Evil 7 draws from the series’ roots of atmospheric survival horror, it also delivers a whole new level of terror. With the RE Engine plus industry leading audio and visual technologies, you experience every abhorrent detail up close and personal in Resident Evil 7. The game is set after the events of Resident Evil 6, taking place in modern day and revolving around a foreboding, derelict plantation mansion in rural America. Players must explore and survive the horrors within the mysterious, sprawling estate.'),
('Batman Arkahm Knight', 35790, '2015-06-23 00:00:00.0', 92, 'http://www.ign.com/games/batman-arkham-knight/xbox-one-20014108', 200, (SELECT id FROM commons.archive WHERE module='Game' AND name='batman-arkham-knight-banner.jpg'), 10, (SELECT id FROM commons.archive WHERE module='Game' AND name='batman-arkham-knight-cover.jpg'), (SELECT id FROM commons.archive WHERE module='Game' AND name='diamond-batman-arkham-knight-cover.jpg'), 'In the explosive finale to the Arkham series, Batman faces the ultimate threat against the city he is sworn to protect. The Scarecrow returns to unite an impressive roster of super villains, including Penguin, Two-Face and Harley Quinn, to destroy The Dark Knight forever. Batman: Arkham Knight introduces the Batmobile to this version of the world of Gotham City, which is drivable for the first time in the franchise. The addition of this legendary vehicle, combined with the acclaimed gameplay of the Batman Arkham series, offers gamers the ultimate and complete Batman experience as they tear through the streets and soar across the skyline of the entirety of Gotham City.'),
('Zelda: Breath Of The Wild', 37321, '2017-03-03 00:00:00.0', 100, 'http://www.ign.com/games/the-legend-of-zelda-breath-of-the-wild/wii-u-110801', 650, (SELECT id FROM commons.archive WHERE module='Game' AND name='zelda-breath-of-the-wild-banner.jpg'), 8, (SELECT id FROM commons.archive WHERE module='Game' AND name='zelda-breath-of-the-wild-cover.jpg'), (SELECT id FROM commons.archive WHERE module='Game' AND name='diamond-zelda-breath-of-the-wild-cover.jpg'), 'Forget everything you know about The Legend of Zelda games. Step into a world of discovery, exploration and adventure in The Legend of Zelda: Breath of the Wild, a boundary-breaking new game in the acclaimed series. Travel across fields, through forests and to mountain peaks as you discover what has become of the ruined kingdom of Hyrule in this stunning open-air adventure.');

INSERT INTO gameclub.game_category(category, game) VALUES
((SELECT id FROM gameclub.category WHERE name='Shooter'), (SELECT id FROM gameclub.game WHERE name='Call Of Duty: Modern Warfare 3')),
((SELECT id FROM gameclub.category WHERE name='Action'), (SELECT id FROM gameclub.game WHERE name='Call Of Duty: Modern Warfare 3')),
((SELECT id FROM gameclub.category WHERE name='Adventure'), (SELECT id FROM gameclub.game WHERE name='Call Of Duty: Modern Warfare 3')),
((SELECT id FROM gameclub.category WHERE name='Shooter'), (SELECT id FROM gameclub.game WHERE name='Gears Of War 4')),
((SELECT id FROM gameclub.category WHERE name='Action'), (SELECT id FROM gameclub.game WHERE name='Gears Of War 4')),
((SELECT id FROM gameclub.category WHERE name='Adventure'), (SELECT id FROM gameclub.game WHERE name='Resident Evil 7 Biohazard')),
((SELECT id FROM gameclub.category WHERE name='Action'), (SELECT id FROM gameclub.game WHERE name='Resident Evil 7 Biohazard')),
((SELECT id FROM gameclub.category WHERE name='Action'), (SELECT id FROM gameclub.game WHERE name='Batman Arkahm Knight')),
((SELECT id FROM gameclub.category WHERE name='Adventure'), (SELECT id FROM gameclub.game WHERE name='Zelda: Breath Of The Wild'));
  
INSERT INTO gameclub.game_console(console, game) VALUES
((SELECT id FROM gameclub.console WHERE name='Play Station 4'), (SELECT id FROM gameclub.game WHERE name='Call Of Duty: Modern Warfare 3')),
((SELECT id FROM gameclub.console WHERE name='XBox One'), (SELECT id FROM gameclub.game WHERE name='Call Of Duty: Modern Warfare 3')),
((SELECT id FROM gameclub.console WHERE name='XBox One'), (SELECT id FROM gameclub.game WHERE name='Gears Of War 4')),
((SELECT id FROM gameclub.console WHERE name='Play Station 4'), (SELECT id FROM gameclub.game WHERE name='Resident Evil 7 Biohazard')),
((SELECT id FROM gameclub.console WHERE name='XBox One'), (SELECT id FROM gameclub.game WHERE name='Resident Evil 7 Biohazard')),
((SELECT id FROM gameclub.console WHERE name='Play Station 4'), (SELECT id FROM gameclub.game WHERE name='Batman Arkahm Knight')),
((SELECT id FROM gameclub.console WHERE name='Nintendo Switch'), (SELECT id FROM gameclub.game WHERE name='Zelda: Breath Of The Wild'));
  
INSERT INTO gameclub.game_magazine(rating, url, game, magazine) VALUES
(74, 'http://www.ign.com/games/call-of-duty-modern-warfare-3/xbox-360-69475', (SELECT id FROM gameclub.game WHERE name='Call Of Duty: Modern Warfare 3'), (SELECT id FROM commons.catalog WHERE code='MGZIGN')),
(63, 'http://www.ign.com/games/call-of-duty-modern-warfare-3/xbox-360-69475', (SELECT id FROM gameclub.game WHERE name='Call Of Duty: Modern Warfare 3'), (SELECT id FROM commons.catalog WHERE code='MGZGSP')),
(93, 'http://www.ign.com/games/call-of-duty-modern-warfare-3/xbox-360-69475', (SELECT id FROM gameclub.game WHERE name='Call Of Duty: Modern Warfare 3'), (SELECT id FROM commons.catalog WHERE code='MGZMTC')),
(92, 'http://www.ign.com/games/gears-of-war-4/xbox-one-20012240', (SELECT id FROM gameclub.game WHERE name='Gears Of War 4'), (SELECT id FROM commons.catalog WHERE code='MGZIGN')),
(95, 'http://www.ign.com/games/gears-of-war-4/xbox-one-20012240', (SELECT id FROM gameclub.game WHERE name='Gears Of War 4'), (SELECT id FROM commons.catalog WHERE code='MGZGSP')),
(93, 'http://www.ign.com/games/gears-of-war-4/xbox-one-20012240', (SELECT id FROM gameclub.game WHERE name='Gears Of War 4'), (SELECT id FROM commons.catalog WHERE code='MGZMTC')),
(77, 'http://www.ign.com/games/resident-evil-7/ps4-20053763', (SELECT id FROM gameclub.game WHERE name='Resident Evil 7 Biohazard'), (SELECT id FROM commons.catalog WHERE code='MGZIGN')),
(85, 'http://www.ign.com/games/resident-evil-7/ps4-20053763', (SELECT id FROM gameclub.game WHERE name='Resident Evil 7 Biohazard'), (SELECT id FROM commons.catalog WHERE code='MGZGSP')),
(81, 'http://www.ign.com/games/resident-evil-7/ps4-20053763', (SELECT id FROM gameclub.game WHERE name='Resident Evil 7 Biohazard'), (SELECT id FROM commons.catalog WHERE code='MGZMTC')),
(92, 'http://www.ign.com/games/batman-arkham-knight/ps4-20014107', (SELECT id FROM gameclub.game WHERE name='Batman Arkahm Knight'), (SELECT id FROM commons.catalog WHERE code='MGZIGN')),
(90, 'http://www.ign.com/games/batman-arkham-knight/ps4-20014107', (SELECT id FROM gameclub.game WHERE name='Batman Arkahm Knight'), (SELECT id FROM commons.catalog WHERE code='MGZGSP')),
(91, 'http://www.ign.com/games/batman-arkham-knight/ps4-20014107', (SELECT id FROM gameclub.game WHERE name='Batman Arkahm Knight'), (SELECT id FROM commons.catalog WHERE code='MGZMTC')),
(100, 'http://www.ign.com/games/the-legend-of-zelda-breath-of-the-wild/nintendo-switch-20052872', (SELECT id FROM gameclub.game WHERE name='Zelda: Breath Of The Wild'), (SELECT id FROM commons.catalog WHERE code='MGZIGN')),
(88, 'http://www.ign.com/games/the-legend-of-zelda-breath-of-the-wild/nintendo-switch-20052872', (SELECT id FROM gameclub.game WHERE name='Zelda: Breath Of The Wild'), (SELECT id FROM commons.catalog WHERE code='MGZGSP')),
(94, 'http://www.ign.com/games/the-legend-of-zelda-breath-of-the-wild/nintendo-switch-20052872', (SELECT id FROM gameclub.game WHERE name='Zelda: Breath Of The Wild'), (SELECT id FROM commons.catalog WHERE code='MGZMTC'));