<%-- 
    Document   : login
    Created on : 18 abr 2025, 12:24:32 a.m.
    Author     : neri
--%>

<%@page import="com.equipoweb.bibliotecanegocio.entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <p>this is my JSP</p>
        
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
        
        <a href="/BibliotecaOnline/login">Iniciar Sesión</a>
        <a href="/BibliotecaOnline/registro">Crear Cuenta</a>
    </body>
</html>
