// Selecciona el contenedor
const nosotrosSection = document.getElementById("nosotros-section");

// Estilos del contenedor principal
nosotrosSection.className = "container-fluid px-5";
nosotrosSection.style.marginBottom = "60px";
nosotrosSection.style.color = "rgb(139, 69, 19)";

// Crea la estructura de la sección
const rowDiv = document.createElement("div");
rowDiv.className = "row justify-content-center";

const colDiv = document.createElement("div");
colDiv.className = "col-12 col-md-10 col-lg-8";
colDiv.style.marginTop = "100px";

// Título ¿Quiénes somos?
const title = document.createElement("h3");
title.innerHTML = "<b>¿Quiénes somos?</b>";
colDiv.appendChild(title);

// Visión
const visionTitle = document.createElement("h3");
visionTitle.style.marginTop = "30px";
visionTitle.innerHTML = "<b>Visión:</b>";
const visionText = document.createElement("p");
visionText.textContent = "Queremos ser el restaurante a primera elección en Los Olivos, y en todas partes, donde la comida deliciosa y un servicio excepcional se combinan para crear momentos felices.";
colDiv.appendChild(visionTitle);
colDiv.appendChild(visionText);

// Misión
const missionTitle = document.createElement("h3");
missionTitle.style.marginTop = "30px";
missionTitle.innerHTML = "<b>Misión:</b>";
const missionText = document.createElement("p");
missionText.textContent = "En Torito Grill, nuestra misión es simple: servir la mejor comida, las bebidas más deliciosas y brindar la mejor atención. Queremos que cada visita sea especial, que disfrutes de tus platos favoritos, bebidas de alta calidad y que te sientas como en casa. Nuestro equipo está dedicado a hacer que tu estadía sea inolvidable, porque tu satisfacción es nuestra prioridad número uno. Buscamos mejorar constantemente para ofrecer la mejor experiencia culinaria.";
colDiv.appendChild(missionTitle);
colDiv.appendChild(missionText);

// Valores
const valuesTitle = document.createElement("h3");
valuesTitle.style.marginTop = "30px";
valuesTitle.innerHTML = "<b>Nuestros Valores:</b>";
const valuesList = document.createElement("ol");

// Lista de valores
const values = [
    { title: "Calidad", description: "Utilizamos los insumos más frescos y de la más alta calidad garantizando así que su estadía sea de total agrado" },
    { title: "Pasión", description: "La pasión por los sabores auténticos impulsa cada plato que servimos. Nuestro equipo está comprometido en brindar una atención cálida y personalizada." },
    { title: "Sostenibilidad", description: "Trabajamos con agricultores y proveedores de la región, promoviendo prácticas sostenibles y responsabilidad social." },
    { title: "Encuentro", description: "Torito Grill es el lugar ideal para compartir momentos especiales a la hora de comer y brindar ya sea en familia, con amigos o en pareja." }
];

// Crear los elementos de la lista
values.forEach(value => {
    const listItem = document.createElement("li");
    listItem.innerHTML = `<b>${value.title}:</b> ${value.description}`;
    valuesList.appendChild(listItem);
});

colDiv.appendChild(valuesTitle);
colDiv.appendChild(valuesList);

// Ensambla toda la estructura
rowDiv.appendChild(colDiv);
nosotrosSection.appendChild(rowDiv);
