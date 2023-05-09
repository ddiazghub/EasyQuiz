/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz.client;

import easyquiz.Player;
import easyquiz.Quiz;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author david
 */
public class ClientRoom {
    private UUID id;
    private HashMap<Integer, Player> players;
    private Player host;
    
    /**
     * Creates a new client room.
     * 
     * @param id  The id of the room
     * @param host  The player Object that creates the game
     * @param players  A HashMap containing all the other players
     */
    public ClientRoom(UUID id, Player host, HashMap<Integer, Player> players) {
        this.id = id;
        this.host = host;
        this.players = players;
    }

    public UUID getId() {
        return id;
    }

    public Player getHost() {
        return host;
    }
    /**
     *@return A HashMap containing all the players in the client room
     */
    public HashMap<Integer, Player> getPlayers() {
        return players;
    }
    /**
     * Adds a new player to the client room and it's HashMap
     * <p>
     * 
     *@param player  A player object
     */
    public void addPlayer(Player player) {
        this.players.put(player.getId(), player);
    }
    
     /**
     * Removes a player from the client room
     * <p>
     * 
     *@param id  The id of the desired player
     */
    public void removePlayer(int id) {
        this.players.remove(id);
    }
}
