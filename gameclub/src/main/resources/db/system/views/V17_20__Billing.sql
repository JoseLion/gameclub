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
	bill.cost,
	bill.weeks,
	bill.shipping_cost,
	bill.fee_game_Club,
	bill.taxes
FROM (SELECT	l.id AS id,
				pu.name || ' ' || pu.last_name AS full_name,
				'Cédula' AS document_type,
				(CASE WHEN document_ruc IS NULL THEN document ELSE document_ruc END) AS document,
				pu.billing_address AS billing_address,
				pu.contact_phone AS contact_phone,
				'' AS contact_cell,
				pu.username AS mail,
				'' AS special_contributor,
				g.name AS game,
				l.creation_date AS loan_date,
				pug.cost AS cost,
				l.weeks AS weeks,
				(bytea_to_decimal(l.shippning_cost, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=l.gamer))) AS shipping_cost,
				(bytea_to_decimal(l.fee_game_club, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=l.gamer))) AS fee_game_Club,
				(bytea_to_decimal(l.taxes, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=l.gamer))) AS taxes
		FROM	gameclub.loan l
				INNER JOIN gameclub.public_user_game pug ON
					l.public_user_game=pug.id
				INNER JOIN game g ON
					pug.game=g.id
				INNER JOIN gameclub.public_user pu ON
					pug.public_user=pu.id
		WHERE	l.was_accepted=true AND l.lender_status_date IS NOT NULL AND l.gamer_status_date IS NOT NULL
		ORDER BY pu.name
) AS bill
;