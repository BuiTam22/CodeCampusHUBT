document.addEventListener("DOMContentLoaded", function () {
    const runButton = document.querySelector(".submit-button");
    const codeEditor = document.getElementById("editor");
    const languageSelector = document.querySelector(".language-selector");
    const testCase = document.querySelector(".test-cases");

    // Ẩn test cases ban đầu
    testCase.style.display = "none";

    // Thêm spinner vào HTML
    runButton.innerHTML = `
        <span class="button-text">Nộp</span>
        <span class="spinner-border spinner-border-sm d-none" role="status"></span>
    `;

    runButton.addEventListener("click", async () => {
        // Vô hiệu hóa nút và hiển thị spinner
        runButton.disabled = true;
        runButton.querySelector(".button-text").textContent = "Đang nộp bài...";
        runButton.querySelector(".spinner-border").classList.remove("d-none");

        const {sourceCode, language, exerciseID } = getCodeSubmission();

        try {
            const response = await fetch("/api/judge/submit", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    language: language,
                    sourceCode: sourceCode,
                    exerciseID: exerciseID
                })
            });

            if (response.status === 409) {
                const errData = await response.json();
                showNotification("warning", errData.message || "Bạn đã nộp bài này rồi!");
                return;
            }

            if (!response.ok) {
                throw new Error("Có lỗi xảy ra khi gửi code.");
            }

            const data = await response.json();
            console.log("Submit accepted:", data);

            // Hiện khu vực kết quả với trạng thái đang chờ
            testCase.style.display = "block";
            showPollingUI(data.submissionId);

            // Bắt đầu polling kết quả
            pollResult(data.submissionId);

        } catch (error) {
            console.error("Lỗi:", error);
            showNotification("error", "Không thể nộp code. Vui lòng thử lại.");
        } finally {
            runButton.disabled = false;
            runButton.querySelector(".button-text").textContent = "Nộp";
            runButton.querySelector(".spinner-border").classList.add("d-none");
        }
    });

    /**
     * Hiện UI đang chờ chấm (polling state)
     */
    function showPollingUI(submissionId) {
        const container = document.getElementById("testResultContainer");
        container.style.display = "block";
        container.className = "test-result polling-state";
        container.innerHTML = `
            <div class="polling-header">
                <div class="polling-spinner"></div>
                <h3>Đang chấm bài...</h3>
            </div>
            <p class="polling-sub">Mã bài nộp: <strong>#${submissionId}</strong></p>
            <div class="polling-progress">
                <div class="progress-bar-track">
                    <div class="progress-bar-fill" id="pollingProgressBar"></div>
                </div>
                <span class="polling-status" id="pollingStatusText">Đang xếp hàng chờ...</span>
            </div>
        `;
    }

    /**
     * Polling kết quả mỗi 1.5s, tối đa 60 lần (90s)
     */
    async function pollResult(submissionId) {
        const MAX_POLLS = 60;
        const POLL_INTERVAL = 1500;
        let pollCount = 0;

        const pollingInterval = setInterval(async () => {
            pollCount++;

            if (pollCount > MAX_POLLS) {
                clearInterval(pollingInterval);
                showTimeoutUI();
                return;
            }

            try {
                const response = await fetch(`/api/judge/result/${submissionId}`);
                if (!response.ok) {
                    console.warn("Poll error:", response.status);
                    return;
                }

                const result = await response.json();
                console.log(`Poll #${pollCount}:`, result);

                // Cập nhật progress bar
                updatePollingProgress(result.status, pollCount);

                // Nếu không còn pending → hiện kết quả
                if (result.status !== "pending") {
                    clearInterval(pollingInterval);
                    showTestResult(result);
                }
            } catch (err) {
                console.error("Poll error:", err);
            }
        }, POLL_INTERVAL);
    }

    /**
     * Cập nhật trạng thái UI trong khi polling
     */
    function updatePollingProgress(status, pollCount) {
        const progressBar = document.getElementById("pollingProgressBar");
        const statusText = document.getElementById("pollingStatusText");

        if (!progressBar || !statusText) return;

        // Tăng progress dần dần (max 90%)
        const progress = Math.min(90, pollCount * 8);
        progressBar.style.width = progress + "%";

        switch (status) {
            case "pending":
                statusText.textContent = "Đang xếp hàng chờ...";
                break;
            case "QUEUED":
                statusText.textContent = "Đang xếp hàng chờ...";
                break;
            case "PROCESSING":
                statusText.textContent = "Đang chấm bài...";
                progressBar.style.width = "60%";
                break;
            default:
                statusText.textContent = "Đang xử lý...";
        }
    }

    /**
     * Hiện kết quả cuối cùng với animation
     */
    function showTestResult(result) {
        const container = document.getElementById("testResultContainer");
        const isAccepted = result.status === "accepted";
        const isCompileError = result.status === "compilation_error";
        const statusClass = isAccepted ? "result-accepted" : "result-failed";
        const statusIcon = isAccepted ? "✅" : (isCompileError ? "⚠️" : "❌");
        const statusLabel = getStatusLabel(result.status);

        container.className = `test-result ${statusClass}`;
        container.innerHTML = `
            <div class="result-header">
                <span class="result-icon">${statusIcon}</span>
                <h3>${statusLabel}</h3>
            </div>
            <div class="result-body">
                <div class="result-stats">
                    <div class="stat-item">
                        <span class="stat-label">Điểm</span>
                        <span class="stat-value score-value">${result.score ?? 0}</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-label">Test cases</span>
                        <span class="stat-value">${result.testCasesPassed ?? 0}/${result.totalTestCases ?? 0}</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-label">Thời gian</span>
                        <span class="stat-value">${result.executionTime ?? 0}ms</span>
                    </div>
                </div>
                ${result.testCasesPassed != null && result.totalTestCases != null ? `
                <div class="test-progress-bar">
                    <div class="test-progress-fill ${isAccepted ? 'fill-accepted' : 'fill-failed'}" 
                         style="width: ${(result.testCasesPassed / Math.max(result.totalTestCases, 1)) * 100}%"></div>
                </div>` : ''}
            </div>
        `;

        // Trigger animation
        container.classList.add("result-animate");
    }

    /**
     * UI timeout — quá lâu không có kết quả
     */
    function showTimeoutUI() {
        const container = document.getElementById("testResultContainer");
        container.className = "test-result result-timeout";
        container.innerHTML = `
            <div class="result-header">
                <span class="result-icon">⏳</span>
                <h3>Quá thời gian chờ</h3>
            </div>
            <p>Hệ thống đang xử lý bài nộp. Vui lòng kiểm tra lại sau.</p>
        `;
    }

    /**
     * Notification popup
     */
    function showNotification(type, message) {
        // Xóa notification cũ
        const existing = document.querySelector(".judge-notification");
        if (existing) existing.remove();

        const notification = document.createElement("div");
        notification.className = `judge-notification notification-${type}`;
        notification.innerHTML = `
            <span class="notification-icon">${type === "warning" ? "⚠️" : "❌"}</span>
            <span class="notification-text">${message}</span>
        `;
        document.body.appendChild(notification);

        // Auto dismiss
        setTimeout(() => {
            notification.classList.add("notification-fadeout");
            setTimeout(() => notification.remove(), 400);
        }, 4000);
    }

    /**
     * Map status code → label tiếng Việt
     */
    function getStatusLabel(status) {
        const labels = {
            "accepted": "Chính xác",
            "wrong_answer": "Sai kết quả",
            "compilation_error": "Lỗi biên dịch",
            "runtime_error": "Lỗi runtime",
            "time_limit_exceeded": "Quá thời gian",
            "memory_limit_exceeded": "Quá bộ nhớ"
        };
        return labels[status] || status;
    }
});
