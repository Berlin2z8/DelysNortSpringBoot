function deleteVenta(id) {
    if (confirm('¿Estás seguro de que deseas eliminar esta venta?')) {
        window.location.href = `/admin/ventas/eliminar/${id}`;
    }
}
const editVentaModal = document.getElementById('editVentaModal');
editVentaModal.addEventListener('show.bs.modal', event => {
    const button = event.relatedTarget;

    const id = button.getAttribute('data-id');
    const nombre = button.getAttribute('data-nombre');
    const email = button.getAttribute('data-email');
    const celular = button.getAttribute('data-celular');
    const productos = button.getAttribute('data-productos');
    const cantidad = button.getAttribute('data-cantidad');
    const total = button.getAttribute('data-total');

    // Asigna los valores a los inputs del modal
    document.getElementById('edit-venta-id').value = id;
    document.getElementById('edit-venta-nombre').value = nombre;
    document.getElementById('edit-venta-email').value = email;
    document.getElementById('edit-venta-celular').value = celular;
    document.getElementById('edit-venta-productos').value = productos;
    document.getElementById('edit-venta-cantidad').value = cantidad;
    document.getElementById('edit-venta-total').value = total;
});
