/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/gulpfile.js to edit this template
 */


//variabale donde se guardan los libros econtrados
let isbnSeleccionado = null; 
let tarjetaSeleccionada = null;

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
document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault(); // Evita el envío tradicional del formulario

    let nombre = document.getElementById('nombre').value; //obtenemos el nombre del libro
    let genero = document.getElementById('genero').value; //obtenemos el genero seleccionado

    if(nombre || genero){ //si el nombre o el genero no estan vacios si realizamos la busqueda
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
                    <p><strong>Portada:</strong> aqui va la imagen pero todavia no hay</p>
                    <p><strong>Autor:</strong> ${libro.autor ? libro.autor.nombre : 'Desconocido'}</p>
                    <p><strong>ISBN:</strong> ${libro.isbn}</p>
                    <p><strong>Descripción:</strong> ${libro.descripcion}</p>
                    <button class="btn-eliminar" onclick="abrirModalEliminar()"> Eliminar </button>
                    <button class="btn-editar" onclick="abrirModalEditar()">Editar</button>
                `;
                
                //evento para eliminar
                div.querySelector('.btn-eliminar').addEventListener('click', () => {
                    isbnSeleccionado = libro.isbn; //isbn de la tarjeta recuperada
                    tarjetaSeleccionada = div; //div de la tarjeta recuperada
                    abrirModalEliminar(); //abrimos la modal para la eliminacion
                });

                // Evento para editar editar 
                div.querySelector('.btn-editar').addEventListener('click', () => {
                    // pasamos el objeto completo del libro y abrimos la modal
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
    
    //cargamos las opciones de autores
    fetch('http://localhost:8080/BibliotecaOnline/autor')
        .then(response => response.json())
        .then(data => {
            let select = document.getElementById('editar-autor');
    
            data.forEach( autor => {
               let option = document.createElement("option");
               option.value = autor.id;
               option.text = autor.nombre;
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
            let select1 = document.getElementById('editar-genero1');
            let select2 = document.getElementById('editar-genero2');

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

    //mostramos la modal
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
function guardarLibro(){
    
}

/**
 * Funcion encargada de la edicion de un libro
 * @returns {undefined}
 */
function editarLibro(libro){
    
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








