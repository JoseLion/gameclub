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
FROM (SELECT	tr.id,
				pu.name AS name,
		        pu.last_name AS last_name,
		        pu.billing_address AS billing_address,
		        pu.document AS document,
		        pu.contact_phone AS contact_phone,
		        (CASE WHEN tr.transaction='ALQUILASTE' THEN 'FEE ALQUILER' ELSE tr.transaction END) AS transaction,
		        tr.game AS game,
		        tr.console AS console,
		        (CASE WHEN tr.transaction='ALQUILASTE' THEN
		        	((bytea_to_decimal(tr.balance_part, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)) * ((SELECT s.value::numeric FROM commons.setting s WHERE s.code='STGFEELND') / 100.0)) / (1.0 - ((SELECT s.value::numeric FROM commons.setting s WHERE s.code='STGFEELND') / 100.0))) * 0.88
		        WHEN tr.transaction='MULTA' THEN
					(COALESCE(bytea_to_decimal(tr.debit_balance, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)), 0) + COALESCE(bytea_to_decimal(tr.debit_card, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)),0 )) - ((COALESCE(bytea_to_decimal(tr.debit_balance, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)), 0) + COALESCE(bytea_to_decimal(tr.debit_card, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)),0 )) * 0.12)
				ELSE
		        	(bytea_to_decimal(tr.debit_balance, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)) + bytea_to_decimal(tr.debit_card, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id))) * 0.88
		        END) AS subtotal,
		        (CASE WHEN tr.transaction='ALQUILASTE' THEN
		        	((bytea_to_decimal(tr.balance_part, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)) * ((SELECT s.value::numeric FROM commons.setting s WHERE s.code='STGFEELND') / 100.0)) / (1.0 - ((SELECT s.value::numeric FROM commons.setting s WHERE s.code='STGFEELND') / 100.0))) * 0.12
		        WHEN tr.transaction='MULTA' THEN
					((COALESCE(bytea_to_decimal(tr.debit_balance, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)), 0) + COALESCE(bytea_to_decimal(tr.debit_card, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)),0 )) * 0.12)
				ELSE
		        	(bytea_to_decimal(tr.debit_balance, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)) + bytea_to_decimal(tr.debit_card, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id))) * 0.12
		        END) AS iva,
		        (CASE WHEN tr.transaction='ALQUILASTE' THEN
		        	(bytea_to_decimal(tr.balance_part, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)) * ((SELECT s.value::numeric FROM commons.setting s WHERE s.code='STGFEELND') / 100.0)) / (1.0 - ((SELECT s.value::numeric FROM commons.setting s WHERE s.code='STGFEELND') / 100.0))
		        WHEN tr.transaction='MULTA' THEN
					COALESCE(bytea_to_decimal(tr.debit_balance, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)), 0) + COALESCE(bytea_to_decimal(tr.debit_card, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)),0 )
				ELSE
		        	bytea_to_decimal(tr.debit_balance, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id)) + bytea_to_decimal(tr.debit_card, (SELECT p.private_key FROM gameclub.public_user p WHERE p.id=pu.id))
		        END) AS total,
		        tr.creation_date AS creation_date
		FROM gameclub.transaction tr
			INNER JOIN gameclub.public_user pu ON tr.public_user_transaction=pu.id
      ORDER BY tr.creation_date DESC
) AS inc;