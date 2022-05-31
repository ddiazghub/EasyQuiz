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

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }
    
    public void addPlayer(Player player) {
        this.players.put(player.getId(), player);
    }
    
    public void removePlayer(int id) {
        this.players.remove(id);
    }
}
