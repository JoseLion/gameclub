INSERT INTO commons.mail_template(code, content, subject)
VALUES('PCHUDT',
	'<p>Sistema Game Club.</p>
	<p></p>
	<p>Se han actualizado los precios de todos los juegos desde pricecharting.com a la fecha: <strong>{{date}}</strong></p>
	<p></p>
	<p>Atentamente,</p>
	<p>El equipo de Game Club</p>',
'Actualización de precios de PriceCharting');