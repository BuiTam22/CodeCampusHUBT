package com.codecampushubt.NCKH2024TQQD.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ExamAnswers")
public class ExamAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnswerID", nullable = false, unique = true)
    private Long answerID;

    @Column(name = "ParticipantID", nullable = false)
    private Long participantID;

    @Column(name = "QuestionID", nullable = false)
    private Long questionID;

    @Column(name = "Answer", columnDefinition = "NVARCHAR(MAX)")
    private String answer;

    @Column(name = "IsCorrect")
    private Boolean isCorrect;

    @Column(name = "Score")
    private Integer score;

    @Column(name = "ReviewerComments", columnDefinition = "NVARCHAR(MAX)")
    private String reviewerComments;

    @Column(name = "SubmittedAt", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Timestamp submittedAt;

    // constructor

    public ExamAnswer() {
    }

    public ExamAnswer(Long participantID, Long questionID, String answer, Boolean isCorrect, Integer score, String reviewerComments, Timestamp submittedAt) {
        this.participantID = participantID;
        this.questionID = questionID;
        this.answer = answer;
        this.isCorrect = isCorrect;
        this.score = score;
        this.reviewerComments = reviewerComments;
        this.submittedAt = submittedAt;
    }

    // getter setter

    public Long getAnswerID() {
        return answerID;
    }

    public Long getParticipantID() {
        return participantID;
    }

    public void setParticipantID(Long participantID) {
        this.participantID = participantID;
    }

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getReviewerComments() {
        return reviewerComments;
    }

    public void setReviewerComments(String reviewerComments) {
        this.reviewerComments = reviewerComments;
    }

    public Timestamp getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Timestamp submittedAt) {
        this.submittedAt = submittedAt;
    }

    @Override
    public String toString() {
        return "ExamAnswer{" +
                "answerID=" + answerID +
                ", participantID=" + participantID +
                ", questionID=" + questionID +
                ", answer='" + answer + '\'' +
                ", isCorrect=" + isCorrect +
                ", score=" + score +
                ", reviewerComments='" + reviewerComments + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }
}
