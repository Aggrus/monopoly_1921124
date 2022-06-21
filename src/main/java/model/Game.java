package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Controller.Observer.Observable;
import Controller.Observer.Observer;
import enums.BuildingEnum;
import enums.PlayerColorEnum;

/**
 * <p>
 * </p>
 *
 * @author gugaz, arthurxvtv
 * @version 1.0 Created on May 8, 2022
 */
public class Game
	implements Observable
{

	private static Integer turn;

	private static List<AbstractCard> cards;

	private static Game game = null;

	private static int numPlayers;

	private static List<Player> playerList;

	private static List<AbstractTile> tiles;

	private static AbstractCard drawnCard;

	public static AbstractCard getDrawnCard() {
		return drawnCard;
	}

	public static Integer getTurn() {
		return turn;
	}

	public static void setTurn(Integer turn) {
		Game.turn = turn;
	}

	private Game()
	{
		createEmptyDeck();
		setPlayerList( new ArrayList<Player>(6) );
		setTiles( new ArrayList<AbstractTile>(40) );
	}

	public List<AbstractCard> createEmptyDeck()
	{
		cards = new ArrayList<AbstractCard>();
		return cards;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the cards.
	 * @see #cards
	 */
	public static List<AbstractCard> getCards()
	{
		return cards;
	}

	public static Game getInstance()
	{
		if ( game == null )
		{
			game = new Game();
		}
		return game;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the numPlayers.
	 * @see #numPlayers
	 */
	public static Integer getNumPlayers()
	{
		return numPlayers;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the playerList.
	 * @see #playerList
	 */
	public static List<Player> getPlayerList()
	{
		return playerList;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the board.
	 * @see #tiles
	 */
	public static List<AbstractTile> getTiles()
	{
		return tiles;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param cards
	 *            The cards to set.
	 * @see #cards
	 */
	public static void setCards( final List<AbstractCard> cards_list )
	{
		cards = cards_list;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param numPlayers
	 *            The numPlayers to set.
	 * @see #numPlayers
	 */
	public static void setNumPlayers( final int num_players )
	{
		numPlayers = num_players;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param playerList
	 *            The playerList to set.
	 * @see #playerList
	 */
	public static void setPlayerList( final List<Player> player_list )
	{
		playerList = player_list;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param board
	 *            The board to set.
	 * @see #tiles
	 */
	public static void setTiles( final List<AbstractTile> board )
	{
		tiles = board;
	}

	public static void setDrawnCard()
	{

		drawnCard = DrawCardTile.getDrawnCard();
	}

	@Override
	public void add( final Observer o )
	{
		this.observadores.add( o );
		o.notifyNumPlayers( getNumPlayers() );
	}

	// implementações do design patter Observer

	@Override
	public void remove( final Observer o )
	{
		this.observadores.remove( o );
	}

	private List<Observer> observadores = new ArrayList<Observer>();

	@Override
	public void update(Observer o) 
	{
		o.notifyNumPlayers( getNumPlayers() );
		
	}

	public static void loadGame(String file)
	{
		try {
            Pattern pattern;
            Matcher matcher;
            
            // recolocando número de jogadores
            pattern = Pattern.compile("(numplayers: )(\\d+)(;)");
            matcher = pattern.matcher(file);
            matcher.find();
            Game.getInstance();
			Game.setNumPlayers(Integer.parseInt(matcher.group(2))); 
            
            // recriando jogadores e propriedades com dono
            List<Player> players = new ArrayList<Player>(6);      
            for (int i = 0; i < numPlayers; i++) {
                pattern = Pattern.compile("(\t)(player )(\\d+)( casa )(\\d+)(, money )(\\d+)(, cartaSair )(false|true)(, preso )(false|true)(, rodadasNaPrisao )(\\d+)(;)(\t\t)(propriedades: )(\\[(\\d+, )*\\d*?\\]);");
                matcher = pattern.matcher(file);
                matcher.find();
				int cor  = Integer.parseInt(matcher.group(3));
                int boardPos = Integer.parseInt(matcher.group(5));
                Long money = Long.parseLong(matcher.group(7));
                boolean cartaSair = Boolean.parseBoolean(matcher.group(9));
                boolean preso = Boolean.parseBoolean(matcher.group(11));
                Integer tempoPreso = Integer.parseInt(matcher.group(13));
                players.add(new Player(PlayerColorEnum.values()[cor], money, boardPos, preso, tempoPreso, cartaSair));
            }
            Game.setPlayerList(players);
            // recuperar de quem era a vez
            pattern = Pattern.compile("(vez: )(\\d+)(;)");
            matcher = pattern.matcher(file);
            matcher.find();
            Game.setTurn(Integer.parseInt(matcher.group(2)));         
            // recuperar as cartas de sorte reves
			ApplyRules.shuffleDeck();
            pattern = Pattern.compile("(cartasSortes: )(\\[(\\d+, )*\\d*?\\]);");
            matcher = pattern.matcher(file);
            matcher.find();
            String[] arrcartas = matcher.group(2).split("(, )|\\[|(\\])");
			List<String> listCards = Arrays.asList(arrcartas);
            if (arrcartas.length > 0) {
				cards.removeIf(card -> 	!listCards.contains(card.getId().toString()));
            }
        } catch (IllegalStateException e) {
            System.out.println("Erro: arquivo em formato inválido.");
            System.exit(0);
        }      
        // recriando casas e hoteis
		ApplyRules.createBoard();
        for (int i = 0; i < tiles.size(); i++) {
            // empresas
            Pattern pattern = Pattern.compile("(\t)(((empresa) " + i + ": dono (\\d+), comprada (false|true);)|((terreno) " + i + ": casa (\\d+), hotel (\\d+), comprada (false|true), dono (\\d+), aluguel (\\d+));))");
            Matcher matcher = pattern.matcher(file);
            matcher.find();
            if (matcher.group(6) != null && matcher.group(6).contentEquals("terreno"))
			{
				if (tiles.get(i) instanceof Property)
				{
					Property t = (Property)tiles.get(i);

                	int casa = Integer.parseInt(matcher.group(11));
                	int hotel = Integer.parseInt(matcher.group(13));
					boolean temHotel = hotel == 1;
					Boolean vendida = Boolean.parseBoolean(matcher.group(15));
					Player dono = Game.getPlayerList().stream().filter(player -> player.getColor().equals(PlayerColorEnum.values()[Integer.parseInt(matcher.group(17))])).findFirst().get();
					Long aluguel = Long.parseLong(matcher.group(19));
                	List<Building> buildings = new ArrayList<Building>(5);
					for (int j = 0; j < casa - 1; j++)
					{
						buildings.add(new Building(i, BuildingEnum.HOUSE, dono, t.getBuildingValue(), t));
					}
					if (temHotel)
					{
						buildings.add(new Building(i, BuildingEnum.HOTEL, dono, t.getBuildingValue(), t));
					}
					t.setBuildings(buildings);
					t.setCanPurchase(vendida);
					t.setOwner(dono);
					t.setRent(aluguel);
				}
				else if (tiles.get(i) instanceof CompanyTile)
				{
					CompanyTile company = (CompanyTile)tiles.get(i);
					//Posso ter errado e ser 5 abaixo
					Player dono = Game.getPlayerList().stream().filter(player -> player.getColor().equals(PlayerColorEnum.values()[Integer.parseInt(matcher.group(4))])).findFirst().get();
					Boolean vendida = Boolean.parseBoolean(matcher.group(6));
					company.setOwner(dono);
					company.setCanPurchase(vendida);
				}
			}
        }
	}
	public static String saveGame(FileWriter file) throws IOException
	{
		file.append("numplayers: " + Game.getNumPlayers() + ";\n");
		for (Player player :  getPlayerList()) {
			file.append("\tplayer " + player.getColor().getIndex() + " ");
			file.append(player.genSaveString());
			file.append(";\n");
		}
		file.append("vez: " + turn + ";\n");

		file.append("cartasSortes: "); 
		file.append(cards.get(0).getId().toString());
		for (AbstractCard card : cards.subList(1, cards.size() -1))
		{
			file.append(", " + card.getId());
		}
		file.append( ";\n");
		file.append("propriedades: " + tiles.size() + ";\n");
		for (AbstractTile tile : getTiles()) {
			if (tile instanceof Property) {
				file.append("\tterreno " + tile.getBoardPosition() + ": ");
				Property property = (Property) tile;
				file.append(property.genSaveString());
				file.append(";\n");
			} 
			else if (tile instanceof CompanyTile){
				file.append("\tempresa " + tile.getBoardPosition() + ": ");
				CompanyTile company = (CompanyTile) tile;
				file.append(company.genSaveString());
				file.append(";\n");
			}
		}
		
		file.close();
		return null;
	}

}
