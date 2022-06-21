package View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controller.SaveAndLoad;

public class SaveGameView {
    public static void loadGame()
    {
        JFileChooser fc = new JFileChooser(".");
        fc.setFileFilter(new FileNameExtensionFilter("TXT Files (*.txt)", "txt"));

        if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            // sair caso tenha clicado cancel ou X
            System.exit(0);
        }
        
        if (fc.getSelectedFile().length() > 10000) {
            System.out.println("Erro: arquivo muito grande. Provavelmente não foi gerado pelo jogo.");
            System.exit(0);
        }
        
        Scanner sc = null;
        String fStr = null;
        try {
            sc = new Scanner(fc.getSelectedFile());
            while (sc.hasNextLine()) {
                fStr += sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println( "Erro: arquivo não encontrado.");
            System.exit(0);
        }
        SaveAndLoad.loadGame(fStr); 
    }
    public static void saveGame() throws IOException
    {
        SaveAndLoad.saveGame(); 
    }
}
