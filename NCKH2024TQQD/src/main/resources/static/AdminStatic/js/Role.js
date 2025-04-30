fetch(`/admin/api/role/show`)
    .then(res => res.json())
    .then(data => {
        const tableBody = document.getElementById("permissionTableBody");
        tableBody.innerHTML = "";

        data.forEach(permission  => {
            const row = `
                <tr id="row-${permission.roleName}-${permission.permissionName}">
                    <td>${permission.roleName}</td>
                    <td id="permissionCell-${permission.roleName}-${permission.permissionName}">
                        ${permission.permissionName}
                    </td>
                    <td id="actionCell-${permission.roleName}-${permission.permissionName}">
                        <button class="btn btn-warning mx-2" onclick="editPermission('${permission.roleName}', '${permission.permissionName}')">Sửa</button>
                        <button class="btn btn-danger" onclick="softDeleteRolePermission('${permission.roleName}', '${permission.permissionName}')">Xóa</button>
                    </td>                                                     
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    })
    .catch(err => {
        console.error("Lỗi khi lấy danh sách role:", err);
    });

function hideAllForms() {
    document.getElementById("RoleAddForm").style.display = "none";
    document.getElementById("rolePermissionTable").style.display = "none"; // r thường
}

function showRoleList() {
    hideAllForms();
    document.getElementById("rolePermissionTable").style.display = "block"; // r thường
}

function showAddRoleFormOnly() {
    hideAllForms();
    document.getElementById("RoleAddForm").style.display = "block";
}

//add permissions
document.getElementById("addRoleForm").addEventListener('submit',function (e){
    e.preventDefault();
    const formdata = new FormData(this);
    const data = {
        roleName: formdata.get("roleName"),
        permissionName: formdata.get("permissionName")
    }
    fetch('/admin/api/role/permissionsAdd',{
        method:"POST",
        headers:{
            'content-Type' : 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(res =>{
            if (res.ok){
                alert("Thêm Thành Công ")
                showRoleList()
                location.reload()
            }else {
                return res.json().then(err=>{throw err;})
            }
        })
        .catch(err => {
            console.log("Lỗi", err)
            alert("Lỗi")
        })
})


// Xóa
function softDeleteRolePermission(roleName , permissionName){
    const url = `/admin/api/role/delete?roleName=${encodeURIComponent(roleName)}&permissionName=${encodeURIComponent(permissionName)}`;
    console.log(roleName)
    console.log(permissionName)
    console.log(url)
    if (confirm("Bạn có chắc chắn muốn Xóa Quyền Này chứ ?")){
        fetch(url,{
            method:"DELETE",
            headers: {
                "Content-Type": "application/json"
            }


        })

            .then(res => {
                    if (res.ok) {
                        alert("Xóa thành công!");
                        location.reload();
                    } else {
                        return res.json().then(errorData => {
                            alert("Xóa thất bại: " + (errorData.message || "Không rõ lỗi"));
                            console.error("Chi tiết lỗi từ backend:", errorData);
                        });
                    }
                })


            .catch(err => {
                console.error("Lỗi khi gọi Api ",err)
                alert("Đã xảy ra Lỗi ")
            })

    }
}
// end Xóa




