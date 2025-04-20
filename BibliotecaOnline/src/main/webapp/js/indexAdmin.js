/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/gulpfile.js to edit this template
 */

window.onload = () => {
    fetch('generos') // <-- nombre del servlet
        .then(response => response.json())
        .then(data => {
            const select = document.getElementById('genero');
            // Opción vacía para "todos"
            const opcionVacia = document.createElement('option');
            opcionVacia.value = "";
            opcionVacia.text = "-- Todos --";
            select.appendChild(opcionVacia);

            data.forEach(genero => {
                const option = document.createElement('option');
                option.value = genero;
                option.text = genero;
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Error al cargar géneros:', error));
};


