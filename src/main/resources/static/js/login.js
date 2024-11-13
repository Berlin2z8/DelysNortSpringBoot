const signUpButton = document.getElementById('signUp');
        const signInButton = document.getElementById('signIn');
        const container = document.getElementById('container');

        // Agrega la clase para activar el panel de registro
        signUpButton.addEventListener('click', () => {
            container.classList.add("right-panel-active");
        });

        // Elimina la clase para volver al panel de inicio de sesión
        signInButton.addEventListener('click', () => {
            container.classList.remove("right-panel-active");
            });