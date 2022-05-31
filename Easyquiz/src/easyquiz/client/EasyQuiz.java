
package easyquiz.client;



import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;


import javax.swing.UIManager;

/**
 *
 * @author aland
 */
public class EasyQuiz {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(new FlatLightLaf());
//            
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
        UIManager.put("Button.select", new Color(69, 196, 134) );
        
        
        MainFrame frame = MainFrame.getInstance();
        frame.setLocationRelativeTo(null);
        
        frame.setVisible(true);
    }
    
}
