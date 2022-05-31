/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz.server;

import easyquiz.Ans3Question;
import easyquiz.Question;
import easyquiz.Quiz;
import easyquiz.tcpsocket.TCPServer;
import easyquiz.tcpsocket.TCPSocket;
import easyquiz.thread.JThread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author david
 */
public class EasyQuizServer extends TCPServer {
    private static EasyQuizServer instance;
    
    private final HashMap<Integer, PlayerHandler> players;
    private final HashMap<UUID, Room> rooms;
    private final HashMap<Integer, Quiz> quizzes;
    
    private EasyQuizServer() {
        super(3000);
        
        this.players = new HashMap<>();
        this.rooms = new HashMap<>();
        this.quizzes = new HashMap<>();
        
        Quiz test = new Quiz("Quiz de prueba");
        test.addQuestion(new Ans3Question("Pregunta", "No se", "Ni idea", "???", 0));
        test.addQuestion(new Ans3Question("Pregunta2", "No se2", "Ni idea2", "???2", 1));
        test.addQuestion(new Ans3Question("Pregunta3", "No se3", "Ni idea3", "???3", 2));
        
        this.quizzes.put(0, test);
    }

    public HashMap<UUID, Room> getRooms() {
        return rooms;
    }
    
    
    public void start() {
        while (true) {
            PlayerHandler client = new PlayerHandler(this.accept());
            System.out.println("New client: " + client.getSocket().toString());
            this.players.put(client.getPlayer().getId(), client);
        }
    }
    
    public void addPlayer(PlayerHandler player) {
        this.players.put(player.getPlayer().getId(), player);
    }
    
    public Room createRoom(int playerId) {
        int quizId = new Random().nextInt(this.quizzes.size());
        Room room = new Room(this.players.get(playerId), this.quizzes.get(quizId));
        this.rooms.put(room.getId(), room);
        return room;
    }
    
    public Room joinRoom(int playerId, UUID roomCode) {
        Room room = this.rooms.get(roomCode);
        room.addPlayer(this.players.get(playerId));
        
        return room;
    }
    
    public void startRoom(UUID roomCode) {
        Room room = this.rooms.get(roomCode);
        room.start();
    }
    
    public void endRoom(UUID roomCode) {
        this.rooms.remove(roomCode);
    }
    
    public static EasyQuizServer getInstance() {
        if (instance == null) {
            instance = new EasyQuizServer();
        }
        
        return instance;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EasyQuizServer easyquiz = EasyQuizServer.getInstance();
        easyquiz.start();
    }
}
