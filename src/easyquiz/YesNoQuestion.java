/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz;

/**
 *
 * @author aland
 */
public class YesNoQuestion extends Question {

    private boolean ans;

    YesNoQuestion(String quest, boolean ans) {
        super(quest);
        this.ans = ans;
    }

}
