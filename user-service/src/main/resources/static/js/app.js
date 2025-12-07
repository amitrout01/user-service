// ===== LOGIN FUNCTION =====
async function login() {
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;

    try {
        const response = await fetch('/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        const data = await response.json();

        if (response.ok) {
            // Save JWT and username
            localStorage.setItem('jwtToken', data.data); // token is in data.data
            localStorage.setItem('username', data.message); // username in message
            document.getElementById('response').innerText = "Login successful! Redirecting...";
            // Redirect to dashboard page
            window.location.href = '/dashboard.html';
        } else {
            document.getElementById('response').innerText = data.message || "Login failed!";
        }
    } catch (err) {
        console.error(err);
        document.getElementById('response').innerText = "Error connecting to server.";
    }
}

// ===== SIGNUP FUNCTION =====
async function signup() {
    const name = document.getElementById('signup-name').value;
    const username = document.getElementById('signup-username').value;
    const email = document.getElementById('signup-email').value;
    const password = document.getElementById('signup-password').value;

    try {
        const response = await fetch('/auth/signup', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name, username, email, password })
        });

        const data = await response.json();

        if (response.ok) {
            document.getElementById('response').innerText = "Sign Up successful! You can now login.";
            // Optionally, switch to login form
            document.getElementById('signup-form').style.display = 'none';
            document.getElementById('login-form').style.display = 'block';
        } else {
            document.getElementById('response').innerText = data.message || "Sign Up failed!";
        }
    } catch (err) {
        console.error(err);
        document.getElementById('response').innerText = "Error connecting to server.";
    }
}
