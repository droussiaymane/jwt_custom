package com.project.demo.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuizRequest {
    private int id;
     private LocalDateTime dueDate;
    private String isActive;
     private String noQuestions;
    private  String teacherId;
    private  String timerH;
    private  String timerM;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getNoQuestions() {
        return noQuestions;
    }

    public void setNoQuestions(String noQuestions) {
        this.noQuestions = noQuestions;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTimerH() {
        return timerH;
    }

    public void setTimerH(String timerH) {
        this.timerH = timerH;
    }

    public String getTimerM() {
        return timerM;
    }

    public void setTimerM(String timerM) {
        this.timerM = timerM;
    }
}
