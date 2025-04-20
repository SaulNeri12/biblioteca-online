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
        <title>Login</title>
    </head>
    <body>

        <%
            // si el usuario tiene una sesion en curso, se lleva directo a la pagna de inicio
            if (session.getAttribute("usuario") != null) {
                response.sendRedirect("index.jsp");
                return;
            }
        %>

        <h1>Inicio de Sesión(Administrador)</h1>

        <form action="<%= request.getContextPath() %>/loginAdmin" method="post">
            <label for="email">Correo Electrónico:</label><br>
            <input type="email" id="email" name="email" required><br><br>

            <label for="contrasena">Contraseña:</label><br>
            <input type="password" id="contrasena" name="contrasena" required autocomplete="off"><br><br>

            <button type="submit">Entrar</button>
        </form>

    </body>
</html>
