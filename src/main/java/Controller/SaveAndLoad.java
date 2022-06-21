package Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Game;

public class SaveAndLoad {
    public static void loadGame(String file)
    {
        Game.loadGame(file);
    }

    public static void saveGame() throws IOException {
		JFileChooser fc = new JFileChooser(".");
		fc.setFileFilter(new FileNameExtensionFilter("TXT Files (*.txt)", "txt"));
		
		if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			// cancelar save caso tenha clicado cancel ou X
			return;
		}
		
		File file = fc.getSelectedFile();
		
		FileWriter writer = new FileWriter(file);
		Game.saveGame(writer);
		
		
        System.out.println("Jogo foi salvo!");
	}
}
