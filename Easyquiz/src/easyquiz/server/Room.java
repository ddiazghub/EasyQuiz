/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz.server;

import easyquiz.Question;
import easyquiz.Quiz;
import easyquiz.shared.MessageParser;
import easyquiz.shared.Timer;
import easyquiz.thread.JThread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author david
 */
public class Room {
    private UUID id;
    private ArrayList<PlayerHandler> players;
    private PlayerHandler host;
    private Quiz quiz;
    private int currentQuestionIndex;
    private Timer timer;
    
    public Room(PlayerHandler host, Quiz quiz) {
        this.id = UUID.randomUUID();
        this.players = new ArrayList<>();
        this.players.add(host);
        this.host = host;
        this.quiz = quiz;
        this.currentQuestionIndex = 0;
    }

    public UUID getId() {
        return id;
    }

    public ArrayList<PlayerHandler> getPlayers() {
        return players;
    }

    public PlayerHandler getHost() {
        return host;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    
    public void start() {
        this.nextQuestion("start");
    }
    
    public void nextQuestion() {
        this.nextQuestion("next");
    }
    
    public void nextQuestion(String command) {
        ArrayList<Question> questions = this.quiz.getQuestions();
        
        if (this.currentQuestionIndex >= questions.size()) {
            sendResults();
            EasyQuizServer.getInstance().endRoom(this.id);
            
            return;
        }
            
        Question question = questions.get(this.currentQuestionIndex);
        this.currentQuestionIndex++;
        
        for (PlayerHandler p : this.players) {
            p.setLastReceived(-1);
            p.getSocket().send(MessageParser.create(command, "question=" + question.getQuestion(), "type=0", "opts=" + String.join(";", question.getOptions()), "correct=" + question.getCorrectOptionIndex(), "starting=" + (System.currentTimeMillis() + 5500), "deadline=" + (System.currentTimeMillis() + 20500)));
        }
        
        this.timer = new Timer(25500, () -> {
            this.nextQuestion();
        });
        
        this.timer.start();
    }
    
    public void addPlayer(PlayerHandler player) {
        for (PlayerHandler p : this.players) {
            p.getSocket().send(MessageParser.create("roomenter", "id=" + player.getPlayer().getId(), "name=" + player.getPlayer().getName()));
        }
        
        this.players.add(player);
    }
    
    public void sendResults() {
        StringBuilder builder = new StringBuilder();

        for (PlayerHandler player : this.players) {
            builder.append(player.getPlayer().getId())
                .append(";")
                .append(player.getPlayer().getScore())
                .append(",");
        }
        
        for (PlayerHandler p : this.players) {
            p.getSocket().send(MessageParser.create("res", "players=" + builder.toString()));
        }
    }
}
