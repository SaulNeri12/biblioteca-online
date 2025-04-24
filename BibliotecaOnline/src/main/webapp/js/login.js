/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 
    Created on : 18 abr 2025
    Author     : sebastian
    Description: JavaScript para el modal de login
*/

document.addEventListener('DOMContentLoaded', function() {
    // Añadir funcionalidad adicional si es necesario
    console.log('Login page loaded');
    
    // Si hay mensajes de error en los parámetros de la URL, mostrarlos
    const urlParams = new URLSearchParams(window.location.search);
    const errorMsg = urlParams.get('error');
    
    if (errorMsg) {
        showError(decodeURIComponent(errorMsg));
    }
});

function showError(message) {
    // Crear un elemento para mostrar el error
    const errorElement = document.createElement('div');
    errorElement.className = 'error-message';
    errorElement.textContent = message;
    
    // Insertar antes del formulario
    const loginBox = document.querySelector('.login-box');
    const form = loginBox.querySelector('form');
    loginBox.insertBefore(errorElement, form);
    
    // Estilizar el mensaje de error
    errorElement.style.color = '#ff3860';
    errorElement.style.marginBottom = '20px';
    errorElement.style.textAlign = 'center';
    errorElement.style.fontSize = '14px';
    
    // Eliminar el mensaje después de 5 segundos
    setTimeout(() => {
        errorElement.style.opacity = '0';
        errorElement.style.transition = 'opacity 0.5s';
        
        setTimeout(() => {
            errorElement.remove();
        }, 500);
    }, 5000);
}

