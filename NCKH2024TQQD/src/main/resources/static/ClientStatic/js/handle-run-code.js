document.addEventListener("DOMContentLoaded", function () {
    const runButton = document.querySelector(".run-button");
    const codeEditor = document.getElementById("editor");
    const languageSelector = document.querySelector(".language-selector");

    runButton.addEventListener("click", async () => {
        const sourceCode = codeEditor.value;
        const language = languageSelector.value;

        // TODO: Lấy exerciseID từ data-* attribute hoặc biến Thymeleaf
        //const exerciseID = /*[[${exercise.id}]]*/ 0; // Nếu dùng Thymeleaf, truyền như sau:
        const exerciseID = document.getElementById('editor').dataset.exerciseId;

        try {
            const response = await fetch("/api/judge/run", {
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

            if (!response.ok) {
                throw new Error("Có lỗi xảy ra khi gửi code.");
            }

            const result = await response.json();

            // TODO: Hiển thị kết quả ở đâu đó trên trang, ví dụ console
            console.log("Kết quả chạy thử:", result);
            alert("Kết quả: " + result.output || result.message);

        } catch (error) {
            console.error("Lỗi:", error);
            alert("Không thể chạy code. Vui lòng thử lại.");
        }
    });
});
