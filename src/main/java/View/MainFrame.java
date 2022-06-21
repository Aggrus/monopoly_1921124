package View;
import javax.swing.*;
import java.awt.*;

import Controller.Observer.Observer;

public class MainFrame extends JFrame implements Observer{
    public final int DFLT_WIDTH = 1100;
    public final int DFLT_HEIGHT = 700;
    private static int num_players = 0;

    private static MainFrame main_frame = null;

    private MainFrame() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screen_width = screenSize.width;
        int screen_height = screenSize.height;
        int x = screen_width/2 - DFLT_WIDTH/2;
        int y = screen_height/2 - DFLT_HEIGHT/2;
        setBounds(x, y, DFLT_WIDTH, DFLT_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.ScreenManager();
    }

    public static MainFrame getInstance() {
        if (main_frame == null) {
            main_frame = new MainFrame();
            main_frame.setTitle("Jogo Monopoly");
            main_frame.setVisible(true);
        }
        return main_frame;
    }

    //  Método que troca os painéis da exibidos na janela
    public void ScreenManager () {
        if (num_players == 0) {
            getContentPane().add(MenuScreen.getInstance(DFLT_WIDTH, DFLT_HEIGHT));
        }
        else {
            getContentPane().removeAll();
            getContentPane().add(GameScreen.getInstance(DFLT_WIDTH, DFLT_HEIGHT, num_players));
            getContentPane().revalidate();
            getContentPane().repaint();
        } 
    }

    public void notifyNumPlayers(Integer msg) {
        num_players = msg;
        System.out.printf("num_players = %d\n", num_players);
        ScreenManager();
    }
}

