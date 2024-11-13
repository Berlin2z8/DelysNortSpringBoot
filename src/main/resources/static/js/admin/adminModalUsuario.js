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