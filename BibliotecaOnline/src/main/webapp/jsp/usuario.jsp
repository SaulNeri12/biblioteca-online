<%-- 
    Document   : usuario
    Created on : 18 abr 2025, 12:17:47 p.m.
    Author     : nerix
--%>

<%@page import="com.equipoweb.bibliotecanegocio.entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% 
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario != null) {
        %>
        <h1>Datos de la sesion</h1>
        
        <p>Nombre: <%= usuario.getNombre() %></p>
        <p>Email: <%= usuario.getEmail() %></p>
        <p>Telefono: <%= usuario.getTelefono()  %></p>
        <p>Fecha de Nacimiento: <%= usuario.getFechaNacimiento() %></p>
        
        <%  } else { %>
        <p> TEST: no hay sesion activa</p>
        <%  } %>
        
    </body>
</html>
