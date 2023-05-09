/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz.client;

import easyquiz.shared.Timer;
import easyquiz.thread.JThread;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author david
 */
public class CountdownThread extends Timer {
    private JLabel label;
    
    /**
     * Creates a thread handling a countdown
     * 
     * @param deadline  The time limit
     * @param label  The label showing the countdown
     * @param callback  The procedure executed on timeout 
     */
    public CountdownThread(long deadline, JLabel label, Runnable callback) {
        super(deadline - System.currentTimeMillis(), callback);
        this.label = label;
        
        this.tick = () -> {
            if (this.label != null ) {
                this.label.setText(Long.toString(this.getRemainingTime() / 1000));
            }
        };
    }
}
