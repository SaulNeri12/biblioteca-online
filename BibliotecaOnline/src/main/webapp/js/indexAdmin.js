/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/gulpfile.js to edit this template
 */


//variabale donde se guardan los libros econtrados
let isbnSeleccionado = null;
let tarjetaSeleccionada = null;
let idLibro = null;

/**
 * Ejecucion inicial que carga los generos disponibles al abrir la pantalla.
 * @returns {undefined}
 */
document.addEventListener("DOMContentLoaded", event => {
    fetch('http://localhost:8080/BibliotecaOnline/generos')
        .then(response => response.json())
        .then(data => {
            let select = document.getElementById('genero');

            // Opción vacía para "todos"
            let opcionVacia = document.createElement('option');
            opcionVacia.value = "";
            opcionVacia.text = "Todos";
            select.appendChild(opcionVacia);

            //cargamos los generos
            data.forEach(genero => {
                let option = document.createElement('option');
                option.value = genero.nombre; // guardamos el id en el combobox
                option.text = genero.nombre;// mostramos el nombre del genero
                select.appendChild(option);
            });
        })
        .catch(error => {
            Swal.fire({
                title: 'Error',
                text: 'Error al eliminar al obtener los generos.',
                icon: 'error',
                timer: 3000,
                showConfirmButton: false,
                toast: true,
                position: 'top-right'
            });
            console.error('Error al cargar los generos:', error);
        });
});

/**
 * Funcion para la busqueda de un libro
 */
document.querySelector('form').addEventListener('submit', function (event) {
    event.preventDefault(); // Evita el envío tradicional del formulario

    let nombre = document.getElementById('nombre').value; //obtenemos el nombre del libro
    let genero = document.getElementById('genero').value; //obtenemos el genero seleccionado

    if (nombre || genero) { //si el nombre o el genero no estan vacios si realizamos la busqueda
        //consultamos en la API los libros que coincidan
        fetch(`http://localhost:8080/BibliotecaOnline/BuscarLibros?libro=${nombre}&genero=${genero}`)
            .then(response => response.json())
            .then(libros => {

                //obtenemos el contenedor donde iran las tarjetas de los libros
                let contenedor = document.querySelector('.contenedor-libros');
                contenedor.innerHTML = ''; // Limpiar resultados anteriores

                //si el contenedor esta vacio damos ese mensaje
                if (libros.length === 0) {
                    contenedor.innerHTML = '<p>No se encontraron libros.</p>';
                    return;
                }

                //ireamos sobre los resultados de la API
                libros.forEach(libro => {
                    let div = document.createElement('div');
                    div.classList.add('tarjeta-libro'); // Para estilos futuros si deseas

                    //agregamos una nueva tarjeta por cada resultado encontrado
                    div.innerHTML = `
                        <h3>${libro.nombre}</h3>
                        <p><strong>Autor:</strong> ${libro.autor ? libro.autor.nombre : 'Desconocido'}</p>
                        <p><strong>ISBN:</strong> ${libro.isbn}</p>
                        <p><strong>Descripción:</strong> ${libro.descripcion}</p>
                        <button class="btn-eliminar" onclick="abrirModalEliminar()"> Eliminar </button>
                        <button class="btn-editar")">Editar</button>
                    `;

                    //evento para eliminar
                    div.querySelector('.btn-eliminar').addEventListener('click', () => {
                        isbnSeleccionado = libro.isbn; //isbn de la tarjeta recuperada
                        tarjetaSeleccionada = div; //div de la tarjeta recuperada
                        abrirModalEliminar(); //abrimos la modal para la eliminacion
                    });

                    // Evento para editar editar 
                    div.querySelector('.btn-editar').addEventListener('click', () => {
                        console.log('Libro a editar:', libro); // Verifica el objeto del libro
                        // Pasamos el objeto completo del libro a la función abrirModalEditar
                        abrirModalEditar(libro);
                    });

                    //agregamos la tajeta al 
                    contenedor.appendChild(div);
                });
            })
            .catch(error => {
                Swal.fire({
                    title: 'Error',
                    text: 'Error al eliminar al obtener los libros.',
                    icon: 'error',
                    timer: 3000,
                    showConfirmButton: false,
                    toast: true,
                    position: 'top-right'
                });
                console.error('Error al obtener los libros:', error);
            });
    }
});

/**
 * Función para abrir la modal de agregar libro
 * @returns {undefined}
 */
