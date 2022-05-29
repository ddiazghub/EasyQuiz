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

    private String title;
    private ArrayList<Question> questions;

    Quiz(String t) {
        this.title = t;
        questions = new ArrayList();
    }
    public Question getFirstQuestion(){
        return questions.get(0);
    }
    public void addQuestion(Question q){
        questions.add(q);
    }

}
