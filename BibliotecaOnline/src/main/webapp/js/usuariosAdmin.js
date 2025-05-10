/*
 * Created on : 6 may 2025
 * Author     : sebastian
 * Description: JavaScript para la página de usuarios registrados
 */

let idUsuarioEliminar;

    document.addEventListener('DOMContentLoaded', function () {
        console.log('Usuarios admin page loaded');

        // Mostrar alerta si no hay usuarios registrados
        const alerta = document.querySelector('.usuarios-alerta');
        if (alerta) {
            setTimeout(() => {
                alerta.style.opacity = '0';
                alerta.style.transition = 'opacity 0.5s';
                setTimeout(() => {
                    alerta.remove();
                }, 500);
            }, 5000);
        }

        // Añadir funcionalidad de búsqueda en la tabla
        const searchInput = document.createElement('input');
        searchInput.type = 'text';
        searchInput.placeholder = 'Buscar usuario...';
        searchInput.style.marginBottom = '20px';
        searchInput.style.padding = '10px';
        searchInput.style.width = '100%';
        searchInput.style.border = '1px solid var(--secondary-color)';
        searchInput.style.borderRadius = '5px';
        searchInput.style.background = 'var(--table-bg)';
        searchInput.style.color = 'var(--text-color)';

        const container = document.querySelector('.usuarios-lista-contenedor');
        container.insertBefore(searchInput, container.firstChild);

        searchInput.addEventListener('input', function () {
            const filter = searchInput.value.toLowerCase();
            const rows = document.querySelectorAll('table tbody tr');

            rows.forEach(row => {
                const cells = row.querySelectorAll('td');
                const match = Array.from(cells).some(cell => cell.textContent.toLowerCase().includes(filter));
                row.style.display = match ? '' : 'none';
            });
        });
    });
    
    
    /**
     * Funcion para abrir la modal de confirmacion de eliminacion del libro
     * 
     * @param {type} event
     * @returns {undefined}
     */
    function abrirModalEliminar(event) {
        // Obtener la fila que contiene el botón pulsado
        const fila = event.target.closest("tr");

        // Verificar si la fila y el id de usuario existen
        const idUsuario = fila?.getAttribute("data-fila-id");
        console.log(idUsuario);
        
        // Abrimos la modal de confirmación de eliminación
        let modal = document.querySelector('#dialog-eliminar');
        modal.showModal(); // Muestra la modal
        
        //le damos una accion al boton de la modal
        document.getElementById("btn-eliminar").addEventListener("click", event =>{
           eliminarCuentaUsuario(idUsuario, fila); 
        });
    }

    /**
     * 
     * @param {type} idUsuario
     * @param {type} fila
     * @returns {undefined}
     */
    async function eliminarCuentaUsuario(idUsuario, fila) {
        try {
            const respuesta = await fetch(`http://localhost:8080/BibliotecaOnline/EliminarUsuario?id_usuario=${idUsuario}`, {
                method: 'DELETE'
            });

            const data = await respuesta.json(); 

            if (!respuesta.ok) {
                Swal.fire({
                    icon: 'error',
                    title: 'Error al eliminar el usuario',
                    text: data.error || data.mensaje || "Error desconocido",
                    timer: 2000,
                    toast: true,
                    position: 'top-right',
                    showConfirmButton: false
                });
                return;
            }

            // Eliminación exitosa
            Swal.fire({
                icon: 'success',
                title: 'Eliminado',
                text: data.mensaje || 'Usuario eliminado con éxito',
                timer: 2000,
                toast: true,
                position: 'top-right',
                showConfirmButton: false
            });

            fila.remove();
            console.log("Usuario eliminado con exito");

        } catch (error) {
            console.error("Error de red o inesperado:", error);
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: "Ocurrió un error al intentar eliminar el usuario.",
                timer: 2000,
                toast: true,
                position: 'top-right',
                showConfirmButton: false
            });
            console.log("error al eliminar el usuario: " + error);
        }
    }

    
    /**
     * 
     * 
     * @param {type} event
     * @returns {undefined}
     */
    function abrirModalEditar(event){
        // Obtener la fila que contiene el botón pulsado
        let fila = event.target.closest("tr");

        // Verificar si la fila y el id de usuario existen
        let idUsuario = fila?.getAttribute("data-fila-id");
        console.log(idUsuario);
        
        //  asignamos los valores obtenidos de la fila a editar en sus campos 
        //  correspondientes
        
        
        //abrimos la modal 
        let modal = document.querySelector('#dialog-editar');
        modal.showModal(); // Muestra la modal
    }

  
  function editarCuentaUsuario(event){
      console.log("entre al evento de editar usuario")
  }