
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


