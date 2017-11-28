DROP TABLE IF EXISTS gameclub.income;

CREATE OR REPLACE VIEW gameclub.income AS
SELECT
	inc.id,
	inc.name,
	inc.last_name,
	inc.billing_address,
	inc.document,
	inc.contact_phone,
	inc.transaction,
	inc.game,
	inc.console,
	inc.subtotal,
	inc.iva,
	inc.total,
	inc.creation_date
FROM (
		SELECT	tr.id,
				pu.name AS name,
		        pu.last_name AS last_name,
		        pu.billing_address AS billing_address,
		        pu.document AS document,
		        pu.contact_phone AS contact_phone,
		        tr.transaction AS transaction,
		        tr.game AS game,
		        tr.console AS console,
		        
		        ((bytea_to_decimal(tr.debit_balance,(SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id))+
				 bytea_to_decimal(tr.debit_card,(SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)))-
		         ((bytea_to_decimal(tr.debit_balance,(SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id))+
				 bytea_to_decimal(tr.debit_card,(SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)))*0.12)) AS subtotal,
		        
		        ((bytea_to_decimal(tr.debit_balance,(SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id))+
				 bytea_to_decimal(tr.debit_card,(SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)))*0.12) AS iva,
		         
		        (bytea_to_decimal(tr.debit_balance,(SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id))+
				 bytea_to_decimal(tr.debit_card,(SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id))) AS total,
		         
		         tr.creation_date AS creation_date
		FROM	gameclub.transaction tr
				INNER JOIN gameclub.public_user pu ON
		        	tr.public_user_transaction=pu.id
		WHERE	tr.transaction <> 'ALQUILASTE'
) AS inc
;