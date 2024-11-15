/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


// Función para cargar un archivo HTML en un contenedor
function loadHTML(url, containerId) {
    fetch(url)
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.text();
        })
        .then((data) => {
            document.getElementById(containerId).innerHTML = data;
        })
        .catch((error) => {
            console.error("Error loading HTML:", error);
        });
}

// Cargar el encabezado y el pie de página
loadHTML('header.html', 'header-container');
loadHTML('footer.html', 'footer-container');