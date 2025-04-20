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
        
        <!-- boton para agregar un nuevo libro -->
        <button id="btn-agregar">agregar</button>
        
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
        
        <!-- modal para la confirmacion de eliminacion de un libro -->
        <dialog class="dialog-agregar" closedby="any">
            <div id="modalAgregarLibro" class="modal" style="display: none;">
                <div class="modal-contenido">
                <span class="cerrar" onclick="cerrarModalAgregar()">&times;</span>
                <h2>Agregar Libro</h2>
                <form id="formAgregarLibro">
                  <label>ISBN:</label>
                  <input type="text" id="isbn" required><br>

                  <label>Nombre:</label>
                  <input type="text" id="nombre" required><br>

                  <label>Descripción:</label>
                  <textarea id="descripcion" required></textarea><br>

                  <label>Contenido Adulto:</label>
                  <input type="checkbox" id="contenidoAdulto"><br>

                  <label>Autor:</label>
                  <select id="autor" required></select><br>

                  <label>Editorial:</label>
                  <input type="text" id="editorial" required><br>

                  <label>Año de Publicación:</label>
                  <input type="number" id="anio" required><br>

                  <label>Número de Páginas:</label>
                  <input type="number" id="numPaginas" required><br>

                  <label>Géneros:</label>
                  <select id="generos" multiple required></select><br>

                  <button type="submit">Guardar Libro</button>
                </form>
                <form method="dialog">
                    <button>Cerrar</button>
                </form>
              </div>
            </div>
        </dialog>
        
        <!-- modal para la confirmacion de eliminacion de un libro -->
        <dialog class="dialog-editar" closedby="any">
            <div id="modalAgregarLibro" class="modal" style="display: none;">
                <div class="modal-contenido">
                <span class="cerrar" onclick="cerrarModalAgregar()">&times;</span>
                <h2>Agregar Libro</h2>
                <form id="formAgregarLibro">
                  <label>ISBN:</label>
                  <input type="text" id="isbn" required><br>

                  <label>Nombre:</label>
                  <input type="text" id="nombre" required><br>

                  <label>Descripción:</label>
                  <textarea id="descripcion" required></textarea><br>

                  <label>Contenido Adulto:</label>
                  <input type="checkbox" id="contenidoAdulto"><br>

                  <label>Autor:</label>
                  <select id="autor" required></select><br>

                  <label>Editorial:</label>
                  <input type="text" id="editorial" required><br>

                  <label>Año de Publicación:</label>
                  <input type="number" id="anio" required><br>

                  <label>Número de Páginas:</label>
                  <input type="number" id="numPaginas" required><br>

                  <label>Géneros:</label>
                  <select id="generos" multiple required></select><br>

                  <button type="submit">Guardar Libro</button>
                </form>
                <form method="dialog">
                    <button>Cerrar</button>
                </form>
              </div>
            </div>
        </dialog>
        
        <script src="${pageContext.request.contextPath}/js/indexAdmin.js"></script>
        
    </body>
</html>
