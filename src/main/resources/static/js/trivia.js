// JavaScript para la sección de Trivia Mejorada y Minijuego
    document.addEventListener('DOMContentLoaded', function() {
        const questions = [
            {
                question: "¿Cuál es la capital de Francia?",
                options: ["París", "Londres", "Madrid", "Berlín"],
                answer: "París",
                image: "https://images.crazygames.com/trivia-crack/20230313103519/trivia-crack-cover?auto=format,compress&q=75&cs=strip"
            },
            {
                question: "¿Cuál es el planeta más grande del sistema solar?",
                options: ["Tierra", "Marte", "Júpiter", "Saturno"],
                answer: "Júpiter",
                image: "https://images.crazygames.com/trivia-crack/20230313103519/trivia-crack-cover?auto=format,compress&q=75&cs=strip"
            },
            // Puedes agregar más preguntas aquí
        ];
    
        let currentQuestionIndex = 0;
        let score = 0;
    
        const triviaContainer = document.getElementById('trivia-container');
        const triviaImage = document.getElementById('trivia-image');
        const triviaQuestion = document.getElementById('trivia-question');
        const triviaOptions = document.getElementById('trivia-options');
        const nextQuestionButton = document.getElementById('next-question');
        const triviaScore = document.getElementById('trivia-score');
    
        // Variables para el minijuego
        let randomNumber;
        const guessNumberGame = document.getElementById('guess-number-game');
        const userGuessInput = document.getElementById('user-guess');
        const submitGuessButton = document.getElementById('submit-guess');
        const guessFeedback = document.getElementById('guess-feedback');
    
        function loadQuestion() {
            const currentQuestion = questions[currentQuestionIndex];
            triviaImage.src = currentQuestion.image;
            triviaQuestion.textContent = currentQuestion.question;
            triviaOptions.innerHTML = '';
    
            currentQuestion.options.forEach(option => {
                const button = document.createElement('button');
                button.textContent = option;
                button.className = 'btn btn-outline-secondary m-2';
                button.onclick = () => checkAnswer(option);
                triviaOptions.appendChild(button);
            });
        }
    
        function checkAnswer(selectedOption) {
            const currentQuestion = questions[currentQuestionIndex];
            if (selectedOption === currentQuestion.answer) {
                score++;
                showConfetti(); // Mostrar confeti al acertar
                alert('¡Correcto!');
            } else {
                alert('Incorrecto. La respuesta correcta era: ' + currentQuestion.answer);
            }
            nextQuestionButton.style.display = 'block';
        }
    
        function showConfetti() {
            // Configuración del confeti
            const duration = 2 * 1000; // Duración en milisegundos
            const animationEnd = Date.now() + duration;
            const colors = ['#bb0000', '#ffffff', '#00bb00', '#0000bb', '#bbbb00'];
    
            (function frame() {
                // Generar confeti
                confetti({
                    particleCount: 7,
                    angle: 60,
                    spread: 55,
                    origin: { x: 0 },
                    colors: colors
                });
                confetti({
                    particleCount: 7,
                    angle: 120,
                    spread: 55,
                    origin: { x: 1 },
                    colors: colors
                });
    
                // Continuar hasta que se cumpla la duración
                if (Date.now() < animationEnd) {
                    requestAnimationFrame(frame);
                }
            })();
        }
    
        nextQuestionButton.onclick = () => {
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.length) {
                loadQuestion();
                nextQuestionButton.style.display = 'none';
            } else {
                triviaContainer.innerHTML = `<h3>¡Has completado la trivia!</h3><p>Tu puntaje es: ${score} de ${questions.length}</p>`;
                startGuessNumberGame(); // Inicia el minijuego al finalizar la trivia
            }
        };
    
        function startGuessNumberGame() {
            randomNumber = Math.floor(Math.random() * 100) + 1; // Número aleatorio entre 1 y 100
            guessNumberGame.style.display = 'block';
            triviaContainer.style.display = 'none'; // Oculta la sección de trivia
        }
    
        submitGuessButton.onclick = () => {
            const userGuess = parseInt(userGuessInput.value);
            if (userGuess === randomNumber) {
                guessFeedback.textContent = '¡Correcto! Has adivinado el número.';
                guessFeedback.style.color = 'green';
            } else if (userGuess < randomNumber) {
                guessFeedback.textContent = 'Demasiado bajo. Intenta de nuevo.';
                guessFeedback.style.color = 'red';
            } else {
                guessFeedback.textContent = 'Demasiado alto. Intenta de nuevo.';
                guessFeedback.style.color = 'red';
            }
            userGuessInput.value = ''; // Limpia el campo de entrada
        };
    
        loadQuestion();
    });