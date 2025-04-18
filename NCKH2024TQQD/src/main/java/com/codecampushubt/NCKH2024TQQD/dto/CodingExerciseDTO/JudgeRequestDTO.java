package com.codecampushubt.NCKH2024TQQD.dto.CodingExerciseDTO;

public class JudgeRequestDTO {
    private String sourceCode;
    private String language;
    private String exerciseID;

    public JudgeRequestDTO(String sourceCode, String language, String exerciseID) {
        this.sourceCode = sourceCode;
        this.language = language;
        this.exerciseID = exerciseID;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(String exerciseID) {
        this.exerciseID = exerciseID;
    }
}
