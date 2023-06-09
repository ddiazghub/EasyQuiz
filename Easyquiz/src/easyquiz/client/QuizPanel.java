/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package easyquiz.client;

import easyquiz.Question;
import easyquiz.TrueFalseQuestion;
import easyquiz.thread.JThread;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author aland
 */
public class QuizPanel extends javax.swing.JPanel {
    private final TopBar statusBar;
    private CardLayout cards;
    private final Ans3QuestionPane ans3Panel;
    private final CountdownPanel countdownPanel;
    
    /**
     * Creates new form TrueFalseQuestion
     */
    public QuizPanel() {
        initComponents();
        
        this.cards = (CardLayout) this.quizPanel.getLayout();
        this.cards.show(this.quizPanel, "countdown");
        
        this.countdownPanel = new CountdownPanel();
        this.quizPanel.add(this.countdownPanel, "countdown");
        
        this.ans3Panel = new Ans3QuestionPane();
        this.quizPanel.add(this.ans3Panel, "ans3");
        
        this.statusBar = new TopBar();
        this.add(this.statusBar);
        this.revalidate();
        this.repaint();
    }

    public Ans3QuestionPane getAns3Panel() {
        return ans3Panel;
    }

    public CountdownPanel getCountdownPanel() {
        return countdownPanel;
    }
    
    
    public void showCard(String card) {
        this.cards.show(this.quizPanel, card);
    }
    
    public void setStatusBarData(String name, int score) {
        this.statusBar.showData(name, score);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        quizPanel = new javax.swing.JPanel();

        setBackground(new java.awt.Color(237, 232, 227));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        quizPanel.setPreferredSize(new java.awt.Dimension(500, 365));
        quizPanel.setLayout(new java.awt.CardLayout());
        add(quizPanel);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel quizPanel;
    // End of variables declaration//GEN-END:variables
}
