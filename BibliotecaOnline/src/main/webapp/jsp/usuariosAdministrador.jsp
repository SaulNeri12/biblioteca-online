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
                        <th>Fecha de Registro</th
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Usuario u : usuarios) {

                    %>
                    <tr data-fila-id="<%= u.getId()%>">
                        <td><%= u.getId()%></td>
                        <td><%= u.getNombre()%></td>
                        <td><%= u.getEmail()%></td>
                        <td><%= u.getTelefono()%></td>
                        <td><%= u.getFechaNacimiento()%></td>
                        <td>
                            <button class="btn-eliminar-usuario" onclick="eliminarCuentaUsuario(event)">Eliminar</button>
                        </td>
                        <td>
                            <button class="btn-editar-usuario" onclick="editarCuentaUsuario(event)">Editar</button>
                        </td>
                    </tr>
                    <%
                            }
                        }
                    %>  
                </tbody>
            </table>
        </div>

        <script>

            async function eliminarCuentaUsuario(evento) {
                // Obtener la fila que contiene el botón pulsado
                const fila = evento.target.closest("tr");

                // Verificar si la fila y el id de usuario existen
                const idUsuario = fila?.getAttribute("data-fila-id");
                
                if (!idUsuario) {
                    alert("ID del usuario no encontrado.");
                    return;
                }

                // Confirmación del usuario antes de eliminar
                const confirmado = window.confirm("¿Estás seguro de que deseas eliminar este usuario?");
                if (!confirmado) {
                    console.log("Eliminación cancelada.");
                    return;
                }

                // Intentamos realizar la eliminación de forma asíncrona
                try {
                    const respuesta = await fetch(`http://localhost:8080/BibliotecaOnline/usuario?id_usuario=${idUsuario}`, {
                        method: 'DELETE'
                    });

                    // Verificamos si la respuesta fue exitosa
                    if (!respuesta.ok) {
                        const errorData = await respuesta.json(); // Intentamos parsear el mensaje de error
                        alert(`Error al eliminar: ${errorData.error || errorData.mensaje}`);
                        return;
                    }

                    // Si todo salió bien, eliminamos la fila del DOM
                    const data = await respuesta.json();
                    alert(data.mensaje);  // Mostramos el mensaje de éxito
                    fila.remove();  // Eliminar la fila de la tabla

                } catch (error) {
                    // Si ocurre algún error (red, servidor, etc.)
                    console.error("Error de red o inesperado:", error);
                    alert("Ocurrió un error al intentar eliminar el usuario.");
                }
            }


        </script>

        <script src="${pageContext.request.contextPath}/js/usuariosAdmin.js"></script>
    </body>
</html>
