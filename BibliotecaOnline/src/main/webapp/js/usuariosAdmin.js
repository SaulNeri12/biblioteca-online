/*
 * Created on : 6 may 2025
 * Author     : sebastian
 * Description: JavaScript para la página de usuarios registrados
 */

document.addEventListener('DOMContentLoaded', function () {
    // Este código se ejecuta cuando la página se ha cargado completamente
    console.log('Usuarios admin page loaded');

    // Comprobar si hay un mensaje de alerta para mostrar
    const alerta = document.querySelector('.usuarios-alerta');
    if (alerta) {
        // Si hay una alerta, hacer que se desvanezca después de 5 segundos
        setTimeout(() => {
            alerta.style.opacity = '0'; // Establecer la opacidad a 0 para comenzar a desvanecer
            alerta.style.transition = 'opacity 0.5s'; // Desvanecer suavemente durante 0.5 segundos
            setTimeout(() => {
                alerta.remove(); // Eliminar la alerta de la página después de que se desvanezca
            }, 500); // Esperar a que la transición de desvanecimiento se complete (0.5 segundos)
        }, 5000); // Esperar 5 segundos antes de comenzar el desvanecimiento
    }

    // Configurar el campo de búsqueda
    const searchInput = document.getElementById('searchInput');
    if (searchInput) {
        // Cuando el usuario escribe algo en el cuadro de búsqueda, esta función se ejecutará
        searchInput.addEventListener('input', function () {
            const filter = this.value.toLowerCase(); // Obtener el término de búsqueda y convertirlo a minúsculas
            const rows = document.querySelectorAll('table tbody tr'); // Obtener todas las filas de la tabla de usuarios

            // Recorrer cada fila de la tabla
            rows.forEach(row => {
                const cells = row.querySelectorAll('td'); // Obtener todas las celdas de la fila actual
                // Comprobar si alguna de las celdas de la fila contiene el término de búsqueda
                const match = Array.from(cells).some(cell => cell.textContent.toLowerCase().includes(filter));
                row.style.display = match ? '' : 'none'; // Mostrar la fila si coincide, ocultarla si no
            });
        });
    }

    // Configurar los diálogos modales para editar y eliminar usuarios
    setupDialogs();
});

/**
 * Configura los diálogos modales y sus eventos
 */
function setupDialogs() {
    // Obtener referencias a los diálogos (Get references to the dialogs)
    const dialogEliminar = document.getElementById('dialog-eliminar'); // Obtiene el diálogo de eliminación
    const dialogEditar = document.getElementById('dialog-editar'); // Obtiene el diálogo de edición

    // Configurar botones de cancelar para cerrar los diálogos (Set up cancel buttons to close the dialogs)
    const btnCancelarEliminar = document.getElementById('btn-cancelar-eliminar'); // Obtiene el botón de cancelar del diálogo de eliminación
    if (btnCancelarEliminar) {
        btnCancelarEliminar.addEventListener('click', () => { // Agrega un evento al botón de cancelar
            dialogEliminar.close(); // Cierra el diálogo de eliminación
            document.body.classList.remove('dialog-open'); // Remueve la clase que indica que el diálogo está abierto
        });
    }

    const btnCancelarEditar = document.getElementById('btn-cancelar-editar'); // Obtiene el botón de cancelar del diálogo de edición
    if (btnCancelarEditar) {
        btnCancelarEditar.addEventListener('click', () => { // Agrega un evento al botón de cancelar
            dialogEditar.close(); // Cierra el diálogo de edición
            document.body.classList.remove('dialog-open'); // Remueve la clase que indica que el diálogo está abierto
        });
    }

    // Manejar eventos para aplicar/quitar la clase dialog-open (Handle events to apply/remove the dialog-open class)
    [dialogEliminar, dialogEditar].forEach(dialog => { // Itera sobre los diálogos de eliminación y edición
        if (!dialog) return; // Si el diálogo no existe, sale de la iteración

        // Cuando se cierra el diálogo (When the dialog is closed)
        dialog.addEventListener('close', () => { // Agrega un evento que se ejecuta cuando se cierra el diálogo
            document.body.classList.remove('dialog-open'); // Remueve la clase que indica que el diálogo está abierto
        });
    });
}

/**
 * Función para abrir la modal de confirmación de eliminación del usuario
 *
 * @param {Event} event - El evento del clic
 */
