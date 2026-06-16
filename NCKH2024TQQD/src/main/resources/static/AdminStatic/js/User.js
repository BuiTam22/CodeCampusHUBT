/**
 * User.js — CODEHUBT Admin User Management
 * Refactored: uses AdminNotify instead of alert()/confirm()
 */

// ─── Load & render users ────────────────────────────────────────────────────
const PAGE_SIZE = 8;
let allUsers = [];
let filteredUsers = [];
let currentPage = 0;

async function loadUsers() {
    try {
        const res = await fetch('/admin/api/user/show');
        allUsers = await res.json();
        filteredUsers = [...allUsers];
        renderPage(0);
    } catch (e) {
        AdminNotify.error('Không thể tải danh sách người dùng.');
        console.error(e);
    }
}

function renderPage(page) {
    currentPage = page;
    const start = page * PAGE_SIZE;
    const slice = filteredUsers.slice(start, start + PAGE_SIZE);
    const tbody = document.getElementById('userTableBody');

    if (slice.length === 0) {
        tbody.innerHTML = `<tr><td colspan="5" style="text-align:center;padding:32px;color:var(--text-muted)">Không có người dùng nào.</td></tr>`;
        document.getElementById('page-info').textContent = '';
        document.getElementById('pagination').innerHTML = '';
        return;
    }

    const roleBadge = (role) => {
        const map = { ADMIN: 'badge-red', TEACHER: 'badge-blue', STUDENT: 'badge-green' };
        return `<span class="badge-admin ${map[role] || 'badge-gray'}">${role}</span>`;
    };

    tbody.innerHTML = slice.map((u, i) => `
        <tr>
            <td style="color:var(--text-muted)">${start + i + 1}</td>
            <td>
                <div style="display:flex;align-items:center;gap:10px">
                    <div style="width:32px;height:32px;border-radius:50%;background:linear-gradient(135deg,var(--primary),var(--accent));display:flex;align-items:center;justify-content:center;color:#fff;font-size:13px;font-weight:700;flex-shrink:0">
                        ${(u.userName || 'U')[0].toUpperCase()}
                    </div>
                    <span style="font-weight:500">${u.userName}</span>
                </div>
            </td>
            <td style="color:var(--text-secondary)">${u.email}</td>
            <td>${roleBadge(u.userRole)}</td>
            <td>
                <div style="display:flex;gap:6px">
                    <button class="btn-admin btn-sm-admin btn-outline-admin btn-icon-admin"
                            onclick="openEditModal(${u.userID})" title="Chỉnh sửa">
                        <i class="fas fa-pen"></i>
                    </button>
                    <button class="btn-admin btn-sm-admin btn-danger-admin btn-icon-admin"
                            onclick="deleteUser(${u.userID})" title="Xóa">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');

    // Pagination info
    const total = filteredUsers.length;
    const totalPages = Math.ceil(total / PAGE_SIZE);
    document.getElementById('page-info').textContent =
        `Hiển thị ${start + 1}–${Math.min(start + PAGE_SIZE, total)} / ${total} người dùng`;

    const pg = document.getElementById('pagination');
    pg.innerHTML = '';
    for (let p = 0; p < totalPages; p++) {
        const btn = document.createElement('button');
        btn.className = 'btn-admin btn-sm-admin ' + (p === page ? 'btn-primary-admin' : 'btn-outline-admin');
        btn.textContent = p + 1;
        btn.onclick = () => renderPage(p);
        pg.appendChild(btn);
    }
}

function filterUsers(query) {
    const q = query.toLowerCase();
    filteredUsers = allUsers.filter(u =>
        (u.userName || '').toLowerCase().includes(q) ||
        (u.email || '').toLowerCase().includes(q) ||
        (u.userRole || '').toLowerCase().includes(q)
    );
    renderPage(0);
}

// ─── Add User ────────────────────────────────────────────────────────────────
async function submitAddUser(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const data = {
        userName:    formData.get('userName'),
        email:       formData.get('email'),
        password:    formData.get('password'),
        fullName:    formData.get('fullName'),
        dateOfBirth: formData.get('dateOfBirth'),
        phoneNumber: formData.get('phoneNumber'),
        address:     formData.get('address'),
        roleName:    formData.get('roleName'),
    };
    try {
        const res = await fetch('/admin/api/user/add', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });
        if (res.ok) {
            AdminNotify.success('Thêm người dùng thành công!');
            AdminNotify.closeModal('modal-add-user');
            event.target.reset();
            await loadUsers();
        } else {
            const msg = await res.text();
            AdminNotify.error('Lỗi từ server: ' + msg);
        }
    } catch (e) {
        AdminNotify.error('Lỗi kết nối: ' + e.message);
    }
}

// ─── Edit User ───────────────────────────────────────────────────────────────
async function openEditModal(userId) {
    try {
        const res = await fetch(`/admin/api/user/showUpdate/${userId}`);
        const user = await res.json();

        document.getElementById('edit-userId').value    = user.userId;
        document.getElementById('edit-userName').value  = user.userName  || '';
        document.getElementById('edit-email').value     = user.email     || '';
        document.getElementById('edit-fullName').value  = user.fullName  || '';
        document.getElementById('edit-dob').value       = user.dateOfBirth || '';
        document.getElementById('edit-phone').value     = user.phoneNumber || '';
        document.getElementById('edit-address').value   = user.address   || '';

        // Role checkboxes
        const allRoles = ['ADMIN', 'STUDENT', 'TEACHER'];
        const userRoles = Array.isArray(user.roleName) ? user.roleName : [];
        const container = document.getElementById('roleCheckboxes');
        container.innerHTML = allRoles.map(role => `
            <label style="display:flex;align-items:center;gap:6px;font-size:13px;cursor:pointer">
                <input type="checkbox" name="roleNames" value="${role}"
                       ${userRoles.includes(role) ? 'checked' : ''}
                       style="accent-color:var(--primary)"/>
                ${role}
            </label>
        `).join('');

        AdminNotify.openModal('modal-edit-user');
    } catch (e) {
        AdminNotify.error('Không thể tải thông tin người dùng.');
        console.error(e);
    }
}

async function submitEditUser() {
    const userId = document.getElementById('edit-userId').value;
    if (!userId) { AdminNotify.warn('Không tìm thấy User ID!'); return; }

    const form = document.getElementById('editUserForm');
    const file = document.getElementById('edit-image').files[0];
    let imageUrl = '';

    if (file) {
        try {
            const fd = new FormData();
            fd.append('file', file);
            const r = await fetch('/admin/api/upload', { method: 'POST', body: fd });
            if (!r.ok) { AdminNotify.error('Lỗi tải ảnh lên.'); return; }
            imageUrl = (await r.json()).url;
        } catch (e) {
            AdminNotify.error('Lỗi upload ảnh: ' + e.message);
            return;
        }
    }

    const roleNames = Array.from(
        form.querySelectorAll("input[name='roleNames']:checked")
    ).map(cb => cb.value);

    const dto = {
        userName:    document.getElementById('edit-userName').value,
        email:       document.getElementById('edit-email').value,
        fullName:    document.getElementById('edit-fullName').value,
        dateOfBirth: document.getElementById('edit-dob').value,
        phoneNumber: document.getElementById('edit-phone').value,
        address:     document.getElementById('edit-address').value,
        image:       imageUrl || undefined,
        roleName:    roleNames,
    };

    try {
        const res = await fetch(`/admin/api/user/update/${userId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dto),
        });
        if (res.ok) {
            AdminNotify.success('Cập nhật người dùng thành công!');
            AdminNotify.closeModal('modal-edit-user');
            await loadUsers();
        } else {
            AdminNotify.error('Lỗi cập nhật: ' + await res.text());
        }
    } catch (e) {
        AdminNotify.error('Lỗi kết nối: ' + e.message);
    }
}

// ─── Delete User ─────────────────────────────────────────────────────────────
function deleteUser(userId) {
    AdminNotify.confirm(
        'Bạn có chắc muốn xóa người dùng này không?',
        async () => {
            try {
                const res = await fetch(`/admin/api/user/delete/${userId}`, { method: 'DELETE' });
                if (res.ok) {
                    AdminNotify.success('Đã xóa người dùng thành công!');
                    await loadUsers();
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
loadUsers();