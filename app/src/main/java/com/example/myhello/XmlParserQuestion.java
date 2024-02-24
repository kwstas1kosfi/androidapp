package com.example.myhello;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlParserQuestion {

    public static List<Question> parseQuestions(Resources resources, int resourceId) {
        List<Question> questions = new ArrayList<>();

        try {
            XmlResourceParser parser = resources.getXml(resourceId);

            int eventType = parser.getEventType();
            String questionText = "";
            List<String> options = new ArrayList<>();
            String correctAnswer = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    switch (tagName) {
                        case "text":
                            parser.next();
                            questionText = parser.getText();
                            break;
                        case "options":
                            while (eventType != XmlPullParser.END_TAG || !"options".equals(parser.getName())) {
                                if (eventType == XmlPullParser.START_TAG && "option".equals(parser.getName())) {
                                    parser.next();
                                    options.add(parser.getText());
                                }
                                eventType = parser.next();
                            }
                            break;

                        case "answer":
                            parser.next();
                            correctAnswer = parser.getText();
                            break;
                    }
                } else if (eventType == XmlPullParser.END_TAG && parser.getName().equals("question")) {
                    // Create a new Question object with the parsed data
                    Question question = new Question(questionText, new ArrayList<>(options), correctAnswer);
                    questions.add(question);

                    // Clear the variables for the next question
                    questionText = "";
                    correctAnswer = "";
                    options.clear();
                }

                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return questions;
    }
}
