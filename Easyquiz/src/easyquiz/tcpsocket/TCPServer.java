/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz.tcpsocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class TCPServer extends TCPSocket {
    private ArrayList<TCPSocket> clients;
    
    public TCPServer(int port) {
        super(port);
        this.clients = new ArrayList<>();
        TCPSocket.bind(this.handle, this.address.getPort());
        TCPSocket.listen(this.handle);
    }

    public ArrayList<TCPSocket> getClients() {
        return clients;
    }
    
    public TCPSocket accept() {
        TCPSocket client = TCPSocket.accept(handle);
        this.clients.add(client);
        
        return client;
    }
    
    public void close() throws IOException {
        for (TCPSocket client : this.clients) {
            try {
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        super.close();
    }
}
