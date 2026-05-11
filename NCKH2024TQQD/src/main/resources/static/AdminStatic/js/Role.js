/**
 * Role.js — CODEHUBT Admin Role & Permissions Management
 * Refactored: uses AdminNotify instead of alert()/confirm()
 */

let allPermissions = [];
let filteredPermissions = [];

async function loadPermissions() {
    try {
        const res = await fetch('/admin/api/role/show');
        allPermissions = await res.json();
        filteredPermissions = [...allPermissions];
        renderPermissions(filteredPermissions);
    } catch (e) {
        AdminNotify.error('Không thể tải danh sách phân quyền.');
        console.error(e);
    }
}

function renderPermissions(list) {
    const tbody = document.getElementById('permissionTableBody');
    if (!list.length) {
        tbody.innerHTML = `<tr><td colspan="4" style="text-align:center;padding:32px;color:var(--text-muted)">Không có dữ liệu.</td></tr>`;
        return;
    }

    const roleColors = { ADMIN: 'badge-red', TEACHER: 'badge-blue', STUDENT: 'badge-green' };

    tbody.innerHTML = list.map((p, i) => `
        <tr id="row-${p.roleName}-${p.permissionName}">
            <td style="color:var(--text-muted)">${i + 1}</td>
            <td><span class="badge-admin ${roleColors[p.roleName] || 'badge-gray'}">${p.roleName}</span></td>
            <td>
                <code style="background:var(--bg-muted);padding:3px 8px;border-radius:4px;font-size:12px">
                    ${p.permissionName}
                </code>
            </td>
            <td>
                <div style="display:flex;gap:6px">
                    <button class="btn-admin btn-sm-admin btn-outline-admin btn-icon-admin"
                            onclick="openEditRoleModal('${p.roleName}', '${p.permissionName}')" title="Sửa">
                        <i class="fas fa-pen"></i>
                    </button>
                    <button class="btn-admin btn-sm-admin btn-danger-admin btn-icon-admin"
                            onclick="deletePermission('${p.roleName}', '${p.permissionName}')" title="Xóa">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function filterRoles(query) {
    const q = query.toLowerCase();
    filteredPermissions = allPermissions.filter(p =>
        (p.roleName || '').toLowerCase().includes(q) ||
        (p.permissionName || '').toLowerCase().includes(q)
    );
    renderPermissions(filteredPermissions);
}

// ─── Add Permission ───────────────────────────────────────────────────────────
async function submitAddRole(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const data = {
        roleName:       formData.get('roleName'),
        permissionName: formData.get('permissionName'),
    };
    try {
        const res = await fetch('/admin/api/role/permissionsAdd', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });
        if (res.ok) {
            AdminNotify.success('Thêm phân quyền thành công!');
            AdminNotify.closeModal('modal-add-role');
            event.target.reset();
            await loadPermissions();
        } else {
            const msg = await res.text();
            AdminNotify.error('Lỗi: ' + msg);
        }
    } catch (e) {
        AdminNotify.error('Lỗi kết nối: ' + e.message);
    }
}

// ─── Edit Permission ──────────────────────────────────────────────────────────
function openEditRoleModal(roleName, permissionName) {
    document.getElementById('edit-roleName').value      = roleName;
    document.getElementById('edit-oldPermission').value = permissionName;
    document.getElementById('edit-roleDisplay').value   = roleName;
    document.getElementById('edit-newPermission').value = permissionName;
    AdminNotify.openModal('modal-edit-role');
}

async function submitEditRole() {
    const roleName         = document.getElementById('edit-roleName').value;
    const oldPermissionName = document.getElementById('edit-oldPermission').value;
    const newPermissionName = document.getElementById('edit-newPermission').value.trim();

    if (!newPermissionName) { AdminNotify.warn('Tên quyền không được để trống!'); return; }

    try {
        const res = await fetch('/admin/api/role/update', {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ roleName, oldPermissionName, newPermissionName }),
        });
        if (res.ok) {
            AdminNotify.success('Cập nhật quyền thành công!');
            AdminNotify.closeModal('modal-edit-role');
            await loadPermissions();
        } else {
            AdminNotify.error('Cập nhật thất bại!');
        }
    } catch (e) {
        AdminNotify.error('Lỗi kết nối: ' + e.message);
    }
}

// ─── Delete Permission ────────────────────────────────────────────────────────
function deletePermission(roleName, permissionName) {
    AdminNotify.confirm(
        `Bạn có chắc muốn xóa quyền <strong>${permissionName}</strong> khỏi vai trò <strong>${roleName}</strong>?`,
        async () => {
            const url = `/admin/api/role/delete?roleName=${encodeURIComponent(roleName)}&permissionName=${encodeURIComponent(permissionName)}`;
            try {
                const res = await fetch(url, { method: 'DELETE' });
                if (res.ok) {
                    AdminNotify.success('Đã xóa quyền thành công!');
                    await loadPermissions();
                } else {
                    AdminNotify.error('Xóa thất bại!');
                }
            } catch (e) {
                AdminNotify.error('Lỗi kết nối: ' + e.message);
            }
        }
    );
}

// Init
loadPermissions();
