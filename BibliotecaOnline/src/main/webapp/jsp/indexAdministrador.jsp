<%-- 
    Document   : indexAdministrador
    Created on : 19 abr. 2025, 17:44:04
    Author     : skevi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <!-- formulario para la busqueda de un libro -->
        <form action="BuscarLibros" method="get">
            <label for="nombre">Nombre del libro:</label><br>
            <input type="text" id="nombre" name="nombre"><br><br>

            <label for="genero">Género:</label><br>
            <select id="genero" name="genero">
                <!-- Aquí se llenarán los géneros con JavaScript -->
            </select><br><br>

            <button type="submit">Buscar</button>
        </form>
        
        <section class="contenedor-libros"> 
            <!-- Aquí se llenarán los libros consultados con JavaScript -->
        </section>
        
        <!-- modal para la confirmacion de eliminacion de un libro -->
        <dialog class="dialog-eliminacion" closedby="any">
            <div id="modal-confirmacion">
              <div class="modal-contenido">
                <h3>¿Estás seguro de que deseas eliminar este libro?</h3>
                <p>Esta acción no se puede deshacer.</p>
                <div class="modal-botones">
                    <form method="dialog">
                        <button id="btn-cancelar">Cancelar</button>
                        <button id="btn-confirmar">Confirmar</button>
                    </form>
                </div>
              </div>
            </div>
        </dialog>
        
        
        <script src="${pageContext.request.contextPath}/js/indexAdmin.js"></script>
        
    </body>
</html>