function abrirModalEliminar(event) {
    // Obtener la fila que contiene el botón pulsado (Get the row that contains the pressed button)
    const fila = event.target.closest("tr");

    // Verificar si la fila y el id de usuario existen (Check if the row and user ID exist)
    const idUsuario = fila?.getAttribute("data-fila-id");
    if (!idUsuario || !fila) return;

    console.log("ID del usuario a eliminar:", idUsuario);

    // Abrimos la modal de confirmación de eliminación (Open the deletion confirmation modal)
    const modal = document.getElementById('dialog-eliminar');

    // Limpiar eventos previos para evitar acumulación de handlers (Clear previous events to avoid accumulating handlers)
    const oldBtn = document.getElementById("btn-eliminar");
    const newBtn = oldBtn.cloneNode(true);
    oldBtn.parentNode.replaceChild(newBtn, oldBtn);

    // Agregar nuevo event listener (Add new event listener)
    document.getElementById("btn-eliminar").addEventListener("click", () => {
        eliminarCuentaUsuario(idUsuario, fila);
        modal.close();
        document.body.classList.remove('dialog-open');
    });

    // Mostrar modal y añadir clase al body (Show modal and add class to body)
    modal.showModal();
    document.body.classList.add('dialog-open');
}

/**
 * Elimina la cuenta de un usuario mediante una solicitud AJAX
 *
 * @param {string} idUsuario - ID del usuario a eliminar
 * @param {HTMLElement} fila - Elemento TR que representa la fila del usuario en la tabla
 */
/**
 * Elimina la cuenta de un usuario mediante una solicitud AJAX
 *
 * @param {string} idUsuario - ID del usuario a eliminar
 * @param {HTMLElement} fila - Elemento TR que representa la fila del usuario en la tabla
 */
async function eliminarCuentaUsuario(idUsuario, fila) {
    try {
        // Mostrar indicador de carga (Show loading indicator)
        Swal.fire({
            title: 'Eliminando usuario...', // Título del indicador de carga
            text: 'Por favor espere', // Texto del indicador de carga
            allowOutsideClick: false, // No permitir que el usuario cierre el indicador haciendo clic fuera de él
            didOpen: () => {
                Swal.showLoading(); // Mostrar el indicador de carga
            }
        });

        const respuesta = await fetch(`http://localhost:8080/BibliotecaOnline/EliminarUsuario?id_usuario=${idUsuario}`, {
            method: 'DELETE' // Método de la solicitud HTTP
        });

const data = await respuesta.json(); // Convertir la respuesta a JSON

        if (!respuesta.ok) {
            // Si la respuesta no es exitosa, mostrar un mensaje de error (If the response is not successful, show an error message)
            Swal.fire({
                icon: 'error', // Icono de error (Error icon)
                title: 'Error al eliminar', // Título del mensaje de error (Error message title)
                text: data.error || data.mensaje || "Error desconocido", // Texto del mensaje de error (Error message text)
                timer: 3000, // Duración del mensaje de error en milisegundos (Error message duration in milliseconds)
                position: 'top-right', // Posición del mensaje de error (Error message position)
                showConfirmButton: false // No mostrar el botón de confirmación (Do not show the confirmation button)
            });
            return;
        }

// Eliminación exitosa (Successful deletion)
        Swal.fire({
            icon: 'success', // Icono de éxito (Success icon)
            title: 'Eliminado', // Título del mensaje de éxito (Success message title)
            text: data.mensaje || 'Usuario eliminado con éxito', // Texto del mensaje de éxito (Success message text)
            timer: 3000, // Duración del mensaje de éxito en milisegundos (Success message duration in milliseconds)
            position: 'top-right', // Posición del mensaje de éxito (Success message position)
            showConfirmButton: false // No mostrar el botón de confirmación (Do not show the confirmation button)
        });

// Eliminar fila con animación (Delete row with animation)
        fila.style.transition = 'opacity 0.5s, transform 0.5s'; // Agregar transición para la opacidad y la transformación (Add transition for opacity and transformation)
        fila.style.opacity = '0'; // Establecer la opacidad a 0 para comenzar a desvanecer (Set opacity to 0 to start fading)
        fila.style.transform = 'translateX(20px)'; // Mover la fila 20 píxeles a la derecha (Move the row 20 pixels to the right)

        setTimeout(() => {
            fila.remove(); // Eliminar la fila después de la animación (Delete the row after the animation)
        }, 500); // Esperar 0.5 segundos para que la animación se complete (Wait 0.5 seconds for the animation to complete)

        console.log("Usuario eliminado con éxito"); // Mostrar mensaje en la consola (Show message in the console)
    } catch (error) {
        // Si ocurre un error, mostrar un mensaje de error (If an error occurs, show an error message)
        console.error("Error de red o inesperado:", error); // Mostrar el error en la consola (Show the error in the console)
        Swal.fire({
            icon: 'error', // Icono de error (Error icon)
            title: 'Error', // Título del mensaje de error (Error message title)
            text: "Ocurrió un error al intentar eliminar el usuario.", // Texto del mensaje de error (Error message text)
            timer: 3000, // Duración del mensaje de error en milisegundos (Error message duration in milliseconds)
            position: 'top-right', // Posición del mensaje de error (Error message position)
            showConfirmButton: false // No mostrar el botón de confirmación (Do not show the confirmation button)
        });
    }
}

