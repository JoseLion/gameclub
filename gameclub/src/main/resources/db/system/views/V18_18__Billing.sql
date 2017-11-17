DROP TABLE IF EXISTS gameclub.billing;

CREATE OR REPLACE VIEW gameclub.billing AS
SELECT
	bill.id,
	bill.full_name,
	bill.document_type,
	bill.document,
	bill.billing_address,
	bill.contact_phone,
	bill.contact_cell,
	bill.mail,
	bill.special_contributor,
	bill.game,
	bill.loan_date,
	bill.id_gamer,
	bill.cost,
	bill.weeks,
	bill.shipping_cost,
	bill.fee_game_Club,
	bill.taxes
FROM (SELECT	pu.id AS id,
				pu.name || '' || pu.last_name AS full_name,
				'Cédula' AS document_type,
				(CASE WHEN document_ruc IS NULL THEN document ELSE document_ruc END) AS document,
				pu.billing_address AS billing_address,
				pu.contact_phone AS contact_phone,
				'' AS contact_cell,
				pu.username AS mail,
				'' AS special_contributor,
				g.name AS game,
				l.creation_date AS loan_date,
				l.gamer AS id_gamer,
				pug.cost AS cost,
				l.weeks AS weeks,
				l.shippning_cost AS shipping_cost,
				l.fee_game_club AS fee_game_Club,
				l.taxes AS taxes
		FROM	gameclub.loan l
				INNER JOIN gameclub.public_user_game pug ON
					l.public_user_game=pug.id
				INNER JOIN game g ON
					pug.game=g.id
				INNER JOIN gameclub.public_user pu ON
					pug.public_user=pu.id
		WHERE	l.was_accepted=true AND l.tracking IS NOT NULL
		ORDER BY pu.name
) AS bill
;