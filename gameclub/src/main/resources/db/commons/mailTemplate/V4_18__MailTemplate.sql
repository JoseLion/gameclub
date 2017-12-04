INSERT INTO commons.mail_template(code, content, subject)
VALUES('WRKWUS',
'<p>Sistema Game Club.</p>
<p></p>
<p>Un usuario desearía trabjar con nosotros:</p>
<p></p>
<p><strong>Nombre:</strong> {{name}}</p>
<p><strong>Correo:</strong> {{email}}</p>
<p><strong>Mensaje:</strong></p>
<p>{{message}}</p>
<p></p>
<p></p>
<p>Atentamente,</p>
<p>El equipo de Game Club</p>',
'Contáctanos Game Club');