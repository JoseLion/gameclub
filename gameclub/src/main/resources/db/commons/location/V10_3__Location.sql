INSERT INTO commons.location(name, code, parent) VALUES
('Ecuador', 'EC', NULL);

	INSERT INTO commons.location(name, code, parent) VALUES
	('Azuay', 'ECAZ', (SELECT id FROM commons.location WHERE code='EC')),
	('Bolivar', 'ECBV', (SELECT id FROM commons.location WHERE code='EC')),
	('Cañar', 'ECCN', (SELECT id FROM commons.location WHERE code='EC')),
	('Carchi', 'ECCH', (SELECT id FROM commons.location WHERE code='EC')),
	('Chimborazo', 'ECCZ', (SELECT id FROM commons.location WHERE code='EC')),
	('Cotopaxi', 'ECCX', (SELECT id FROM commons.location WHERE code='EC')),
	('El Oro', 'ECEO', (SELECT id FROM commons.location WHERE code='EC')),
	('Esmeraldas', 'ECEM', (SELECT id FROM commons.location WHERE code='EC')),
	('Galápagos', 'ECGP', (SELECT id FROM commons.location WHERE code='EC')),
	('Guayas', 'ECGY', (SELECT id FROM commons.location WHERE code='EC')),
	('Imbabura', 'ECIM', (SELECT id FROM commons.location WHERE code='EC')),
	('Loja', 'ECLJ', (SELECT id FROM commons.location WHERE code='EC')),
	('Los Ríos', 'ECLR', (SELECT id FROM commons.location WHERE code='EC')),
	('Manabí', 'ECMB', (SELECT id FROM commons.location WHERE code='EC')),
	('Morona Santiago', 'ECMS', (SELECT id FROM commons.location WHERE code='EC')),
	('Napo', 'ECNP', (SELECT id FROM commons.location WHERE code='EC')),
	('Orellana', 'ECOR', (SELECT id FROM commons.location WHERE code='EC')),
	('Pastaza', 'ECPZ', (SELECT id FROM commons.location WHERE code='EC')),
	('Pichincha', 'ECPC', (SELECT id FROM commons.location WHERE code='EC')),
	('Santa Elena', 'ECSE', (SELECT id FROM commons.location WHERE code='EC')),
	('Santo Domingo de los Tsachilas', 'ECSD', (SELECT id FROM commons.location WHERE code='EC')),
	('Sucumbios', 'ECSB', (SELECT id FROM commons.location WHERE code='EC')),
	('Tungurahua', 'ECTH', (SELECT id FROM commons.location WHERE code='EC')),
	('Zamora Chinchipe', 'ECZC', (SELECT id FROM commons.location WHERE code='EC'));
	
		INSERT INTO commons.location(name, code, parent) VALUES
		('Camilo Ponce Enríquez', 'ECAZCP', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Chordeleg', 'ECAZCH', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Cuenca', 'ECAZCU', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('El Pan', 'ECAZEP', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Girón', 'ECAZGR', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Guachapala', 'ECAZGC', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Gualaceo', 'ECAZGL', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Oña', 'ECAZOA', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Paute', 'ECAZPT', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('San Fernando', 'ECAZSF', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Santa Isabel', 'ECAZSI', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Sevilla de Oro', 'ECAZSV', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Sig Sig', 'ECAZSG', (SELECT id FROM commons.location WHERE code='ECAZ')),
		
		('Caluma', 'ECBVCL', (SELECT id FROM commons.location WHERE code='ECBV')),
		('Chillanes', 'ECBVCL', (SELECT id FROM commons.location WHERE code='ECBV')),
		('Chimbo', 'ECBVCH', (SELECT id FROM commons.location WHERE code='ECBV')),
		('Echeandía', 'ECBVEH', (SELECT id FROM commons.location WHERE code='ECBV')),
		('Guaranda', 'ECBVGR', (SELECT id FROM commons.location WHERE code='ECBV')),
		('San Miguel', 'ECBVSM', (SELECT id FROM commons.location WHERE code='ECBV')),
		
		('Azogues', 'ECCNAZ', (SELECT id FROM commons.location WHERE code='ECCN')),
		('Biblian', 'ECCNBL', (SELECT id FROM commons.location WHERE code='ECCN')),
		('Cañar', 'ECCNCN', (SELECT id FROM commons.location WHERE code='ECCN')),
		('Deleg', 'ECCNDL', (SELECT id FROM commons.location WHERE code='ECCN')),
		('El Tambo', 'ECCNET', (SELECT id FROM commons.location WHERE code='ECCN')),
		('La Troncal', 'ECCNLT', (SELECT id FROM commons.location WHERE code='ECCN')),
		('Suscal', 'ECCNSC', (SELECT id FROM commons.location WHERE code='ECCN')),
		
		('Bolívar', 'ECCHBV', (SELECT id FROM commons.location WHERE code='ECCH')),
		('Espejo', 'ECCHEP', (SELECT id FROM commons.location WHERE code='ECCH')),
		('Mira', 'ECCHMR', (SELECT id FROM commons.location WHERE code='ECCH')),
		('Montúfar', 'ECCHMT', (SELECT id FROM commons.location WHERE code='ECCH')),
		('San Pedro de Huaca', 'ECCHSP', (SELECT id FROM commons.location WHERE code='ECCH')),
		('Tulcán', 'ECCHTC', (SELECT id FROM commons.location WHERE code='ECCH')),
		
		('Aluasí', 'ECCZAL', (SELECT id FROM commons.location WHERE code='ECCZ')),
		('Chambo', 'ECCZCM', (SELECT id FROM commons.location WHERE code='ECCZ')),
		('Chunchi', 'ECCZCH', (SELECT id FROM commons.location WHERE code='ECCZ')),
		('Colta', 'ECCZCL', (SELECT id FROM commons.location WHERE code='ECCZ')),
		('Cumandá', 'ECCZCD', (SELECT id FROM commons.location WHERE code='ECCZ')),
		('Guamote', 'ECCZGM', (SELECT id FROM commons.location WHERE code='ECCZ')),
		('Guano', 'ECCZGN', (SELECT id FROM commons.location WHERE code='ECCZ')),
		('Papallacta', 'ECCZPL', (SELECT id FROM commons.location WHERE code='ECCZ')),
		('Penipe', 'ECCZPN', (SELECT id FROM commons.location WHERE code='ECCZ')),
		('Riobamba', 'ECCZRB', (SELECT id FROM commons.location WHERE code='ECCZ')),
		
		('La Maná', 'ECCXLM', (SELECT id FROM commons.location WHERE code='ECCX')),
		('Latacunga', 'ECCXLT', (SELECT id FROM commons.location WHERE code='ECCX')),
		('Pujilí', 'ECCXPJ', (SELECT id FROM commons.location WHERE code='ECCX')),
		('Salcedo', 'ECCXSC', (SELECT id FROM commons.location WHERE code='ECCX')),
		('Saquisilí', 'ECCXSQ', (SELECT id FROM commons.location WHERE code='ECCX')),
		('Sigchos', 'ECCXSG', (SELECT id FROM commons.location WHERE code='ECCX')),
		
		('Arenillas', 'ECEOAR', (SELECT id FROM commons.location WHERE code='ECEO')),
		('Atahualpa', 'ECEOAT', (SELECT id FROM commons.location WHERE code='ECEO')),
		('Balsas', 'ECEOBL', (SELECT id FROM commons.location WHERE code='ECEO')),
		('El Huabo', 'ECEOEH', (SELECT id FROM commons.location WHERE code='ECEO')),
		('Huaquillas', 'ECEOHQ', (SELECT id FROM commons.location WHERE code='ECEO')),
		('Machala', 'ECEOMC', (SELECT id FROM commons.location WHERE code='ECEO')),
		('Marcabelli', 'ECEOMR', (SELECT id FROM commons.location WHERE code='ECEO')),
		('Pasaje', 'ECEOPJ', (SELECT id FROM commons.location WHERE code='ECEO')),
		('Piñas', 'ECEOPN', (SELECT id FROM commons.location WHERE code='ECEO')),
		('Portovelo', 'ECEOPT', (SELECT id FROM commons.location WHERE code='ECEO')),
		('Santa Rosa', 'ECEOSR', (SELECT id FROM commons.location WHERE code='ECEO')),
		('Zaruma', 'ECEOZR', (SELECT id FROM commons.location WHERE code='ECEO')),
		
		('Atacames', 'ECEMAT', (SELECT id FROM commons.location WHERE code='ECEM')),
		('Eloy Alfaro', 'ECEMEA', (SELECT id FROM commons.location WHERE code='ECEM')),
		('Esmeraldas', 'ECEMEM', (SELECT id FROM commons.location WHERE code='ECEM')),
		('Muisne', 'ECEMMN', (SELECT id FROM commons.location WHERE code='ECEM')),
		('Quinindé', 'ECEMQN', (SELECT id FROM commons.location WHERE code='ECEM')),
		('San Lorenzo', 'ECEMSL', (SELECT id FROM commons.location WHERE code='ECEM')),
		
		('Isabela', 'ECGPIB', (SELECT id FROM commons.location WHERE code='ECGP')),
		('San Cristóbal', 'ECGPSC', (SELECT id FROM commons.location WHERE code='ECGP')),
		('Santa Cruz', 'ECGPSC', (SELECT id FROM commons.location WHERE code='ECGP')),
		
		('Baloa', 'ECGYBL', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Balzar', 'ECGYBZ', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Colimes', 'ECGYCL', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Coronel Marcelino Maridueña', 'ECGYCM', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Daule', 'ECGYDL', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Duran', 'ECGYDR', (SELECT id FROM commons.location WHERE code='ECGY')),
		('El Empalme', 'ECGYEE', (SELECT id FROM commons.location WHERE code='ECGY')),
		('El Triunfo', 'ECGYET', (SELECT id FROM commons.location WHERE code='ECGY')),
		('General Antonio Elizalde', 'ECGYGA', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Guayaquil', 'ECGYGQ', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Isidro Ayora', 'ECGYIA', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Lomas de Sangertillo', 'ECGYLS', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Milagro', 'ECGYML', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Naranjal', 'ECGYNJ', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Naranjito', 'ECGYNT', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Nebol', 'ECGYNB', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Palestina', 'ECGYPL', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Pedro Carbo', 'ECGYPC', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Playas', 'ECGYPL', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Puerto Baquerizo Moreno', 'ECGYPB', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Salitre', 'ECGYSL', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Samborondón', 'ECGYSM', (SELECT id FROM commons.location WHERE code='ECGY')),
		('San Jacinto de Yaguachi', 'ECGYSJ', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Santa Lucia', 'ECGYSL', (SELECT id FROM commons.location WHERE code='ECGY')),
		('Simón Bolívar', 'ECGYSB', (SELECT id FROM commons.location WHERE code='ECGY')),
		
		('Atuntaqui', 'ECIMAT', (SELECT id FROM commons.location WHERE code='ECIM')),
		('Cotacachi', 'ECIMCT', (SELECT id FROM commons.location WHERE code='ECIM')),
		('Ibarra', 'ECIMIB', (SELECT id FROM commons.location WHERE code='ECIM')),
		('Otavalo', 'ECIMOT', (SELECT id FROM commons.location WHERE code='ECIM')),
		('Pimampiro', 'ECIMPM', (SELECT id FROM commons.location WHERE code='ECIM')),
		('San Miguel de Urcuqui', 'ECIMSM', (SELECT id FROM commons.location WHERE code='ECIM')),
		
		('Calvas', 'ECLJCV', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Catamayo', 'ECLJCT', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Celica', 'ECLJCL', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Chaguarpamba', 'ECLJCG', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Espindola', 'ECLJEP', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Gonzanama', 'ECLJGZ', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Loja', 'ECLJLJ', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Macará', 'ECLJMC', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Olmedo', 'ECLJOL', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Paltas', 'ECLJPT', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Pindal', 'ECLJPN', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Puyango', 'ECLJPY', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Quilanga', 'ECLJQL', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Saraguro', 'ECLJSR', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Sazoranga', 'ECLJSZ', (SELECT id FROM commons.location WHERE code='ECLJ')),
		('Zapotillo', 'ECLJZP', (SELECT id FROM commons.location WHERE code='ECLJ')),
		
		('Baba', 'ECLRBA', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Babahoyo', 'ECLRBH', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Buena Fé', 'ECLRBF', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Mocache', 'ECLRMC', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Montalvo', 'ECLRMN', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Pelenque', 'ECLRPL', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Puebloviejo', 'ECLRPV', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Quevedo', 'ECLRQV', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Quinsaloma', 'ECLRQS', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Urdaneta', 'ECLRUN', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Valencia', 'ECLRVL', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Ventanas', 'ECLRVN', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Vinces', 'ECLRVC', (SELECT id FROM commons.location WHERE code='ECLR')),
		
		('24 de Mayo', 'ECMBMY', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Bahía de Caráquez', 'ECMBBC', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Bolívar', 'ECMBBV', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Chone', 'ECMBCN', (SELECT id FROM commons.location WHERE code='ECMB')),
		('El Carmen', 'ECMBEC', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Flavio Alfaro', 'ECMBFA', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Jama', 'ECMBJM', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Jaramijo', 'ECMBJR', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Jipijapa', 'ECMBJP', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Junín', 'ECMBJN', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Manta', 'ECMBMT', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Montecristi', 'ECMBMC', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Olmedo', 'ECMBOM', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Paján', 'ECMBPJ', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Pedernales', 'ECMBPD', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Pichincha', 'ECMBPC', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Portoviejo', 'ECMBPT', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Puerto López', 'ECMBPL', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Rocafuerte', 'ECMBRF', (SELECT id FROM commons.location WHERE code='ECMB')),
		('San Vicente', 'ECMBSV', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Santa Ana', 'ECMBSA', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Tosagua', 'ECMBTS', (SELECT id FROM commons.location WHERE code='ECMB')),
		
		('Gualaquiza', 'ECMSGL', (SELECT id FROM commons.location WHERE code='ECMS')),
		('Logroño', 'ECMSLG', (SELECT id FROM commons.location WHERE code='ECMS')),
		('Macas', 'ECMSMC', (SELECT id FROM commons.location WHERE code='ECMS')),
		('Santiago', 'ECMSST', (SELECT id FROM commons.location WHERE code='ECMS')),
		('Sucua', 'ECMSSC', (SELECT id FROM commons.location WHERE code='ECMS')),
		('Tiwintza', 'ECMSTW', (SELECT id FROM commons.location WHERE code='ECMS')),
		
		('Archidona', 'ECNPAR', (SELECT id FROM commons.location WHERE code='ECNP')),
		('Carlos Julio Arosemena Tola', 'ECNPCJ', (SELECT id FROM commons.location WHERE code='ECNP')),
		('El Chaco', 'ECNPEC', (SELECT id FROM commons.location WHERE code='ECNP')),
		('El Tena', 'ECNPET', (SELECT id FROM commons.location WHERE code='ECNP')),
		('Quijos', 'ECNPQJ', (SELECT id FROM commons.location WHERE code='ECNP')),
		
		('El Coca', 'ECOREC', (SELECT id FROM commons.location WHERE code='ECOR')),
		('Sachas', 'ECORSC', (SELECT id FROM commons.location WHERE code='ECOR')),
		('Loreto', 'ECORLR', (SELECT id FROM commons.location WHERE code='ECOR')),
		
		('El Puyo', 'ECPZEP', (SELECT id FROM commons.location WHERE code='ECPZ')),
		('Mera', 'ECPZMR', (SELECT id FROM commons.location WHERE code='ECPZ')),
		
		('Cayambe', 'ECPCCY', (SELECT id FROM commons.location WHERE code='ECPC')),
		('Mejía', 'ECPCMJ', (SELECT id FROM commons.location WHERE code='ECPC')),
		('Pedro Moncayo', 'ECPCPC', (SELECT id FROM commons.location WHERE code='ECPC')),
		('Pedro Vicente Maldonado', 'ECPCPV', (SELECT id FROM commons.location WHERE code='ECPC')),
		('Puerto Quito', 'ECPCPQ', (SELECT id FROM commons.location WHERE code='ECPC')),
		('Quito', 'ECPCQT', (SELECT id FROM commons.location WHERE code='ECPC')),
		('Rimiñahui', 'ECPCRM', (SELECT id FROM commons.location WHERE code='ECPC')),
		('San Miguel de los Bancos', 'ECPCLB', (SELECT id FROM commons.location WHERE code='ECPC')),
		
		('La Libertad', 'ECSELB', (SELECT id FROM commons.location WHERE code='ECSE')),
		('Salinas', 'ECSESL', (SELECT id FROM commons.location WHERE code='ECSE')),
		('Santa Elena', 'ECSESE', (SELECT id FROM commons.location WHERE code='ECSE')),
		
		('La Concordia', 'ECSDLC', (SELECT id FROM commons.location WHERE code='ECSD')),
		('Santo Domingo', 'ECSDSD', (SELECT id FROM commons.location WHERE code='ECSD')),
		
		('Cascales', 'ECSBCS', (SELECT id FROM commons.location WHERE code='ECSB')),
		('Gonzalo Pizarro', 'ECSBGP', (SELECT id FROM commons.location WHERE code='ECSB')),
		('Lago Agrio', 'ECSBLA', (SELECT id FROM commons.location WHERE code='ECSB')),
		('Putumayo', 'ECSBPT', (SELECT id FROM commons.location WHERE code='ECSB')),
		('Shushufindi', 'ECSBSF', (SELECT id FROM commons.location WHERE code='ECSB')),
		
		('Ambato', 'ECTHAB', (SELECT id FROM commons.location WHERE code='ECTH')),
		('Baños de Agua Santa', 'ECTHBA', (SELECT id FROM commons.location WHERE code='ECTH')),
		('Cevallos', 'ECTHCV', (SELECT id FROM commons.location WHERE code='ECTH')),
		('Mocha', 'ECTHMC', (SELECT id FROM commons.location WHERE code='ECTH')),
		('Patate', 'ECTHPT', (SELECT id FROM commons.location WHERE code='ECTH')),
		('Quero', 'ECTHQR', (SELECT id FROM commons.location WHERE code='ECTH')),
		('San Pedro de Pelileo', 'ECTHPL', (SELECT id FROM commons.location WHERE code='ECTH')),
		('Santiago de Pillaro', 'ECTHPR', (SELECT id FROM commons.location WHERE code='ECTH')),
		('Tisaleo', 'ECTHTL', (SELECT id FROM commons.location WHERE code='ECTH')),
		
		('Centinela del Condor', 'ECZCCN', (SELECT id FROM commons.location WHERE code='ECZC')),
		('Chinchipe', 'ECZCCP', (SELECT id FROM commons.location WHERE code='ECZC')),
		('El Pangui', 'ECZCEP', (SELECT id FROM commons.location WHERE code='ECZC')),
		('Nangaritza', 'ECZCNG', (SELECT id FROM commons.location WHERE code='ECZC')),
		('Palanda', 'ECZCPL', (SELECT id FROM commons.location WHERE code='ECZC')),
		('Paquisha', 'ECZCPQ', (SELECT id FROM commons.location WHERE code='ECZC')),
		('Yucuambi', 'ECZCYC', (SELECT id FROM commons.location WHERE code='ECZC')),
		('Yantzaza', 'ECZCYT', (SELECT id FROM commons.location WHERE code='ECZC')),
		('Zamora', 'ECZCZM', (SELECT id FROM commons.location WHERE code='ECZC'));