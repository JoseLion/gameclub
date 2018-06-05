DROP TABLE IF EXISTS gameclub.platform_games;

CREATE OR REPLACE VIEW gameclub.platform_games AS
SELECT
	pg.id,
	pg.game,
	pg.console,
	pg.release_date,
	pg.full_name,
	pg.upload_date,
	pg.status,
	pg.recommended_price,
	pg.cost,
	pg.total_requested_rentals,
	pg.total_rent_aceppted,
	pg.total_rent_requested,
	pg.creation_date
FROM (SELECT	pug.id,
				g.name AS game,
				c.name AS console,
				g.release_date AS release_date,
				pu.name || ' ' || pu.last_name AS full_name,
				pug.creation_date AS upload_date,
				pug.status AS status,
				(g.upload_payment/(SELECT cast(s.value as int) FROM commons.setting s WHERE s.code='STGRETWEK')) AS recommended_price,
				pug.cost AS cost,
				(SELECT	COUNT(l.id) FROM loan l WHERE l.gamer=pu.id) AS total_requested_rentals,
				(SELECT	COUNT(l.id) FROM loan l WHERE l.gamer=pu.id AND l.was_accepted=TRUE) AS total_rent_aceppted,
				(SELECT	COUNT(l.id) FROM loan l WHERE l.gamer=pu.id AND l.status=FALSE) AS total_rent_requested,
				pug.creation_date AS creation_date
				
			FROM	gameclub.public_user_game pug
				INNER JOIN gameclub.game g ON
					pug.game=g.id
				INNER JOIN gameclub.console c ON
					pug.console=c.id
				INNER JOIN gameclub.public_user pu ON
					pug.public_user=pu.id
			
) AS pg
;