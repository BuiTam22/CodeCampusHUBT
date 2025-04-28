// Cập nhật file login.js
document.getElementById("loginForm").addEventListener("submit", async function(event) {
    event.preventDefault();
    
    const submitBtn = document.getElementById("submitBtn");
    const originalText = submitBtn.querySelector(".button-text").textContent;
    
    try {
        // Vô hiệu hóa nút và hiển thị loading
        submitBtn.disabled = true;
        submitBtn.classList.add("loading");
        submitBtn.querySelector(".button-text").textContent = "Loading...";

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
        const apiUrl = `${apiBaseUrl}/api/user/login`;

        const response = await fetch(apiUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password }),
            credentials: "include"
        });

        if (response.ok) {
            window.location.href = "/";
        } else {
            const errorData = await response.json();
            alert(errorData.message || "Login failed!");
        }
    } catch (error) {
        alert("Login failed!");
    } finally {
        // Khôi phục trạng thái nút
        submitBtn.disabled = false;
        submitBtn.classList.remove("loading");
        submitBtn.querySelector(".button-text").textContent = originalText;
    }
});