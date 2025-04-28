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
                        <button class="btn btn-danger" onclick="deletePermission('${permission.roleName}', '${permission.permissionName}')">Xóa</button>
                    </td>                                                     
                </tr>
            `;
            tableBody.innerHTML += row;
        });
    })
    .catch(err => {
        console.error("Lỗi khi lấy danh sách role:", err);
    });

function editPermission(roleName, permissionName) {
    const permissionCell = document.getElementById(`permissionCell-${roleName}-${permissionName}`);
    const actionCell = document.getElementById(`actionCell-${roleName}-${permissionName}`);

    // Đổi thành input
    permissionCell.innerHTML = `<input type="text" id="input-${roleName}-${permissionName}" value="${permissionName}" class="form-control">`;

    // Đổi nút
    actionCell.innerHTML = `
        <button class="btn btn-success mx-2" onclick="savePermission('${roleName}', '${permissionName}')">Lưu</button>
        <button class="btn btn-secondary" onclick="cancelEdit('${roleName}', '${permissionName}')">Hủy</button>
    `;
}


function savePermission(roleName, oldPermissionName) {
    const input = document.getElementById(`input-${roleName}-${oldPermissionName}`);
    const newPermissionName = input.value;

    const updatedPermission = {
        roleName: roleName,
        permissionName: newPermissionName
    };

    fetch('/admin/api/role/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedPermission)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Cập nhật thất bại');
            }
            return response.json();
        })
        .then(data => {
            alert('Cập nhật thành công!');
            fetchPermissions();
        })
        .catch(error => {
            console.error('Lỗi cập nhật:', error);
            alert('Cập nhật thất bại!');
        });
}

function cancelEdit(roleName, permissionName) {
    const permissionCell = document.getElementById(`permissionCell-${roleName}-${permissionName}`);
    const actionCell = document.getElementById(`actionCell-${roleName}-${permissionName}`);

    // Khôi phục lại tên permission cũ
    permissionCell.innerHTML = permissionName;

    // Khôi phục lại 2 nút "Sửa" + "Xóa"
    actionCell.innerHTML = `
        <button class="btn btn-warning mx-2" onclick="editPermission('${roleName}', '${permissionName}')">Sửa</button>
        <button class="btn btn-danger" onclick="deletePermission('${roleName}', '${permissionName}')">Xóa</button>
    `;
}
