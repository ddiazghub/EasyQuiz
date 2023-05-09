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
    
    /**
     * Creates a new player
     *
     * @param id The player's id
     * @param name The player's username
     */
    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.score = 0;
    }

    /**
     * @return The score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score An integer representing the score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return The player's id.
     */
    public int getId() {
        return id;
    }
    
    /**
     * @return The player's name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name  The name of the player.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return true if the player is hosting a quiz.
     */
    public boolean isHost() {
        return this.getId() == MainFrame.getInstance().getRoom().getHost().getId();
    }
}
