/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package easyquiz.client;

import easyquiz.Quiz;
import easyquiz.TrueFalseQuestion;
import easyquiz.Ans3Question;
import easyquiz.Player;
import easyquiz.Question;
import easyquiz.shared.MessageParser;
import easyquiz.tcpsocket.SocketAddress;
import easyquiz.tcpsocket.TCPSocket;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import javax.swing.JOptionPane;

/**
 *
 * @author aland
 */
public class MainFrame extends javax.swing.JFrame {
    private static MainFrame instance;
    
    private TCPSocket socket;
    private SocketHandler handler;
    private CardLayout cards;
    private ClientRoom room;
    private QuizPanel quizPanel;
    private Player player;
    private final WaitingRoom waitingRoomPanel;
    private final Ans3QuestionPane ans3Panel;
    private final CountdownPanel countdownPanel;
    private Leaderboard leaderboardPanel;
    
    /**
     * Creates new form MainFrame
     */
    private MainFrame() {
        initComponents();
        
        this.setTitle("EasyQuiz");
        this.quizPanel = new QuizPanel();
        this.mainPanel.add(this.quizPanel, "quiz");
        this.cards = (CardLayout) this.mainPanel.getLayout();
        this.cards.show(this.mainPanel, "main");
        
        this.room = null;
        this.socket = new TCPSocket(new SocketAddress("20.211.41.7", 3000));
        this.handler = new SocketHandler(this.socket);
        
        this.waitingRoomPanel = new WaitingRoom();
        this.mainPanel.add(this.waitingRoomPanel, "waiting");
        
        this.ans3Panel = this.quizPanel.getAns3Panel();
        this.countdownPanel = this.quizPanel.getCountdownPanel();
        
        this.leaderboardPanel = new Leaderboard();
        this.mainPanel.add(this.leaderboardPanel, "leaderboard");
    }

    public TCPSocket getSocket() {
        return socket;
    }
    
    /**
     *@return The Socket's handler
     */
    public SocketHandler getHandler() {
        return handler;
    }

    public CardLayout getCards() {
        return cards;
    }

    public ClientRoom getRoom() {
        return room;
    }
    /**
     *@return A player object
     */
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    /**
     * Adds the gained score to the player and updates the status bar accordingly
     * 
     * @param score  The player's earned score
     */
    public void addScore(int score) {
        int s = this.player.getScore();
        this.player.setScore(s + score);
        this.quizPanel.setStatusBarData(this.player.getName(), s + score);
    }
    /**
     *Displays the desired panel
     * 
     * @param name  The panel's name
     */
    public void showCard(String name) {
        String[] cardPath = name.split("\\.");
        this.cards.show(this.mainPanel, cardPath[0]);
        
        if (cardPath.length == 2) {
            this.quizPanel.showCard(cardPath[1]);
        }
    }
    
    /**
     * Adds a player to the game room
     * 
     * @param player  A player object
     */
    public void roomEnter(Player player) {
        if (this.room == null) {
            return;
        }
        
        this.room.addPlayer(player);
        this.waitingRoomPanel.show(this.room);
    }
    
    /**
     * Removes a player from the room
     * 
     * @param playerId  The player's unique id.
     */
    public void roomLeave(int playerId) {
        if (this.room == null) {
            return;
        }
        
        this.room.removePlayer(playerId);
        this.waitingRoomPanel.show(this.room);
    }
    
    /**
     * Adds a player to the game
     * 
     * @param code  The code of the room
     * @param host  The player hosting room
     * @param players  The HashMap with all the joined players
     */
    public void joinRoom(UUID code, Player host, HashMap<Integer, Player> players) {
        this.room = new ClientRoom(code, host, players);
        showCard("waiting");
        this.waitingRoomPanel.show(this.room);
    }
    
     /**
     * Displays the loading screen for the next question
     * 
     * @param question  A question object
     * @param starting  The countdown's starting number
     * @param deadline  The time limit for the countdown
     */
    public void nextQuestion(Question question, long starting, long deadline) {
        if (this.room == null)
            return;
        
        showCard("quiz.countdown");
        this.countdownPanel.countdown(starting, deadline, question);
        this.quizPanel.setStatusBarData(this.player.getName(), this.player.getScore());
    }
    
    /**
     * Displays a three option question
     * 
     * @param deadline  The time limit 
     * @param question  A question object
     */
    public void showQuestion(Question question, long deadline) {
        if (this.room == null) {
            return;
        }
        
        showCard("quiz.ans3");
        this.ans3Panel.showQuestion((Ans3Question) question, deadline);
    }
    
    /**
     * Sends the chosen answer to the server.
     * <p>
     * 
     * @param option  The selected option's index
     * @param score  The score of that question
     */
    public void sendAnswer(int option, int score) {
        if (this.room == null) {
            return;
        }
        
        this.socket.send(MessageParser.create("ans", "sel=" + option, "score=" + score));
    }
    
    /**
     * Exit room without notifying server
     */
    public void exitRoomNoSend() {
        this.room = null;
        this.player = null;
        this.showCard("main");
    }
    
    /**
     * Exit room
     */
    public void exitRoom() {
        this.exitRoomNoSend();
        this.socket.send(MessageParser.create("exit"));
    }
    
