/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz;

import easyquiz.client.MainFrame;

/**
 *
 * @author david
 */
public class Player {
    private int id;
    private String name;
    private int score;
    
    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isHost() {
        return this.getId() == MainFrame.getInstance().getRoom().getHost().getId();
    }
}
