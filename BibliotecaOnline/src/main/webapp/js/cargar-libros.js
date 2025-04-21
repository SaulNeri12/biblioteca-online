/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
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

    if (libro.generos && libro.generos.length < 1) {
        generos_str = libro.generos.join(", ");
    }

    let portada_libro = `https://covers.openlibrary.org/b/isbn/${libro.isbn}-M.jpg`;

    return `
        <div class="carta-libro" data-numero-carta="${numero}">
            <div class="portada-contenedor">
                <img class="portada-libro" src="${portada_libro}">
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
    });
}

document.addEventListener("DOMContentLoaded", async () => {
    const libros = await buscarLibros();
    console.log("Libros encontrados:", libros);
    cargarLibros(libros);  // Llamar a la función para cargar los libros
});

