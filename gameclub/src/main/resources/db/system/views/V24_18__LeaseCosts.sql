DROP TABLE IF EXISTS gameclub.lease_costs;

CREATE OR REPLACE VIEW gameclub.lease_costs AS
SELECT
	lc.id,
	lc.name,
	lc.last_name,
	lc.document,
	lc.contact_phone,
	lc.transaction,
	lc.game,
	lc.console,
	lc.total,
	lc.creation_date
FROM (
		SELECT	tr.id AS id,
				pu.name AS name,
		        pu.last_name AS last_name,
		        pu.document AS document,
		        pu.contact_phone AS contact_phone,
		        tr.transaction AS transaction,
		        tr.game AS game,
		        tr.console AS console,
		         
		        (bytea_to_decimal(tr.debit_balance,(SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id))+
				 bytea_to_decimal(tr.debit_card,(SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id))) AS total,
		         
		         tr.creation_date AS creation_date
		        
		FROM	gameclub.transaction tr
				INNER JOIN gameclub.public_user pu ON
		        	tr.public_user_transaction=pu.id
		WHERE	tr.transaction = 'JUGASTE'
) AS lc
;