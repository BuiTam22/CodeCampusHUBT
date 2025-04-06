document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const apiUrl = `${apiBaseUrl}/api/user/login`;

    fetch(apiUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password }),
        credentials: "include" // ⚠️ Rất quan trọng để cookie được gửi về!
    })
    .then(response => {
        if (response.ok) {
            window.location.href = "/problem/";
        } else {
            alert("Login failed!");
        }
    })
    .catch(error => alert("Login failed!"));
});
