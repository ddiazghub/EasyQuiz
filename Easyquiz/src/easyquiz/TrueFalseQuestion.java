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
public class TrueFalseQuestion extends Question {
    private static ArrayList<String> options = new ArrayList<>(Arrays.asList(new String[] {
        "Verdadero",
        "Falso"
    }));
    
    private boolean ans;

    TrueFalseQuestion(String quest, boolean ans) {
        super(quest, options, ans ? 0 : 1);
        this.ans = ans;
    }
}
