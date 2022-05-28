
package easyquiz;

import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;

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
        try {
            UIManager.setLookAndFeel(new FlatCyanLightIJTheme());
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
        MainFrame frame = new MainFrame();
        frame.setLocationRelativeTo(null);
        
        frame.setVisible(true);
    }
    
}
