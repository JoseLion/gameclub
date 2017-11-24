DROP TABLE IF EXISTS gameclub.logistics_kits;

CREATE OR REPLACE VIEW gameclub.logistics_kits AS
SELECT
	lk.id,
	lk.name,
	lk.last_name,
	lk.city,
	lk.address,
	lk.mail,
	lk.document,
	lk.contact_phone,
	lk.transaction_type,
	lk.status
FROM (
		SELECT	wk.id AS id,
			pu.name AS name,
			pu.last_name AS last_name,
			lo.name AS city,
			wk.address AS address,
			pu.username AS mail,
			pu.document AS document,
			pu.contact_phone AS contact_phone,
			CASE WHEN wk.quantity=0 THEN 'Welcome Kits' ELSE 'Shipping Kits' END AS transaction_type,
			ca.name AS status
		FROM	gameclub.welcome_kit wk
			INNER JOIN commons.catalog ca ON
				wk.shipping_status=ca.id
			INNER JOIN gameclub.public_user pu ON
				wk.public_user=pu.id
			INNER JOIN commons.location lo ON
				pu.location=lo.id
) AS lk
;