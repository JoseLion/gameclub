INSERT INTO commons.settings(code, category, name, value, type, is_editable) VALUES
('STGPOWNGVGM', 'Multas', 'Dueño no entrega el juego al courier ($)', '5', 'number',true),
('STGPGMNRCGM', 'Multas', 'Jugador no recibe el juego al momento de la entrega por el courier ($)', '5', 'number', true),
('STGPWTOUADD', 'Multas', 'Jugador o dueño no confirma la dirección de retiro/entrega en los mensajes ($)', '5', 'number', true),
('STGPGMNGVGM', 'Multas', 'Jugador no entrega el juego al courier al finalizar el préstamo ($)', '5', 'number', true),
('STGPGM2TMNGV', 'Multas', 'Jugador no entrega el juego al courier por segunda vez al finalizar el préstamo ($)', '5', 'number', true),
('STGPOWNRCGM', 'Multas', 'Dueño no recibe el juego a la entrega del courier ($)', '5', 'number', true),

('STPCHGMAX', 'Price Charting', 'Máxima diferencia de precio (%)', '10', 'percentage', false),
('STPCHGMIN', 'Price Charting', 'Mínima diferencia de precio (%)', '10', 'percentage', false),
('STPCHG', 'Price Charting', 'Nacionalizar precio price charting (%)', '10', 'percentage', false),
('STGREF', 'Referidos', 'Recompensa por referido ($)', '5', 'number',true),
('STGSHIGO', 'Shipping', 'Valor de shipping de ida  (%)', '1.5', 'number', false),
('STGSHIBK', 'Shipping', 'Valor de shipping de vuelta (%)', '1.5', 'number', false),
('STGSHIKIT', 'Shipping', 'Valor del shipping kit ($)', '5', 'number', false),
('STGRCO', 'Recuperación', 'Semanas de recuperación del valor del juego ($)', '15', 'string', null),
('STGFGM', 'Fee Arriendo', 'Fee arriendo al jugador ($)', '15', 'percentage', false),
('STGFLO', 'Fee Arriendo', 'Fee arriendo al arrendador ($)', '15', 'percentage', false);