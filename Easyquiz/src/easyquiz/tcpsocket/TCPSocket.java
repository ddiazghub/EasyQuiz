/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package easyquiz.tcpsocket;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import easyquiz.thread.JThread;
import easyquiz.thread.JThreadManager;

/**
 *
 * @author david
 */
public class TCPSocket implements Closeable {
    public static final int BUFFER_LENGTH = 1024;
    
    static {
        try {
            System.setProperty("java.library.path", Paths.get(System.getProperty("user.dir"), "/lib").toString());
            
            final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
            sysPathsField.setAccessible(true);
            sysPathsField.set(null, null);
            AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                System.loadLibrary("socket");
                return null;
            });
            
            winsockInit();
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(TCPSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(TCPSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(TCPSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TCPSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static native int winsockInit();
    protected static native int socket();
    protected static native int connect(int handle, String ip, int port);
    protected static native int bind(int handle, int port);
    protected static native int listen(int handle);
    protected static native TCPSocket accept(int handle);
    protected static native int send(int handle, byte[] data);
    protected static native int receive(int handle, byte[] buffer);
    protected static native int close(int handle);
    
    protected final byte[] buffer = new byte[BUFFER_LENGTH];
    protected int handle;
    protected SocketAddress address;
    
    public TCPSocket(int port) {
        this(new SocketAddress("127.0.0.1", port), false);
    }
    
    public TCPSocket(SocketAddress address) {
        this(address, true);
    }
    
    public TCPSocket(int handle, String ip, int port) {
        this.handle = handle;
        this.address = new SocketAddress(ip, port);
    }
    
    public TCPSocket(SocketAddress address, boolean connect) {
        this.handle = socket();
        this.address = address;
        
        if (connect) {
            connect(this.handle, address.getIp(), address.getPort());
        }
    }
    
    public int getHandle() {
        return handle;
    }
    
    public SocketAddress getAddress() {
        return address;
    }
    
    public void send(byte[] data) {
        System.out.println("Sent: " + new String(data, StandardCharsets.UTF_8));
        ByteBuffer buffer = ByteBuffer.allocate(data.length + 1);
        buffer.put(data).put((byte) 0);
        send(this.handle, buffer.array());
    }
    
    public byte[] receive() throws IOException {
        int received = receive(this.handle, this.buffer);
        
        if (received > 0) {
            return Arrays.copyOf(this.buffer, received - 1);
        } else {
            throw new IOException("Connection ended");
        }
    }
    
    public String toString() {
        return "Socket [IP: " + this.getAddress().getIp() + ", PORT: " + this.getAddress().getPort() + "]";
    }
    
    @Override
    public void close() throws IOException {
        if (close(this.handle) != 0) {
            throw new IOException("Failed to close Socket");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*new JThread(() -> {
            TCPServer server = new TCPServer(3000);
            System.out.println("Server started successfully");
            TCPSocket client = server.accept();
        
            while (true) {
                byte[] buf = client.receive();

                if (buf.length > 0) {
                    String message = new String(buf, StandardCharsets.UTF_8);

                    if (message.equals("end"))
                        break;

                    System.out.println("Received from client: " + message);
                }
            }

            try {
                server.close();
            } catch (IOException ex) {
                Logger.getLogger(TCPSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        
        TCPSocket socket = new TCPSocket(new SocketAddress("127.0.0.1", 3000));
        System.out.println("Successfully connected to client");
        
        Scanner in = new Scanner(System.in);
        
        while (true) {
            JThreadManager.getInstance().getThreads().size();
            String message = in.nextLine();
            socket.send(message.getBytes(StandardCharsets.UTF_8));
        }
*/
    }
}
