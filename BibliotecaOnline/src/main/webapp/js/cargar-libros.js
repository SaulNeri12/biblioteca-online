/*
 * Author: neri
 * Description: Script que DEFINE funciones para buscar libros y aplicar efectos visuales.
 * La carga inicial es ahora manejada por favoritos.js
 * Updated by AI: Removed DOMContentLoaded, original crearCartaLibro, original cargarLibros.
 */

/**
 * Busca libros en el backend.
 * @param {string} [titulo] - Título a buscar (opcional).
 * @param {string} [genero] - Género a buscar (opcional).
 * @param {string} [autor] - Autor a buscar (opcional).
 * @returns {Promise<Array<Object>>} - Promesa que resuelve a una lista de libros.
 */
async function buscarLibros(titulo, genero, autor) {
    // Asegúrate que BASE_URL esté definida globalmente o pásala como argumento si es necesario.
    // Si BASE_URL solo está en favoritos.js, necesitarás definirla aquí también o refactorizar.
    // Por ahora, asumimos que favoritos.js la define y este script se carga antes,
    // pero es mejor definirla en ambos o en un script de configuración común.
    const BASE_URL_LIBROS = "http://localhost:8080/BibliotecaOnline"; // Definición temporal aquí si es necesario
    let url = `${BASE_URL_LIBROS}/BuscarLibros`;
    const params = new URLSearchParams();
    if (titulo)
        params.append("libro", titulo);
    if (genero)
        params.append("genero", genero);
    // El endpoint /BuscarLibros original no parece aceptar 'autor', verificar si es necesario
    // if (autor)
    //     params.append("autor", autor);

    // Solo añadir '?' si hay parámetros
    const queryString = params.toString();
    if (queryString) {
        url += "?" + queryString;
    }

    try {
        const response = await fetch(url);
        if (!response.ok) {
            // Intentar obtener mensaje de error del backend
            const errorText = await response.text();
             console.error(`Error ${response.status} al buscar libros: ${errorText}`);
            throw new Error(`Ocurrió un problema (${response.status}) para encontrar los libros.`);
        }
        const data = await response.json();
        console.log("Libros encontrados por buscarLibros:", data); // Log para depuración
        return data;
    } catch (error) {
        console.error("Error en buscarLibros:", error.message);
         // Propagar el error para que el llamador (en favoritos.js) lo maneje
         throw error;
        // return []; // Devolver vacío podría ocultar el error
    }
}


/**
 * Función para añadir efectos visuales a las cartas de libro.
 * @param {HTMLElement} contenedor - El elemento contenedor de las cartas.
 */
function agregarEfectosVisuales(contenedor) {
    if (!contenedor) {
        console.warn("Contenedor no proporcionado para agregarEfectosVisuales");
        return;
    }
    // Usar el contenedor para ser más específico
    const cartasLibro = contenedor.querySelectorAll('.carta-libro');

    cartasLibro.forEach((carta, index) => {
        // Añadir efecto de entrada con retardo basado en el índice
        carta.style.opacity = '0';
        carta.style.transform = 'translateY(20px)';
        // Asegurarse que la transición esté definida en CSS para mejor rendimiento
        // carta.style.transition = `opacity 0.5s ease, transform 0.5s ease`; // Mejor en CSS

        setTimeout(() => {
            carta.style.opacity = '1';
            carta.style.transform = 'translateY(0)';
        }, 100 * index); // El retardo sigue siendo útil

        // Eventos de hover (asegúrate que no interfieran con el clic en la estrella)
        carta.addEventListener('mouseenter', function (e) {
             // No aplicar si el hover es sobre el icono de favorito
             if (!e.target.closest('.icono-favorito')) {
                 this.style.boxShadow = '0 10px 20px rgba(0, 0, 0, 0.3), 0 0 15px var(--secondary-color)';
                 this.style.transform = 'translateY(-5px) scale(1.02)'; // Añadir un ligero scale
             }
        });

        carta.addEventListener('mouseleave', function () {
            this.style.boxShadow = ''; // Vuelve al shadow definido en CSS
            this.style.transform = ''; // Vuelve a la transformación base
        });
    });

     // Añadir efectos visuales para los enlaces del nav (si esta es la única inicialización)
     // Si favoritos.js también tiene un DOMContentLoaded, esto podría ejecutarse dos veces.
     // Es mejor centralizar esto en el DOMContentLoaded de favoritos.js
     /*
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
     */
}

// IMPORTANTE: Se ha eliminado el listener DOMContentLoaded de este archivo.
// La inicialización ahora la maneja `favoritos.js`.