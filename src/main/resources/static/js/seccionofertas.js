// Selecciona el contenedor
const ofertasSection = document.getElementById("ofertas-section");

// Agrega la clase de contenedor y otros estilos
ofertasSection.className = "container ofertas mt-5";

// Título de la sección
const title = document.createElement("h2");
title.className = "text-center mb-4";
title.textContent = "Ofertas Especiales";
ofertasSection.appendChild(title);

// Contenedor de las ofertas
const rowDiv = document.createElement("div");
rowDiv.className = "row";

// Datos de las ofertas
const ofertas = [
    {
        imgSrc: "/images/delys/ofertasnack.jpg",
        altText: "Oferta 1",
        title: "Descuento del 20%",
        description: "En todos los productos de la categoría Snacks.",
        link: "#"
    },
    {
        imgSrc: "/images/delys/coditodulce.jpg",
        altText: "Oferta 2",
        title: "Combo Familiar",
        description: "Llévate 3 productos por el precio de 2.",
        link: "#"
    },
    {
        imgSrc: "/images/delys/camotesnack.jpg",
        altText: "Oferta 3",
        title: "Envío Gratis",
        description: "En compras mayores a S/ 50.",
        link: "#"
    }
];

// Genera las tarjetas de oferta
ofertas.forEach(oferta => {
    const colDiv = document.createElement("div");
    colDiv.className = "col-md-4";

    const cardDiv = document.createElement("div");
    cardDiv.className = "card";

    const img = document.createElement("img");
    img.src = oferta.imgSrc;
    img.className = "card-img-top";
    img.alt = oferta.altText;

    const cardBody = document.createElement("div");
    cardBody.className = "card-body";

    const cardTitle = document.createElement("h5");
    cardTitle.className = "card-title";
    cardTitle.textContent = oferta.title;

    const cardText = document.createElement("p");
    cardText.className = "card-text";
    cardText.textContent = oferta.description;

    const link = document.createElement("a");
    link.href = oferta.link;
    link.className = "btn btn-primary";
    link.textContent = "Ver Más";

    // Ensambla la tarjeta
    cardBody.appendChild(cardTitle);
    cardBody.appendChild(cardText);
    cardBody.appendChild(link);

    cardDiv.appendChild(img);
    cardDiv.appendChild(cardBody);

    colDiv.appendChild(cardDiv);
    rowDiv.appendChild(colDiv);
});

// Agrega el contenedor de tarjetas a la sección de Ofertas
ofertasSection.appendChild(rowDiv);
