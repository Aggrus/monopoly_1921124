package View;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;

import Controller.MenuController;

class MenuScreen extends JPanel implements MouseListener {
    private static int window_width;
    private static int window_height;
    private static boolean new_game = false;
    private Image background_img, box_img;
    private final Integer initPosX = 712;
    private final Integer initPosY = 170;
    private final Integer boxWidth = 220;
    private final Integer boxHeight = 50;
    private final Integer boxBufferHeight = 30;

    private static MenuScreen menu_screen = null;

    
    private MenuScreen (int w, int h) {
        MenuScreen.window_width = w;
        MenuScreen.window_height = h;
        addMouseListener(this);
    }
    
    public static MenuScreen getInstance(int w, int h) {
        if (menu_screen == null) {
            menu_screen = new MenuScreen(w, h);
        }
        return menu_screen;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Font header_font, gamemode_font;
        header_font = new Font("SansSerif", Font.BOLD, 25);
        gamemode_font = new Font("SansSerif", Font.BOLD, 18);
        
        import_images();
        draw_basic_screen(g2d);

        if (!new_game) {    // Mostra opções de início de jogo (novo/carregar)
            draw_startgame_options(g2d, header_font, gamemode_font);
        }
        else {    // Mostra opções de número de jogadores para novo jogo
            draw_numplayers_options(g2d, header_font, gamemode_font);
        }
    }

