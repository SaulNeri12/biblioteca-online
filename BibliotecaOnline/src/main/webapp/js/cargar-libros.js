/* 
 * Updated on: 18 abr 2025
 * Author: neri
 * Description: Script para cargar y mostrar libros con efectos visuales neón
 */

async function buscarLibros(titulo, genero, autor) {
    let url = "http://localhost:8080/BibliotecaOnline/BuscarLibros";
    const params = new URLSearchParams();
    if (titulo)
        params.append("libro", titulo);
    if (genero)
        params.append("genero", genero);
    if (autor)
        params.append("autor", autor);
    url += "?" + params.toString();
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error("Ocurrió un problema para encontrar los libros");
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Error:", error.message);
        return [];
    }
}
;

function crearCartaLibro(libro, numero = 0) {
    let generos_str = "";
    if (libro.generos && libro.generos.length > 0) { // Corregido: cambié < 1 a > 0
        generos_str = libro.generos.join(", ");
    }
    let portada_libro = `https://covers.openlibrary.org/b/isbn/${libro.isbn}-M.jpg`;

    return `
        <div class="carta-libro" data-numero-carta="${numero}">
            <div class="portada-contenedor">
                <img class="portada-libro" src="${portada_libro}" alt="Portada de ${libro.nombre}">
            </div>
            <div class="informacion-libro">
                <div class="contenedor-titulo-libro">
                    <p class="titulo-libro">${libro.nombre}</p>
                    <p class="autor-libro" data-id-autor="${libro.autor.id}">${libro.autor.nombre}</p>
                </div>
                <div class="pie-carta-libro">
                    <p class="libro-generos">${generos_str}</p>
                </div>
            </div>
        </div>`;
}

function cargarLibros(libros = []) {
    // Asegurarse de que la lista de libros no sea null ni vacía
    if (libros === null || libros.length === 0) {
        const $contenedorLibros = document.querySelector("#contenedor-libros");
        $contenedorLibros.innerHTML = '<p class="no-books-message">No hay libros disponibles en este momento.</p>';
        return;
    }

    const $contenedorLibros = document.querySelector("#contenedor-libros");
    // Limpiar el contenedor antes de agregar los libros
    $contenedorLibros.innerHTML = "";

    // Agregar cada libro al contenedor
    let contador = 0;
    libros.forEach(libro => {
        const $cartaLibro = crearCartaLibro(libro, contador);
        $contenedorLibros.insertAdjacentHTML("beforeend", $cartaLibro);
        contador++;
    });

    // Aplicar efectos visuales después de cargar los libros
    setTimeout(agregarEfectosVisuales, 100);
}

// Función para añadir efectos visuales a las cartas
function agregarEfectosVisuales() {
    const cartasLibro = document.querySelectorAll('.carta-libro');

    cartasLibro.forEach((carta, index) => {
        // Añadir efecto de entrada con retardo basado en el índice
        carta.style.opacity = '0';
        carta.style.transform = 'translateY(20px)';
        carta.style.transition = `opacity 0.5s ease, transform 0.5s ease`;

        setTimeout(() => {
            carta.style.opacity = '1';
            carta.style.transform = 'translateY(0)';
        }, 100 * index);

        // Añadir evento para mostrar efecto hover mejorado
        carta.addEventListener('mouseenter', function () {
            this.style.boxShadow = '0 10px 20px rgba(0, 0, 0, 0.3), 0 0 15px var(--secondary-color)';
            this.style.transform = 'translateY(-5px)';
        });

        carta.addEventListener('mouseleave', function () {
            this.style.boxShadow = '';
            this.style.transform = '';
        });
    });
}

document.addEventListener("DOMContentLoaded", async () => {
    try {
        // Mostrar indicador de carga
        const $contenedorLibros = document.querySelector("#contenedor-libros");
        $contenedorLibros.innerHTML = '<div class="loading-indicator">Cargando libros...</div>';

        // Buscar libros desde la API
        const libros = await buscarLibros();
        console.log("Libros encontrados:", libros);

        // Cargar los libros con efectos visuales
        cargarLibros(libros);

        // Añadir efectos visuales para los enlaces del nav
        const navLinks = document.querySelectorAll('.nav-link');
        navLinks.forEach(link => {
            if (!link.classList.contains('neon-button')) {
                link.addEventListener('mouseenter', function () {
                    this.style.textShadow = `0 0 8px var(--secondary-color), 0 0 20px var(--secondary-color)`;
                });

                link.addEventListener('mouseleave', function () {
                    this.style.textShadow = '';
                });
            }
        });
    } catch (error) {
        console.error("Error al cargar los libros:", error);
        const $contenedorLibros = document.querySelector("#contenedor-libros");
        $contenedorLibros.innerHTML = '<p class="error-message">Ocurrió un error al cargar los libros. Por favor, intenta de nuevo más tarde.</p>';
    }
});