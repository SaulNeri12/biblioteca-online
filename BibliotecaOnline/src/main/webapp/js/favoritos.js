/*
 * favoritos.js
 * Maneja la funcionalidad de favoritos y ORQUESTA la carga inicial de la página.
 * Depende de: SweetAlert2, FontAwesome, y funciones de cargar-libros.js (buscarLibros, agregarEfectosVisuales).
 * 
 */

// URL Base de la aplicación
const BASE_URL = "http://localhost:8080/BibliotecaOnline";

// Almacenará los ISBNs de los libros favoritos del usuario actual
let listaIsbnFavoritos = new Set();

// --- FUNCIONES API (agregarFavoritoAPI, eliminarFavoritoAPI, obtenerFavoritosAPI) ---
// Mantener las definiciones que ya tenías aquí, son correctas.

async function agregarFavoritoAPI(isbn) {
    const params = new URLSearchParams();
    params.append('isbn', isbn);
    try {
        const response = await fetch(`${BASE_URL}/AgregarFavorito`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: params
        });
        if (response.ok) {
            console.log(`Libro ${isbn} agregado a favoritos.`);
            listaIsbnFavoritos.add(isbn);
            return true;
        } else {
            const errorData = await response.json().catch(() => ({ error: `Error ${response.status}` }));
            const errorMessage = errorData?.error || `Error ${response.status} al agregar favorito.`;
            console.error("Error al agregar favorito:", response.status, errorMessage);
            Swal.fire({ icon: 'error', title: 'Error', text: errorMessage, toast: true, position: 'top-end', timer: 3000, showConfirmButton: false });
            if (response.status === 401) { console.warn("Usuario no autenticado."); /* Considera redirigir */ }
            return false;
        }
    } catch (error) {
        console.error("Error de red al agregar favorito:", error);
        Swal.fire({ icon: 'error', title: 'Error de Red', text: 'No se pudo conectar.', toast: true, position: 'top-end', timer: 3000, showConfirmButton: false });
        return false;
    }
}

async function eliminarFavoritoAPI(isbn) {
    try {
        const response = await fetch(`${BASE_URL}/EliminarFavorito?isbn=${encodeURIComponent(isbn)}`, { method: 'DELETE' });
        if (response.ok) {
            console.log(`Libro ${isbn} eliminado de favoritos.`);
            listaIsbnFavoritos.delete(isbn);
            return true;
        } else {
            const errorData = await response.json().catch(() => ({ error: `Error ${response.status}` }));
            const errorMessage = errorData?.error || errorData?.mensaje || `Error ${response.status} al eliminar favorito.`;
            console.error("Error al eliminar favorito:", response.status, errorMessage);
            Swal.fire({ icon: 'error', title: 'Error', text: errorMessage, toast: true, position: 'top-end', timer: 3000, showConfirmButton: false });
            if (response.status === 401) { console.warn("Usuario no autenticado."); }
            return false;
        }
    } catch (error) {
        console.error("Error de red al eliminar favorito:", error);
        Swal.fire({ icon: 'error', title: 'Error de Red', text: 'No se pudo conectar.', toast: true, position: 'top-end', timer: 3000, showConfirmButton: false });
        return false;
    }
}

async function obtenerFavoritosAPI() {
    try {
        const response = await fetch(`${BASE_URL}/Favoritos`);
        if (response.ok) {
            const librosFavoritos = await response.json();
            listaIsbnFavoritos = new Set(librosFavoritos.map(libro => libro.isbn));
            console.log("Lista ISBN Favoritos actualizada:", listaIsbnFavoritos);
            return librosFavoritos;
        } else {
             if (response.status === 401 || response.headers.get("content-type")?.includes("html")) { // Si no está logueado, el backend podría redirigir a login (HTML) o dar 401
                console.warn("Usuario no autenticado al obtener favoritos. Estableciendo lista vacía.");
                listaIsbnFavoritos = new Set();
                return [];
            }
            const errorData = await response.json().catch(() => ({ error: `Error ${response.status}` }));
            const errorMessage = errorData?.error || `Error ${response.status} al obtener favoritos.`;
            console.error("Error al obtener favoritos:", response.status, errorMessage);
            // No mostramos Swal aquí necesariamente, podría ser normal no tener favoritos o no estar logueado.
            return [];
        }
    } catch (error) {
        console.error("Error de red al obtener favoritos:", error);
        // No mostramos Swal aquí tampoco, la página debe cargar igual.
        return [];
    }
}


