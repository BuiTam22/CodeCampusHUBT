document.addEventListener("DOMContentLoaded", function() {
        const tabs = document.querySelectorAll(".nav-tab");
        const currentUrl = window.location.href;

        tabs.forEach(tab => {
            if (tab.href && currentUrl.includes(tab.getAttribute('href'))) {
                tabs.forEach(t => t.classList.remove('active')); // Bỏ active cũ
                tab.classList.add('active'); // Active tab mới
            }
        });
    });