/**
 * Función para abrir la modal de edición de un usuario
 *
 * @param {Event} event - El evento del clic
 */
function abrirModalEditar(event) {
    // Obtener la fila que contiene el botón pulsado (Get the row that contains the pressed button)
    const fila = event.target.closest("tr");

    // Verificar si la fila y el id de usuario existen (Check if the row and user ID exist)
    const idUsuario = fila?.getAttribute("data-fila-id");
    if (!idUsuario || !fila) return;

    // Obtener datos de la fila (Get data from the row)
    const celdas = fila.querySelectorAll("td");
    const nombre = celdas[1]?.textContent.trim();
    const correo = celdas[2]?.textContent.trim();
    const telefono = celdas[3]?.textContent.trim();
    const fechaRegistro = celdas[4]?.textContent.trim();

    // Llenar los campos del formulario (Fill the form fields)
    document.getElementById("nombre-usuario").value = nombre;
    document.getElementById("correo-usuario").value = correo;
    document.getElementById("telefono-usuario").value = telefono;
    document.getElementById("fecha-registro-usuario").value = fechaRegistro;

    // Limpiar eventos previos para evitar acumulación de handlers (Clear previous events to avoid accumulating handlers)
    const oldBtn = document.getElementById("btn-actualizar");
    const newBtn = oldBtn.cloneNode(true);
    oldBtn.parentNode.replaceChild(newBtn, oldBtn);

    // Agregar nuevo event listener (Add new event listener)
    document.getElementById("btn-actualizar").addEventListener("click", () => {
        editarCuentaUsuario(idUsuario, fila);
    });

    // Abrir modal y añadir clase al body (Open modal and add class to body)
    const modal = document.getElementById('dialog-editar');
    modal.showModal();
    document.body.classList.add('dialog-open');
}

/**
 * Edita la cuenta de un usuario mediante una solicitud AJAX
 * 
 * @param {string} idUsuario - ID del usuario a editar
 * @param {HTMLElement} fila - Elemento TR que representa la fila del usuario en la tabla
 */
async function editarCuentaUsuario(idUsuario, fila) {
    const nombre = document.getElementById("nombre-usuario").value.trim();
    const correo = document.getElementById("correo-usuario").value.trim();
    const telefono = document.getElementById("telefono-usuario").value.trim();
    const fechaInput = document.getElementById("fecha-registro-usuario").value.trim();

    // Validar campos
    if (!nombre || !correo || !telefono || !fechaInput) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: "Todos los campos son obligatorios.",
            timer: 3000,
            position: 'top-right',
            showConfirmButton: false
        });
        return;
    }

    // Validar formato de correo
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(correo)) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: "Por favor ingrese un correo electrónico válido.",
            timer: 3000,
            position: 'top-right',
            showConfirmButton: false
        });
        return;
    }

    try {
        const fecha = new Date(fechaInput);
        const fechaNacimiento = fecha.toISOString();

        // Mostrar indicador de carga
        Swal.fire({
            title: 'Actualizando usuario...',
            text: 'Por favor espere',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });

        const datos = {
            id: idUsuario,
            nombre: nombre,
            email: correo,
            telefono: telefono,
            fecha_nacimiento: fechaNacimiento
        };

        const response = await fetch("http://localhost:8080/BibliotecaOnline/EditarUsuario", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(datos)
        });

        if (!response.ok) {
            throw new Error("Error al actualizar el usuario");
        }

        const data = await response.json();

        // Actualización exitosa
        Swal.fire({
            icon: 'success',
            title: 'Éxito',
            text: "Usuario actualizado correctamente",
            timer: 3000,
            position: 'top-right',
            showConfirmButton: false
        });

        // Cerrar el diálogo de edición
        document.getElementById('dialog-editar').close();

        // Actualizar las celdas de la fila con una animación
        const celdas = fila.getElementsByTagName("td");

        // Aplicar efecto de actualización
        fila.style.transition = 'background-color 0.5s';
        fila.style.backgroundColor = 'rgba(14, 255, 255, 0.2)';

        // Actualizar datos visibles en la tabla
        celdas[1].textContent = nombre;
        celdas[2].textContent = correo;
        celdas[3].textContent = telefono;
        celdas[4].textContent = fechaInput;

        // Quitar el efecto después de un tiempo
        setTimeout(() => {
            fila.style.backgroundColor = '';
        }, 1500);

    } catch (error) {
        console.error("Error:", error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: "Error al actualizar al usuario",
            timer: 3000,
            position: 'top-right',
            showConfirmButton: false
        });
    }
}
