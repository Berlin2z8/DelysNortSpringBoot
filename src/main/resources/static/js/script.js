var logoLink = document.querySelector('.navegaciones .enlace');
var logo = document.querySelector('.navegaciones .logo');

logo.style.transition = 'transform 0.3s ease'; // Esto agrega transición suave en el logo de la imagen

logoLink.addEventListener('mouseover', function () {
    logo.style.transform = 'scale(1.2)'; // Esto aumenta el tamaño del logo al pasar el mouse sobre el enlace
});

logoLink.addEventListener('mouseout', function () {
    logo.style.transform = 'scale(1)'; // En esta parte regresa el logo a su tamaño original al quitar el mouse del enlace
});

    function eliminarProducto(index) {
        fetch(`/carrito/eliminar/${index}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) {
                // Recargar el modal o la página para reflejar los cambios
                location.reload();
            } else {
                alert('Error al eliminar el producto.');
            }
        })
        .catch(error => console.error('Error:', error));
    }

    // JavaScript para rellenar el modal con los datos del usuario seleccionado
    const editModal = document.getElementById('editModal');
    editModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;
        const id = button.getAttribute('data-id');
        const nombre = button.getAttribute('data-nombre');
        const email = button.getAttribute('data-email');
        const rol = button.getAttribute('data-rol');
        const password = button.getAttribute('data-contrasena'); // Get password data

        // Asignar los valores a los inputs del modal
        document.getElementById('edit-id').value = id;
        document.getElementById('edit-nombre').value = nombre;
        document.getElementById('edit-email').value = email;
        document.getElementById('edit-rol').value = rol;
        document.getElementById('edit-contrasena').value = password; // Assign password value
    });

    // Función para eliminar usuario
    function deleteUsuario(id) {
        if (confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
            window.location.href = `/admin/eliminar/${id}`;
        }
    }
