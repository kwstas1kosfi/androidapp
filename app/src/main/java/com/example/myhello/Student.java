package com.example.myhello;

public class Student {

    private String academicId;
    private String fullName;
    private String semester;
    private int score;

    public Student(String academicId, String fullName, String semester, int score) {
        this.academicId = academicId;
        this.fullName = fullName;
        this.semester = semester;
        this.score = score;
    }

    public String getAcademicId() {
        return academicId;
    }

    public void setAcademicId(String academicId) {
        this.academicId = academicId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
