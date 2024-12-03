paypal.Buttons({
    style: {
        layout: 'vertical',
        color:  'blue',
        shape:  'rect',
        label:  'paypal'
    },
    
    createOrder: function(data, actions) {
        return actions.order.create({
            purchase_units: [{
                amount: {
                    value: '100.00'
                }
            }]
        });
    },

    onApprove: function(data, actions) {
        return actions.order.capture().then(function(orderData) {
            console.log('Capture result', orderData, JSON.stringify(orderData, null, 2));
            const transaction = orderData.purchase_units[0].payments.captures[0];
            alert(`Transaction ${transaction.status}: ${transaction.id}\n\nSee console for all available details`);
        });
    }
}).render('#paypal-button-container');

// Configuración para Pay Later
paypal.Buttons({
    fundingSource: paypal.FUNDING.PAYLATER,
    style: {
        layout: 'vertical',
        color:  'blue',
        shape:  'rect',
        label:  'pay'
    },
    
    createOrder: function(data, actions) {
        const total = document.getElementById('total').textContent;
        return actions.order.create({
            purchase_units: [{
                amount: {
                    value: total
                }
            }]
        });
    },

    onApprove: function(data, actions) {
        return actions.order.capture().then(function(details) {
            document.getElementById('result-message').innerHTML = 
                'Transacción completada por ' + details.payer.name.given_name;
            document.querySelector('form').submit();
        });
    }
}).render('#paypal-button-container');

// Configuración para tarjetas de crédito/débito
paypal.Buttons({
    fundingSource: paypal.FUNDING.CARD,
    style: {
        layout: 'vertical',
        color:  'blue',
        shape:  'rect',
        label:  'pay'
    },
    
    createOrder: function(data, actions) {
        const total = document.getElementById('total').textContent;
        return actions.order.create({
            purchase_units: [{
                amount: {
                    value: total
                }
            }]
        });
    },

    onApprove: function(data, actions) {
        return actions.order.capture().then(function(details) {
            document.getElementById('result-message').innerHTML = 
                'Transacción completada por ' + details.payer.name.given_name;
            document.querySelector('form').submit();
        });
    }
}).render('#paypal-button-container');

// Asegurarse de que el total se actualice cuando cambie
function actualizarBotonesPaypal() {
    const totalElement = document.getElementById('total');
    if (totalElement) {
        const nuevoTotal = totalElement.textContent;
        // Los botones se actualizarán automáticamente con el nuevo total
        console.log('Total actualizado para PayPal:', nuevoTotal);
    }
}

// Agregar el listener para cuando cambie el total
document.addEventListener('DOMContentLoaded', function() {
    const totalElement = document.getElementById('total');
    if (totalElement) {
        const observer = new MutationObserver(actualizarBotonesPaypal);
        observer.observe(totalElement, { childList: true, characterData: true, subtree: true });
    }
});