/*TRUNCATE commons.location;*/
INSERT INTO commons.location(name, code, parent) VALUES
('Ecuador', 'EC', NULL);
 /*Azuay*/
	INSERT INTO commons.location(name, code, other, parent) VALUES
	('Cuenca', 'ECAZCU', '01001050', (SELECT id FROM commons.location WHERE code='ECAZ')),
        /*El Oro*/
	('Machala', 'ECEOMC','07001050', (SELECT id FROM commons.location WHERE code='ECEO')),
        /*Esmeraldas*/
	('Esmeraldas', 'ECEMEM','08001050', (SELECT id FROM commons.location WHERE code='ECEM')),
        /*Guayas*/
        ('Duran', 'ECGYDR', '09006050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Guayaquil', 'ECGYGQ','09001050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Pascuales', 'ECGYPA','09001054', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Puerto Alfredo Baquerizo Moreno', 'ECGYPB','09002050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Chongon', 'ECGYBL', '09001051', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Daule', 'ECGYDL', '09006050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Milagro', 'ECGYML', '09010050', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Posorja', 'ECGYPO', '09001056', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Roberto Astudillo', 'ECGYRA', '09010054', (SELECT id FROM commons.location WHERE code='ECGY')),
        ('Samborondón', 'ECGYSM', '09016050', (SELECT id FROM commons.location WHERE code='ECGY')),
        /*Imbabura*/
        ('San Pablo', 'ECIMSP', '10004057', (SELECT id FROM commons.location WHERE code='ECIM')),
		('Ibarra', 'ECIMIB', '10001050',(SELECT id FROM commons.location WHERE code='ECIM')),
        ('San Miguel de Ibarra', 'ECIMSM', '10001050', (SELECT id FROM commons.location WHERE code='ECIM')),
		('Otavalo', 'ECIMOT', '10004050', (SELECT id FROM commons.location WHERE code='ECIM')),
        /*Loja*/
		('Loja', 'ECLJLJ','11001050', (SELECT id FROM commons.location WHERE code='ECLJ')),
        /*Los Rios*/   
		('Babahoyo', 'ECLRBH', '12001050', (SELECT id FROM commons.location WHERE code='ECLR')),
		('Quevedo', 'ECLRQV', '12005050', (SELECT id FROM commons.location WHERE code='ECLR')),
        /*Manabi*/
		('Chone', 'ECMBCN', '13003050', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Manta', 'ECMBMT', '13008050', (SELECT id FROM commons.location WHERE code='ECMB')),
		('Portoviejo', 'ECMBPT', '13001050', (SELECT id FROM commons.location WHERE code='ECMB')),
         /* Pichincha */
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
        ('Zámbiza', 'ECPCZA', '17001086', (SELECT id FROM commons.location WHERE code='ECPC')),
        /*Santa Elena*/
        ('La Libertad', 'ECSELB', '24002050',(SELECT id FROM commons.location WHERE code='ECSE')),
		('Salinas', 'ECSESL', '24003050', (SELECT id FROM commons.location WHERE code='ECSE')),
		('Santa Elena', 'ECSESE', '24001050', (SELECT id FROM commons.location WHERE code='ECSE')),
        /*Santo Domingo de los tsachilas*/
		('Santo Domingo', 'ECSDSD','23001050', (SELECT id FROM commons.location WHERE code='ECSD')),
        /*Tungurahua*/
        ('Ambato', 'ECTHAB', '18001050', (SELECT id FROM commons.location WHERE code='ECTH'));