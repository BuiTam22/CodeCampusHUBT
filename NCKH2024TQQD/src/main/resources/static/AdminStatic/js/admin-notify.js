/**
 * admin-notify.js
 * Global toast & confirm-modal utility for CODEHUBT Admin.
 * Usage:
 *   AdminNotify.success('Thêm thành công!');
 *   AdminNotify.error('Có lỗi xảy ra.');
 *   AdminNotify.warn('Kiểm tra lại dữ liệu.');
 *   AdminNotify.info('Đang xử lý...');
 *   AdminNotify.confirm('Bạn chắc chắn muốn xóa?', () => { doDelete(); });
 */

const AdminNotify = (() => {
    // Inject container once
    function getContainer() {
        let c = document.getElementById('toast-container');
        if (!c) {
            c = document.createElement('div');
            c.id = 'toast-container';
            document.body.appendChild(c);
        }
        return c;
    }

    const ICONS = {
        success: '<i class="fas fa-check-circle" style="color:#16a34a"></i>',
        error:   '<i class="fas fa-times-circle" style="color:#dc2626"></i>',
        warn:    '<i class="fas fa-exclamation-triangle" style="color:#f59e0b"></i>',
        info:    '<i class="fas fa-info-circle" style="color:#2563eb"></i>',
    };

    function show(message, type = 'success', duration = 3500) {
        const container = getContainer();
        const toast = document.createElement('div');
        toast.className = `toast-item toast-${type}`;
        toast.innerHTML = `
            <span class="toast-icon">${ICONS[type] || ICONS.info}</span>
            <span class="toast-msg">${message}</span>
            <button class="toast-close" onclick="this.closest('.toast-item').remove()">×</button>
        `;
        container.appendChild(toast);
        setTimeout(() => {
            toast.classList.add('toast-out');
            toast.addEventListener('animationend', () => toast.remove());
        }, duration);
    }

    // Confirm modal (replaces window.confirm)
    function confirm(message, onConfirm, onCancel) {
        // Remove existing
        const old = document.getElementById('_admin-confirm-overlay');
        if (old) old.remove();

        const overlay = document.createElement('div');
        overlay.id = '_admin-confirm-overlay';
        overlay.className = 'modal-overlay open';
        overlay.innerHTML = `
            <div class="modal-box" style="max-width:380px">
                <div class="modal-header">
                    <span class="modal-title">Xác nhận</span>
                </div>
                <div class="modal-body" style="padding:20px 24px 8px">
                    <p style="font-size:14px;color:var(--text-secondary);margin:0">${message}</p>
                </div>
                <div class="modal-footer">
                    <button id="_confirm-cancel" class="btn-admin btn-outline-admin">Hủy</button>
                    <button id="_confirm-ok" class="btn-admin btn-danger-admin">Xác nhận</button>
                </div>
            </div>
        `;
        document.body.appendChild(overlay);

        document.getElementById('_confirm-ok').onclick = () => {
            overlay.remove();
            if (onConfirm) onConfirm();
        };
        document.getElementById('_confirm-cancel').onclick = () => {
            overlay.remove();
            if (onCancel) onCancel();
        };
        overlay.addEventListener('click', (e) => {
            if (e.target === overlay) { overlay.remove(); if (onCancel) onCancel(); }
        });
    }

    // Open / close generic modal helper
    function openModal(id) {
        const el = document.getElementById(id);
        if (el) el.classList.add('open');
    }
    function closeModal(id) {
        const el = document.getElementById(id);
        if (el) el.classList.remove('open');
    }
    // Close on overlay click
    document.addEventListener('click', (e) => {
        if (e.target.classList.contains('modal-overlay')) {
            e.target.classList.remove('open');
        }
    });

    return {
        success: (msg, dur) => show(msg, 'success', dur),
        error:   (msg, dur) => show(msg, 'error',   dur),
        warn:    (msg, dur) => show(msg, 'warn',     dur),
        info:    (msg, dur) => show(msg, 'info',     dur),
        confirm,
        openModal,
        closeModal,
    };
})();
