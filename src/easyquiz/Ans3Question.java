/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz;

/**
 *
 * @author aland
 */
public class Ans3Question extends Question {

    private String op1;
    private String op2;
    private String op3;

    public String getOp1() {
        return op1;
    }

    public String getOp2() {
        return op2;
    }

    public String getOp3() {
        return op3;
    }
    private int rightAns;

    Ans3Question(String quest, String op1, String op2, String op3, int rightAns){
        super(quest);
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.rightAns= rightAns;
    }

}
