/*
 * Created on : 6 may 2025
 * Author     : sebastian
 * Description: JavaScript para la página de usuarios registrados
 */

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
     * @returns {undefined}
     */
    function abrirModalEliminar(){
        //abrimos la modal de confirmacion de eliminacion
        let modal = document.querySelector('.dialog-agregar');
        modal.showModal();  // Muestra la modal
    }
    
    /**
     * 
     * @returns {undefined}
     */
    function abrirModalEditar(){
        //asignamos los valores obtenidos de la fila a editar en sus campos 
        //  correspondientes
        
        //abrimos la modal 
    }

    function eliminarCuentaUsuario(evento) {
        // Obtener la fila que contiene el botón pulsado
        const fila = evento.target.closest("tr");

        // Verificar si la fila y el id de usuario existen
        const idUsuario = fila?.getAttribute("data-fila-id");
        console.log(idUsuario);
                
        if (!idUsuario) {
        alert("ID del usuario no encontrado.");
            return;
         }

        // Confirmación del usuario antes de eliminar
        const confirmado = window.confirm("¿Estás seguro de que deseas eliminar este usuario?");
        if (!confirmado) {
        console.log("Eliminación cancelada.");
           return;
        }

        // Intentamos realizar la eliminación de forma asíncrona
        try {
        const respuesta = fetch(`http://localhost:8080/BibliotecaOnline/usuario?id_usuario=${idUsuario}`, {
                method: 'DELETE'
        });

        // Verificamos si la respuesta fue exitosa
        if (!respuesta.ok) {
        const errorData =  respuesta.json(); // Intentamos parsear el mensaje de error
            alert(`Error al eliminar: ${errorData.error || errorData.mensaje}`);
            return;
        }

        // Si todo salió bien, eliminamos la fila del DOM
        const data = respuesta.json();
        alert(data.mensaje);  // Mostramos el mensaje de éxito
        fila.remove();  // Eliminar la fila de la tabla

        } catch (error) {
            // Si ocurre algún error (red, servidor, etc.)
            console.error("Error de red o inesperado:", error);
            alert("Ocurrió un error al intentar eliminar el usuario.");
        }

  }
  
  function editarCuentaUsuario(event){
      console.log("entre al evento de editar usuario")
  }