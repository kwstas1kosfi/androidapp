package com.example.myhello;

import java.util.List;

public class Question {
    private String text;
    private String imageResourceName; // Resource name of the image
    private List<String> options;
    private String correctAnswer;

    public Question(String text, String imageResourceName, List<String> options, String correctAnswer) {
        this.text = text;
        this.imageResourceName = imageResourceName;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageResourceName() {
        return imageResourceName;
    }

    public void setImageResourceName(String imageResourceName) {
        this.imageResourceName = imageResourceName;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
