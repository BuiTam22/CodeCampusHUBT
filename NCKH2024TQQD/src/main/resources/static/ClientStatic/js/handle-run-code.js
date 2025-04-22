document.addEventListener("DOMContentLoaded", function () {
    const runButton = document.querySelector(".run-button");
    const codeEditor = document.getElementById("editor");
    const languageSelector = document.querySelector(".language-selector");

    const testCase = document.querySelector(".test-cases");

    // Ẩn test cases ban đầu
    testCase.style.display = "none";

    runButton.addEventListener("click", async () => {
        const sourceCode = codeEditor.value;
        const language = languageSelector.value;
        const exerciseID = parseInt(codeEditor.dataset.exerciseId);

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

            // Hiện test cases sau khi chạy xong
            testCase.style.display = "block";

            console.log("Kết quả chạy thử:", result);
            alert("Kết quả: " + (result.output || result.message));

        } catch (error) {
            console.error("Lỗi:", error);
            alert("Không thể chạy code. Vui lòng thử lại.");
        }
    });
});
