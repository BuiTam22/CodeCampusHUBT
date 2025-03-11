package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="Calls")
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Thêm trường id làm khóa chính

    @Entity
    @Table(name = "EventTechnologies")
    public static class EventTechnologie {

        @Id
        @Column(name = "EventID", nullable = false)
        private Long eventID;

        @Id
        @Column(name = "Technology", length = 100, nullable = false)
        private String technology;

        // Constructor mặc định
        public EventTechnologie() {
        }

        // Getters và Setters

        public Long getEventID() {
            return eventID;
        }

        public void setEventID(Long eventID) {
            this.eventID = eventID;
        }

        public String getTechnology() {
            return technology;
        }

        public void setTechnology(String technology) {
            this.technology = technology;
        }

        // toString method để in thông tin
        @Override
        public String toString() {
            return "EventTechnologies{" +
                    "eventID=" + eventID +
                    ", technology='" + technology + '\'' +
                    '}';
        }

        @Entity
        @Table(name = "Events")
        public static class Event {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "EventID", nullable = false, updatable = false)
            private Long eventID;

            @Column(name = "Title", nullable = false, length = 255)
            private String title;

            @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
            private String description;

            @Column(name = "Category", length = 50)
            private String category;

            @Column(name = "EventDate")
            private LocalDate eventDate;

            @Column(name = "EventTime", nullable = false)
            private LocalTime eventTime;

            @Column(name = "Location", length = 255)
            private String location;

            @Column(name = "ImageUrl", length = 500)
            private String imageUrl;

            @Column(name = "MaxAttendees")
            private Integer maxAttendees;

            @Column(name = "CurrentAttendees")
            private Integer currentAttendees;

            @Column(name = "Price", precision = 10, scale = 2)
            private BigDecimal price;

            @Column(name = "Organizer", length = 255)
            private String organizer;

            @Column(name = "Difficulty", length = 20)
            private String difficulty;

            @Column(name = "Status", length = 20)
            private String status;

            @Column(name = "CreatedBy")
            private Long createdBy;

            @Column(name = "CreatedAt")
            private LocalDateTime createdAt;

            @Column(name = "UpdatedAt")
            private LocalDateTime updatedAt;

            @Column(name = "DeletedAt")
            private LocalDateTime deletedAt;

            // Constructor mặc định
            public Event() {
            }

            // Getter và Setter

            public Long getEventID() {
                return eventID;
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

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public LocalDate getEventDate() {
                return eventDate;
            }

            public void setEventDate(LocalDate eventDate) {
                this.eventDate = eventDate;
            }

            public LocalTime getEventTime() {
                return eventTime;
            }

            public void setEventTime(LocalTime eventTime) {
                this.eventTime = eventTime;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public Integer getMaxAttendees() {
                return maxAttendees;
            }

            public void setMaxAttendees(Integer maxAttendees) {
                this.maxAttendees = maxAttendees;
            }

            public Integer getCurrentAttendees() {
                return currentAttendees;
            }

            public void setCurrentAttendees(Integer currentAttendees) {
                this.currentAttendees = currentAttendees;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public String getOrganizer() {
                return organizer;
            }

            public void setOrganizer(String organizer) {
                this.organizer = organizer;
            }

            public String getDifficulty() {
                return difficulty;
            }

            public void setDifficulty(String difficulty) {
                this.difficulty = difficulty;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Long getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(Long createdBy) {
                this.createdBy = createdBy;
            }

            public LocalDateTime getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
            }

            public LocalDateTime getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
            }

            public LocalDateTime getDeletedAt() {
                return deletedAt;
            }

            public void setDeletedAt(LocalDateTime deletedAt) {
                this.deletedAt = deletedAt;
            }

            // toString method để in thông tin
            @Override
            public String toString() {
                return "Event{" +
                        "eventID=" + eventID +
                        ", title='" + title + '\'' +
                        ", description='" + description + '\'' +
                        ", category='" + category + '\'' +
                        ", eventDate=" + eventDate +
                        ", eventTime=" + eventTime +
                        ", location='" + location + '\'' +
                        ", imageUrl='" + imageUrl + '\'' +
                        ", maxAttendees=" + maxAttendees +
                        ", currentAttendees=" + currentAttendees +
                        ", price=" + price +
                        ", organizer='" + organizer + '\'' +
                        ", difficulty='" + difficulty + '\'' +
                        ", status='" + status + '\'' +
                        ", createdBy=" + createdBy +
                        ", createdAt=" + createdAt +
                        ", updatedAt=" + updatedAt +
                        ", deletedAt=" + deletedAt +
                        '}';
            }
        }

        @Entity
        @Table(name = "EventAchievements")
        public static class EventAchievements {

            // Khóa chính, tự động tăng
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "AchievementID", nullable = false, updatable = false)
            private Long achievementID;

            // Khóa ngoại tham chiếu Event
            @Column(name = "EventID")
            private Long eventID;

            // Khóa ngoại tham chiếu User
            @Column(name = "UserID")
            private Long userID;

            // Vị trí đạt được
            @Column(name = "Position")
            private Integer position;

            // Điểm số đạt được
            @Column(name = "Points")
            private Integer points;

            // Loại huy hiệu đạt được
            @Column(name = "BadgeType", length = 50)
            private String badgeType;

            // Ngày giờ được trao thưởng
            @Column(name = "AwardedAt", nullable = false)
            private LocalDateTime awardedAt;

            // Constructor không tham số
            public EventAchievements() {}

            // Constructor đầy đủ
            public EventAchievements(Long eventID, Long userID, Integer position, Integer points, String badgeType, LocalDateTime awardedAt) {
                this.eventID = eventID;
                this.userID = userID;
                this.position = position;
                this.points = points;
                this.badgeType = badgeType;
                this.awardedAt = awardedAt;
            }

            // Getter và Setter
            public Long getAchievementID() {
                return achievementID;
            }

            public Long getEventID() {
                return eventID;
            }

            public void setEventID(Long eventID) {
                this.eventID = eventID;
            }

            public Long getUserID() {
                return userID;
            }

            public void setUserID(Long userID) {
                this.userID = userID;
            }

            public Integer getPosition() {
                return position;
            }

            public void setPosition(Integer position) {
                this.position = position;
            }

            public Integer getPoints() {
                return points;
            }

            public void setPoints(Integer points) {
                this.points = points;
            }

            public String getBadgeType() {
                return badgeType;
            }

            public void setBadgeType(String badgeType) {
                this.badgeType = badgeType;
            }

            public LocalDateTime getAwardedAt() {
                return awardedAt;
            }

            public void setAwardedAt(LocalDateTime awardedAt) {
                this.awardedAt = awardedAt;
            }

            // toString để debug
            @Override
            public String toString() {
                return "Achievement{" +
                        "achievementID=" + achievementID +
                        ", eventID=" + eventID +
                        ", userID=" + userID +
                        ", position=" + position +
                        ", points=" + points +
                        ", badgeType='" + badgeType + '\'' +
                        ", awardedAt=" + awardedAt +
                        '}';
            }
        }

        @Entity
        @Table(name = "EventPrizes")
        public static class EventPrizes {

            // Khóa chính, tự động tăng
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "PrizeID", nullable = false, updatable = false)
            private Long prizeID;

            // Khóa ngoại tham chiếu Event
            @Column(name = "EventID")
            private Long eventID;

            // Xếp hạng (giải nhất, nhì, ba, ...)
            @Column(name = "Rank")
            private Integer rank;

            // Số tiền giải thưởng
            @Column(name = "PrizeAmount", precision = 10, scale = 2)
            private BigDecimal prizeAmount;

            // Mô tả giải thưởng
            @Column(name = "Description", length = 500)
            private String description;

            // Constructor không tham số
            public EventPrizes() {}

            // Constructor đầy đủ
            public EventPrizes(Long eventID, Integer rank, BigDecimal prizeAmount, String description) {
                this.eventID = eventID;
                this.rank = rank;
                this.prizeAmount = prizeAmount;
                this.description = description;
            }

            // Getter và Setter
            public Long getPrizeID() {
                return prizeID;
            }

            public Long getEventID() {
                return eventID;
            }

            public void setEventID(Long eventID) {
                this.eventID = eventID;
            }

            public Integer getRank() {
                return rank;
            }

            public void setRank(Integer rank) {
                this.rank = rank;
            }

            public BigDecimal getPrizeAmount() {
                return prizeAmount;
            }

            public void setPrizeAmount(BigDecimal prizeAmount) {
                this.prizeAmount = prizeAmount;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            // toString để dễ debug/log
            @Override
            public String toString() {
                return "Prize{" +
                        "prizeID=" + prizeID +
                        ", eventID=" + eventID +
                        ", rank=" + rank +
                        ", prizeAmount=" + prizeAmount +
                        ", description='" + description + '\'' +
                        '}';
            }
        }

        @Entity
        @Table(name = "EventProgrammingLanguages")
        public static class EventProgrammingLanguages {

            // Cột EventID kiểu bigint, bắt buộc (nullable = false)
            @Id
            @Column(name = "EventID", nullable = false)
            private Long eventID;

            // Cột Language kiểu varchar(50), bắt buộc (nullable = false)
            @Column(name = "Language", nullable = false, length = 50)
            private String language;

            // Constructor mặc định
            public EventProgrammingLanguages() {
            }

            // Constructor đầy đủ
            public EventProgrammingLanguages(Long eventID, String language) {
                this.eventID = eventID;
                this.language = language;
            }

            // Getter và Setter
            public Long getEventID() {
                return eventID;
            }

            public void setEventID(Long eventID) {
                this.eventID = eventID;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            // toString() để dễ dàng in ra khi cần
            @Override
            public String toString() {
                return "EventLanguage{" +
                        "eventID=" + eventID +
                        ", language='" + language + '\'' +
                        '}';
            }
        }

        @Entity
        @Table(name = "EventRounds")
        public static class EventRounds {

            // RoundID - Khóa chính, không null
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "RoundID", nullable = false, updatable = false)
            private Long roundID;

            // EventID - bigint, có thể null
            @Column(name = "EventID")
            private Long eventID;

            // Name - nvarchar(255), có thể null
            @Column(name = "Name", length = 255)
            private String name;

            // Duration - int, có thể null
            @Column(name = "Duration")
            private Integer duration;

            // Problems - int, có thể null
            @Column(name = "Problems")
            private Integer problems;

            // Description - nvarchar(MAX), có thể null
            @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
            private String description;

            // StartTime - datetime, có thể null
            @Column(name = "StartTime")
            private LocalDateTime startTime;

            // EndTime - datetime, không null
            @Column(name = "EndTime", nullable = false)
            private LocalDateTime endTime;

            // Constructor mặc định
            public EventRounds() {
            }

            // Constructor đầy đủ
            public EventRounds(Long eventID, String name, Integer duration, Integer problems, String description, LocalDateTime startTime, LocalDateTime endTime) {
                this.eventID = eventID;
                this.name = name;
                this.duration = duration;
                this.problems = problems;
                this.description = description;
                this.startTime = startTime;
                this.endTime = endTime;
            }

            // Getter và Setter
            public Long getRoundID() {
                return roundID;
            }

            public Long getEventID() {
                return eventID;
            }

            public void setEventID(Long eventID) {
                this.eventID = eventID;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getDuration() {
                return duration;
            }

            public void setDuration(Integer duration) {
                this.duration = duration;
            }

            public Integer getProblems() {
                return problems;
            }

            public void setProblems(Integer problems) {
                this.problems = problems;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public LocalDateTime getStartTime() {
                return startTime;
            }

            public void setStartTime(LocalDateTime startTime) {
                this.startTime = startTime;
            }

            public LocalDateTime getEndTime() {
                return endTime;
            }

            public void setEndTime(LocalDateTime endTime) {
                this.endTime = endTime;
            }

            // toString để hiển thị thông tin
            @Override
            public String toString() {
                return "Round{" +
                        "roundID=" + roundID +
                        ", eventID=" + eventID +
                        ", name='" + name + '\'' +
                        ", duration=" + duration +
                        ", problems=" + problems +
                        ", description='" + description + '\'' +
                        ", startTime=" + startTime +
                        ", endTime=" + endTime +
                        '}';
            }
        }

        @Entity
        @Table(name = "EventSchedule")
        public static class EventSchedule {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "ScheduleID", nullable = false, updatable = false)
            private Long scheduleID;

            @Column(name = "EventID")
            private Long eventID;

            @Column(name = "ActivityName", length = 255)
            private String activityName;

            @Column(name = "StartTime")
            private LocalDateTime startTime;

            @Column(name = "EndTime")
            private LocalDateTime endTime;

            @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
            private String description;

            @Column(name = "Location", length = 255)
            private String location;

            @Column(name = "Type", length = 50, nullable = false)
            private String type;

            // Constructor mặc định
            public EventSchedule() {
            }

            // Getters và Setters

            public Long getScheduleID() {
                return scheduleID;
            }

            public void setScheduleID(Long scheduleID) {
                this.scheduleID = scheduleID;
            }

            public Long getEventID() {
                return eventID;
            }

            public void setEventID(Long eventID) {
                this.eventID = eventID;
            }

            public String getActivityName() {
                return activityName;
            }

            public void setActivityName(String activityName) {
                this.activityName = activityName;
            }

            public LocalDateTime getStartTime() {
                return startTime;
            }

            public void setStartTime(LocalDateTime startTime) {
                this.startTime = startTime;
            }

            public LocalDateTime getEndTime() {
                return endTime;
            }

            public void setEndTime(LocalDateTime endTime) {
                this.endTime = endTime;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            // toString method để in thông tin
            @Override
            public String toString() {
                return "EventSchedule{" +
                        "scheduleID=" + scheduleID +
                        ", eventID=" + eventID +
                        ", activityName='" + activityName + '\'' +
                        ", startTime=" + startTime +
                        ", endTime=" + endTime +
                        ", description='" + description + '\'' +
                        ", location='" + location + '\'' +
                        ", type='" + type + '\'' +
                        '}';
            }
        }
    }
}
