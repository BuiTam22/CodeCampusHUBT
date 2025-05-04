package com.codecampushubt.NCKH2024TQQD.dto.CourseDTO;

import java.math.BigDecimal;

public class CreateCourseDTO {
    private String title ;
    private String description ;
    private String shortDescription ;
    private String level;
    private Integer duration;
    private Integer capacity;
    private BigDecimal price;
    private String imageUrl;

    public CreateCourseDTO() {}

    public CreateCourseDTO(String title, String description, String shortDescription, String level, Integer duration, Integer capacity, BigDecimal price, String imageUrl) {
        this.title = title;
        this.description = description;
        this.shortDescription = shortDescription;
        this.level = level;
        this.duration = duration;
        this.capacity = capacity;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "CreateCourseDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", level='" + level + '\'' +
                ", duration=" + duration +
                ", capacity=" + capacity +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
