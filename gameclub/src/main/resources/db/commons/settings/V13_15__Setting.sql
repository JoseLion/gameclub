INSERT INTO commons.settings(code, category, name, value, type, is_editable) VALUES
('STGPOWNGVGM', 'Multas', 'Dueño no entrega el juego al courier ($)', '5', 'TOSNBR',true),
('STGPGMNRCGM', 'Multas', 'Jugador no recibe el juego al momento de la entrega por el courier ($)', '5', 'TOSNBR', true),
('STGPWTOUADD', 'Multas', 'Jugador o dueño no confirma la dirección de retiro/entrega en los mensajes ($)', '5', 'TOSNBR', true),
('STGPGMNGVGM', 'Multas', 'Jugador no entrega el juego al courier al finalizar el préstamo ($)', '5', 'TOSNBR', true),
('STGPGM2TMNGV', 'Multas', 'Jugador no entrega el juego al courier por segunda vez al finalizar el préstamo ($)', '5', 'TOSNBR', true),
('STGPOWNRCGM', 'Multas', 'Dueño no recibe el juego a la entrega del courier ($)', '5', 'TOSNBR', true),
('STGMLTVEM', 'Multas', 'Valor entregado al prestador cuando el jugador no recibibe el juego ($)', '5', 'number', true),

('STPCHGMAX', 'Price Charting', 'Máxima diferencia de precio (%)', '10', 'TOSPRC', false),
('STPCHGMIN', 'Price Charting', 'Mínima diferencia de precio (%)', '10', 'TOSPRC', false),
('STPCHG', 'Price Charting', 'Nacionalizar precio price charting (%)', '10', 'TOSPRC', false),
('STGREF', 'Referidos', 'Recompensa por referido ($)', '5', 'TOSNBR',true),
('STGSHIGO', 'Shipping', 'Valor de shipping de ida  (%)', '1.5', 'TOSNBR', false),
('STGSHIBK', 'Shipping', 'Valor de shipping de vuelta (%)', '1.5', 'TOSNBR', false),
('STGSHIKIT', 'Shipping', 'Valor del shipping kit ($)', '5', 'number', false),
('STGRCO', 'Recuperación', 'Semanas de recuperación del valor del juego ($)', '15', 'TOSSTR', null),
('STGFGM', 'Fee Arriendo', 'Fee arriendo al jugador ($)', '15', 'TOSPRC', false),
('STGFLO', 'Fee Arriendo', 'Fee arriendo al arrendador ($)', '15', 'TOSPRC', false);