DROP TABLE IF EXISTS gameclub.amount_request_report;

CREATE OR REPLACE VIEW gameclub.amount_request_report AS
SELECT
	am.id,
	am.application_date,
	am.full_name,
	am.document,
	am.amount,
	am.status,
	am.payment_date,
	am.id_user
FROM (
		SELECT	ar.id,
			ar.creation_date AS application_date,
			pu.name || ' ' || pu.last_name AS full_name,
			pu.document AS document,
			ar.amount AS amount,	
			ca.name AS status,
			ar.payment_date AS payment_date,
			pu.id AS id_user
		FROM	gameclub.amount_request ar
			INNER JOIN gameclub.public_user pu ON
				ar.public_user_amount_request=pu.id
			INNER JOIN commons.catalog ca ON
				ar.request_status_amount_request=ca.id
) AS am
;