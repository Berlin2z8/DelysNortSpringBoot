function compartirEnFacebook() {
    const url = encodeURIComponent(window.location.href);
    window.open(`https://www.facebook.com/sharer/sharer.php?u=${url}`, '_blank');
}

function compartirEnTwitter() {
    const url = encodeURIComponent(window.location.href);
    const texto = encodeURIComponent('¡Mira estos increíbles snacks!');
    window.open(`https://twitter.com/intent/tweet?url=${url}&text=${texto}`, '_blank');
}

function compartirEnWhatsApp() {
    const url = encodeURIComponent(window.location.href);
    const texto = encodeURIComponent('¡Mira estos increíbles snacks!');
    window.open(`https://wa.me/?text=${texto}%20${url}`, '_blank');
}