function abrirModalAgregar() {

    //cargamos las opciones de autores
    fetch('http://localhost:8080/BibliotecaOnline/autor')
        .then(response => response.json())
        .then(data => {
            let select = document.getElementById('agregar-autor');
            data.forEach(autor => {
                let option = document.createElement('option');
                option.value = autor.id;
                option.textContent = autor.nombre;
                select.appendChild(option);
            });
        })
        .catch(error => {
            Swal.fire({
                title: 'Error',
                text: 'Error al eliminar al cargar los autores.',
                icon: 'error',
                timer: 3000,
                showConfirmButton: false,
                toast: true,
                position: 'top-right'
            });
            console.error('Error al obtener los autores:', error);
        });

    //agregamos las opciones de genero a los comboBox
    fetch('http://localhost:8080/BibliotecaOnline/generos')
        .then(response => response.json())
        .then(data => {
            let select1 = document.getElementById('agregar-genero1');
            let select2 = document.getElementById('agregar-genero2');

            data.forEach(genero => {
                let option1 = document.createElement('option');
                option1.value = genero.id;
                option1.text = genero.nombre;
                select1.appendChild(option1);

                let option2 = document.createElement('option');
                option2.value = genero.id;
                option2.text = genero.nombre;
                select2.appendChild(option2);
            });
        })
        .catch(error => {
            Swal.fire({
                title: 'Error',
                text: 'Error al eliminar al cargar los generos.',
                icon: 'error',
                timer: 3000,
                showConfirmButton: false,
                toast: true,
                position: 'top-right'
            });
            console.error('Error al obtener los generos:', error);
        });

    let modal = document.querySelector('.dialog-agregar');
    modal.showModal();  // Muestra la modal
}

/**
 * Función para abrir la modal de agregar libro
 * @param {type} libro
 * @returns {undefined}
 */
function abrirModalEditar(libro) {

    console.log('Objeto libro recibido en abrirModalEditar:', libro);

    this.idLibro = libro.id;

    console.log("id obtenido: ", this.idLibro);

    // Mostrar los datos del libro seleccionado en la modal
    document.getElementById('editar-isbn').value = libro.isbn; // Campo de ISBN
    document.getElementById('editar-nombre').value = libro.nombre; // Campo de nombre del libro
    document.getElementById('editar-descripcion').value = libro.descripcion; // Campo de descripción    
    document.getElementById('editar-contenidoAdulto').checked = libro.contenido_adulto; // Checkbox de contenido adulto
    document.getElementById('editar-editorial').value = libro.editorial; // Campo de editorial
    document.getElementById('editar-anio').value = libro.anio_publicacion; // Campo de año de publicación
    document.getElementById('editar-numPaginas').value = libro.num_paginas; // Campo de número de páginas

    // Cargar autor
    fetch('http://localhost:8080/BibliotecaOnline/autor')
        .then(response => response.json())
        .then(data => {
            let select = document.getElementById('editar-autor');
            select.innerHTML = ''; // Limpiar opciones previas

            // Añadir los autores al select
            data.forEach(autor => {
                let option = document.createElement("option");
                option.value = autor.id;
                option.text = autor.nombre;
                // Seleccionar el autor correspondiente
                if (autor.id === libro.autor.id) {
                    option.selected = true;
                }
                select.appendChild(option);
            });
        })
        .catch(error => {
            Swal.fire({
                title: 'Error',
                text: 'Error al cargar los autores.',
                icon: 'error',
                timer: 3000,
                showConfirmButton: false,
                toast: true,
                position: 'top-right'
            });
            console.error('Error al obtener los autores:', error);
        });

    // Cargar géneros
    fetch('http://localhost:8080/BibliotecaOnline/generos')
        .then(response => response.json())
        .then(data => {
            let select1 = document.getElementById('editar-genero1');
            let select2 = document.getElementById('editar-genero2');

            select1.innerHTML = ''; // Limpiar opciones previas
            select2.innerHTML = '';

            // Añadir géneros al select
            data.forEach(genero => {
                let option1 = document.createElement('option');
                option1.value = genero.id;
                option1.text = genero.nombre;
                // Seleccionar los géneros correspondientes
                if (libro.generos.some(g => g.id === genero.id)) {
                    option1.selected = true;
                }
                select2.appendChild(option1);

                let option2 = document.createElement('option');
                option2.value = genero.id;
                option2.text = genero.nombre;
                select1.appendChild(option2);
            });
        })
        .catch(error => {
            Swal.fire({
                title: 'Error',
                text: 'Error al cargar los géneros.',
                icon: 'error',
                timer: 3000,
                showConfirmButton: false,
                toast: true,
                position: 'top-right'
            });
            console.error('Error al obtener los géneros:', error);
        });

    // Mostrar la modal
    let modal = document.querySelector('.dialog-editar');
    modal.showModal();  // Muestra la modal
}

