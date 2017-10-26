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