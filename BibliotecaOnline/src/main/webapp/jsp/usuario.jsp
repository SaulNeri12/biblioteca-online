<%-- 
    Document   : usuario
    Created on : 18 abr 2025, 12:17:47 p.m.
    Author     : nerix
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Datos del usuario registrado:</h2>
        <p><strong>Nombre:</strong> ${nombre}</p>
        <p><strong>Email:</strong> ${email}</p>
        <p><strong>Teléfono:</strong> ${telefono}</p>
        <p><strong>Fecha de nacimiento:</strong> ${fechaNac}</p>
        <p><strong>Contraseña:</strong> ${contrasena}</p> <!-- Ojo, solo para fines de prueba -->
    </body>
</html>
