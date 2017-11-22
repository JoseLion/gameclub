DROP TABLE IF EXISTS gameclub.registered_users;

CREATE OR REPLACE VIEW gameclub.registered_users AS
SELECT	
	regus.id,
	regus.public_user_id,
	regus.total_games,
	regus.total_requested_rentals,
	regus.total_rent_aceppted,
	regus.total_rent_requested,
	regus.total_applications_received,
	regus.total_request_accepted,
	regus.total_request_rejected
	
FROM 	(SELECT  
		DISTINCT(pu.id) AS id,
		pu.id AS public_user_id,
		(SELECT COUNT(pug.id) FROM public_user_game pug WHERE pug.public_user=pu.id) AS total_games,

		(SELECT	COUNT(l.id) FROM loan l WHERE l.gamer=pu.id) AS total_requested_rentals,

		(SELECT	COUNT(l.id) FROM loan l WHERE l.gamer=pu.id AND l.was_accepted=TRUE) AS total_rent_aceppted,

		(SELECT	COUNT(l.id) FROM loan l WHERE l.gamer=pu.id AND l.status=FALSE) AS total_rent_requested,

		(SELECT	COUNT(l.id)
		FROM	loan l
			INNER JOIN public_user_game pug ON
				l.public_user_game=pug.id
		WHERE	pug.public_user=pu.id) AS total_applications_received,

		(SELECT	COUNT(l.id)
		FROM	loan l
			INNER JOIN public_user_game pug ON
				l.public_user_game=pug.id
		WHERE	pug.public_user=pu.id AND l.was_accepted=TRUE) AS total_request_accepted,

		(SELECT	COUNT(l.id)
		FROM	loan l
			INNER JOIN public_user_game pug ON
				l.public_user_game=pug.id
		WHERE	pug.public_user=pu.id AND l.was_accepted=FALSE) AS total_request_rejected
		
	FROM	gameclub.public_user pu
		LEFT JOIN gameclub.public_user_game pug ON
			pu.id=pug.public_user
		LEFT JOIN gameclub.loan l ON
			pug.id=l.public_user_game

	ORDER BY pu.id)
AS regus;