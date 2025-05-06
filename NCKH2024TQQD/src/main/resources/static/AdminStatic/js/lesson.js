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
                const [title, slug] = entry.split(" - ");
                const option = document.createElement('option');
                option.value = slug;
                option.textContent = title;
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

// end api them lesson
