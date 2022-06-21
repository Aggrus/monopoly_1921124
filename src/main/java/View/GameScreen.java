package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Controller.GameController;
import Controller.Observer.GameObserver;
import Controller.Observer.PlayerObserver;

public class GameScreen
	extends JPanel
	implements MouseListener, PlayerObserver, GameObserver
{

	private static Integer currentTurn = 0;

	private static GameScreen game_screen = null;

	private static int num_players;

	private static List<Long> playerMoney;

	private static List<Integer> playerPositions;

	private static List<Integer> prisionTimes;

	private static int window_height;

	private static int window_width;

	private GameScreen( final int w, final int h, final int numPLayers )
	{
		GameScreen.window_width = w;
		GameScreen.window_height = h;
		GameScreen.num_players = numPLayers;
		GameScreen.playerMoney = new ArrayList<Long>( 6 );
		GameScreen.prisionTimes = new ArrayList<Integer>( 6 );
		GameScreen.playerPositions = new ArrayList<Integer>( 6 );
		this.dice = new ArrayList<Integer>();
		this.hasRolled = false;
		GameScreen.hasBought = false;
		this.message = "";
		while ( GameScreen.playerPositions.size() < num_players )
		{
			GameScreen.playerPositions.add( 0 );
			GameScreen.playerMoney.add( ( long ) 0 );
			GameScreen.prisionTimes.add( 0 );
		}
		addMouseListener( this );
	}

	public static GameScreen getInstance( final int w, final int h, final int numPLayers )
	{
		if ( game_screen == null )
		{
			game_screen = new GameScreen( w, h, numPLayers );
			GameController.getInstance().createPlayers(GameScreen.getInstance(MainFrame.WIDTH, MainFrame.HEIGHT, num_players));
		}
		return game_screen;
	}

	private boolean didClickRollDice( final int x, final int y )
	{
		final boolean xBound = ( ( x >= 700 ) && ( x <= 1000 ) );
		final boolean yBound = ( ( y >= 30 ) && ( y <= 80 ) );
		return xBound && yBound && !hasRolled;
	}

	private boolean didClickNextTurn( final int x, final int y )
	{
		final boolean xBound = ( ( x >= 700 ) && ( x <= 1000 ) );
		final boolean yBound = ( ( y >= 500 ) && ( y <= 550 ) );
		return xBound && yBound && hasRolled;
	}

	// Desenha o Tabuleiro e o Background na tela
	private void draw_basic_board( final Graphics g )
	{
		g.drawImage( this.background_img, 0, 0, null );
		g
			.drawImage(
				this.board_img,
				17,
				( window_height - ( ( 90 * this.board_img.getHeight( null ) ) / 100 ) ) / 4,
				( 90 * this.board_img.getWidth( null ) ) / 100,
				( 90 * this.board_img.getWidth( null ) ) / 100,
				null );
	}

	public void drawPlayers( final Graphics g )
	{
		Integer index = 0;
		for ( final Image player : this.player_imgs )
		{
			final List<Integer> playerPos = PlayerView.getPlayerPos( index, playerPositions.get( index ) );
			g.drawImage( player, playerPos.get( 0 ), playerPos.get( 1 ), playerPos.get( 2 ), playerPos.get( 3 ), null );
			index++;
		}
	}

	private void drawCards( final Graphics g )
	{
		if (cardId != null)
		{
			g.drawImage( card_imgs[cardId], 250, 250, 150, 210, null );
			cardId = null;
		}
	}

	public void displayPlayersMoney(List<Long> playerMoney, final Graphics g)
	{
		int index = 0;
		g.setColor( Color.LIGHT_GRAY );
		g.fillRect( 700, (200 +  30*index), 300, 30*playerMoney.size() + 30);
		for (Long money : playerMoney)
		{
			setColorByTurn(g, index);
			g.setFont( g.getFont().deriveFont( g.getFont().getStyle(), 20 ) );
			String playerTag = String.format("Jogador %d: ",index+1);
			g.drawChars( playerTag.toCharArray(), 0, playerTag.length(), 750, (230 +  30*index));
			g.setColor( Color.BLACK );
			String moneyString = "R$ " + money.toString() + ",00";
			g.drawChars( moneyString.toCharArray(), 0, moneyString.length(), 870, (230 +  30*index));
			index++;
		}
		System.out.printf("money: %d\n", playerMoney.get(currentTurn));
	}

	private void setColorByTurn(Graphics g, Integer turn)
	{
		switch ( GameController.getInstance().getColorIndexByPlayerTurn(turn))
		{
			case 0 :
			{
				g.setColor( Color.RED );
				break;
			}
			case 1 :
			{
				g.setColor( Color.BLUE );
				break;
			}
			case 2 :
			{
				g.setColor( Color.getHSBColor((float)0.084, (float)1.0, (float)1.0) );
				break;
			}
			case 3 :
			{
				g.setColor( Color.YELLOW );
				break;
			}
			case 4 :
			{
				g.setColor( Color.MAGENTA );
				break;
			}
			case 5 :
			{
				g.setColor( Color.DARK_GRAY );
				break;
			}
		}
	}

	private boolean didClickBuyTileButton(final int x, final int y)
	{
		final boolean xBound = ( ( x >= 700 ) && ( x <= 1000 ) );
		final boolean yBound = ( ( y >= 30 ) && ( y <= 80 ) );
		return xBound && yBound && hasRolled && !hasBought;
	}

	private void drawBuyTileButton( final Graphics g )
	{	
		if (hasRolled && GameController.getInstance().canBuyTile(playerPositions.get(currentTurn)) && !hasBought)
		{
			message = "Comprar terreno!";
			g.setColor( Color.WHITE );
			g.drawRect( 700, 30, 300, 50 );
			g.fillRect( 700, 30, 300, 50 );
			setColorByTurn( g, currentTurn );
			g.setFont( g.getFont().deriveFont( g.getFont().getStyle(), 20 ) );
			g.drawChars( message.toCharArray(), 0, message.length(), 700 + (message.length()*20)/4, 60 );
			message = "";
		}
	}

	private boolean didClickBuyHouseButton(final int x, final int y)
	{
		final boolean xBound = ( ( x >= 650 ) && ( x <= (("Comprar casa!".length() + 1)*20)) );
		final boolean yBound = ( ( y >= 30 ) && ( y <= 80 ) );
		return xBound && yBound && hasRolled && !hasBought;
	}

	private boolean didClickBuyHotelButton(final int x, final int y)
	{
		int firstButtonLength = 650 + (("Comprar casa!".length() + 1)*20);	
		final boolean xBound = ( ( x >= firstButtonLength ) && ( x <= (firstButtonLength + (("Comprar hotel!".length() + 1)*20))) );
		final boolean yBound = ( ( y >= 30 ) && ( y <= 80 ) );
		return xBound && yBound && hasRolled && !hasBought;
	}

	private void drawBuyHouseButton( final Graphics g )
	{	
		if (hasRolled && GameController.getInstance().canBuyHouse(currentTurn, playerPositions.get(currentTurn)) && !hasBought)
		{
			message = "Comprar casa!";	
			Integer fontSize = 20;
			g.setColor( Color.BLACK );
			int widthBox = (message.length()) * fontSize;
			g.drawRect( 650, 30, widthBox, 50 );
			g.setColor( Color.WHITE );
			g.fillRect( 650, 30, widthBox, 50 );
			setColorByTurn( g, currentTurn );
			g.setFont( g.getFont().deriveFont( g.getFont().getStyle(), fontSize ) );		
			g.drawChars( message.toCharArray(), 0, message.length(), 650 + widthBox/4, 60 );
			message = "";
		}
	}

	private void drawBuyHotelButton( final Graphics g )
	{	
		if (hasRolled && GameController.getInstance().canBuyHotel(currentTurn, playerPositions.get(currentTurn)) && !hasBought)
		{
			Integer fontSize = 20;
			Integer width = (message.length() ) * fontSize;
			message = "Comprar hotel!";
			Integer newWidth = (message.length() ) * fontSize;
			g.setColor( Color.BLACK );
			g.drawRect( 650 + width, 30, newWidth, 50 );
			g.setColor( Color.WHITE );
			g.fillRect( 650 + width, 30, newWidth, 50 );
			setColorByTurn( g, currentTurn );
			g.setFont( g.getFont().deriveFont( g.getFont().getStyle(), fontSize ) );		
			g.drawChars( message.toCharArray(), 0, message.length(), 650 + width + newWidth/2, 60 );
			message = "";
		}
	}

	private void drawMessage( final Graphics g )
	{
		if (hasBought)
		{
			g.setColor( Color.WHITE );
			g.drawChars( message.toCharArray(), 0, message.length(), 800, 60 );
		}
	}

	private void drawRollDiceButton( final Graphics g )
	{	
		if (!hasRolled)
		{
			setColorByTurn( g, currentTurn );
			g.drawRect( 700, 30, 300, 50 );
			g.fillRect( 700, 30, 300, 50 );
			g.setColor( Color.WHITE );
			g.setFont( g.getFont().deriveFont( g.getFont().getStyle(), 20 ) );
			g.drawChars( "Rolar dados".toCharArray(), 0, 11, 800, 60 );
		}
	}

	private void drawNextTurn( final Graphics g )
	{	
		if (hasRolled)
		{
			setColorByTurn( g, currentTurn );
			g.drawRect( 700, 500, 300, 50 );
			g.fillRect( 700, 500, 300, 50 );
			g.setColor( Color.WHITE );
			g.setFont( g.getFont().deriveFont( g.getFont().getStyle(), 20 ) );
			g.drawChars( "Encerrar Turno".toCharArray(), 0, 14, 780, 530 );
		}
	}

	private void drawDice( final Graphics g )
	{
		if (hasRolled)
		{
			int index = 0;
			for (Integer result : dice)
			{
				g.drawImage( dice_imgs[result - 1], 700 + 50*index, 120, 50, 50, null );
				index++;
			}
		}
	}

	// Importa as imagens que serão utilizadas ao longo do jogo
	private void import_images()
	{
		int i;
		try
		{
			// Importa a imagem do background
			this.background_img = ImageIO.read( new File( "src/main/java/data/background.png" ) );
			// Importa a imagem do tabuleiro
			this.board_img = ImageIO.read( new File( "src/main/java/data/tabuleiro.png" ) );
			// Importa as imagens dos dados
			for ( i = 0; i < 6; i++ )
			{
				this.dice_imgs[i] = ImageIO.read( new File( "src/main/java/data/dados/die_face_" + ( i + 1 ) + ".png" ) );
			}
			// Importa as imagens das cartas
			for ( i = 0; i < 30; i++ )
			{
				this.card_imgs[i] = ImageIO.read( new File( "src/main/java/data/sorteReves/chance" + ( i + 1 ) + ".png" ) );
			}
			// Importa as imagens das propriedades
			for ( i = 0; i < 22; i++ )
			{
				this.property_imgs[i] = ImageIO
					.read( new File( "src/main/java/data/territorios/territory" + ( i + 1 ) + ".png" ) );
			}
			// Importa as imagens das companhias
			for ( i = 0; i < 6; i++ )
			{
				this.company_imgs[i] = ImageIO
					.read( new File( "src/main/java/data/companhias/company" + ( i + 1 ) + ".png" ) );
			}
			// Importa as imagens dos jogadores
			this.player_imgs = new Image[num_players];
			for ( i = 0; i < num_players; i++ )
			{
				this.player_imgs[i] = ImageIO.read( new File( "src/main/java/data/pinos/pin" + i + ".png" ) );
			}
		}
		catch ( final IOException e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}

	@Override
	public void mouseClicked( final MouseEvent e )
	{
	}

	@Override
	public void mouseEntered( final MouseEvent e )
	{
	}

	@Override
	public void mouseExited( final MouseEvent e )
	{
	}

	// Tratador de Eventos do Mouse
	@Override
	public void mousePressed( final MouseEvent e )
	{
		final int x = e.getX(), y = e.getY();

		System.out.printf( "x = %d\ny = %d\n", x, y );
		if (didClickBuyTileButton(x, y))
		{
			message = GameController.getInstance().buyTile(currentTurn, GameScreen.getInstance(MainFrame.WIDTH, MainFrame.HEIGHT, num_players));
			repaint();
		}
		else if (didClickBuyHouseButton(x, y))
		{
			message = GameController.getInstance().buyHouse(currentTurn, playerPositions.get(currentTurn), GameScreen.getInstance(MainFrame.WIDTH, MainFrame.HEIGHT, num_players));
			repaint();
		}
		else if (didClickBuyHotelButton(x, y))
		{
			message = GameController.getInstance().buyHotel(currentTurn, playerPositions.get(currentTurn), GameScreen.getInstance(MainFrame.WIDTH, MainFrame.HEIGHT, num_players));
			repaint();
		}
		else if ( didClickRollDice( x, y ) )
		{
			List<Integer> result = GameController.getInstance().moveAction(currentTurn, GameScreen.getInstance(MainFrame.WIDTH, MainFrame.HEIGHT, num_players));
			cardId = result.get(0);
			dice = result.subList(1, result.size());
			hasRolled = !hasRolled;
			repaint();
		}
		else if (didClickNextTurn( x, y ))
		{
			GameController.getInstance().nextTurn(GameScreen.getInstance(MainFrame.WIDTH, MainFrame.HEIGHT, num_players));
			hasRolled = !hasRolled;
			GameController.getInstance().resetHasBought(GameScreen.getInstance(MainFrame.WIDTH, MainFrame.HEIGHT, num_players));
			repaint();
		}
	}

	@Override
	public void mouseReleased( final MouseEvent e )
	{
	}

	@Override
	public void nofityBoardPosition( final Integer boardPosition, final Integer playerId )
	{
		GameScreen.playerPositions.set( playerId, boardPosition );
	}

	// public void notifyFreeRide(boolean freeRide, Integer playerId);
	@Override
	public void notifyMoney( final Long money, final Integer playerId )
	{
		GameScreen.playerMoney.set( playerId, money );
	}

	@Override
	public void notifyNumPlayers( final Integer numPlayers )
	{
		GameScreen.num_players = numPlayers;
	}

	@Override
	public void notifyTurn(Integer turn) {
		GameScreen.currentTurn = turn;		
	}

	@Override
	public void notifyPrisionTime( final Integer prisionTime, final Integer playerId )
	{
		GameScreen.prisionTimes.set( playerId, prisionTime );
	}

	@Override
	public void notifyHasBought(boolean hasBought) {
		GameScreen.hasBought = hasBought;
		
	}

	@Override
	public void paintComponent( final Graphics g )
	{
		super.paintComponent( g );
		final Graphics2D g2d = ( Graphics2D ) g;

		import_images();
		draw_basic_board( g2d );
		drawPlayers( g2d );
		drawCards( g2d );
		drawRollDiceButton( g2d );
		drawDice( g2d );
		drawNextTurn( g2d );
		displayPlayersMoney(playerMoney, g2d);
		drawBuyTileButton(g2d);
		drawBuyHouseButton(g2d);
		drawBuyHotelButton(g2d);
		drawMessage(g2d);

		this.graphics = g.create();
	}

	private Image background_img, board_img;

	private Image[] card_imgs = new Image[30];

	private Image[] company_imgs = new Image[6];

	private Image[] dice_imgs = new Image[6];

	Graphics graphics;

	private Image[] player_imgs;

	private Integer cardId;

	private Image[] property_imgs = new Image[22];

	private List<Integer> dice;

	private boolean hasRolled;

	private static boolean hasBought;

	private String message;
}
