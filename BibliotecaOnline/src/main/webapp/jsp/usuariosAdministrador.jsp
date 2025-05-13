<%-- 
    Document   : usuariosAdmin
    Created on : 6 may 2025, 1:33:44 p.m.
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

        <a href="${pageContext.request.contextPath}/jsp/indexAdministrador.jsp">Inicio</a>

        <h2>Usuarios Registrados</h2>

        <div class="usuarios-lista-contenedor">
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

                if (!usuarios.isEmpty()) {
            %>
            <table class="users-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Correo</th>
                        <th>Telefono</th>
                        <th>Fecha de Registro</th>
                        <th>Opciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    %>
                    <%
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
        <dialog id="dialog-eliminar" closedby="any">
            <div class="contenido-dialog-eliminar">
                <p>¿Esta seguro que desea eliminar al usuario?</p>
                <p>esta accion no puede ser desecha</p>
                <form method="dialog">
                    <button id="btn-eliminar">
                        Eliminar
                    </button>
                </form>
            </div>
        </dialog>
        
        <!-- ventana de dialogo para la edicion de un usuario -->
        <dialog id="dialog-editar" closedby="any">  
            <div class="contenido-dialog-editar">
                <form class="form-editar-usuario" method="dialog">
                    <label>Nombre:</label>
                    <input type="text" id="nombre-usuario" required><br>
                    <label>Correo:</label>
                    <input type="text" id="correo-usuario" required><br>
                    <label>Telefono:</label>
                    <input type="text" id="telefono-usuario" required><br>
                    <label>Fecha de registro:</label>
                    <input type="date" id="fecha-registro-usuario" required><br>
                    <button id="btn-actualizar">Actualizar</button>
                </form>
            </div>
        </dialog>
        
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="${pageContext.request.contextPath}/js/usuariosAdmin.js"></script>
        
    </body>
</html>
