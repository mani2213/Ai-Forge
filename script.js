// Basic in-memory store (simulate DB)
const userStore = {};

const signupWrapper = document.getElementById('signup-wrapper');
const loginWrapper = document.getElementById('login-wrapper');
const homePage = document.getElementById('home-page');

const signupForm = document.getElementById('signup-form');
const loginForm = document.getElementById('login-form');

const signupMessage = document.getElementById('signup-message');
const loginMessage = document.getElementById('login-message');

document.getElementById('show-login').addEventListener('click', e => {
    e.preventDefault();
    clearMessages();
    signupWrapper.style.display = 'none';
    loginWrapper.style.display = 'block';
});

document.getElementById('show-signup').addEventListener('click', e => {
    e.preventDefault();
    clearMessages();
    loginWrapper.style.display = 'none';
    signupWrapper.style.display = 'block';
});

signupForm.addEventListener('submit', e => {
    e.preventDefault();
    clearMessages();

    const email = document.getElementById('signup-email').value.trim().toLowerCase();
    const password = document.getElementById('signup-password').value;
    const confirmPassword = document.getElementById('signup-confirm-password').value;

    if (password !== confirmPassword) {
        showMessage(signupMessage, 'Passwords do not match', 'error');
        return;
    }

    if (userStore[email]) {
        showMessage(signupMessage, 'This email is already registered', 'error');
        return;
    }

    userStore[email] = password;
    showMessage(signupMessage, 'Signup successful! You can now login.', 'success');
    signupForm.reset();
});

loginForm.addEventListener('submit', e => {
    e.preventDefault();
    clearMessages();

    const email = document.getElementById('login-email').value.trim().toLowerCase();
    const password = document.getElementById('login-password').value;

    if (userStore[email] && userStore[email] === password) {
        showMessage(loginMessage, 'Login successful!', 'success');

        // Navigate to home page after short delay
        setTimeout(() => {
            loginWrapper.style.display = 'none';
            homePage.style.display = 'block';
        }, 1000);
    } else {
        showMessage(loginMessage, 'Incorrect email or password', 'error');
    }
});

function showMessage(element, message, type) {
    element.textContent = message;
    element.className = 'message ' + type;
}

function clearMessages() {
    signupMessage.textContent = '';
    loginMessage.textContent = '';
    signupMessage.className = 'message';
    loginMessage.className = 'message';
}
