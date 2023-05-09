/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author aland
 */
public class Quiz {
    private int id;
    private String title;
    private ArrayList<Question> questions;

    /**
     * Creates a new Quiz with the specified title
     * <p>
     * 
     * @param t The title of the quiz
     */
    public Quiz(String t) {
        this.title = t;
        questions = new ArrayList();
    }
    
    /**
     * @return The first question object stored in the quiz
     */
    public Question getFirstQuestion(){
        return questions.get(0);
    }
    /**
     * Adds a new question object to the quiz
     * <p>
     * 
     * @param  q The question object
     */
    public void addQuestion(Question q){
        questions.add(q);
    }
    /**
     *@return The ID of the quiz
     */
    public int getId() {
        return id;
    }
    /**
     *@return The title of the quiz
     */
    public String getTitle() {
        return title;
    }
    /**
     *@return An ArrayList containing the question objects in the quiz
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
