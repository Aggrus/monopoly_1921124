

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
		System.out.println("(\t)(player )(["+1+"-6]+)( casa )(\\d+)(, money )(\\d+)(, cartaSair )(false|true)(, preso )(false|true)(, rodadasNaPrisao )(\\d+)(;)");
    	Game.getInstance().createEmptyDeck();
		ApplyRules.shuffleDeck();
		ApplyRules.createBoard();
		Game.getInstance().add(MainFrame.getInstance());
    }
}
