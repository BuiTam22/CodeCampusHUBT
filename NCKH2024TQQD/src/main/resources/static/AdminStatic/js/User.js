fetch('http://localhost:3000/admin/api/user/show')

    .then(response => response.json())
    .then(data => {
        const tableBody = document.getElementById("userTableBody");
        tableBody.innerHTML = ""; // Xóa dữ liệu cũ

        data.forEach(user => {
            const row = `
                    <tr>
                        <td>${user.userID}</td>
                        <td>${user.userName}</td>
                        <td>${user.email}</td>
                        <td>${user.userRole}</td>
                        <td>
                        <button class="btn btn-success" onclick="showDetailForm(${user.userID}, '${user.userName}', '${user.email}', '${user.userRole}')">Chi tiết</button>
                        <button class="btn btn-warning mx-2" onclick="showEditForm(${user.userID}, '${user.userName}', '${user.email}', '${user.userRole}')">Sửa</button>
                        <button class="btn btn-danger">  Xóa </button>
                        </td>
                    </tr>
                `;
            tableBody.innerHTML += row;
        });
    })
    .catch(error => {
        console.error("Lỗi khi lấy danh sách user:", error);
    });


//an hien form
    function hideForms() {
    document.getElementById("userAddForm").style.display = "none";
    document.getElementById("userDetailForm").style.display = "none";
    document.getElementById("userEditForm").style.display = "none";
}

    function showAddForm() {
    hideForms();
    document.getElementById("userAddForm").style.display = "block";
}

    function showDetailForm() {
    hideForms();
    document.getElementById("userDetailForm").style.display = "block";
}

    function showEditForm() {
    hideForms();
    document.getElementById("userEditForm").style.display = "block";
}

//end an hien form

function showDetailForm(id, name, email, role) {
    hideForms();
    document.getElementById("detailUserId").innerText = id;
    document.getElementById("detailUserName").innerText = name;
    document.getElementById("detailEmail").innerText = email;
    document.getElementById("detailUserRole").innerText = role;
    document.getElementById("userDetailForm").style.display = "block";
}

function showEditForm(id, name, email, role) {
    hideForms();
    const form = document.getElementById("userEditForm");
    form.querySelector("input[type='hidden']").value = id;
    form.querySelector("input[type='text']").value = name;
    form.querySelector("input[type='email']").value = email;
    form.querySelector("select").value = role;
    form.style.display = "block";
}


// thêm người DÙng
document.getElementById("addUserForm").addEventListener("submit", function (event) {
    event.preventDefault();

    const formData = new FormData(event.target);

    const data = {
        userName: formData.get("userName"),
        email: formData.get("email"),
        password: formData.get("password"),
        fullName: formData.get("fullName"),
        dateOfBirth: formData.get("dateOfBirth"),
        phoneNumber: formData.get("phoneNumber"),
        address: formData.get("address"),
        roleName: formData.get("roleName") // nếu form chỉ chọn 1 quyền
    };

    fetch("/admin/api/user/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data)
    })
        .then(res => {
            if (res.ok) {
                alert("Thêm thành công!");
                document.getElementById("userAddForm").style.display = "none";
            } else {
                return res.text().then(errorMessage => {
                    alert("Lỗi từ server: " + errorMessage);
                    console.error("Chi tiết lỗi:", errorMessage);
                });
            }
        })
        .catch(error => {
            alert("Lỗi khi gọi API: " + error.message);
            console.error("Lỗi mạng hoặc lỗi khác:", error);
        });
});


//end thêm người dùng

//upload hình ảnh
async function uploadImage(file) {
    const formData = new FormData();
    formData.append("file", file);

    const res = await fetch("/api/upload/image", {
        method: "POST",
        body: formData
    });

    const data = await res.json();
    document.getElementById("avatarUrl").value = data.url;
    document.getElementById("preview").src = data.url;
    document.getElementById("preview").style.display = "block";
}

//end upluad hình ảnh