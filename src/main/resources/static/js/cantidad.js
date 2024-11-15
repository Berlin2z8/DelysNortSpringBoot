 // Script para aumentar y disminuir la cantidad
 document.getElementById('increaseBtn').addEventListener('click', function() {
    var input = document.getElementById('cantidadProducto');
    input.value = parseInt(input.value) + 1;
});

document.getElementById('decreaseBtn').addEventListener('click', function() {
    var input = document.getElementById('cantidadProducto');
    if (input.value > 1) {
        input.value = parseInt(input.value) - 1;
    }
});