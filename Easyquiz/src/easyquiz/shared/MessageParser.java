/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easyquiz.shared;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 *
 * @author david
 */
public class MessageParser {
    public static byte[] create(String command, String... args) {
        StringBuilder builder = new StringBuilder("com=" + command);
        
        for (String arg : args) {
            builder.append("\n").append(arg);
        }
        
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }
    
    public static HashMap<String, String> parse(byte[] bytes) {
        HashMap<String, String> fields = new HashMap<>();
        String message = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("Received " + bytes.length + " bytes: " + message);
        
        for (String line : message.split("\n")) {
            String[] words = line.split("=");
            
            if (words.length == 2) {
                fields.put(words[0], words[1]);
            }
        }
        
        return fields;
    }
}
