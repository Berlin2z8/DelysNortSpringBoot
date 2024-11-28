// Mostrar el modal de factura
        document.getElementById("btnFactura").addEventListener("click", function () {
          const modalTipoDocumento = document.getElementById("modalTipoDocumento");
          const bootstrapModalTipo = bootstrap.Modal.getInstance(modalTipoDocumento);
          bootstrapModalTipo.hide(); // Oculta el modal actual
          new bootstrap.Modal(document.getElementById("modalFactura")).show(); // Muestra el modal de factura
        });

        // Procesar venta con tipo de documento
        document.getElementById("btnBoleta").addEventListener("click", function () {
          procesarVenta("boleta");
        });

        function procesarVenta(tipoDocumento) {
          const form = document.querySelector("form");
          const input = document.createElement("input");
          input.type = "hidden";
          input.name = "tipoDocumento";
          input.value = tipoDocumento;
          form.appendChild(input);
          form.submit();
        }