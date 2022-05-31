/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author aland
 */
public class Ans3Question extends Question {
    public Ans3Question(String quest, String op1, String op2, String op3, int rightAns){
        super(quest, new ArrayList<>(Arrays.asList(new String[] {
            op1,
            op2,
            op3
        })), rightAns);
    }
}
