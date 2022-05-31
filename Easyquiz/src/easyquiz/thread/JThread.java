/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package easyquiz.thread;

import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class JThread {
    private static int currentId = 0;
    
    static {
        try {
            System.setProperty("java.library.path", Paths.get(System.getProperty("user.dir"), "/lib").toString());
            
            final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
            sysPathsField.setAccessible(true);
            sysPathsField.set(null, null);
            AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                System.loadLibrary("thread");
                return null;
            });
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(JThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(JThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(JThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int id;
    private final Runnable runnable;
    private boolean alive;
    private boolean running;
    private boolean once;
    
    private native int create();

    public JThread(Runnable runnable) {
        this.runnable = runnable;
        this.id = -1;
        this.alive = false;
        this.running = false;
        this.once = false;
    }
    
    public JThread(Runnable runnable, boolean runOnce) {
        this(runnable);
        this.once = runOnce;
    }

    public int getId() {
        return id;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isRunning() {
        return running;
    }
    
    public void start() {
        this.id = currentId;
        currentId += 1;
        this.alive = true;
        this.running = true;
        create();
        JThreadManager.getInstance().addThread(this);
    }
    
    public void run() {
        if (this.once) {
            this.runnable.run();
        } else {
            while (this.running) {
                this.runnable.run();
            }
        }
        
        this.alive = false;
        this.running = false;
        JThreadManager.getInstance().removeThread(this.id);
    }
    
    public void stop() {
        this.running = false;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JThread t1 = new JThread(() -> {
            System.out.println("Hello this is thread 1");
        });
        
        JThread t2 = new JThread(() -> {
            System.out.println("Hello this is thread 2");
        });
        
        t1.start();
        t2.start();
        
        long start = System.currentTimeMillis();
        
        while (System.currentTimeMillis() - start < 5000) {
            
        }
        
        t1.stop();
        
        while (System.currentTimeMillis() - start < 9000) {
            
        }
        
        t2.stop();
    }
}