    /**
     * Show quiz results
     */
    public void showResults() {
        this.showCard("leaderboard");
        this.leaderboardPanel.showResults(new ArrayList<>(this.room.getPlayers().values()));
    }
    
    /**
     * Show an error message
     * 
     * <p>
     * 
     * @param message  The errr message
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        startPane = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        sTitleLabel = new javax.swing.JLabel();
        codeLabel = new javax.swing.JLabel();
        codeField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        joinBtn = new javax.swing.JButton();
        joinBtn1 = new javax.swing.JButton();
        newBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(74, 232, 227));
        setMinimumSize(new java.awt.Dimension(500, 400));

        mainPanel.setBackground(new java.awt.Color(237, 232, 227));
        mainPanel.setLayout(new java.awt.CardLayout());

        startPane.setBackground(new java.awt.Color(237, 232, 227));

        titleLabel.setFont(new java.awt.Font("Segoe Print", 1, 48)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("EasyQuiz");

        sTitleLabel.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        sTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sTitleLabel.setText("Join a quiz!");

        codeLabel.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        codeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        codeLabel.setText("Code:");

        nameLabel.setFont(new java.awt.Font("Segoe Print", 1, 24)); // NOI18N
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameLabel.setText("Name:");

        joinBtn.setBackground(new java.awt.Color(49, 205, 99));
        joinBtn.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        joinBtn.setForeground(new java.awt.Color(244, 243, 246));
        joinBtn.setText("Join");
        joinBtn.setFocusPainted(false);
        joinBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinBtnActionPerformed(evt);
            }
        });

        joinBtn1.setBackground(new java.awt.Color(49, 205, 99));
        joinBtn1.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        joinBtn1.setForeground(new java.awt.Color(244, 243, 246));
        joinBtn1.setText("Create");
        joinBtn1.setFocusPainted(false);
        joinBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinBtn1ActionPerformed(evt);
            }
        });

        newBtn.setBackground(new java.awt.Color(49, 205, 99));
        newBtn.setFont(new java.awt.Font("Segoe Print", 1, 14)); // NOI18N
        newBtn.setForeground(new java.awt.Color(244, 243, 246));
        newBtn.setText("New");
        newBtn.setFocusPainted(false);
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout startPaneLayout = new javax.swing.GroupLayout(startPane);
        startPane.setLayout(startPaneLayout);
        startPaneLayout.setHorizontalGroup(
            startPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(startPaneLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(startPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(startPaneLayout.createSequentialGroup()
                        .addComponent(codeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(codeField))
                    .addGroup(startPaneLayout.createSequentialGroup()
                        .addGroup(startPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(joinBtn)
                            .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(startPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(startPaneLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(startPaneLayout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(newBtn)))))
                .addGap(0, 122, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, startPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(startPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sTitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titleLabel))
                .addGap(44, 44, 44)
                .addComponent(joinBtn1)
                .addGap(14, 14, 14))
        );
        startPaneLayout.setVerticalGroup(
            startPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, startPaneLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(startPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(joinBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sTitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(startPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeLabel)
                    .addComponent(codeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(startPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(startPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(joinBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        mainPanel.add(startPane, "main");

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void joinBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinBtnActionPerformed
        /*Quiz q = new Quiz("Test Quiz");
        TopBar bar = new TopBar(nameField.getText(),"Test Quiz");
        //q.addQuestion(new TrueFalseQuestion("Is tabata a good person?",true));
        q.addQuestion(new Ans3Question("Is tabata a good person?","yes","of course", "absolutely", 3));
        this.remove(startPane);
        this.add(bar,BorderLayout.NORTH);
        if(q.getFirstQuestion() instanceof TrueFalseQuestion){
            this.add(new YesNoQuestionPane((TrueFalseQuestion)q.getFirstQuestion()),BorderLayout.CENTER);
        }else{
            this.add(new Ans3QuestionPane((Ans3Question)q.getFirstQuestion()),BorderLayout.CENTER);
        }
        this.revalidate();
        this.repaint();
        */
        
        String name = this.nameField.getText();
        String code = this.codeField.getText();
        
        this.socket.send(MessageParser.create("join", "code=" + code, "name=" + name));
    }//GEN-LAST:event_joinBtnActionPerformed

    private void joinBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinBtn1ActionPerformed
        String name = this.nameField.getText();
        String code = this.codeField.getText();
        
        if (name == null || name.isEmpty() || code == null || code.isEmpty()) {
            this.showError("Name and room code parameters are required");
        }
        
        this.socket.send(MessageParser.create("join", "code=" + code, "name=" + name));
    }//GEN-LAST:event_joinBtn1ActionPerformed

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        String name = this.nameField.getText();
        
        if (name == null || name.isEmpty()) {
            this.showError("Room code parameter is required");
        }
        
        this.socket.send(MessageParser.create("room", "name=" + name));
    }//GEN-LAST:event_newBtnActionPerformed

    /**
     *@return The instance of the frame
     */
    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        
        return instance;
    } 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codeField;
    private javax.swing.JLabel codeLabel;
    private javax.swing.JButton joinBtn;
    private javax.swing.JButton joinBtn1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton newBtn;
    private javax.swing.JLabel sTitleLabel;
    private javax.swing.JPanel startPane;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
