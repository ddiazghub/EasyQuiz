/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz.server;

import easyquiz.Ans3Question;
import easyquiz.Player;
import easyquiz.Question;
import easyquiz.Quiz;
import easyquiz.tcpsocket.TCPServer;
import easyquiz.tcpsocket.TCPSocket;
import easyquiz.thread.JThread;
import java.io.File;
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
        
        Quiz capitals = new Quiz("Capitales");
        capitals.addQuestion(new Ans3Question("Capital de Nicaragua", "Managua", "Tegucigalpa", "San José", 0));
        capitals.addQuestion(new Ans3Question("Capital de Nicaragua", "Madrid", "Palikir", "Lima", 2));
        capitals.addQuestion(new Ans3Question("Capital de Indonesia", "Ankara", "Minsk", "Jakarta", 2));
        capitals.addQuestion(new Ans3Question("Capital de las Bahamas", "Nassau", "Cairo", "Copenhague", 0));
        capitals.addQuestion(new Ans3Question("Capital de Portugal", "Buenos Aires", "Lisboa", "Caracas", 1));
        
        Quiz countries = new Quiz("Países");
        countries.addQuestion(new Ans3Question("¿En cuál país están las 10 ciudades mas frías del mundo?", "Canada", "Noruega", "Rusia", 2));
        countries.addQuestion(new Ans3Question("¿Cuál país tiene 3 ciudades capitales", "Sudáfrica", "Australia", "China", 0));
        countries.addQuestion(new Ans3Question("¿Cuál de estos países tiene territorio que abarca en total 12 zonas zonas horarias?", "Rusia", "Francia", "China", 1));
        countries.addQuestion(new Ans3Question("¿Cuál es el país más pequeño?", "Ciudad del vaticano", "Liechtenstein", "Chipre", 0));
        countries.addQuestion(new Ans3Question("¿Cuál de estos países NO es cruzado por el Ecuador", "Ecuador", "Kenia", "Egipto", 2));
        
        Quiz quiz3 = new Quiz("Quiz 3");
        quiz3.addQuestion(new Ans3Question("¿En cuál ciudad está el edificio más alto del mundo?", "Dubai", "Nueva York", "Shanghai", 0));
        quiz3.addQuestion(new Ans3Question("¿Cuál es el continente más elevado con una altura promedio de 2.51 km?", "Asia", "Europa", "Antártica", 2));
        quiz3.addQuestion(new Ans3Question("¿Cuál es la nación-isla más pequeña del mundo?", "Barbados", "Nauru", "Malvinas", 1));
        quiz3.addQuestion(new Ans3Question("¿Cuál es la capital más elevada del mundo?", "Bogotá, Colombia", "Kathmandú, Nepal", "La Paz, Bolivia", 2));
        quiz3.addQuestion(new Ans3Question("¿Cuál es el continente más grande del mundo?", "America del Sur", "Africa", "Asia", 2));
        
        this.quizzes.put(0, capitals);
    }

    public HashMap<UUID, Room> getRooms() {
        return rooms;
    }
    
    
    public void start() {
        System.out.println("Server started");
        System.out.println(new File("").getAbsolutePath());
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
        System.out.println("Started room: " + roomCode);
    }
    
    public void removePlayer(int playerId, UUID roomCode) {
        Room room = this.rooms.get(roomCode);
        room.removePlayer(playerId);
    }
    
    public void deleteRoom(UUID roomCode) {
        this.rooms.remove(roomCode);
        System.out.println("Deleted room: " + roomCode);
    }
    
    public void endRoom(UUID roomCode) {
        this.rooms.remove(roomCode);
        System.out.println("Deleted room: " + roomCode);
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
