document.addEventListener("DOMContentLoaded", function(){
    const lessonListDiv = document.getElementById("CourseLessonList")
    console.log(lessonListDiv)
    fetch('/admin/api/lesson/show',{
        method :'GET',
        headers:{
            'Content-Type' : 'application/json'
        }

    })
        .then(res => res.json())
        .then(data => {
            console.log(data)
            if (data.length === 0 ){
                lessonListDiv.innerHTML="<p>Không có câu hỏi nào !</p>"

            }
            const isAdmin = data.some(lesson => lesson.rolename === "ADMIN")
            console.log(isAdmin)
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