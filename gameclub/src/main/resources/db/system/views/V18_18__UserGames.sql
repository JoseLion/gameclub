DROP TABLE IF EXISTS gameclub.user_games;

CREATE OR REPLACE VIEW gameclub.user_games AS
SELECT
	ug.id,
	ug.public_user, 
	ug.game,
	ug.upload_date,
	ug.loans
FROM (SELECT
		pg.id AS id,
		p.id AS public_user,
		g.id AS game,
		pg.creation_date AS upload_date,
		COUNT(l.public_user_game) AS loans
	FROM gameclub.public_user_game pg, gameclub.public_user p, gameclub.game g,  gameclub.loan l WHERE
		pg.public_user=p.id AND
		pg.game=g.id AND
		pg.id=l.public_user_game
	GROUP BY pg.id, p.id, g.id, pg.id ORDER BY g.name)
AS ug;