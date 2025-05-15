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

//ẩn Hiện Form

function hideAllForm() {
    document.getElementById("loginForm").style.display = "none";
    document.getElementById("registerForm").style.display = "none";
    document.getElementById("forgotForm").style.display = "none";
    document.getElementById("navButtons").style.display = "none";
}

function showFormLogin() {
    hideAllForm();
    document.getElementById("loginForm").style.display = "block";
    document.getElementById("navButtons").style.display = "block";
}

function showFormRegister() {
    hideAllForm();
    document.getElementById("registerForm").style.display = "block";
}

function showFormForgot() {
    hideAllForm();
    document.getElementById("forgotForm").style.display = "block";
}

//end Ẩn Hiện Form

//regiter
    document.getElementById("registerForm").addEventListener("submit", function (e) {
        e.preventDefault()
        const formData = new FormData(e.target)
        // console.log(formData)
        const data = {
            email:formData.get("userName"),
            fullName:formData.get("email"),
            userName:formData.get("password"),
            password: formData.get("fullName"),
            dateOfBirth :formData.get("dateOfBirth"),
            phoneNumber :formData.get("phoneNumber"),
            address :formData.get("address"),
            roleName:formData.get("role")



        }
        console.log(data);
        fetch("http://localhost:3000/api/user/register",{

            method: "POST",
            headers : {
                "Content-Type" : "application/json",
            },
            body:JSON.stringify(data)
        })


            .then(res => {
                if (res.ok) {
                    alert("Thêm Thành Công Người Dùng !")
                    location.reload()

                } else {
                    return res.text().then(errmes => {
                        alert("Lỗi Từ Server " + errmes)
                        console.log("Chi TIết Lỗi " + errmes)
                    })

                }

            })
            .catch(err =>{
                alert("Lỗi Khi Gọi AP" + err.message)
                console.log("Lỗi " +  err)

            })
    })
//end regitter