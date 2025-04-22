<%-- 
    Document   : login.jsp
    Created on : 18 abr 2025, 12:33:27 a.m.
    Author     : nerix
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <%
            // si el usuario tiene una sesion en curso, se lleva directo a la pagna de inicio
            if (session.getAttribute("usuario") != null) {
                response.sendRedirect("index.jsp");
                return;
            }
        %>
        <div class="container">
            <div class="login-box">
                <h2>Inicio de Sesión</h2>
                <form action="login" method="post">
                    <div class="input-group">
                        <input type="email" id="email" name="email" required>
                        <span class="floating-label">Correo Electrónico</span>
                    </div>
                    <div class="input-group">
                        <input type="password" id="contrasena" name="contrasena" required>
                        <span class="floating-label">Contraseña</span>
                    </div>
                    <button type="submit" class="neon-button">
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        Entrar
                    </button>
                </form>
                <div class="register-link">
                    <p>¿No tienes una cuenta? <a href="/BibliotecaOnline/registro">Crear Cuenta</a></p>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/js/login.js"></script>
    </body>
</html>