// --- MANIPULACIÓN DEL DOM Y EVENTOS ---

/**
 * Crea el HTML para una tarjeta de libro, incluyendo el icono de favorito.
 * ESTA ES LA VERSIÓN CORRECTA que debe usarse.
 * @param {Object} libro El objeto libro con sus datos.
 * @param {number} numero Índice opcional.
 * @param {boolean} esFavorito Indica si el libro está marcado como favorito.
 * @returns {string} El HTML de la tarjeta del libro.
 */
function crearCartaLibro(libro, numero = 0, esFavorito = false) {
    // Verificar si libro o libro.isbn existen
    if (!libro || !libro.isbn) {
        console.error("Intento de crear carta con datos de libro inválidos:", libro);
        return '<div class="carta-libro error-carta"><p>Error al cargar datos del libro</p></div>'; // Devuelve algo indicando error
    }

    let generos_str = 'Sin género';
    if (libro.generos && Array.isArray(libro.generos) && libro.generos.length > 0) {
        // Mapea asegurándose de que cada elemento sea un string o tenga 'nombre'
        generos_str = libro.generos.map(g => (typeof g === 'string' ? g : g?.nombre) || '').filter(Boolean).join(", ");
        if (!generos_str) generos_str = 'Géneros inválidos'; // Fallback si el mapeo falla
    }

    let portada_libro = `https://covers.openlibrary.org/b/isbn/${libro.isbn}-M.jpg`;
    let default_portada = "${pageContext.request.contextPath}/img/default_cover.png"; // <-- ¡¡¡IMPORTANTE!!! Necesitas una imagen por defecto real

    const claseIconoFavorito = esFavorito ? 'fas fa-star' : 'far fa-star';
    const claseActivo = esFavorito ? 'favorito-activo' : '';

    // Asegurarse que autor existe antes de acceder a sus propiedades
    const nombreAutor = libro.autor?.nombre || 'Desconocido';
    const idAutor = libro.autor?.id || '0'; // Usar un ID por defecto o null si prefieres

    return `
        <div class="carta-libro" data-numero-carta="${numero}" data-isbn-carta="${libro.isbn}">
             <span class="icono-favorito ${claseActivo}" data-isbn="${libro.isbn}" title="Marcar como favorito">
                 <i class="${claseIconoFavorito}"></i>
             </span>
             <div class="portada-contenedor">
                 <img class="portada-libro"
                      src="${portada_libro}"
                      alt="Portada de ${libro.nombre || 'Libro sin título'}"
                      onerror="this.onerror=null; this.src='${default_portada}';"> {/* Manejo de error de imagen */}
            </div>
             <div class="informacion-libro">
                 <div class="contenedor-titulo-libro">
                     <p class="titulo-libro">${libro.nombre || 'Sin título'}</p>
                     <p class="autor-libro" data-id-autor="${idAutor}">${nombreAutor}</p>
                 </div>
                 <div class="pie-carta-libro">
                     <p class="libro-generos">${generos_str}</p>
                 </div>
             </div>
         </div>`;
}

/**
 * Carga y muestra los libros en un contenedor específico.
 * Utiliza la lista global `listaIsbnFavoritos` para marcar las estrellas.
 * @param {Array<Object>} libros Lista de objetos libro a mostrar.
 * @param {HTMLElement} contenedor El elemento HTML donde se insertarán las cartas.
 */
