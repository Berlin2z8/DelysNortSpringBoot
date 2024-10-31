var logoLink = document.querySelector('.navegaciones .enlace');
var logo = document.querySelector('.navegaciones .logo');

logo.style.transition = 'transform 0.3s ease'; // Esto agrega transición suave en el logo de la imagen

logoLink.addEventListener('mouseover', function () {
    logo.style.transform = 'scale(1.2)'; // Esto aumenta el tamaño del logo al pasar el mouse sobre el enlace
});

logoLink.addEventListener('mouseout', function () {
    logo.style.transform = 'scale(1)'; // En esta parte regresa el logo a su tamaño original al quitar el mouse del enlace
});