INSERT INTO commons.setting(code, category, name, value, type, is_editable) VALUES
('STGFNELDD', 'Multas', 'Dueño no entrega el juego al courier ($)', '5', 'number',true),
('STGFNEPDR', 'Multas', 'Jugador no recibe el juego al momento de la entrega por el courier ($)', '5', 'number', true),
('STGFNEANC', 'Multas', 'Jugador o dueño no confirma la dirección de retiro/entrega en los mensajes ($)', '5', 'number', true),
('STGFNEPDD', 'Multas', 'Jugador no entrega el juego al courier al finalizar el préstamo ($)', '5', 'number', true),
('STGFNEPD2', 'Multas', 'Jugador no entrega el juego al courier por segunda vez al finalizar el préstamo ($)', '5', 'number', true),
('STGFNELDR', 'Multas', 'Dueño no recibe el juego a la entrega del courier ($)', '5', 'number', true),
('STGFNEVPL', 'Multas', 'Valor entregado al prestador cuando el jugador no recibibe el juego ($)', '5', 'number', true),

('STGPCHMAX', 'Price Charting', 'Máxima diferencia de precio (%)', '10', 'percentage', false),
('STGPCHMIN', 'Price Charting', 'Mínima diferencia de precio (%)', '10', 'percentage', false),
('STGPCHNAP', 'Price Charting', 'Nacionalizar precio price charting (%)', '10', 'percentage', false),
('STGREFREW', 'Referidos', 'Recompensa por referido ($)', '5', 'number',true),
('STGSHPVGO', 'Shipping', 'Valor de shipping de ida  (%)', '1.5', 'number', false),
('STGSHPVRT', 'Shipping', 'Valor de shipping de vuelta (%)', '1.5', 'number', false),
('STGSHPKIT', 'Shipping', 'Valor del shipping kit ($)', '5', 'number', false),
('STGRETWEK', 'Recuperación', 'Semanas de recuperación del valor del juego ($)', '15', 'string', null),
('STGFEEPLY', 'Fee Arriendo', 'Fee arriendo al jugador ($)', '15', 'percentage', false),
('STGFEELND', 'Fee Arriendo', 'Fee arriendo al arrendador ($)', '15', 'percentage', false);