/**
 * Función para abrir la modal de agregar libro
 * @returns {undefined}
 */
function abrirModalEliminar() {
    let modal = document.querySelector('.dialog-eliminacion');
    modal.showModal();  // Muestra la modal
}

/**
 * Funcion encargada de guardar un libro
 * @returns {undefined}
 */
function agregarLibro(event) {
    event.preventDefault();

    let isbn = document.getElementById('agregar-isbn').value.trim();
    let nombre = document.getElementById('agregar-nombre').value.trim();
    let descripcion = document.getElementById('agregar-descripcion').value.trim();
    let contenidoAdulto = document.getElementById('agregar-contenidoAdulto').checked;
    let autorId = document.getElementById('agregar-autor').value;
    let editorial = document.getElementById('agregar-editorial').value.trim();
    let anio = parseInt(document.getElementById('agregar-anio').value);
    let numPaginas = parseInt(document.getElementById('agregar-numPaginas').value);
    let genero1 = document.getElementById('agregar-genero1').value;
    let genero2 = document.getElementById('agregar-genero2').value;

    // Validación rápida de campos
    if (!isbn || !nombre || !descripcion || !autorId || !editorial || !anio
        || !numPaginas || !genero1 || !genero2) {
        Swal.fire({
            icon: 'warning',
            title: 'Faltan datos',
            text: 'Por favor completa todos los campos',
            toast: true,
            position: 'top-right',
            timer: 2000,
            showConfirmButton: false
        });
        return;
    }

    let libro = {
        isbn,
        nombre,
        descripcion,
        contenido_adulto: contenidoAdulto,
        autor: {
            id: parseInt(autorId)
        },
        editorial,
        anio_publicacion: anio,
        num_paginas: numPaginas,
        generos: [
            { id: parseInt(genero1) },
            { id: parseInt(genero2) }
        ]
    };

    console.log(JSON.stringify(libro));

    fetch(`http://localhost:8080/BibliotecaOnline/AgregarLibro`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(libro)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('No se pudo guardar el libro');
            }
            return response.json();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Guardado',
                text: 'Libro agregado exitosamente',
                timer: 2000,
                toast: true,
                position: 'top-right',
                showConfirmButton: false
            });

            document.getElementById('formAgregarLibro').reset();
            document.querySelector(".dialog-agregar").close();
        })
        .catch(error => {
            console.error('Error al guardar el libro:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Ocurrió un problema al guardar el libro',
                toast: true,
                position: 'top-right',
                timer: 3000,
                showConfirmButton: false
            });
        });
}

/**
 * Funcion encargada de la edicion de un libro
 * @returns {undefined}
 */
function editarLibro() {
    event.preventDefault();

    let id = this.idLibro;
    let isbn = document.getElementById('editar-isbn').value.trim();
    let nombre = document.getElementById('editar-nombre').value.trim();
    let descripcion = document.getElementById('editar-descripcion').value.trim();
    let contenidoAdulto = document.getElementById('editar-contenidoAdulto').checked;
    let autorId = document.getElementById('editar-autor').value;
    let editorial = document.getElementById('editar-editorial').value.trim();
    let anio = parseInt(document.getElementById('editar-anio').value);
    let numPaginas = parseInt(document.getElementById('editar-numPaginas').value);
    let genero1 = document.getElementById('editar-genero1').value;
    let genero2 = document.getElementById('editar-genero2').value;

    // Validación rápida de campos
    if (!isbn || !nombre || !descripcion || !autorId || !editorial || !anio
        || !numPaginas || !genero1 || !genero2) {
        Swal.fire({
            icon: 'warning',
            title: 'Faltan datos',
            text: 'Por favor completa todos los campos',
            toast: true,
            position: 'top-right',
            timer: 3000,
            showConfirmButton: false
        });
        return;
    }

    let libro2 = {
        id,
        isbn,
        nombre,
        descripcion,
        contenido_adulto: contenidoAdulto,
        autor: {
            id: parseInt(autorId)
        },
        editorial,
        anio_publicacion: anio,
        num_paginas: numPaginas,
        generos: [
            { id: parseInt(genero1) },
            { id: parseInt(genero2) }
        ]
    };

    console.log(JSON.stringify(libro2));

    fetch(`http://localhost:8080/BibliotecaOnline/EditarLibro`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(libro2)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('No se pudo guardar el libro');
            }
            return response.json();
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                title: 'Guardado',
                text: 'Libro actualizado exitosamente',
                timer: 3000,
                toast: true,
                position: 'top-right',
                showConfirmButton: false
            });
        })
        .catch(error => {
            console.error('Error al actualizar el libro:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Ocurrió un problema al actualizar el libro',
                toast: true,
                position: 'top-right',
                timer: 3000,
                showConfirmButton: false
            });
        });
}

