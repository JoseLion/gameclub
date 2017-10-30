DELETE FROM commons.setting WHERE code IN('STGSHPVGO', 'STGSHPVRT');
UPDATE commons.setting SET name = 'Fee arriendo de jugador (%)' WHERE code = 'STGFEEPLY';
UPDATE commons.setting SET name = 'Fee arriendo de arrendador (%)' WHERE code = 'STGFEELND';
UPDATE commons.setting SET value = '12.5' WHERE code = 'STGSHPKIT';