package Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controller.Observer.GameObserver;
import Controller.Observer.Observer;
import View.MainFrame;
import model.Game;

public class SaveAndLoad {
    public static void loadGame(String file, Observer o)
    {
        Game.loadGame(file, o);
		if (o instanceof GameObserver)
		{
			if (!Game.getInstance().hasObserver(o) )
			{
				Game.getInstance().add(o);
			}
			else
			{
				Game.getInstance().update(o);
			}
		}
		if (Game.getInstance().hasObserver(MainFrame.getInstance()) )
		{
			Game.getInstance().add(MainFrame.getInstance());
		}
		else
		{
			Game.getInstance().update(MainFrame.getInstance());
		}
    }

    public static String saveGame() throws IOException {
		JFileChooser fc = new JFileChooser(".");
		fc.setFileFilter(new FileNameExtensionFilter("TXT Files (*.txt)", "txt"));
		
		if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			// cancelar save caso tenha clicado cancel ou X
			return "Salvamento cancelado!";
		}
		
		File file = fc.getSelectedFile();
		
		FileWriter writer = new FileWriter(file);
		return Game.saveGame(writer);
	}
}
