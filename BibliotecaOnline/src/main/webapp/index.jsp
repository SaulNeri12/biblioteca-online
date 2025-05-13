<%-- 
    Document   : index.jsp (Versión Final con Favoritos Integrado)
    Created on : 18 abr 2025, 12:24:32 a.m.
    Author     : neri
    Description: Página principal de la Biblioteca Online con funcionalidad de favoritos.
--%>
<%@page import="com.equipoweb.bibliotecanegocio.entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es"> <%-- Añadido lang="es" para indicar el idioma --%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Biblioteca Online</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">

        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">

        <%-- Asegúrate que estas rutas o CDN sean accesibles --%>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-9usAa10IRO0HhonpyAIVpjrylPvoDwiPUiKdWk5t3PyolY1cOd4DSE0Ga+ri4AuTroPR5aQvXU9xC6qOPnzFeg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    </head>
    <body>
        <%-- Contenedor principal de la página --%>
        <div class="container">

            <%-- Cabecera con título y navegación --%>
            <header class="header">
                <div class="header-content">
                    <h1 class="site-title">Biblioteca<span class="neon-text">Online</span></h1>
                    <nav class="main-nav">
                        <%
                            // Obtener usuario de la sesión
                            Usuario usuario = (Usuario) session.getAttribute("usuario");

                            // *** Recordatorio Crucial ***
                            // Asegúrate que en tu Servlet de Login, al validar correctamente al usuario,
                            // guardas tanto el objeto Usuario como su ID (Long) en la sesión:
                            // session.setAttribute("usuario", usuarioLogueado);
                            // session.setAttribute("usuarioId", usuarioLogueado.getId());
                            // Los servlets de Favoritos necesitan "usuarioId".

                            if (usuario != null) {
                                // --- Navegación para Usuario Logueado ---
                        %>
                            <a href="${pageContext.request.contextPath}/perfil" class="nav-link">Mi Perfil</a> <%-- Usar JSTL/EL es preferible a scriptlets --%>

                            <%-- Botón para abrir el modal de favoritos --%>
                            <button id="btn-ver-favoritos" class="nav-link" title="Ver mis libros favoritos">
                                <i class="fas fa-star"></i> Mis Favoritos
                            </button>

                            <a href="${pageContext.request.contextPath}/logout" class="nav-link">Cerrar Sesión</a>
                        <%
                            } else {
                                // --- Navegación para Usuario NO Logueado ---
                        %>
                            <a href="${pageContext.request.contextPath}/login" class="nav-link neon-button">Iniciar Sesión</a>
                            <a href="${pageContext.request.contextPath}/registro" class="nav-link neon-button">Crear Cuenta</a>
                        <%
                            } // Fin del else
                        %>
                    </nav>
                </div>
            </header>

            <%-- Contenido Principal (cambia según si el usuario está logueado) --%>
            <main class="main-content">
                <% if (usuario != null) { %>
                    <%-- Sección de Información del Usuario Logueado --%>
                    <section class="user-info-section">
                         <div class="card user-card">
                             <h2 class="card-title">Datos de Usuario</h2>
                             <div class="user-details">
                                 <div class="detail-item">
                                     <span class="detail-label">Nombre:</span>
                                     <%-- Usar JSTL/EL <c:out value="${usuario.nombre}"/> es más seguro que scriptlets --%>
                                     <span class="detail-value"><%= usuario.getNombre() != null ? usuario.getNombre() : "" %></span>
                                 </div>
                                 <div class="detail-item">
                                     <span class="detail-label">Email:</span>
                                     <span class="detail-value"><%= usuario.getEmail() != null ? usuario.getEmail() : "" %></span>
                                 </div>
                                 <div class="detail-item">
                                     <span class="detail-label">Teléfono:</span>
                                     <span class="detail-value"><%= usuario.getTelefono() != null ? usuario.getTelefono() : "" %></span>
                                 </div>
                                 <div class="detail-item">
                                     <span class="detail-label">Fecha de Nacimiento:</span>
                                     <%-- Formatear la fecha sería ideal aquí --%>
                                     <span class="detail-value"><%= usuario.getFechaNacimiento() != null ? usuario.getFechaNacimiento().toString() : "" %></span>
                                 </div>
                             </div>
                         </div>
                    </section>
                <% } else { %>
                     <%-- Sección de Bienvenida para Usuarios NO Logueados --%>
                    <section class="welcome-section">
                         <div class="welcome-card">
                             <h2 class="welcome-title">Bienvenido a la Biblioteca Online</h2>
                             <p class="welcome-message">Explora nuestra colección de libros digitales. Para acceder a todas las funcionalidades, inicia sesión o crea una cuenta.</p>
                         </div>
                     </section>
                <% } %>

                <%-- Sección donde se cargarán los libros dinámicamente --%>
                <section class="books-section">
                    <h2 class="section-title">Libros Disponibles</h2>
                    <div id="contenedor-libros" class="books-container">
                        <%-- El JS reemplazará esto, pero dejamos un indicador inicial --%>
                        <div class="loading-indicator">Cargando libros...</div>
                    </div>
                </section>
            </main>

            <%-- Pie de página --%>
            <footer class="footer">
                <p>&copy; <%= java.time.Year.now().getValue() %> BibliotecaOnline - Todos los derechos reservados</p> <%-- Año dinámico --%>
            </footer>

        </div> <%-- Fin de .container --%>

        <%-- Estructura HTML del Modal de Favoritos (fuera del .container principal es común) --%>
        <dialog id="modal-favoritos" class="dialog-favoritos" closedby="any">
          <h2 class="modal-title">Mis Libros Favoritos</h2>
          <%-- Contenedor donde el JS cargará las tarjetas de libros favoritos --%>
          <div id="contenedor-favoritos-modal" class="books-container modal-books-container">
             <p class="loading-indicator">Cargando favoritos...</p> <%-- Indicador inicial --%>
          </div>
          <%-- Acciones del modal (botón de cierre) --%>
          
        </dialog>

        <%-- Inclusión de Scripts JavaScript al final del body --%>
        <%-- `defer` asegura que se ejecuten después de parsear el HTML y en orden --%>
        <script src="${pageContext.request.contextPath}/js/cargar-libros.js" defer></script>
        <script src="${pageContext.request.contextPath}/js/favoritos.js" defer></script>

    </body>
</html>