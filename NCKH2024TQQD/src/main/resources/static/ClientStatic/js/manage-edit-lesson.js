document.addEventListener("DOMContentLoaded", function () {
  const manageScore = document.getElementById("manage-score");
  if (manageScore) {
    manageScore.addEventListener("click", function () {
      const slug = this.getAttribute("data-slug");
      const type = this.getAttribute("data-lesson-type");
      window.location.href = `/management/contest/score/${type}/${slug}`;
    });
  }

  const editBtn = document.getElementById("get-started-btn");
  if (!editBtn || !editBtn.hasAttribute("data-lesson-id")) {
    return;
  }

  editBtn.addEventListener("click", function (event) {
    event.preventDefault();

    const lessonId = parseInt(editBtn.getAttribute("data-lesson-id"), 10);
    const title = document.getElementById("title").value;
    const descEl = document.getElementById("desc");
    const description = descEl ? descEl.value : "";
    const durationRaw = document.getElementById("duration").value;
    const contestType = document.getElementById("contest-type").value;

    const optionRadio = document.querySelector('input[name="option"]:checked');
    const selectedOption = optionRadio ? optionRadio.value : "";
    const startDate = document.getElementById("start-date").value;
    const startTime = document.getElementById("start-time").value;
    const endDate = document.getElementById("end-date").value;
    const endTime = document.getElementById("end-time").value;

    const startDateTime = `${startDate}T${startTime}:00`;
    const endDateTime = `${endDate}T${endTime}:00`;

    const data = {
      lessonId: lessonId,
      title: title,
      description: description,
      duration: parseInt(durationRaw, 10),
      type: contestType,
      isContest: selectedOption === "Contest",
      contestStartTime: startDateTime,
      contestEndTime: endDateTime,
    };

    fetch("/api/lesson/contest/update", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((res) => {
        if (!res.ok) {
          return res.text().then((t) => {
            throw new Error(t || res.statusText);
          });
        }
        return res.json();
      })
      .then((res) => {
        if (res.status === "success") {
          alert("Cập nhật thành công");
        } else {
          alert("Cập nhật thất bại");
        }
      })
      .catch((err) => {
        console.error("Error updating lesson:", err);
        alert("Có lỗi xảy ra khi cập nhật");
      });
  });
});
