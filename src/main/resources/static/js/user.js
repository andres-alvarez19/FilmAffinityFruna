document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('userForm').addEventListener('submit', submitUserForm);
});

function submitUserForm(event) {
    event.preventDefault();

    const user = {
        username: document.getElementById('name').value,
        password: document.getElementById('password').value,
        email: document.getElementById('email').value,
    };
    const messageDiv = document.getElementById('message');

    fetch('/user/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
        .then(response => response.text())
        .then(data => {
            messageDiv.innerHTML = '<p style="color: green;" >User created successfully</p>';
        })
        .catch((error) => {
            messageDiv.innerHTML = '<p style="color: red;">Error creating user '+ '</p>';
        });
}
