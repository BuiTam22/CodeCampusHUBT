document.addEventListener("DOMContentLoaded", function(){
    const lessonListDiv = document.getElementById("CourseLessonList")
    // console.log(lessonListDiv)
    fetch('/admin/api/lesson/show',{
        method :'GET',
        headers:{
            'Content-Type' : 'application/json'
        }

    })
        .then(res => res.json())
        .then(data => {
            // console.log(data)
            if (data.length === 0 ){
                lessonListDiv.innerHTML="<p>Không có câu hỏi nào !</p>"

            }
            const isAdmin = data.some(lesson => lesson.rolename === "ADMIN")
            // console.log(isAdmin)
            let html = `
            <table>
                <thead>
                    <tr>
        `;

            if (isAdmin) {
                html += `<th>STT</th>`;
            }

            html += `
                        <th>Tên</th>
                        <th>Mô Tả</th>
                        <th>Thuộc Tính</th>
                        <th>Thời Gian</th>
        `;

            if (isAdmin) {
                html += `<th>Người Tạo</th>`;
            }

            html += `
                    </tr>
                </thead>
                <tbody>
        `;

            data.forEach(lesson => {
                html += `<tr>`;

                if (isAdmin) {
                    html += `<td>${lesson.lessonId}</td>`;
                }

                html += `
                <td>${lesson.title}</td>
                <td>${lesson.description}</td>
                <td>${lesson.type}</td>
                <td>${lesson.duration}</td>
            `;

                if (isAdmin) {
                    html += `<td>${lesson.userName}</td>`;
                }

                html += `</tr>`;
            });

            html += `
                </tbody>
            </table>
        `;

            lessonListDiv.innerHTML = html;

        })
        .catch(err=> {
            console.log("Lỗi Khi gọi API ",err)
            lessonListDiv.innerHTML="<p>Đã Xảy Ra Lỗi !</p>"
        })
});


//ẩn hiện form
function showAddlessonFormOnly(){
    var addForm = document.getElementById("lesson_add")
    var showform = document.getElementById("CourseLessonList")

    addForm.style.display="block"
    showform.style.display="none"
}

function cancelAddLesson() {
    document.getElementById("lesson_add").style.display = "none";
    document.getElementById("CourseLessonList").style.display = "block";
}
//end ẩn hiện form

//call api khoas hoc
fetch('http://localhost:3000/admin/api/lesson/add')
    .then(response =>{
        if(! response.ok){
            throw new Error("Failed to fetch course list")
        }
        return response.json()
    })
    .then(data =>{
        const select = document.getElementById("Course")
        const label = document.getElementById('courseLabel')
        console.log(data)

        if (data && data.length > 0 ){
            label.style.display ="block"
            select.innerHTML = '<option disabled selected>-- Select a Course --</option>'
            data.forEach(function(entry) {
                const option = document.createElement('option');
                option.value = entry.slug;
                option.textContent = entry.title;
                select.appendChild(option);
            });

        }else {
            label.style.display = 'none'
        }
    })
    .catch(err =>{
        console.log("Lỗi gọi API" , err)
    })
//end api khoa hojc

// api thêm lesson

document.getElementById('submitlessonadd').addEventListener("Submit",function (e){
    e.preventDefault();

    const form = document.getElementById("lesson-form-add");
    const formData = new FormData(form);

    // Lấy dữ liệu từ các trường
    const data = {
        title: formData.get("title"),
        courseSlug: document.getElementById("Course").value,
        description: formData.get("description"),
        type: form.querySelector('select option:checked').textContent.toLowerCase(),
        duration: document.getElementById("duration").value
    };
    fetch("/admin/api/lesson/add",{
        method:"POST",
        headers : {
            "Content-Type" : "application"
         },
        body:JSON.stringify(data)
    })
        .then(res => {
            if(!res.ok) throw new Error("Gửi Dữ Liệu Thất Bại ")
            return res.json()
        })
        .then(result =>{
            alert("Thêm Bài Học Thành Công ")
            location.reload()
        })
        .then(err => {
            console.log("Lỗi " + err)
            alert("Đã Xảy ra lỗi Khi thêm Bài Học ")
        })
})

// end api them lesson
