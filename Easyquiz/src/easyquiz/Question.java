/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz;

import java.util.ArrayList;

/**
 *
 * @author aland
 */
public class Question {
    private String question;
    private ArrayList<String> options;
    private int correctOptionIndex;
    
    public Question(String question, ArrayList<String> options, int correctOptionIndex){
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestion() {
        return this.question;
    }

    public ArrayList<String> getOptions() {
        return this.options;
    }

    public int getCorrectOptionIndex() {
        return this.correctOptionIndex;
    }
}