function cargarLibrosEnContenedor(libros = [], contenedor) {
    if (!contenedor) {
        console.error("Contenedor para cargar libros no encontrado.");
        return;
    }
    contenedor.innerHTML = ""; // Limpiar contenedor

    if (!libros || libros.length === 0) {
        contenedor.innerHTML = '<p class="no-books-message">No hay libros para mostrar.</p>';
        return;
    }

    let contador = 0;
    libros.forEach(libro => {
        if (!libro || !libro.isbn) { // Saltar libros sin ISBN o inválidos
             console.warn("Libro omitido por datos inválidos:", libro);
             return; // Continúa con el siguiente libro
        }
        const esFavorito = listaIsbnFavoritos.has(libro.isbn);
        const $cartaLibroHTML = crearCartaLibro(libro, contador, esFavorito);
        contenedor.insertAdjacentHTML("beforeend", $cartaLibroHTML);
        contador++;
    });
}

/**
 * Maneja el clic en un icono de favorito para agregarlo o eliminarlo.
 * (Sin cambios respecto a tu versión anterior, parece correcta)
 * @param {Event} event El evento de clic.
 */
async function toggleFavoritoHandler(event) {
    const iconoSpan = event.target.closest('.icono-favorito');
    if (!iconoSpan) return;

    const isbn = iconoSpan.dataset.isbn;
    const esActivo = iconoSpan.classList.contains('favorito-activo');
    const iconoInterno = iconoSpan.querySelector('i');

    if (!isbn || !iconoInterno) return;

    let exito = false;
    iconoSpan.style.pointerEvents = 'none'; // Prevenir doble clic

    if (esActivo) {
        exito = await eliminarFavoritoAPI(isbn);
        if (exito) {
            iconoSpan.classList.remove('favorito-activo');
            iconoInterno.classList.remove('fas');
            iconoInterno.classList.add('far');
             // Opcional: Si estamos en el modal de favoritos, quitar la tarjeta
             const modalContenedor = document.getElementById('contenedor-favoritos-modal');
             if (modalContenedor && iconoSpan.closest('#contenedor-favoritos-modal')) {
                 iconoSpan.closest('.carta-libro').remove();
                 // Verificar si el modal quedó vacío
                 if (!modalContenedor.querySelector('.carta-libro')) {
                     modalContenedor.innerHTML = '<p class="no-books-message">Ya no tienes libros favoritos.</p>';
                 }
             }
        }
    } else {
        exito = await agregarFavoritoAPI(isbn);
        if (exito) {
            iconoSpan.classList.add('favorito-activo');
            iconoInterno.classList.remove('far');
            iconoInterno.classList.add('fas');
        }
    }
    iconoSpan.style.pointerEvents = 'auto'; // Rehabilitar clic
}

/**
 * Abre el modal de favoritos y carga la lista de libros.
 * (Sin cambios respecto a tu versión anterior, parece correcta)
 * @param {Event} event El evento de clic.
 */
async function mostrarModalFavoritos() {
    const modal = document.getElementById('modal-favoritos');
    const contenedorModal = document.getElementById('contenedor-favoritos-modal');
    if (!modal || !contenedorModal) { console.error("Elementos del modal no encontrados."); return; }

    contenedorModal.innerHTML = '<p class="loading-indicator">Cargando tus favoritos...</p>';
    modal.showModal();

    const librosFavoritos = await obtenerFavoritosAPI(); // Obtiene la lista actualizada

    // Usar la misma función para cargar, pasándole el contenedor del modal
    cargarLibrosEnContenedor(librosFavoritos, contenedorModal);

    // Opcional: Aplicar efectos visuales también al modal si es necesario
    if (typeof agregarEfectosVisuales === 'function') {
       // Esperar un poco para que el DOM se actualice antes de aplicar efectos
       setTimeout(() => agregarEfectosVisuales(contenedorModal), 150);
    }
}


// --- INICIALIZACIÓN ---

