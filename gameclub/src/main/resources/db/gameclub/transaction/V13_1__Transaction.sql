-- Para guardar en la tabla TRANSACTION al ejecutar una multa:
CREATE FUNCTION transation_update_fine_fn() RETURNS TRIGGER AS $$
    BEGIN
        IF (TG_OP = 'UPDATE') THEN
            INSERT INTO gameclub.transaction (balance_part,debit_balance, debit_card, game, transaction, weeks, public_user_transaction) 
					VALUES(NEW.amount, NEW.balance_part, NEW.card_part, '','COBRO MULTA', 0, NEW.public_user_fine);
            RETURN NEW;
        END IF;
    END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_transation_tg
AFTER  UPDATE ON gameclub.fine
    FOR EACH ROW EXECUTE PROCEDURE transation_update_fine_fn();

-- Para guardar en la tabla de TRANSACTION al solicitar un SHIPPING KIT:
CREATE FUNCTION gameclub.add_transation_shipping_kit_fn()
RETURNS TRIGGER LANGUAGE 'plpgsql'
AS $BODY$
	DECLARE
		statusCode VARCHAR;
	BEGIN
		statusCode = (SELECT cat.code FROM commons.catalog cat WHERE cat.id = new.shipping_status);
		IF (statusCode = 'SHPETG' AND new.was_confirmed IS TRUE AND new.quantity > 0) THEN
			INSERT INTO gameclub.transaction(balance_part, debit_balance, debit_card, game, transaction, weeks, public_user_transaction)
			VALUES((SELECT pu.balance FROM gameclub.public_user pu WHERE pu.id = new.public_user), NEW.amount_balance, NEW.amount_card, '-', 'SHIPPING KIT', 0, new.public_user);
		END IF;
		RETURN new;
	END;
$BODY$;

CREATE TRIGGER add_transation_shipping_kit_tg AFTER UPDATE ON gameclub.welcome_kit
FOR EACH ROW EXECUTE PROCEDURE gameclub.add_transation_shipping_kit_fn();