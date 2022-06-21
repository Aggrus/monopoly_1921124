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
import Controller.Observer.PlayerObserver;
import model.ApplyRules;

public class GameScreen
	extends JPanel
	implements MouseListener, PlayerObserver
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
			GameController.getInstance().createPlayers();
		}
		return game_screen;
	}

	private boolean didClickRollDice( final int x, final int y )
	{
		final boolean xBound = ( ( x >= 700 ) && ( x <= 1000 ) );
		final boolean yBound = ( ( y >= 30 ) && ( y <= 80 ) );
		return xBound && yBound;
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

	private void drawPlayers( final Graphics g )
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
		}
	}

	private void drawRollDiceButton( final Graphics g )
	{
		switch ( currentTurn )
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
				g.setColor( Color.ORANGE );
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
				g.setColor( Color.GRAY );
				break;
			}
		}

		g.drawRect( 700, 30, 300, 50 );
		g.fillRect( 700, 30, 300, 50 );
		g.setColor( Color.WHITE );
		g.setFont( g.getFont().deriveFont( g.getFont().getStyle(), 20 ) );
		g.drawChars( "Rolar dados".toCharArray(), 0, 11, 800, 60 );
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
		if ( didClickRollDice( x, y ) )
		{
			System.out.printf( "Clicou em rolar dados\n" );
			List<Integer> dice = ApplyRules.moveRollDice( currentTurn );
			System.out.printf("Dados: ");
			dice.stream().forEach(dado -> System.out.printf("%d, ",dado));
			cardId = ApplyRules.applyTileRuleToPlayerById( currentTurn );
			System.out.println( "\nmoney: " + playerMoney + "positions: " + playerPositions + "\n\n" );
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
		// TODO Auto-generated method stub
	}

	@Override
	public void notifyPrisionTime( final Integer prisionTime, final Integer playerId )
	{
		GameScreen.prisionTimes.set( playerId, prisionTime );
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

}
