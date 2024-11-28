// Mostrar el modal al hacer clic en el botón "Guardar Venta"
      function mostrarModal(event) {
        event.preventDefault(); // Evitar el envío del formulario
        document.getElementById("modalTipoDocumento").style.display = "block";
      }

      // Ocultar el modal
      function ocultarModal() {
        document.getElementById("modalTipoDocumento").style.display = "none";
      }

      // Función para enviar el formulario con el tipo de documento seleccionado
      function procesarVenta(tipoDocumento) {
        const form = document.querySelector("form");

        // Agregar el tipo de documento al formulario como campo oculto
        const tipoDocumentoInput = document.createElement("input");
        tipoDocumentoInput.type = "hidden";
        tipoDocumentoInput.name = "tipoDocumento";
        tipoDocumentoInput.value = tipoDocumento;
        form.appendChild(tipoDocumentoInput);

        // Enviar el formulario
        form.target = "_blank"; // Abrir en una nueva pestaña
        form.submit();

        ocultarModal();
      }

      // Botón de "Guardar Venta" abre el modal
      document
        .querySelector("form button[type='submit']")
        .addEventListener("click", mostrarModal);

      // Botón de "Boleta" en el modal
      document.getElementById("btnBoleta").onclick = function () {
        procesarVenta("boleta");
      };