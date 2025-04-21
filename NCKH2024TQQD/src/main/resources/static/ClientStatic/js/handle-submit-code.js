document.addEventListener("DOMContentLoaded", function () {
    const runButton = document.querySelector(".submit-button");
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

            if (!response.ok) {
                throw new Error("Có lỗi xảy ra khi gửi code.");
            }

            const result = await response.json();

            // Hiện test cases sau khi chạy xong
            testCase.style.display = "block";

            console.log("Kết quả", result);
            showTestResult(result);



        } catch (error) {
            console.error("Lỗi:", error);
            alert("Không thể chạy code. Vui lòng thử lại.");
        }
    });
    function showTestResult(result) {
        document.getElementById("testResultContainer").style.display = "block";
        document.getElementById("resultLanguage").innerText = result.language;
        document.getElementById("resultStatus").innerText = result.status;
        document.getElementById("resultPassed").innerText = result.testCasesPassed;
        document.getElementById("resultTotal").innerText = result.totalTestCases;
        document.getElementById("resultScore").innerText = result.score;

//        const detailDiv = document.getElementById("testCaseDetails");
//        detailDiv.innerHTML = "";
//
//        result.testCaseResults.forEach(tc => {
//            const tcDiv = document.createElement("div");
//            tcDiv.classList.add("test-case");
//            tcDiv.classList.add(tc.passed ? "passed" : "failed");
//
//            tcDiv.innerHTML = `
//                <div class="test-case-header">Test Case ${tc.index}</div>
//                <pre><strong>Input:</strong> ${tc.input}</pre>
//                <pre><strong>Expected:</strong> ${tc.expectedOutput}</pre>
//                <pre><strong>Output:</strong> ${tc.actualOutput}</pre>
//                <div class="test-case-status">${tc.passed ? "✓ Qua" : "✗ Sai"}</div>
//            `;
//            detailDiv.appendChild(tcDiv);
//        });
    }
});
