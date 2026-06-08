document.addEventListener("DOMContentLoaded", function () {
    const submitButton = document.querySelector(".submit-button");
    const essayEditor = document.querySelector(".essay-editor");
    const exerciseID = essayEditor.getAttribute("data-exercise-id");

    // Thêm spinner vào button
    submitButton.innerHTML = `
        <span class="button-text">Nộp Bài</span>
        <span class="spinner-border spinner-border-sm d-none" role="status"></span>
    `;

    // Container kết quả (đã có trong HTML)
    const resultContainer = document.getElementById("essayResultContainer");

    submitButton.addEventListener("click", async function () {
        const userAnswer = essayEditor.innerText.trim();

        if (!userAnswer) {
            showNotification("warning", "Vui lòng nhập nội dung bài làm.");
            return;
        }

        // === Disable button + show spinner ===
        submitButton.disabled = true;
        submitButton.querySelector(".button-text").textContent = "Đang chấm bài...";
        submitButton.querySelector(".spinner-border").classList.remove("d-none");

        // === Show polling UI ===
        resultContainer.style.display = "block";
        resultContainer.className = "test-result polling-state result-animate";
        resultContainer.innerHTML = `
            <div class="polling-header">
                <div class="polling-spinner"></div>
                <h3>Đang chấm bài tự luận...</h3>
            </div>
            <p class="polling-sub">AI đang phân tích và chấm điểm bài làm của bạn</p>
            <div class="polling-progress">
                <div class="progress-bar-track">
                    <div class="progress-bar-fill" id="essayProgressBar" style="width: 0%"></div>
                </div>
                <span class="polling-status" id="essayStatusText">Đang gửi bài lên hệ thống...</span>
            </div>
        `;

        // Animate progress bar (giả lập vì Gemini call đồng bộ)
        const progressBar = document.getElementById("essayProgressBar");
        const statusText = document.getElementById("essayStatusText");
        let progress = 0;
        const progressInterval = setInterval(() => {
            if (progress < 30) {
                progress += 5;
                statusText.textContent = "Đang gửi bài lên hệ thống...";
            } else if (progress < 60) {
                progress += 3;
                statusText.textContent = "AI đang đọc bài làm của bạn...";
            } else if (progress < 85) {
                progress += 2;
                statusText.textContent = "AI đang phân tích và chấm điểm...";
            } else if (progress < 95) {
                progress += 1;
                statusText.textContent = "Đang hoàn tất...";
            }
            progressBar.style.width = progress + "%";
        }, 500);

        try {
            const response = await fetch("/api/judge/essay/submit", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    exerciseID: exerciseID,
                    content: userAnswer
                })
            });

            clearInterval(progressInterval);
            progressBar.style.width = "100%";

            if (response.status === 409) {
                const errData = await response.json();
                showEssayResult("warning", errData.message || "Bạn đã nộp bài này rồi!", null);
                return;
            }

            if (!response.ok) {
                throw new Error("Có lỗi xảy ra khi chấm bài.");
            }

            const data = await response.json();
            showEssayResult("success", null, data);

        } catch (error) {
            clearInterval(progressInterval);
            if (error.message !== "already_submitted") {
                console.error("Lỗi:", error);
                showEssayResult("error", error.message || "Đã có lỗi xảy ra khi nộp bài.", null);
            }
        } finally {
            // Reset button
            submitButton.disabled = false;
            submitButton.querySelector(".button-text").textContent = "Nộp Bài";
            submitButton.querySelector(".spinner-border").classList.add("d-none");
        }
    });

    // ===== Hiển thị kết quả chấm essay =====
    function showEssayResult(type, message, data) {
        if (type === "warning") {
            resultContainer.className = "test-result result-timeout result-animate";
            resultContainer.innerHTML = `
                <div class="result-header">
                    <span class="result-icon">⚠️</span>
                    <h3>Đã nộp bài trước đó</h3>
                </div>
                <p style="color: #856404;">${message}</p>
            `;
            return;
        }

        if (type === "error") {
            resultContainer.className = "test-result result-failed result-animate";
            resultContainer.innerHTML = `
                <div class="result-header">
                    <span class="result-icon">❌</span>
                    <h3>Lỗi chấm bài</h3>
                </div>
                <p style="color: #dc3545;">${message}</p>
            `;
            return;
        }

        // Success — hiển thị điểm + feedback
        const score = data.score != null ? parseFloat(data.score).toFixed(1) : "N/A";
        const feedback = data.feedback || "Không có nhận xét.";
        const scoreNum = parseFloat(score);
        const isGood = scoreNum >= 7;

        resultContainer.className = `test-result ${isGood ? "result-accepted" : "result-failed"} result-animate`;
        resultContainer.innerHTML = `
            <div class="result-header">
                <span class="result-icon">${isGood ? "✅" : "📝"}</span>
                <h3>${isGood ? "Bài làm tốt!" : "Cần cải thiện"}</h3>
            </div>
            <div class="result-stats">
                <div class="stat-item">
                    <span class="stat-label">Điểm</span>
                    <span class="stat-value score-value" style="color: ${isGood ? '#28a745' : '#dc3545'}">${score}/10</span>
                </div>
                <div class="stat-item">
                    <span class="stat-label">Đánh giá</span>
                    <span class="stat-value" style="font-size: 1rem">${scoreNum >= 8 ? "Xuất sắc" : scoreNum >= 7 ? "Khá" : scoreNum >= 5 ? "Trung bình" : "Yếu"}</span>
                </div>
            </div>
            <div class="essay-feedback" style="background: #f8f9fa; border-radius: 8px; padding: 16px; margin-top: 12px;">
                <strong style="display: block; margin-bottom: 8px; color: #333;">💬 Nhận xét của AI:</strong>
                <p style="color: #555; line-height: 1.6; margin: 0; white-space: pre-wrap;">${feedback}</p>
            </div>
        `;
    }

    // ===== Toast notification =====
    function showNotification(type, message) {
        const existing = document.querySelector(".judge-notification");
        if (existing) existing.remove();

        const iconMap = { warning: "⚠️", error: "❌", success: "✅" };
        const notification = document.createElement("div");
        notification.className = `judge-notification notification-${type}`;
        notification.innerHTML = `
            <span class="notification-icon">${iconMap[type] || "ℹ️"}</span>
            <span class="notification-text">${message}</span>
        `;
        document.body.appendChild(notification);

        setTimeout(() => {
            notification.classList.add("notification-fadeout");
            setTimeout(() => notification.remove(), 400);
        }, 4000);
    }
});
