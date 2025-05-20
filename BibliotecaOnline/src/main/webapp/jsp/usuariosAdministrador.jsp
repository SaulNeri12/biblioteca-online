<%-- 
    Document   : usuariosAdmin
    Created on : 6 may 2025, 1:33:44 p.m.
    Author     : nerix
--%>

<%@page import="com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException"%>
<%@page import="java.util.Optional"%>
<%@page import="com.equipoweb.bibliotecanegocio.entidades.Usuario"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="com.equipoweb.bibliotecanegocio.dao.interfaces.IUsuariosDAO"%>
<%@page import="com.equipoweb.bibliotecanegocio.dao.FabricaUsuariosDAO"%>
<%@page import="com.equipoweb.bibliotecanegocio.dao.FabricaUsuariosDAO"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administracion | Usuarios Registrados</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/usuariosAdmin.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    </head>
    <body>
        <%
            // si el usuario tiene una sesion en curso, se lleva directo a la pagna de inicio
            if (session.getAttribute("usuarioAdmin") == null) {
                request.getRequestDispatcher("/jsp/loginAdministrador.jsp").forward(request, response);
                return;
            }

            IUsuariosDAO usuariosDAO = FabricaUsuariosDAO.getInstance().crearDAO();
        %>

        <a href="${pageContext.request.contextPath}/jsp/indexAdministrador.jsp" class="btn-inicio">← Volver al Inicio</a>

        <h2>Usuarios Registrados</h2>

        <div class="usuarios-lista-contenedor">
            <input type="text" id="searchInput" placeholder="Buscar usuario..." class="search-input">
            
            <%
                List<Usuario> usuarios = null;

                try {
                    usuarios = Optional.of(usuariosDAO.obtenerUsuariosTodos()).orElse(null);

                    if (usuarios == null || usuarios.isEmpty()) {
                        throw new DAOException("No se encontraron usuarios registrados.");
                    }
                } catch (DAOException ex) {
            %>
            <div class="usuarios-alerta">
                <p><%= ex.getMessage()%></p>
            </div>
                
            <%
                }

                if (usuarios != null && !usuarios.isEmpty()) {
            %>
            <table class="users-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Correo</th>
                        <th>Telefono</th>
                        <th>Fecha de Nacimiento</th>
                        <th colspan="2">Opciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        for (Usuario u : usuarios) {
                    %>
                    <tr data-fila-id="<%= u.getId()%>">
                        <td><%= u.getId()%></td>
                        <td><%= u.getNombre()%></td>
                        <td><%= u.getEmail()%></td>
                        <td><%= u.getTelefono()%></td>
                        <td><%= sdf.format(u.getFechaNacimiento()) %></td>
                        <td>
                            <button class="btn-eliminar-usuario" onclick="abrirModalEliminar(event)">Eliminar</button>
                        </td>
                        <td>
                            <button class="btn-editar-usuario" onclick="abrirModalEditar(event)">Editar</button>
                        </td>
                    </tr>
                    <%
                        }
                    }
                    %>  
                </tbody>
            </table>
        </div>
        
        <!-- ventana de dialogo para la confirmacion de eliminacion -->
<dialog id="dialog-eliminar" class="login-box">
            <div class="contenido-dialog-eliminar">
                <h3>Confirmar eliminación</h3>
                <p>¿Está seguro que desea eliminar al usuario?</p>
                <p>Esta acción no puede ser deshecha</p>
                <div class="dialog-buttons">
<button id="btn-eliminar" class="neon-button">
                        Eliminar
                    <span></span><span></span><span></span><span></span>
                    </button>
<button id="btn-cancelar-eliminar" class="neon-button">
                        Cancelar
                    <span></span><span></span><span></span><span></span>
                    </button>
                </div>
            </div>
        </dialog>
        
        <!-- ventana de dialogo para la edicion de un usuario -->
<dialog id="dialog-editar" class="login-box">
            <div class="contenido-dialog-editar">
                <h3>Editar usuario</h3>
                <form class="form-editar-usuario">
                    <div class="form-group">
                        <label for="nombre-usuario">Nombre:</label>
                        <input type="text" id="nombre-usuario" required>
                    </div>
                    <div class="form-group">
                        <label for="correo-usuario">Correo:</label>
                        <input type="email" id="correo-usuario" required>
                    </div>
                    <div class="form-group">
                        <label for="telefono-usuario">Teléfono:</label>
                        <input type="text" id="telefono-usuario" required>
                    </div>
                    <div class="form-group">
                        <label for="fecha-registro-usuario">Fecha de Nacimiento:</label>
                        <input type="date" id="fecha-registro-usuario" required>
                    </div>
                    <div class="dialog-buttons">
<button id="btn-actualizar" class="neon-button" type="button">Actualizar</button>
<span></span><span></span><span></span><span></span>
<button id="btn-cancelar-editar" class="neon-button" type="button">Cancelar</button>
<span></span><span></span><span></span><span></span>
                    </div>
                </form>
            </div>
        </dialog>
        
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="${pageContext.request.contextPath}/js/usuariosAdmin.js"></script>
        
    </body>
</html>
