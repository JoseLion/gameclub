CREATE EXTENSION IF NOT EXISTS pgcrypto
    SCHEMA gameclub;
    
    
CREATE OR REPLACE FUNCTION bytea_to_decimal(bytea, bytea) RETURNS DOUBLE PRECISION AS $BODY$
BEGIN
    return (SELECT  CAST((substring((encode(gameclub.decrypt($1, $2, 'aes'), 'escape'))  from 1 FOR
                              ((position('.' IN encode(gameclub.decrypt($1, $2, 'aes'), 'escape')))+4))) AS DOUBLE PRECISION));
end;$BODY$
LANGUAGE 'plpgsql' VOLATILE
COST 100;