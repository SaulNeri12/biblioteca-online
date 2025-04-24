<%-- 
    Document   : login
    Created on : 18 abr 2025, 12:24:32 a.m.
    Author     : neri
--%>
<%@page import="com.equipoweb.bibliotecanegocio.entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Biblioteca Online</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <header class="header">
                <div class="header-content">
                    <h1 class="site-title">Biblioteca<span class="neon-text">Online</span></h1>
                    <nav class="main-nav">
                        <% 
                            Usuario usuario = (Usuario) session.getAttribute("usuario");
                            if (usuario != null) {
                        %>
                            <a href="/BibliotecaOnline/perfil" class="nav-link">Mi Perfil</a>
                            <a href="/BibliotecaOnline/logout" class="nav-link">Cerrar Sesión</a>
                        <% } else { %>
                            <a href="/BibliotecaOnline/login" class="nav-link neon-button">Iniciar Sesión</a>
                            <a href="/BibliotecaOnline/registro" class="nav-link">Crear Cuenta</a>
                        <% } %>
                    </nav>
                </div>
            </header>

            <main class="main-content">
                <% if (usuario != null) { %>
                    <section class="user-info-section">
                        <div class="card user-card">
                            <h2 class="card-title">Datos de Usuario</h2>
                            <div class="user-details">
                                <div class="detail-item">
                                    <span class="detail-label">Nombre:</span>
                                    <span class="detail-value"><%= usuario.getNombre() %></span>
                                </div>
                                <div class="detail-item">
                                    <span class="detail-label">Email:</span>
                                    <span class="detail-value"><%= usuario.getEmail() %></span>
                                </div>
                                <div class="detail-item">
                                    <span class="detail-label">Teléfono:</span>
                                    <span class="detail-value"><%= usuario.getTelefono() %></span>
                                </div>
                                <div class="detail-item">
                                    <span class="detail-label">Fecha de Nacimiento:</span>
                                    <span class="detail-value"><%= usuario.getFechaNacimiento() %></span>
                                </div>
                            </div>
                        </div>
                    </section>
                <% } else { %>
                    <section class="welcome-section">
                        <div class="welcome-card">
                            <h2 class="welcome-title">Bienvenido a la Biblioteca Online</h2>
                            <p class="welcome-message">Explora nuestra colección de libros digitales. Para acceder a todas las funcionalidades, inicia sesión o crea una cuenta.</p>
                        </div>
                    </section>
                <% } %>

                <section class="books-section">
                    <h2 class="section-title">Libros Disponibles</h2>
                    <div id="contenedor-libros" class="books-container">
                        <!-- El contenido se cargará dinámicamente desde JavaScript -->
                    </div>
                </section>
            </main>
            
            <footer class="footer">
                <p>&copy; 2025 BibliotecaOnline - Todos los derechos reservados</p>
            </footer>
        </div>
        
        <script src="${pageContext.request.contextPath}/js/cargar-libros.js"></script>
    </body>
</html>