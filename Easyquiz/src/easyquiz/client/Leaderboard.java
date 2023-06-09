/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package easyquiz.client;

import easyquiz.Player;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author aland
 */
public class Leaderboard extends javax.swing.JPanel {
    private DefaultTableModel model;
    
    /**
     * Creates new form leaderboard
     */
    public Leaderboard() {
        initComponents();
        highTable.getTableHeader().setForeground(new Color(25,29,99));
        highTable.getTableHeader().setFont(new Font("Segoe Print", Font.BOLD,12));
        highTable.getColumnModel().getColumn(0).setPreferredWidth(45);
        highTable.getColumnModel().getColumn(0).setMaxWidth(90);
        
        this.model = new DefaultTableModel();
        this.model.addColumn("Place");
        this.model.addColumn("User");
        this.model.addColumn("Score");
        this.highTable.setModel(this.model);
    }

    /**
     * Displays all the players's scores in a table.
     * 
     * @param players  An ArrayList of players
     */
    public void showResults(ArrayList<Player> players) {
        Collections.sort(players, (p1, p2) -> {
            return p2.getScore() - p1.getScore();
        });
        
        this.model.setRowCount(0);
        
        for (int i = 1; i <= players.size(); i++) {
            this.model.addRow(new Object[] {
                i,
                players.get(i - 1).getName(),
                players.get(i - 1).getScore()
            });
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        highLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        highTable = new javax.swing.JTable();
        exitBtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(237, 232, 227));
        setLayout(new java.awt.BorderLayout());

        highLabel.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        highLabel.setForeground(new java.awt.Color(25, 29, 99));
        highLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        highLabel.setText("Leaderboard");
        highLabel.setMaximumSize(new java.awt.Dimension(250, 100));
        highLabel.setPreferredSize(new java.awt.Dimension(250, 100));
        add(highLabel, java.awt.BorderLayout.NORTH);

        jScrollPane1.setMaximumSize(new java.awt.Dimension(32767, 800));
        jScrollPane1.setOpaque(false);

        highTable.setFont(new java.awt.Font("Segoe Print", 1, 12)); // NOI18N
        highTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Place", "User", "Score"
            }
        ));
        highTable.setMaximumSize(new java.awt.Dimension(2147483647, 1200));
        highTable.getTableHeader().setResizingAllowed(false);
        highTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(highTable);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        exitBtn.setBackground(new java.awt.Color(255, 51, 51));
        exitBtn.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        exitBtn.setForeground(new java.awt.Color(244, 243, 246));
        exitBtn.setText("Exit");
        exitBtn.setFocusPainted(false);
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });
        add(exitBtn, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        MainFrame.getInstance().exitRoomNoSend();
    }//GEN-LAST:event_exitBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitBtn;
    private javax.swing.JLabel highLabel;
    private javax.swing.JTable highTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}