/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener("DOMContentLoaded", () => {
    const $form = document.querySelector("#form-registro");

    $form.addEventListener('submit', e => {
        e.preventDefault();

        const nombre = document.getElementById('nombre').value;
        const email = document.getElementById('email').value;
        const telefono = document.getElementById('telefono').value;
        const fechaNac = document.getElementById('fecha_nac').value;
        const contrasena = document.getElementById('contrasena').value;
        const contrasenaConfirm = document.getElementById('contrasena-confirm').value;
        
        if (contrasena !== contrasenaConfirm) {
            // mostrar alerta en el form (no alert, un rectangulo rojo indicando el error)
            alert("las contraseñas deben de coincidir");
            return;
        }
        
        // realizar validaciones...
        
        console.log("sending...");
        
        $form.submit();
    });
});

