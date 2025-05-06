package com.codecampushubt.NCKH2024TQQD.dto.LessonDTO;
import com.codecampushubt.NCKH2024TQQD._enum.Admin.Lesson.type;

public class CreateLessonsDTO {
    private String courseName;
    private String title;
    private String description;
    private type type;
    private String content;
    private Integer duration;
    private String image;
    public CreateLessonsDTO(){}

    public CreateLessonsDTO(String courseName, String title, String description, com.codecampushubt.NCKH2024TQQD._enum.Admin.Lesson.type type, String content, Integer duration,String image) {
        this.courseName = courseName;
        this.title = title;
        this.description = description;
        this.type = type;
        this.content = content;
        this.duration = duration;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public com.codecampushubt.NCKH2024TQQD._enum.Admin.Lesson.type getType() {
        return type;
    }

    public void setType(com.codecampushubt.NCKH2024TQQD._enum.Admin.Lesson.type type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "CreateLessonsDTO{" +
                "courseName='" + courseName + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", duration=" + duration +
                ", image='" + image + '\'' +
                '}';
    }
}