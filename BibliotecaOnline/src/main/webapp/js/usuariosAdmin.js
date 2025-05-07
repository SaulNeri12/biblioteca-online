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