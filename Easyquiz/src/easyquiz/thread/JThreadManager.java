/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author david
 */
public class JThreadManager {
    private static JThreadManager instance;
    
    private final Map<Integer, JThread> threads;
    
    private JThreadManager() {
        this.threads = new HashMap<>();
    }
    
    public void addThread(JThread thread) {
        this.threads.put(thread.getId(), thread);
    }
    
    public void removeThread(int id) {
        this.threads.remove(id);
    }
    
    public ArrayList<JThread> getThreads() {
        ArrayList<JThread> threadList = new ArrayList<>();
        
        for (JThread thread : threads.values()) {
            threadList.add(thread);
        }
        
        return threadList;
    }
    
    public static JThreadManager getInstance() {
        if (instance == null) {
            instance = new JThreadManager();
        }
        
        return instance;
    }
}
