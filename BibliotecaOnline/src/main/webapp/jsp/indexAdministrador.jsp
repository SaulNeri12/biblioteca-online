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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    </head>
    <body>
        
        <!-- boton para agregar un nuevo libro -->
        <button id="btn-agregar" onclick="abrirModalAgregar()">agregar</button>
        
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
                        <button id="btn-confirmar" onclick="eliminarLibro()">Confirmar</button>
                    </form>
                </div>
              </div>
            </div>
        </dialog>
        
        <!-- modal para agregar un libro de un libro -->
        <dialog class="dialog-agregar" closedby="any">
            <div id="modalAgregarLibro" class="modal">
                <div class="modal-contenido">
                <h2>Agregar Libro</h2>
                <form id="formAgregarLibro">
                  <label>ISBN:</label>
                  <input type="text" id="agregar-isbn" required><br>

                  <label>Nombre:</label>
                  <input type="text" id="agregar-nombre" required><br>

                  <label>Descripción:</label>
                  <textarea id="agregar-descripcion" required></textarea><br>

                  <label>Contenido Adulto:</label>
                  <input type="checkbox" id="agregar-contenidoAdulto"><br>

                  <label>Autor:</label>
                  <select id="agregar-autor" required></select><br>

                  <label>Editorial:</label>
                  <input type="text" id="agregar-editorial" required><br>

                  <label>Año de Publicación:</label>
                  <input type="number" id="agregar-anio" required min="1000" max="2100">

                  <label>Número de Páginas:</label>
                  <input type="number" id="agregar-numPaginas" required><br>

                  <label>Géneros:</label>
                  <select id="agregar-genero1" required>
                        <!-- Aquí se llenarán los géneros con JavaScript -->
                    </select>
                  <label>y</label>
                  <select id="agregar-genero2" required>
                        <!-- Aquí se llenarán los géneros con JavaScript -->
                  </select>
                  <button class="btn-agregar" onclick="agregarLibro(event)">Agregar</button>
                </form>
                <form method="dialog">
                    <button>Cancelar</button>
                </form>
              </div>
            </div>
        </dialog>
        
        <!-- modal para la edicion de un libro -->
        <dialog class="dialog-editar" closedby="any">
            <div id="modalEditarLibro" class="modal">
                <h2>Agregar Libro</h2>
                <form id="formEditarLibro">
                    
                  <label>ISBN:</label>
                  <input type="text" id="editar-isbn" required><br>

                  <label>Nombre:</label>
                  <input type="text" id="editar-nombre" required><br>

                  <label>Descripción:</label>
                  <textarea id="editar-descripcion" required></textarea><br>

                  <label>Contenido Adulto:</label>
                  <input type="checkbox" id="editar-contenidoAdulto"><br>

                  <label>Autor:</label>
                  <select id="editar-autor" required></select><br>

                  <label>Editorial:</label>
                  <input type="text" id="editar-editorial" required><br>

                  <label>Año de Publicación:</label>
                  <input type="number" id="editar-anio" required min="1" max="2030"><br>

                  <label>Número de Páginas:</label>
                  <input type="number" id="editar-numPaginas" required><br>

                  <label>Géneros:</label>
                  <select id="editar-genero1" required>
                        <!-- Aquí se llenarán los géneros con JavaScript -->
                    </select>
                  <label>y</label>
                  <select id="editar-genero2" required>
                        <!-- Aquí se llenarán los géneros con JavaScript -->
                  </select>
                  <button class="btn-editar" onclick="editarLibro()">Editar</button>
                </form>
                <form method="dialog">
                    <button>Cancelar</button>
                </form>
              </div>
        </dialog>
        
        
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="${pageContext.request.contextPath}/js/indexAdmin.js"></script>
        
    </body>
</html>
