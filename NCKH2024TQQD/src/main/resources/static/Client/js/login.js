document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Ngăn chặn form submit theo cách truyền thống

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("/api/login", { // Gửi dữ liệu đến API backend
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            window.location.href = "/dashboard"; // Chuyển hướng nếu đăng nhập thành công
        } else {
            alert("Login failed!"); // Hiển thị lỗi nếu đăng nhập thất bại
        }
    })
    .catch(error => console.error("Error:", error));
});