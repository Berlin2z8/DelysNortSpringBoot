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