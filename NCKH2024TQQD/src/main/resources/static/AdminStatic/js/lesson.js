/**
 * lesson.js — CODEHUBT Admin Lesson Management
 * Refactored: renders table using design system classes + AdminNotify
 */

let allLessons = [];
let filteredLessonsArr = [];

// ─── Load & render lessons ──────────────────────────────────────────────────
async function loadLessons() {
    try {
        const res = await fetch('/admin/api/lesson/show');
        allLessons = await res.json();
        filteredLessonsArr = [...allLessons];
        renderLessons(filteredLessonsArr);
    } catch (e) {
        AdminNotify.error('Không thể tải danh sách bài học.');
        console.error(e);
    }
}

function renderLessons(list) {
    const tbody = document.getElementById('lessonTableBody');
    if (!list || list.length === 0) {
        tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;padding:32px;color:var(--text-muted)">
            <i class="fas fa-book" style="font-size:20px;display:block;margin-bottom:8px;opacity:0.4"></i>
            Không có bài học nào.
        </td></tr>`;
        return;
    }

    const typeIcons = {
        video: 'fa-play-circle',
        text: 'fa-file-alt',
        quiz: 'fa-question-circle',
        assignment: 'fa-tasks',
        coding: 'fa-code',
    };
    const typeColors = {
        video: 'badge-blue',
        text: 'badge-gray',
        quiz: 'badge-orange',
        assignment: 'badge-purple',
        coding: 'badge-green',
    };

    tbody.innerHTML = list.map((lesson, i) => `
        <tr>
            <td style="color:var(--text-muted)">${i + 1}</td>
            <td>
                <div style="display:flex;align-items:center;gap:10px">
                    <div style="width:36px;height:36px;border-radius:var(--radius-sm);background:var(--primary-pale);display:flex;align-items:center;justify-content:center;color:var(--primary);font-size:14px;flex-shrink:0">
                        <i class="fas ${typeIcons[lesson.type] || 'fa-book-open'}"></i>
                    </div>
                    <div>
                        <div style="font-weight:600;font-size:13.5px">${lesson.title}</div>
                        <div style="font-size:11px;color:var(--text-muted);max-width:250px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis">
                            ${lesson.description || '—'}
                        </div>
                    </div>
                </div>
            </td>
            <td style="color:var(--text-secondary);font-size:13px">${lesson.userName || '—'}</td>
            <td>
                <span class="badge-admin ${typeColors[lesson.type] || 'badge-gray'}">
                    <i class="fas ${typeIcons[lesson.type] || 'fa-tag'}" style="font-size:9px;margin-right:4px"></i>
                    ${(lesson.type || '—').charAt(0).toUpperCase() + (lesson.type || '').slice(1)}
                </span>
            </td>
            <td>
                ${lesson.duration
                    ? `<span style="font-size:13px;color:var(--text-secondary)"><i class="fas fa-clock" style="margin-right:4px;opacity:0.5"></i>${lesson.duration} phút</span>`
                    : `<span style="color:var(--text-muted)">—</span>`
                }
            </td>
            <td>
                <div style="display:flex;gap:6px">
                    <button class="btn-admin btn-sm-admin btn-outline-admin btn-icon-admin"
                            onclick="editLesson(${lesson.lessonId})" title="Chỉnh sửa">
                        <i class="fas fa-pen"></i>
                    </button>
                    <button class="btn-admin btn-sm-admin btn-danger-admin btn-icon-admin"
                            onclick="deleteLesson(${lesson.lessonId})" title="Xóa">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function filterLessons(query) {
    const q = query.toLowerCase();
    filteredLessonsArr = allLessons.filter(l =>
        (l.title || '').toLowerCase().includes(q) ||
        (l.description || '').toLowerCase().includes(q) ||
        (l.type || '').toLowerCase().includes(q) ||
        (l.userName || '').toLowerCase().includes(q)
    );
    renderLessons(filteredLessonsArr);
}

// ─── Load course dropdown for Add form ──────────────────────────────────────
async function loadCourseDropdown() {
    try {
        const res = await fetch('/admin/api/lesson/add');
        if (!res.ok) return;
        const data = await res.json();
        const select = document.getElementById('Course');
        if (!select) return;

        select.innerHTML = '<option value="" disabled selected>— Chọn khóa học —</option>';
        data.forEach(entry => {
            const opt = document.createElement('option');
            opt.value = entry.slug;
            opt.textContent = entry.title;
            select.appendChild(opt);
        });
    } catch (e) {
        console.warn('Không thể tải dropdown khóa học', e);
    }
}

// ─── Upload image helper ────────────────────────────────────────────────────
async function uploadImage(file) {
    const formData = new FormData();
    formData.append('file', file);
    const res = await fetch('/admin/api/upload', { method: 'POST', body: formData });
    const data = await res.json();
    return data.url;
}

// ─── Submit add lesson ──────────────────────────────────────────────────────
async function submitAddLesson(event) {
    event.preventDefault();
    const form = event.target;
    const formdata = new FormData(form);

    const file = document.getElementById('lesson-image')?.files[0];
    let imgUrl = null;
    if (file) {
        try {
            imgUrl = await uploadImage(file);
        } catch (e) {
            AdminNotify.error('Lỗi khi tải ảnh: ' + e.message);
            return;
        }
    }

    const data = {
        courseName:  formdata.get('course'),
        title:       formdata.get('title'),
        description: formdata.get('description'),
        type:        formdata.get('type'),
        content:     formdata.get('content'),
        duration:    formdata.get('duration'),
        image:       imgUrl,
        orderIndex:  formdata.get('OrderIndex'),
    };

    try {
        const res = await fetch('/admin/api/lesson/add', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data),
        });
        if (res.ok) {
            AdminNotify.success('Thêm bài học thành công!');
            AdminNotify.closeModal('modal-add-lesson');
            form.reset();
            await loadLessons();
        } else {
            AdminNotify.error('Thêm bài học thất bại!');
        }
    } catch (e) {
        AdminNotify.error('Lỗi kết nối: ' + e.message);
    }
}

