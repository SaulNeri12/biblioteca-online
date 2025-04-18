<%-- 
    Document   : login.jsp
    Created on : 18 abr 2025, 12:33:27 a.m.
    Author     : nerix
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css"/>
    </head>
    <body>
        
        <%
            // si el usuario tiene una sesion en curso, se lleva directo a la pagna de inicio
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("index.jsp");
                return;
            }
        %>
        
        <h1>Inicio de Sesión</h1>
        
        <p class="parrafo">sheesh</p>
        
        <script src="${pageContext.request.contextPath}/js/index.js"></script>
    </body>
</html>