/**
 * Funcion encargada de la eliminacion de un libro.
 * @returns {undefined}
 */
function eliminarLibro() {
    fetch(`http://localhost:8080/BibliotecaOnline/EliminarLibro?isbn=${isbnSeleccionado}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("No se pudo eliminar el libro");
            }
            return response.json(); // si deseas usar el mensaje JSON del servidor
        })
        .then(data => {
            tarjetaSeleccionada.remove(); // removemos la tarjeta del libro eliminado

            // notificación para confirmación de eliminación
            Swal.fire({
                title: 'Eliminado',
                text: data.mensaje || 'Libro eliminado con éxito',
                icon: 'info',
                timer: 3000,
                showConfirmButton: false,
                toast: true,
                position: 'top-right'
            });
        })
        .catch(error => {
            Swal.fire({
                title: 'Error',
                text: 'Error al eliminar el libro.',
                icon: 'error',
                timer: 3000,
                showConfirmButton: false,
                toast: true,
                position: 'top-right'
            });
            console.error('Error al eliminar libro:', error);
        });
}

/*
 * Created on : 6 may 2025
 * Author     : sebastian
 * Description: JavaScript para la página de administración
 */

document.addEventListener('DOMContentLoaded', function () {
    console.log('Admin index page loaded');

    // Botón para abrir el modal de agregar libro
    const btnAgregar = document.getElementById('btn-agregar');
    const dialogAgregar = document.querySelector('.dialog-agregar');

    btnAgregar.addEventListener('click', function () {
        dialogAgregar.showModal();
    });

    // Botón para cerrar el modal de agregar libro
    const btnCancelarAgregar = dialogAgregar.querySelector('form[method="dialog"] button');
    btnCancelarAgregar.addEventListener('click', function () {
        dialogAgregar.close();
    });

    // Botón para confirmar eliminación
    const btnConfirmarEliminar = document.getElementById('btn-confirmar');
    const dialogEliminacion = document.querySelector('.dialog-eliminacion');

    btnConfirmarEliminar.addEventListener('click', function () {
        eliminarLibro();
        dialogEliminacion.close();
    });

    // Función para eliminar un libro
    function eliminarLibro() {
        Swal.fire({
            title: 'Libro eliminado',
            text: 'El libro ha sido eliminado correctamente.',
            icon: 'success',
            confirmButtonText: 'Aceptar',
        });
    }

    // Función para agregar un libro
    const formAgregarLibro = document.getElementById('formAgregarLibro');
    formAgregarLibro.addEventListener('submit', function (event) {
        event.preventDefault();
        Swal.fire({
            title: 'Libro agregado',
            text: 'El libro ha sido agregado correctamente.',
            icon: 'success',
            confirmButtonText: 'Aceptar',
        });
        dialogAgregar.close();
    });

    // Función para editar un libro
    const formEditarLibro = document.getElementById('formEditarLibro');
    const dialogEditar = document.querySelector('.dialog-editar');

    formEditarLibro.addEventListener('submit', function (event) {
        event.preventDefault();
        Swal.fire({
            title: 'Libro editado',
            text: 'El libro ha sido editado correctamente.',
            icon: 'success',
            confirmButtonText: 'Aceptar',
        });
        dialogEditar.close();
    });

    // Botón para cerrar el modal de edición
    const btnCancelarEditar = dialogEditar.querySelector('form[method="dialog"] button');
    btnCancelarEditar.addEventListener('click', function () {
        dialogEditar.close();
    });
});








