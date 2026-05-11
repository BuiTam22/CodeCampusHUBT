/**
 * scriptsCourse.js — CODEHUBT Admin Course Management
 * Refactored: renders table using design system classes
 */

let allCourses = [];
let filteredCourses = [];

async function loadCourses() {
    try {
        const res = await fetch('/admin/api/course/show');
        allCourses = await res.json();
        filteredCourses = [...allCourses];
        renderCourses(filteredCourses);
    } catch (e) {
        AdminNotify.error('Không thể tải danh sách khóa học.');
        console.error(e);
    }
}

function renderCourses(list) {
    const tbody = document.getElementById('courseTableBody');
    if (!list || list.length === 0) {
        tbody.innerHTML = `<tr><td colspan="5" style="text-align:center;padding:32px;color:var(--text-muted)">
            <i class="fas fa-folder-open" style="font-size:20px;display:block;margin-bottom:8px;opacity:0.4"></i>
            Không có khóa học nào.
        </td></tr>`;
        return;
    }

    const isAdmin = list.some(c => c.roleName === 'ADMIN');

    tbody.innerHTML = list.map((course, i) => `
        <tr>
            <td style="color:var(--text-muted)">${i + 1}</td>
            <td>
                <div style="display:flex;align-items:center;gap:10px">
                    <div style="width:36px;height:36px;border-radius:var(--radius-sm);background:var(--primary-pale);display:flex;align-items:center;justify-content:center;color:var(--primary);font-size:14px;flex-shrink:0">
                        <i class="fas fa-graduation-cap"></i>
                    </div>
                    <div>
                        <div style="font-weight:600;font-size:13.5px">${course.title}</div>
                        ${isAdmin ? `<div style="font-size:11px;color:var(--text-muted)">Tạo bởi: ${course.instructorUserName || '—'}</div>` : ''}
                    </div>
                </div>
            </td>
            <td>
                <span style="font-weight:600;color:var(--text-primary)">${formatPrice(course.price)}</span>
            </td>
            <td>
                ${course.discountPrice
                    ? `<span class="badge-admin badge-green">${formatPrice(course.discountPrice)}</span>`
                    : `<span style="color:var(--text-muted)">—</span>`
                }
            </td>
            <td>
                <span class="badge-admin badge-green">
                    <i class="fas fa-circle" style="font-size:5px;margin-right:4px;vertical-align:middle"></i>
                    Hoạt động
                </span>
            </td>
            <td>
                <div style="display:flex;gap:6px">
                    <button class="btn-admin btn-sm-admin btn-outline-admin btn-icon-admin"
                            onclick="editCourse(${course.courseID})" title="Chỉnh sửa">
                        <i class="fas fa-pen"></i>
                    </button>
                    <button class="btn-admin btn-sm-admin btn-danger-admin btn-icon-admin"
                            onclick="deleteCourse(${course.courseID})" title="Xóa">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function formatPrice(price) {
    if (price == null || price === 0) return 'Miễn phí';
    return Number(price).toLocaleString('vi-VN') + ' đ';
}

function filterCourses(query) {
    const q = query.toLowerCase();
    filteredCourses = allCourses.filter(c =>
        (c.title || '').toLowerCase().includes(q) ||
        (c.instructorUserName || '').toLowerCase().includes(q)
    );
    renderCourses(filteredCourses);
}

// ─── Add Course ──────────────────────────────────────────────────────────────
async function submitAddCourse(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    const data = {
        title:         formData.get('title'),
        description:   formData.get('description'),
        price:         formData.get('price') || 0,
        discountPrice: formData.get('discountPrice') || null,
    };
    try {
        const res = await fetch('/admin/api/course/add', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });
        if (res.ok) {
            AdminNotify.success('Thêm khóa học thành công!');
            AdminNotify.closeModal('modal-add-course');
            form.reset();
            await loadCourses();
        } else {
            const msg = await res.text();
            AdminNotify.error('Lỗi: ' + msg);
        }
    } catch (e) {
        AdminNotify.error('Lỗi kết nối: ' + e.message);
    }
}

// ─── Edit Course ─────────────────────────────────────────────────────────────
async function editCourse(courseId) {
    try {
        const res = await fetch(`/admin/api/course/showUpdate/${courseId}`);
        if (!res.ok) { AdminNotify.error('Không tìm thấy khóa học.'); return; }
        const course = await res.json();

        document.getElementById('edit-courseId').value       = course.courseID;
        document.getElementById('edit-courseTitle').value     = course.title || '';
        document.getElementById('edit-courseDesc').value      = course.description || '';
        document.getElementById('edit-coursePrice').value     = course.price || 0;
        document.getElementById('edit-courseDiscount').value  = course.discountPrice || 0;

        AdminNotify.openModal('modal-edit-course');
    } catch (e) {
        AdminNotify.error('Lỗi khi tải thông tin khóa học.');
        console.error(e);
    }
}

async function submitEditCourse() {
    const courseId = document.getElementById('edit-courseId').value;
    const dto = {
        title:         document.getElementById('edit-courseTitle').value,
        description:   document.getElementById('edit-courseDesc').value,
        price:         document.getElementById('edit-coursePrice').value,
        discountPrice: document.getElementById('edit-courseDiscount').value || null,
    };
    try {
        const res = await fetch(`/admin/api/course/update/${courseId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dto),
        });
        if (res.ok) {
            AdminNotify.success('Cập nhật khóa học thành công!');
            AdminNotify.closeModal('modal-edit-course');
            await loadCourses();
        } else {
            AdminNotify.error('Cập nhật thất bại: ' + await res.text());
        }
    } catch (e) {
        AdminNotify.error('Lỗi kết nối: ' + e.message);
    }
}

// ─── Delete Course ───────────────────────────────────────────────────────────
function deleteCourse(courseId) {
    AdminNotify.confirm(
        'Bạn có chắc muốn xóa khóa học này không?',
        async () => {
            try {
                const res = await fetch(`/admin/api/course/delete/${courseId}`, { method: 'DELETE' });
                if (res.ok) {
                    AdminNotify.success('Đã xóa khóa học thành công!');
                    await loadCourses();
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
document.addEventListener('DOMContentLoaded', loadCourses);