document.addEventListener("DOMContentLoaded", async () => {
    console.log("DOM Cargado. Inicializando script principal (favoritos.js)...");

    // Referencias a elementos del DOM
    const $contenedorLibrosPrincipal = document.getElementById("contenedor-libros");
    const $btnVerFavoritos = document.getElementById('btn-ver-favoritos');
    const $modalFavoritos = document.getElementById('modal-favoritos');
    const $btnCerrarModalFavoritos = document.getElementById('btn-cerrar-modal-favoritos');

    // --- PASO 1: Obtener lista de favoritos (Actualiza listaIsbnFavoritos) ---
    await obtenerFavoritosAPI();

    // --- PASO 2: Cargar libros en el contenedor principal ---
    if ($contenedorLibrosPrincipal) {
        try {
            console.log("Buscando libros principales...");
            // Llama a buscarLibros (definida en cargar-libros.js)
            const librosTodos = await buscarLibros(); // Sin argumentos para obtener todos inicialmente

            console.log("Cargando libros en el contenedor principal...");
            // Llama a cargarLibrosEnContenedor (definida aquí en favoritos.js)
            cargarLibrosEnContenedor(librosTodos, $contenedorLibrosPrincipal);

            console.log("Agregando efectos visuales...");
            // Llama a agregarEfectosVisuales (definida en cargar-libros.js)
            // Esperar un poco para asegurar que el DOM esté listo
             setTimeout(() => {
                 if (typeof agregarEfectosVisuales === 'function') {
                     agregarEfectosVisuales($contenedorLibrosPrincipal);
                 } else {
                    console.warn("La función agregarEfectosVisuales no está definida.");
                 }
            }, 150); // Ajusta el tiempo si es necesario

        } catch (error) {
            console.error("Error durante la carga inicial de libros:", error);
            $contenedorLibrosPrincipal.innerHTML = `<p class="error-message">Ocurrió un error al cargar los libros: ${error.message}. Intenta recargar la página.</p>`;
        }
    } else {
        console.error("El contenedor principal #contenedor-libros no fue encontrado.");
    }

     // --- PASO 3: Añadir Listener para Toggling de Favoritos (Delegación) ---
     console.log("Añadiendo listener para toggle favoritos...");
     // Usar document.body asegura que funcione para libros cargados dinámicamente en principal y modal
    document.body.addEventListener('click', toggleFavoritoHandler);

    // --- PASO 4: Añadir Listeners para el Modal ---
    if ($btnVerFavoritos) {
         console.log("Añadiendo listener para botón ver favoritos...");
        $btnVerFavoritos.addEventListener('click', mostrarModalFavoritos);
    } else {
        // Solo advertir si el botón debería existir (usuario logueado)
        // Podríamos verificar si el botón existe basado en la presencia del link de logout, por ejemplo
         if(document.querySelector('a[href="/BibliotecaOnline/logout"]')) {
            console.warn("Botón para ver favoritos (#btn-ver-favoritos) no encontrado, pero el usuario parece logueado.");
         }
    }

    if ($modalFavoritos && $btnCerrarModalFavoritos) {
         console.log("Añadiendo listeners para cerrar modal...");
         $btnCerrarModalFavoritos.addEventListener('click', () => $modalFavoritos.close());
         $modalFavoritos.addEventListener('click', (event) => {
             if (event.target === $modalFavoritos) $modalFavoritos.close();
         });
    } else {
         console.warn("Modal de favoritos (#modal-favoritos) o su botón de cierre no encontrado.");
    }

     // --- PASO 5: (Opcional) Centralizar efectos de Nav Links ---
     // Mover esto aquí desde cargar-libros.js para evitar duplicados
     const navLinks = document.querySelectorAll('.nav-link');
     navLinks.forEach(link => {
         if (!link.classList.contains('neon-button') && link.tagName === 'A') { // Aplicar solo a enlaces <a>
             link.addEventListener('mouseenter', function () {
                 this.style.textShadow = `0 0 8px var(--secondary-color), 0 0 20px var(--secondary-color)`;
             });
             link.addEventListener('mouseleave', function () {
                 this.style.textShadow = '';
             });
         }
     });

    console.log("Inicialización de favoritos.js completada.");
});