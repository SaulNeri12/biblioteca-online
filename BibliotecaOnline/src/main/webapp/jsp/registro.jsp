<%-- 
    Document   : signup
    Created on : 18 abr 2025, 12:36:40 a.m.
    Author     : nerix
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear Cuenta</title>
    </head>
    <body>
        <%
            // si el usuario tiene una sesion en curso, se lleva directo a la pagna de inicio
            if (session.getAttribute("usuario") != null) {
                response.sendRedirect("index.jsp");
                return;
            }
        %>
        <h1>Crear Cuenta</h1>

        <form id="form-registro" action="registro" method="post">
            <label for="nombre">Nombre de usuario:</label>
            <input type="text" name="nombre" id="nombre" required>

            <label for="email">Correo Electrónico:</label>
            <input type="email" name="email" id="email" required>

            <label for="telefono">Teléfono:</label>
            <input type="text" name="telefono" id="telefono" required>

            <label for="fecha_nac">Fecha de Nacimiento:</label>
            <input type="date" name="fecha_nac" id="fecha_nac" required>

            <label for="contrasena">Contraseña:</label>
            <input type="password" name="contrasena" id="contrasena" required>

            <label for="contrasena-confirm">Confirmar Contraseña:</label>
            <input type="password" name="contrasena-confirm" id="contrasena-confirm" required>

            <button type="submit">Registrar</button>
        </form>
        
        <script src="${pageContext.request.contextPath}/js/registro-form.js"></script>
    </body>
</html>
