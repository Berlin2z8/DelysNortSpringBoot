document.addEventListener('DOMContentLoaded', function() {
    const sections = document.querySelectorAll('.form-section');
    const steps = document.querySelectorAll('.step');
    let currentStep = 0;

    // Mostrar solo la primera sección inicialmente
    showSection(currentStep);

    function showSection(stepIndex) {
        sections.forEach((section, index) => {
            if (index === stepIndex) {
                section.style.display = 'block';
                steps[index].classList.add('active');
            } else {
                section.style.display = 'none';
                steps[index].classList.remove('active');
            }
        });

        // Actualizar botones de navegación
        updateNavigationButtons();
    }

    function updateNavigationButtons() {
        const prevBtn = document.getElementById('prevBtn');
        const nextBtn = document.getElementById('nextBtn');
        const submitBtn = document.getElementById('submitBtn');

        if (currentStep === 0) {
            prevBtn.style.display = 'none';
        } else {
            prevBtn.style.display = 'block';
        }

        if (currentStep === sections.length - 1) {
            nextBtn.style.display = 'none';
            submitBtn.style.display = 'block';
        } else {
            nextBtn.style.display = 'block';
            submitBtn.style.display = 'none';
        }
    }

    // Event listeners para los botones de navegación
    document.getElementById('prevBtn').addEventListener('click', () => {
        if (currentStep > 0) {
            currentStep--;
            showSection(currentStep);
        }
    });

    document.getElementById('nextBtn').addEventListener('click', () => {
        if (validateCurrentSection() && currentStep < sections.length - 1) {
            currentStep++;
            showSection(currentStep);
        }
    });

    function validateCurrentSection() {
        const currentSection = sections[currentStep];
        const requiredFields = currentSection.querySelectorAll('[required]');
        let isValid = true;

        requiredFields.forEach(field => {
            if (!field.value) {
                isValid = false;
                field.classList.add('error');
            } else {
                field.classList.remove('error');
            }
        });

        return isValid;
    }
}); 