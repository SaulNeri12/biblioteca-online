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
        </section>>
        
        <script src="${pageContext.request.contextPath}/js/indexAdmin.js"></script>
        
    </body>
</html>
