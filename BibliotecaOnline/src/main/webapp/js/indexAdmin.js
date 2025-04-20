/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/gulpfile.js to edit this template
 */


/**
 * Ejecucion inicial
 * @returns {undefined}
 */
document.addEventListener("DOMContentLoaded", event => {
    fetch('http://localhost:8080/BibliotecaOnline/generos') // <-- nombre del servlet
        .then(response => response.json())
        .then(data => {
            const select = document.getElementById('genero');
            // Opción vacía para "todos"
            const opcionVacia = document.createElement('option');
            opcionVacia.value = "";
            opcionVacia.text = "Todos";
            select.appendChild(opcionVacia);

            data.forEach(genero => {
                const option = document.createElement('option');
                option.value = genero;
                option.text = genero;
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Error al cargar géneros:', error));
});

/**
 * 
 */
document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault(); // Evita el envío tradicional del formulario

    const nombre = document.getElementById('nombre').value;
    const genero = document.getElementById('genero').value;

    fetch(`http://localhost:8080/BibliotecaOnline/BuscarLibros?libro=${nombre}&genero=$(genero)}`)
        .then(response => response.json())
        .then(libros => {
            const contenedor = document.querySelector('.contenedor-libros');
            contenedor.innerHTML = ''; // Limpiar resultados anteriores

            if (libros.length === 0) {
                contenedor.innerHTML = '<p>No se encontraron libros.</p>';
                return;
            }

            libros.forEach(libro => {
                const div = document.createElement('div');
                div.classList.add('tarjeta-libro'); // Para estilos futuros si deseas

                div.innerHTML = `
                    <h3>${libro.titulo}</h3>
                    <p><strong>Autor:</strong> ${libro.autor}</p>
                    <p><strong>Género:</strong> ${libro.genero.nombre}</p>
                    <p><strong>Descripción:</strong> ${libro.descripcion}</p>
                    
                `;
                contenedor.appendChild(div);
            });
        })
        .catch(error => {
            console.error('Error al buscar libros:', error);
        });
});






