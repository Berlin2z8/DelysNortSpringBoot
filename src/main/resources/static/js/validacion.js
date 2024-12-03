document.addEventListener('DOMContentLoaded', function() {
    const telefonoInput = document.querySelector('input[name="celular"]');
    
    telefonoInput.addEventListener('input', function(e) {
        // Eliminar cualquier carácter que no sea número
        this.value = this.value.replace(/\D/g, '');
        
        // Limitar a 9 dígitos
        if (this.value.length > 9) {
            this.value = this.value.slice(0, 9);
        }
    });

    // Validación en el formulario
    const form = document.querySelector('form');
    form.addEventListener('submit', function(e) {
        if (telefonoInput.value.length < 9) {
            e.preventDefault();
            telefonoInput.classList.add('error');
            
            // Crear mensaje de error si no existe
            if (!telefonoInput.nextElementSibling?.classList.contains('error-message')) {
                const errorMessage = document.createElement('div');
                errorMessage.className = 'error-message';
                errorMessage.textContent = 'El número de teléfono debe tener 9 dígitos';
                telefonoInput.parentNode.insertBefore(errorMessage, telefonoInput.nextSibling);
            }
        } else {
            telefonoInput.classList.remove('error');
            const errorMessage = telefonoInput.nextElementSibling;
            if (errorMessage?.classList.contains('error-message')) {
                errorMessage.remove();
            }
        }
    });
});
