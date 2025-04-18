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
            // si el usuario tiene una sesion en curso, se lleva directo
            // a la pagna de inicio
            if (session.getAttribute("usuario") != null) {
                response.sendRedirect("index.jsp");
                return;
            }
        %>
        <h1>Crear Cuenta</h1>
    </body>
</html>
