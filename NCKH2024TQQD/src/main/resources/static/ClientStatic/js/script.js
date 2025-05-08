
document.addEventListener('DOMContentLoaded', function () {
    const userProfile = document.getElementById('userProfile');
    const dropdown = document.getElementById('userDropdown');

    userProfile.addEventListener('click', function (e) {
        e.stopPropagation(); // Ngăn không cho sự kiện lan ra ngoài
        dropdown.classList.toggle('hidden');
    });

    document.addEventListener('click', function () {
        dropdown.classList.add('hidden');
    });

});

document.addEventListener('DOMContentLoaded', function() {
    const currentUrl = window.location.pathname;

    const problemTab = document.querySelector('.problem-tab');
    const submissionsTab = document.querySelector('.submissions-tab');
    const leaderboardTab = document.querySelector('.leaderboard-tab');
    const tutorialTab = document.querySelector('.tutorial-tab');

    const tabs = [problemTab, submissionsTab, leaderboardTab, tutorialTab];

    tabs.forEach(tab => {
        if (tab) {
            tab.classList.remove('active');
        }
    });

    if (currentUrl.startsWith('/practice/lesson/problem/')) {
        if (problemTab) problemTab.classList.add('active');
    } else if (currentUrl.startsWith('/practice/lesson/submissions/')) {
        if (submissionsTab) submissionsTab.classList.add('active');
    } else if (currentUrl.startsWith('/practice/lesson/leaderboard/')) {
        if (leaderboardTab) leaderboardTab.classList.add('active');
    } else if (currentUrl.startsWith('/practice/lesson/tutorial/')) {
        if (tutorialTab) tutorialTab.classList.add('active');
    }

});

// logout button click: vì token được set httpOnly(true) nên client không thể tự xóa
// phải xóa token thông qua server (api)
document.querySelector('.logout-button').addEventListener('click', function (e) {
    e.preventDefault();

    try {
        fetch('/api/user/logout', {
            method: 'POST',
            credentials: 'include'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Logout failed with status ' + response.status);
            }
            window.location.href = '/';
        })
        .catch(error => {
            console.error('Error during logout:', error);
            alert('Đăng xuất thất bại. Vui lòng thử lại.');
        });
    } catch (error) {
        console.error('Unexpected error during logout setup:', error);
        alert('Đã xảy ra lỗi không xác định.');
    }
});

document.addEventListener('DOMContentLoaded', function () {
  const createContestButton = document.querySelector('#createContestButton');
  // Kiểm tra nếu nút "Create Contest" tồn tại
  if (createContestButton) {
    createContestButton.addEventListener('click', function() {
      // Chuyển hướng tới trang tạo cuộc thi
      window.location.href = '/management/contest/create';
    });
  } else {
    console.log('Create Contest button not found');
  }
});



