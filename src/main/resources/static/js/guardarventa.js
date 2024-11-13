function calcularTotal() {
        const precios = document.querySelectorAll('.precio-producto');
        const cantidades = document.querySelectorAll('input[type="number"]');
        let total = 0;

        let productos = []; // Array to store product names

        for (let i = 0; i < precios.length; i++) {
            const precio = parseFloat(precios[i].value) || 0; // Obtener el precio del producto
            const cantidad = parseInt(cantidades[i].value) || 0; // Obtener la cantidad
            total += precio * cantidad; // Sumar al total acumulado

            // Get the product name
            const productoNombre = precios[i].parentElement.querySelector('span').innerText; // Get the product name from the span
            productos.push(productoNombre); // Add the product name to the array
        }

        // Update the total in the HTML
        document.getElementById('total').textContent = total.toFixed(2); // Show total with two decimals
        document.getElementById('totalHidden').value = total.toFixed(2); // Set the hidden total field

        // Set the hidden product names field as a JSON string
        document.getElementById('nombresProductosHidden').value = JSON.stringify(productos);
    }

    function beforeSubmit() {
        calcularTotal(); // Ensure the total is calculated before submission
        return true; // Continue with the form submission
    }