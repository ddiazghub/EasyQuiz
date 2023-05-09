/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz.server;

import easyquiz.Player;
import easyquiz.shared.MessageParser;
import easyquiz.tcpsocket.TCPSocket;
import easyquiz.thread.JThread;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class PlayerHandler {
    private static int idSequence = 0;
    
    private TCPSocket socket;
    private JThread receiveThread;
    private long lastReceived;
    private Player player;
    private UUID roomCode;
    
    public PlayerHandler(TCPSocket socket) {
        this.player = new Player(idSequence, "null");
        idSequence++;
        
        this.socket = socket;
        
        this.receiveThread = new JThread(() -> {
            byte[] buffer;
            
            try {
                buffer = this.socket.receive();
            } catch (IOException ex) {
                this.receiveThread.stop();
                
                return;
            }

            if (this.receiveThread.isRunning()) {
                if (buffer.length > 0) {
                    HashMap<String, String> message = MessageParser.parse(buffer);
                    EasyQuizServer easyquiz = EasyQuizServer.getInstance();
                    
                    switch (message.get("com")) {
                        case "get":
                            easyquiz.removePlayer(this.player.getId(), this.roomCode);
                            break;
                            
                        case "join":
                            this.player.setName(message.get("name"));
                            Room room = easyquiz.joinRoom(this.player.getId(), UUID.fromString(message.get("code")));
                            StringBuilder builder = new StringBuilder();
                            this.player.setScore(0);
                            this.roomCode = room.getId();
                            
                            for (PlayerHandler player : room.getPlayers().values()) {
                                builder.append(player.getPlayer().getId())
                                        .append(";")
                                        .append(player.getPlayer().getName())
                                        .append(",");
                            }
                            
                            socket.send(MessageParser.create("join", "code=" + room.getId().toString(), "players=" + builder.toString(), "host=" + room.getHost().getPlayer().getId(), "id=" + this.getPlayer().getId(), "name=" + this.getPlayer().getName()));
                            
                            break;
                        
                        case "room":
                            this.player.setName(message.get("name"));
                            room = easyquiz.createRoom(this.player.getId());
                            socket.send(MessageParser.create("join", "code=" + room.getId().toString(), "players=" + this.getPlayer().getId() + ";" + this.getPlayer().getName(), "host=" + this.getPlayer().getId(), "id=" + this.getPlayer().getId(), "name=" + this.getPlayer().getName()));
                            this.player.setScore(0);
                            this.roomCode = room.getId();
                            
                            break;
                            
                        case "start":
                            easyquiz.startRoom(UUID.fromString(message.get("code")));
                            break;
                            
                        case "ans":
                            if (this.lastReceived > -1) {
                                return;
                            }
                            
                            this.lastReceived = Integer.parseInt(message.get("sel"));
                            this.player.setScore(this.player.getScore() + Integer.parseInt(message.get("score")));
                            System.out.println("Player Score: " + this.player.getScore());
                            break;
                            
                        case "exit":
                            easyquiz.removePlayer(this.player.getId(), this.roomCode);
                            break;
                    }
                }
            }
        });
        
        EasyQuizServer.getInstance().addPlayer(this);
        this.receiveThread.start();
    }

    public TCPSocket getSocket() {
        return socket;
    }

    public void setLastReceived(long lastReceived) {
        this.lastReceived = lastReceived;
    }

    public long getLastReceived() {
        return lastReceived;
    }

    public Player getPlayer() {
        return player;
    }
    
    public void kill() {
        this.receiveThread.stop();
        
        try {
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(PlayerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