    // Importa as imagens que serão usadas no menu
    private void import_images() {   
        try {
            background_img = ImageIO.read(new File("src/main/java/data/background.png"));
            box_img = ImageIO.read(new File("src/main/java/data/box.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    //  Desenha as imagens do menu
    private void draw_basic_screen(Graphics g) {
            g.drawImage(background_img, 0, 0, null);
            g.drawImage(box_img, 37, (window_height - box_img.getHeight(null))/2, null);
    }

    // Desenha as opções de início de jogo na tela (novo jogo ou carregar jogo)
    private void draw_startgame_options (Graphics2D g2d, Font header_font, Font gamemode_font) {
        //Desenha o cabeçalho "Começar Jogo"
        draw_alligned_header(g2d, "Começar jogo:", header_font, Color.WHITE, 140);

        //Desenha a caixa da opção para iniciar novo jogo
        Rectangle2D rect_newgame = new Rectangle2D.Double(initPosX, initPosY, boxWidth, boxHeight);
        g2d.setPaint(Color.WHITE);
        g2d.fill(rect_newgame);
        draw_str_in_rect(g2d, "Novo Jogo", gamemode_font, Color.BLACK, rect_newgame);    

        //Desenha a caixa da opção para carregar jogo
        Rectangle2D rect3 = new Rectangle2D.Double(initPosX, initPosY + boxHeight + boxBufferHeight, boxWidth, boxHeight);
        g2d.setPaint(Color.WHITE);
        g2d.fill(rect3);
        draw_str_in_rect(g2d, "Carregar Jogo", gamemode_font, Color.BLACK, rect3); 
    }

    // Desenha as opções do número de jogadores que o usuário irá escolher
    private void draw_numplayers_options(Graphics2D g2d, Font header_font, Font gamemode_font) {
        //Desenha o cabeçalho "Novo Jogo"
        draw_alligned_header(g2d, "Novo Jogo:", header_font, Color.WHITE, 140);

        Rectangle2D rect_2players = new Rectangle2D.Double(initPosX, initPosY, boxWidth, boxHeight);
        g2d.setPaint(Color.WHITE);
        g2d.fill(rect_2players);
        draw_str_in_rect(g2d, "2 Jogadores", gamemode_font, Color.BLACK, rect_2players);    
        
        //Desenha a caixa da opção para 3 jogadores
        Rectangle2D rect_3players = new Rectangle2D.Double(initPosX, initPosY + boxHeight + boxBufferHeight, boxWidth, boxHeight);
        g2d.setPaint(Color.WHITE);
        g2d.fill(rect_3players);
        draw_str_in_rect(g2d, "3 Jogadores", gamemode_font, Color.BLACK, rect_3players);    

        //Desenha a caixa da opção para 4 jogadores
        Rectangle2D rect_4players = new Rectangle2D.Double(initPosX, initPosY + 2*(boxHeight + boxBufferHeight), boxWidth, boxHeight);
        g2d.setPaint(Color.WHITE);
        g2d.fill(rect_4players);
        draw_str_in_rect(g2d, "4 Jogadores", gamemode_font, Color.BLACK, rect_4players);    

        //Desenha a caixa da opção para 5 jogadores
        Rectangle2D rect_5players = new Rectangle2D.Double(initPosX, initPosY + 3*(boxHeight + boxBufferHeight), boxWidth, boxHeight);
        g2d.setPaint(Color.WHITE);
        g2d.fill(rect_5players);
        draw_str_in_rect(g2d, "5 Jogadores", gamemode_font, Color.BLACK, rect_5players);    

        //Desenha a caixa da opção para 6 jogadores
        Rectangle2D rect_6players = new Rectangle2D.Double(initPosX, initPosY + 4*(boxHeight + boxBufferHeight), boxWidth, boxHeight);
        g2d.setPaint(Color.WHITE);
        g2d.fill(rect_6players);
        draw_str_in_rect(g2d, "6 Jogadores", gamemode_font, Color.BLACK, rect_6players);    
    }

    // Desenha os cabeçalhos das opções do menu (Começar Jogo/Novo Jogo) de forma alinhada na tela
    private void draw_alligned_header (Graphics g, String str, Font font, Color cor, int height) {
        FontMetrics metrics = g.getFontMetrics(font);
        g.setFont(font);
        g.setColor(cor);
        g.drawString(str, 37 + 3*(window_width - metrics.stringWidth(str))/4, height); 
    }

    // Desenha uma string dada no centro de um retângulo dado, de forma a desenhar as caixas de opções exibidas no menu
    private void draw_str_in_rect (Graphics g, String str, Font font, Color cor, Rectangle2D rect) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x, y;
        x = (int)rect.getX() + ((int)rect.getWidth() - metrics.stringWidth(str))/2;
        y = (int)rect.getY() + (((int)rect.getHeight() - metrics.getHeight())/2) + metrics.getAscent();
        g.setFont(font);
        g.setColor(cor);
        g.drawString(str, x, y); 
    }

    // Tratador de eventos do Mouse
    public void mousePressed(MouseEvent e) {
        int x = e.getX(), y = e.getY();

        System.out.printf("x = %d\ny = %d\n", x, y);
        
        if (new_game == true) { 
            if (x >= initPosX && x <= initPosX + boxWidth) {
                if (y >= initPosY && y <= initPosY + boxHeight) {    // Clique na caixa de opção de 2 jogadores
                    MenuController.getInstance().set_num_players(2);
                }
                else if (y >= initPosY + boxHeight + boxBufferHeight && y <= initPosY + 2*boxHeight + boxBufferHeight) {    // Clique na caixa de opção de 3 jogadores
                    MenuController.getInstance().set_num_players(3);
                }
                else if (y >= initPosY + 2*(boxHeight + boxBufferHeight) && y <= initPosY + 3*boxHeight + 2*boxBufferHeight) {    // Clique na caixa de opção de 4 jogadores
                    MenuController.getInstance().set_num_players(4);
                }
                else if (y >= initPosY + 3*(boxHeight + boxBufferHeight) && y <= initPosY + 4*boxHeight + 3*boxBufferHeight + boxHeight) {    // Clique na caixa de opção de 5 jogadores
                    MenuController.getInstance().set_num_players(5);
                }
                else if (y >= initPosY + 4*(boxHeight + boxBufferHeight) && y <= initPosY + 5*boxHeight + 4*boxBufferHeight + boxHeight) {    // Clique na caixa de opção de 6 jogadores
                    MenuController.getInstance().set_num_players(6);
                }
            }
        }
        else {
            if (x >= initPosX && x <= initPosX + boxWidth) {    // Clique na caixa de opção de iniciar novo jogo
                if (y >= initPosY && y <= initPosY + boxHeight) {
                    new_game = true;
                    repaint();
                }
                // else if (y >= 250 && y <= 250 + boxHeight) {    // Clique na caixa de opção de carregar jogo
                   // futuras operações para carregamento de jogo
                // }
            }    
        }       
    }

    public void mouseEntered(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}    
}