// ─── Edit Lesson ─────────────────────────────────────────────────────────────
async function editLesson(lessonId) {
    try {
        const res = await fetch(`/admin/api/lesson/showUpdate/${lessonId}`);
        if (!res.ok) { AdminNotify.error('Không tìm thấy bài học.'); return; }
        const lesson = await res.json();

        document.getElementById('edit-lessonId').value     = lesson.lessonId;
        document.getElementById('edit-lessonTitle').value   = lesson.title || '';
        document.getElementById('edit-lessonDesc').value    = lesson.description || '';
        document.getElementById('edit-lessonType').value    = lesson.type || 'video';
        document.getElementById('edit-lessonDuration').value = lesson.duration || '';
        document.getElementById('edit-lessonContent').value = lesson.content || '';

        AdminNotify.openModal('modal-edit-lesson');
    } catch (e) {
        AdminNotify.error('Lỗi khi tải thông tin bài học.');
        console.error(e);
    }
}

async function submitEditLesson() {
    const lessonId = document.getElementById('edit-lessonId').value;
    const dto = {
        title:       document.getElementById('edit-lessonTitle').value,
        description: document.getElementById('edit-lessonDesc').value,
        type:        document.getElementById('edit-lessonType').value,
        duration:    document.getElementById('edit-lessonDuration').value || null,
        content:     document.getElementById('edit-lessonContent').value,
    };
    try {
        const res = await fetch(`/admin/api/lesson/update/${lessonId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dto),
        });
        if (res.ok) {
            AdminNotify.success('Cập nhật bài học thành công!');
            AdminNotify.closeModal('modal-edit-lesson');
            await loadLessons();
        } else {
            AdminNotify.error('Cập nhật thất bại: ' + await res.text());
        }
    } catch (e) {
        AdminNotify.error('Lỗi kết nối: ' + e.message);
    }
}

// ─── Delete Lesson ───────────────────────────────────────────────────────────
function deleteLesson(lessonId) {
    AdminNotify.confirm(
        'Bạn có chắc muốn xóa bài học này không?',
        async () => {
            try {
                const res = await fetch(`/admin/api/lesson/delete/${lessonId}`, { method: 'DELETE' });
                if (res.ok) {
                    AdminNotify.success('Đã xóa bài học thành công!');
                    await loadLessons();
                } else {
                    AdminNotify.error('Xóa thất bại!');
                }
            } catch (e) {
                AdminNotify.error('Lỗi kết nối: ' + e.message);
            }
        }
    );
}

// ─── Init ───────────────────────────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => {
    loadLessons();
    loadCourseDropdown();
});
