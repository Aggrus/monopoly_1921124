

import View.MainFrame;
import model.ApplyRules;
import model.Game;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Game.getInstance().createEmptyDeck();
		ApplyRules.shuffleDeck();
		ApplyRules.createBoard();
		MainFrame.getInstance();
    }
}
