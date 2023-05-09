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
    
    /**
     *  Creates a question for general purposes.
     *  <p>
     * 
     *  For the app to be able to know how to display the question it's best to use the Ans3Question or TrueFalseQuestion classes
     * 
     * @param  question  The question itself
     * @param  options The posible answers
     * @param  correctOptionIndex the index of the right answer stored in the array
     */
    public Question(String question, ArrayList<String> options, int correctOptionIndex){
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }
    /**
     * @return The question itself.
     */
    public String getQuestion() {
        return this.question;
    }
    /**
     * @return An ArrayList with the options of the question.
     */
    public ArrayList<String> getOptions() {
        return this.options;
    }
    /**
     *  @return The index of the right answer stored int the answers array.
     */
    public int getCorrectOptionIndex() {
        return this.correctOptionIndex;
    }
}
