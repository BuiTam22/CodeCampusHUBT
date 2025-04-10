const editor = document.getElementById('editor');
const lineNumbers = document.getElementById('lineNumbers');

function updateLineNumbers() {
    const lines = editor.value.split('\n');
    const numbers = Array.from({
        length: lines.length
    }, (_, i) => i + 1).join('<br>');
    lineNumbers.innerHTML = numbers;

    // Đồng bộ hóa chiều cao
    lineNumbers.style.height = editor.scrollHeight + 'px';
    editor.style.height = editor.scrollHeight + 'px';
}

function syncScroll() {
    lineNumbers.scrollTop = editor.scrollTop;
    lineNumbers.scrollLeft = editor.scrollLeft;
}

// Xử lý các sự kiện
editor.addEventListener('input', () => {
    updateLineNumbers();
    syncScroll();
});

editor.addEventListener('scroll', syncScroll);
editor.addEventListener('paste', updateLineNumbers);

// Khởi tạo ban đầu
updateLineNumbers();

// Đồng bộ kích thước ban đầu
lineNumbers.style.height = '500px';
editor.style.height = '500px';


// bat su kien bam nut problem
document.addEventListener('DOMContentLoaded', function() {
    // Xử lý active tab
    const currentPath = window.location.pathname;
    const tabs = document.querySelectorAll('.nav-tab');

    tabs.forEach(tab => {
        if (tab.getAttribute('href') === currentPath) {
            tab.classList.add('active');
        }

        tab.addEventListener('click', function(e) {
            // Xóa active khỏi tất cả các tab
            tabs.forEach(t => t.classList.remove('active'));

            // Thêm active cho tab được click
            this.classList.add('active');

            // Chuyển hướng trang
            window.location.href = this.href;
        });
    });
});