/*
 * Created on : 21 abr 2025
 * Author     : sebastian
 * Description: JavaScript para la página de login de administrador
 */

document.addEventListener('DOMContentLoaded', function() {
    console.log('Admin login page loaded');
    
    // Añadir badge de administrador
    const adminBadge = document.createElement('div');
    adminBadge.className = 'admin-badge';
    adminBadge.textContent = 'Área Administrativa';
    document.body.appendChild(adminBadge);
    
    // Añadir funcionalidad al botón para efectos neón
    const button = document.querySelector('button');
    
    // Añadir los spans para el efecto neón
    for (let i = 0; i < 4; i++) {
        const span = document.createElement('span');
        button.prepend(span);
    }
    
    // Verificar si hay mensajes de error en los parámetros de la URL
    const urlParams = new URLSearchParams(window.location.search);
    const errorMsg = urlParams.get('error');
    
    if (errorMsg) {
        showError(decodeURIComponent(errorMsg));
    }
    
    // Añadir validación al formulario
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        // Prevenir envío por defecto
        event.preventDefault();
        
        // Validar campos
        if (validateForm()) {
            // Si todo está bien, enviar el formulario
            form.submit();
        }
    });
    
    // Añadir enlace para volver a la página principal
    const returnLink = document.createElement('div');
    returnLink.className = 'return-link';
    returnLink.innerHTML = '<a href="/BibliotecaOnline/">Volver a la página principal</a>';
    form.parentNode.insertBefore(returnLink, form.nextSibling);
    
    // Aplicar efecto de floating label a los inputs
    setupFloatingLabels();
});

function validateForm() {
    const form = document.querySelector('form');
    const email = form.querySelector('#email').value.trim();
    const contrasena = form.querySelector('#contrasena').value;
    
    // Validar email (formato correcto)
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        showError('Introduce un correo electrónico válido');
        return false;
    }
    
    // Validar contraseña (no vacía)
    if (contrasena === '') {
        showError('La contraseña es obligatoria');
        return false;
    }
    
    return true;
}

function showError(message) {
    // Eliminar mensajes de error previos
    const existingError = document.querySelector('.error-message');
    if (existingError) {
        existingError.remove();
    }
    
    // Crear elemento de error
    const errorElement = document.createElement('div');
    errorElement.className = 'error-message';
    errorElement.textContent = message;
    
    // Insertar antes del formulario
    const form = document.querySelector('form');
    form.parentNode.insertBefore(errorElement, form);
    
    // Eliminar el mensaje después de 5 segundos
    setTimeout(() => {
        errorElement.style.opacity = '0';
        errorElement.style.transition = 'opacity 0.5s';
        
        setTimeout(() => {
            errorElement.remove();
        }, 500);
    }, 5000);
}

// Función para configurar los floating labels
function setupFloatingLabels() {
    const inputs = document.querySelectorAll('input');
    const form = document.querySelector('form');
    
    // Modificar la estructura HTML para soportar floating labels
    inputs.forEach(input => {
        const label = document.querySelector(`label[for="${input.id}"]`);
        if (label) {
            // Obtener el texto de la etiqueta original y eliminar el <br>
            const labelText = label.innerText;
            const nextSibling = label.nextSibling;
            
            // Crear un nuevo div contenedor
            const inputGroup = document.createElement('div');
            inputGroup.className = 'input-group';
            
            // Reemplazar el input original y su etiqueta con el nuevo grupo
            form.insertBefore(inputGroup, label);
            
            // Eliminar el <br> que sigue a la etiqueta
            if (nextSibling && nextSibling.nodeType === 3) {
                form.removeChild(nextSibling);
            }
            
            // Eliminar la etiqueta original
            form.removeChild(label);
            
            // Mover el input al nuevo grupo
            inputGroup.appendChild(input);
            
            // Crear una nueva etiqueta flotante
            const floatingLabel = document.createElement('span');
            floatingLabel.className = 'floating-label';
            floatingLabel.textContent = labelText.replace(':', '');
            inputGroup.appendChild(floatingLabel);
            
            // Quitar el <br><br> que sigue al input
            const nextInputSibling = inputGroup.nextSibling;
            if (nextInputSibling && nextInputSibling.nodeType === 3) {
                form.removeChild(nextInputSibling);
            }
        }
    });
    
    // Añadir estilos CSS para los floating labels
    const style = document.createElement('style');
    style.textContent = `
        .input-group {
            position: relative;
            margin-bottom: 30px;
        }
        
        .input-group input {
            width: 100%;
            padding: 10px 0;
            font-size: 16px;
            color: var(--text-color);
            margin-bottom: 5px;
            border: none;
            border-bottom: 1px solid var(--admin-accent-color);
            outline: none;
            background: transparent;
            transition: 0.5s;
        }
        
        .input-group .floating-label {
            position: absolute;
            top: 0;
            left: 0;
            padding: 10px 0;
            font-size: 16px;
            color: var(--text-color);
            pointer-events: none;
            transition: 0.5s;
        }
        
        .input-group input:focus ~ .floating-label,
        .input-group input:valid ~ .floating-label {
            top: -20px;
            left: 0;
            color: var(--admin-accent-color);
            font-size: 12px;
        }
    `;
    document.head.appendChild(style);
}

// Detectar intento de entrada mediante evento keydown en el documento
document.addEventListener('keydown', function(event) {
    // Animar el botón cuando se presiona Enter
    if (event.key === 'Enter') {
        const button = document.querySelector('button');
        button.classList.add('button-flash');
        
        setTimeout(() => {
            button.classList.remove('button-flash');
        }, 200);
    }
});

// Añadir efecto de typing al título
document.addEventListener('DOMContentLoaded', function() {
    const title = document.querySelector('h1');
    const originalText = title.textContent;
    
    // Solo ejecutar el efecto typing una vez
    if (!sessionStorage.getItem('adminLoginTypingDone')) {
        title.textContent = '';
        let i = 0;
        
        function typeWriter() {
            if (i < originalText.length) {
                title.textContent += originalText.charAt(i);
                i++;
                setTimeout(typeWriter, 100);
            }
        }
        
        typeWriter();
        sessionStorage.setItem('adminLoginTypingDone', 'true');
    }
});