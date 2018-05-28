CREATE OR REPLACE VIEW gameclub.lease_cost AS
SELECT
	lc.id,
	lc.name,
	lc.last_name,
	lc.billing_address,
	lc.document,
	lc.contact_phone,
	lc.transaction,
	lc.game,
	lc.console,
	lc.total,
	lc.creation_date
FROM (SELECT	tr.id AS id,
				pu.name AS name,
		        pu.last_name AS last_name,
		        pu.billing_address AS billing_address, 
		        pu.document AS document,
		        pu.contact_phone AS contact_phone,
		        tr.transaction AS transaction,
		        tr.game AS game,
		        tr.console AS console,
		        bytea_to_decimal(tr.balance_part, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)) AS total,
		        tr.creation_date AS creation_date
		FROM gameclub.transaction tr
			INNER JOIN gameclub.public_user pu ON tr.public_user_transaction=pu.id
		WHERE tr.transaction='ALQUILASTE'
		ORDER BY tr.creation_date DESC
) AS lc;