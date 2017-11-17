DROP TABLE IF EXISTS gameclub.log_tracking;

CREATE OR REPLACE VIEW gameclub.log_tracking AS
SELECT
	lt.id,	
	lt.box_number,
	lt.game,
	lt.referral_guide,
	lt.name,
	lt.last_name,
	lt.city,
	lt.address,
	lt.mail,
	lt.document,
	lt.contact_phone,
	lt.name_lender,
	lt.last_name_lender,
	lt.city_lender,
	lt.address_lender,
	lt.mail_lender,
	lt.document_lender,
	lt.contact_phone_lender,
	lt.transaction_type,
	lt.status
FROM (
		SELECT	l.id AS id,	
			l.box_number AS box_number,
			g.name AS game,
			'' AS referral_guide,
			pu.name AS name,
			pu.last_name AS last_name,
			lo.name AS city,
			l.gamer_address AS address,
			pu.username AS mail,
			pu.document AS document,
			pul.contact_phone AS contact_phone,
			pul.name AS name_lender,
			pul.last_name AS last_name_lender,
			lol.name AS city_lender,
			l.lender_address AS address_lender,
			pul.username AS mail_lender,
			pul.document AS document_lender,
			pul.contact_phone AS contact_phone_lender,
			'préstamo' AS transaction_type,
			ca.name AS status
			
		FROM	gameclub.loan l
			INNER JOIN gameclub.public_user_game pug ON
				l.public_user_game=pug.id
			INNER JOIN gameclub.game g ON
				pug.game=g.id
			INNER JOIN gameclub.public_user pu ON
				l.gamer=pu.id
			INNER JOIN commons.location lo ON
				pu.location=lo.id
			INNER JOIN gameclub.public_user pul ON
				pug.public_user=pul.id
			INNER JOIN commons.location lol ON
				pul.location=lol.id
			INNER JOIN commons.catalog ca ON
				l.shipping_status=ca.id
		UNION
		
		SELECT	r.id AS id,
			lr.box_number AS box_number,
			gr.name AS game,
			'' AS referral_guide,
			pulr.name AS name,
			pulr.last_name AS last_name,
			lolr.name AS city,
			pulr.billing_address AS address,
			pulr.username AS mail,
			pulr.document AS document,
			pulr.contact_phone AS contact_phone,
			pur.name AS name_lender,
			pur.last_name AS last_name_lender,
			lor.name AS city_lender,
			pur.billing_address AS address_lender,
			pur.username AS mail_lender,
			pur.document AS document_lender,
			pulr.contact_phone AS contact_phone_lender,
			'devolución' AS transaction_type,
			car.name AS status
		FROM	gameclub.restore r
			INNER JOIN gameclub.loan lr ON
				r.loan=lr.id
			INNER JOIN gameclub.public_user_game pugr ON
				r.public_user_game=pugr.id
			INNER JOIN gameclub.game gr ON
				pugr.game=gr.id
			INNER JOIN gameclub.public_user pur ON
				r.gamer=pur.id
			INNER JOIN commons.location lor ON
				pur.location=lor.id
			INNER JOIN gameclub.public_user pulr ON
				pugr.public_user=pulr.id
			INNER JOIN commons.location lolr ON
				pulr.location=lolr.id
			INNER JOIN commons.catalog car ON
				r.shipping_status=car.id
) AS lt
;