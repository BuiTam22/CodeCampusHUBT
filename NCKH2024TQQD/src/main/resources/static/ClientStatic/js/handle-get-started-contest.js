const button = document.getElementById("get-started-btn");

    button.addEventListener("click", function (event) {
      event.preventDefault(); // Ngăn form submit mặc định nếu cần

      const title = document.getElementById("title").value;
      const duration = document.getElementById("duration").value;
      const contestType = document.getElementById("contest-type").value;

      const optionRadio = document.querySelector('input[name="option"]:checked');
      const selectedOption = optionRadio ? optionRadio.value : '';
      const startDate = document.getElementById("start-date").value;
      const startTime = document.getElementById("start-time").value;
      const endDate = document.getElementById("end-date").value;
      const endTime = document.getElementById("end-time").value;

      // Ví dụ in ra console
      console.log("Title:", title);
      console.log("Duration:", duration);
      console.log("Type:", contestType);
      console.log("Selected Option:", selectedOption);
      console.log("Start Date:", startDate);
      console.log("Start Time:", startTime);
      console.log("End Date:", endDate);
      console.log("End Time:", endTime);

      // TODO: Gửi dữ liệu qua API hoặc xử lý tiếp
    });