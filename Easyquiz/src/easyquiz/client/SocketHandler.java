/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz.client;

import easyquiz.Ans3Question;
import easyquiz.Player;
import easyquiz.Question;
import easyquiz.server.EasyQuizServer;
import easyquiz.shared.MessageParser;
import easyquiz.tcpsocket.TCPSocket;
import easyquiz.thread.JThread;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class SocketHandler {
    private TCPSocket socket;
    private JThread receiveThread;
    
    public SocketHandler(TCPSocket socket) {
        this.socket = socket;
        
        this.receiveThread = new JThread(() -> {
            byte[] buffer;
            
            try {
                buffer = this.socket.receive();
            } catch (IOException ex) {
                this.receiveThread.stop();
                
                return;
            }
            
            MainFrame main = MainFrame.getInstance();
            
            if (this.receiveThread.isRunning()) {
                if (buffer.length > 0) {
                    HashMap<String, String> message = MessageParser.parse(buffer);
                    
                    switch (message.get("com")) {
                        case "join":
                            Player player = new Player(Integer.parseInt(message.get("id")), message.get("name"));
                            main.setPlayer(player);
                            UUID roomCode = UUID.fromString(message.get("code"));
                            HashMap<Integer, Player> players = getPlayers(message.get("players"));
                            Player host = players.get(Integer.parseInt(message.get("host")));
                            main.joinRoom(roomCode, host, players);
                            
                            break;
                        
                        case "roomenter":
                            System.out.println("PLayer entered");
                            int playerId = Integer.parseInt(message.get("id"));
                            String playerName = message.get("name");
                            main.roomEnter(new Player(playerId, playerName));
                            
                            break;
                        
                        case "roomleave":
                            playerId = Integer.parseInt(message.get("id"));
                            main.roomLeave(playerId);
                            
                            break;
                            
                        case "next":
                        case "start":
                            long starting = Long.parseLong(message.get("starting"));
                            long deadline = Long.parseLong(message.get("deadline"));
                            String[] options = message.get("opts").split(";");
                            String question = message.get("question");
                            int correct = Integer.parseInt(message.get("correct"));
                            Question quest = new Ans3Question(question, options[0], options[1], options[2], correct);
                            main.nextQuestion(quest, starting, deadline);
                            
                            break;
                            
                        case "end":
                            break;
                            
                        case "add":
                            break;
                    }
                    
                    //this.lastReceived = System.currentTimeMillis();
                }
            }
        });
        
        this.receiveThread.start();
    }
    
    private static HashMap<Integer, Player> getPlayers(String rawField) {
        HashMap<Integer, Player> players = new HashMap<>();

        for (String rawPlayer : rawField.split(",")) {
            String[] parts = rawPlayer.split(";");

            if (parts.length == 2) {
                int id = Integer.parseInt(parts[0]);
                players.put(id, new Player(id, parts[1]));
            }
        }
        
        return players;
    }
}
