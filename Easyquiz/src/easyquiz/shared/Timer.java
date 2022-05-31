/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz.shared;

import easyquiz.client.CountdownPanel;
import easyquiz.thread.JThread;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author david
 */
public class Timer {
    protected long deadline;
    protected JThread thread;
    protected Runnable callback;
    protected Runnable tick;
    
    public Timer(long duration, Runnable callback) {
        this(duration, callback, null);
    }
    
    public Timer(long duration, Runnable callback, Runnable tick) {
        this.deadline = System.currentTimeMillis() + duration;
        this.callback = callback;
        this.tick = tick;
        
        this.thread =  new JThread(() -> {
            long remaining = this.getRemainingTime();
            
            if (remaining > 0) {
                if (this.tick != null ) {
                    this.tick.run();
                }
            } else {
                this.thread.stop();
                this.callback.run();
                
                return;
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(CountdownPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public long getRemainingTime() {
        return this.deadline - System.currentTimeMillis();
    }
    
    public void start() {
        this.thread.start();
    }
}
