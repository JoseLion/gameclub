/*TRUNCATE commons.location;*/
INSERT INTO commons.location(name, code, parent) VALUES
('Ecuador', 'EC', NULL);
 	/*Azuay*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Azuay', 'ECAZ', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
		('Cuenca', 'ECAZCU', '01001050', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Chordeleg', 'ECAZCH', '01011050', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Gualaceo', 'ECAZGL', '01003050', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Paute', 'ECAZPT', '01005050', (SELECT id FROM commons.location WHERE code='ECAZ')),
		('Sigsig', 'ECAZSG', '01009050', (SELECT id FROM commons.location WHERE code='ECAZ'));
		
	/*Cañar*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Cañar', 'ECCN', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
		('Cañar', 'ECCNCN', '03003050', (SELECT id FROM commons.location WHERE code='ECCN')),
		('Azogues', 'ECCNAZ', '03001050', (SELECT id FROM commons.location WHERE code='ECCN')),
		('Biblián', 'ECCNBL', '03002050', (SELECT id FROM commons.location WHERE code='ECCN')),
		('La Troncal', 'ECCNTR', '03004050', (SELECT id FROM commons.location WHERE code='ECCN'));
		
	/*Carchi*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Carchi', 'ECCH', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
		('Tulcán', 'ECCHTL', '04001050', (SELECT id FROM commons.location WHERE code='ECCH'));
	
	/*Chimborazo*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Chimborazo', 'ECCM', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
		('Riobamba', 'ECCMRB', '06001050', (SELECT id FROM commons.location WHERE code='ECCM')),
		('Colta', 'ECCMCT', '06003050', (SELECT id FROM commons.location WHERE code='ECCM'));
		
	/*Cotopaxi*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Cotopaxi', 'ECCX', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
		('Latacunga', 'ECCXLT', '05001050', (SELECT id FROM commons.location WHERE code='ECCX'));
	
	/*El Oro*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('El Oro', 'ECEO', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
		('Machala', 'ECEOMC','07001050', (SELECT id FROM commons.location WHERE code='ECEO')),
		('El Guabo', 'ECEOEG', '07006050', (SELECT id FROM commons.location WHERE code='ECEO')),
		('Pasaje', 'ECEOPJ', '07009050', (SELECT id FROM commons.location WHERE code='ECEO')),
		('Santa Rosa', 'ECEOSR', '07012050', (SELECT id FROM commons.location WHERE code='ECEO'));
		
		
    /*Esmeraldas*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Esmeraldas', 'ECEM', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
		('Esmeraldas', 'ECEMEM','08001050', (SELECT id FROM commons.location WHERE code='ECEM'));
       
	/*Guayas*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Guayas', 'ECGY', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
        ('Durán', 'ECGYDR', '09006050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Guayaquil', 'ECGYGQ','09001050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Pascuales', 'ECGYPA','09001054', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Daule', 'ECGYDL', '09006050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('El Triunfo', 'ECGYET', '09009050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Milagro', 'ECGYML', '09010050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Naranjal', 'ECGYNJ', '09011050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Naranjito', 'ECGYNT', '09012050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Nobol', 'ECGYNB', '09025050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Playas', 'ECGYPY', '09021050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Posorja', 'ECGYPO', '09001056', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Samborondón', 'ECGYSM', '09016050', (SELECT id FROM commons.location WHERE code='ECGY'));
        
	/*Imbabura*/
    INSERT INTO commons.location(name, code, other, parent) VALUES
	('Imbabura', 'ECIM', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
        ('San Pablo', 'ECIMSP', '10004057', (SELECT id FROM commons.location WHERE code='ECIM')),
		('Ibarra', 'ECIMIB', '10001050',(SELECT id FROM commons.location WHERE code='ECIM')),
        ('San Miguel de Ibarra', 'ECIMSM', '10001050', (SELECT id FROM commons.location WHERE code='ECIM')),
		('Otavalo', 'ECIMOT', '10004050', (SELECT id FROM commons.location WHERE code='ECIM'));
		
	/*Loja*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Loja', 'ECLJ', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
		('Loja', 'ECLJLJ','11001050', (SELECT id FROM commons.location WHERE code='ECLJ'));
	
	/*Los Rios*/ 
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Los Ríos', 'ECLR', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
		('Babahoyo', 'ECLRBH', '12001050', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Quevedo', 'ECLRQV', '12005050', (SELECT id FROM commons.location WHERE code='ECLR'));
	
	
    /*Manabi*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Manabí', 'ECMB', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
		('Chone', 'ECMBCN', '13003050', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Manta', 'ECMBMT', '13008050', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Portoviejo', 'ECMBPT', '13001050', (SELECT id FROM commons.location WHERE code='ECMB'));
		
	/*Orellana*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Orellana', 'ECON', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
		('El Coca', 'ECONEC', '22001050', (SELECT id FROM commons.location WHERE code='ECON'));
     
	/* Pichincha */
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Pichincha', 'ECPC', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
        ('Alangasí', 'ECPCAL', '17001051', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Amaguaña', 'ECPCAM', '17001052', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Calacalí', 'ECPCCA', '17001054', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Calderón', 'ECPCCL', '17001055', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Checa', 'ECPCCH', '17001059', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Conocoto', 'ECPCCO', '17001056', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Cumbaya', 'ECPCCU', '17001057', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('El Chaupi', 'ECPCEC', '17003054',(SELECT id FROM commons.location WHERE code='ECPC')),
        ('Guangopolo', 'ECPCGU', '17001062', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Guayllabamba', 'ECPCGA', '17001063', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('La Merced', 'ECPCLM', '17001064', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Llano Chico', 'ECPCLC', '17001065', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Machachi', 'ECPCMA', '17003050', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Nayón', 'ECPCNY', '17001070', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Pifo', 'ECPCPI', '17001075', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Pintag', 'ECPCPN', '17001076', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Pomasqui', 'ECPCPO', '17001077', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Puembo', 'ECPCPU', '17001079', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Quinche', 'ECPCQU', '17001060', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Quito', 'ECPCQT', '17001050', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Rumiñahui', 'ECPCRU', '17005050', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Rumipamba', 'ECPCRP', '17005052', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('San Antonio', 'ECPCSA', '17001080', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('San Pedro de Taboada', 'ECPCST', '17005002', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('San Rafael', 'ECPCSR', '17005003', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Sangolqui', 'ECPCSG', '17005050', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Santa Rosa de Cuzubamba', 'ECPCSC', '17002055',(SELECT id FROM commons.location WHERE code='ECPC')),
        ('Tababela', 'ECPCTB', '17001083', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Tambillo', 'ECPCTA', '17003056', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Tumbaco', 'ECPCTU', '17001084', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Uyumbicho', 'ECPCUY', '17003057',(SELECT id FROM commons.location WHERE code='ECPC')),
        ('Yaruqui', 'ECPCYA', '17001085', (SELECT id FROM commons.location WHERE code='ECPC')),
        ('Zámbiza', 'ECPCZA', '17001086', (SELECT id FROM commons.location WHERE code='ECPC'));
        
    /*Santa Elena*/
    INSERT INTO commons.location(name, code, other, parent) VALUES
	('Santa Elena', 'ECSE', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
        ('La Libertad', 'ECSELB', '24002050',(SELECT id FROM commons.location WHERE code='ECSE')),
		('Salinas', 'ECSESL', '24003050', (SELECT id FROM commons.location WHERE code='ECSE')),
		('Santa Elena', 'ECSESE', '24001050', (SELECT id FROM commons.location WHERE code='ECSE'));
		
    /*Santo Domingo de los tsachilas*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Santo Domingo de los Tsachilas', 'ECSD', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
		('Santo Domingo', 'ECSDSD','23001050', (SELECT id FROM commons.location WHERE code='ECSD'));
		
	/*Sucumbios*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Sucumbios', 'ECSC', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
		('Lago Agrio', 'ECSCLG', '21001050', (SELECT id FROM commons.location WHERE code='ECSC'));
		
    /*Tungurahua*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Tungurahua', 'ECTH', NULL, (SELECT id FROM commons.location WHERE code='EC'));
		INSERT INTO commons.location(name, code, other, parent) VALUES
        ('Ambato', 'ECTHAB', '18001050', (SELECT id FROM commons.location WHERE code='ECTH')),
        ('Cevallos', 'ECTHCV', '18003050', (SELECT id FROM commons.location WHERE code='ECTH'));