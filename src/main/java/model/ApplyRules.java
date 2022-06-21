package model;

import java.util.ArrayList;
import java.util.List;

import Controller.Observer.Observer;
import Exception.IllegalRuleException;
import enums.BuildingEnum;
import enums.PlayerColorEnum;
import enums.TileColorEnum;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 22, 2022
 */
public class ApplyRules
{

	public static Integer applyTileRuleToPlayerById( final Integer playerId )
	{
		final Player player = Game.getPlayerList().get( playerId );
		final AbstractTile playerTile = Game.getTiles().get( player.getBoardPosition() );
		switch ( player.getBoardPosition() )
		{
			case 0 :
			{
				( ( StartTile ) playerTile ).tileRule( playerId );
				break;
			}
			case 2 :
			case 12 :
			case 16 :
			case 22 :
			case 27 :
			case 37 :
			{
				( ( DrawCardTile ) playerTile ).tileRule( playerId );
				return DrawCardTile.drawnCard.getId();
			}
			case 5 :
			case 7 :
			case 15 :
			case 25 :
			case 32 :
			case 35 :
			{
				( ( CompanyTile ) playerTile ).tileRule( playerId );
				break;
			}
			case 10 :
			{
				( ( Prision ) playerTile ).tileRule( playerId );
				break;
			}
			case 18 :
			{
				( ( ProfitTile ) playerTile ).tileRule( playerId );
				break;
			}
			case 20 :
			{
				( ( FreeStop ) playerTile ).tileRule( playerId );
				break;
			}
			case 24 :
			{
				( ( TaxesTile ) playerTile ).tileRule( playerId );
				break;
			}
			case 30 :
			{
				( ( GoToPrision ) playerTile ).tileRule( playerId );
				break;
			}
			default :
			{
				( ( Property ) playerTile ).tileRule( playerId );
				break;
			}
		}
		return null;
	}

	public static String buyBuilding(
		final Integer playerId,
		final Integer boardPosition,
		final BuildingEnum buildingType )
	{
		String response = "";
		final Player player = Game.getPlayerList().get( playerId );
		final Property property = ( Property ) Game.getTiles().get( boardPosition );
		final Building building = new Building(
			boardPosition,
			buildingType,
			player,
			property.getBuildingValue(),
			property );

		final boolean canBuyHotel = property.buyBuilding( playerId, building );

		if ( canBuyHotel )
		{
			response = "Sucesso! Contrução comprada!";
		}
		else
		{
			response = "Falha! Não pode comprar construção aqui!";
		}
		return response;
	}

	public static String buyTile( final Integer playerId )
	{
		final Player player = Game.getPlayerList().get( playerId );
		final AbstractTile currentTile = Game.getTiles().get( player.getBoardPosition() );
		if ( currentTile.getCanPurchase() )
		{
			try
			{
				switch ( player.getBoardPosition() )
				{
					case 5 :
					case 7 :
					case 15 :
					case 25 :
					case 32 :
					case 35 :
					{
						( ( CompanyTile ) currentTile ).buyCompany( player );
						break;
					}
					default :
					{
						( ( Property ) currentTile ).buyProperty( player );
						break;
					}
				}
			}
			catch ( final IllegalRuleException e )
			{
				e.printStackTrace();
				return e.getMessage();
			}
		}
		return "Compra feita com sucesso!";
	}

	public static boolean checkPlayerFreed( final List<Integer> roll, final Integer playerId )
	{
		final Player player = Game.getPlayerList().get( playerId );
		if ( player.isInPrision() )
		{
			boolean isFree = ( roll.size() > 2 );
			player.setInPrision( !isFree );
			if (!isFree)
			{
				player.addPriosionTime();
			}
			else
			{
				player.resetPrisionTime();
			}
		}

		if ( player.isInPrision() && player.getPrisionTime().equals( 4 ) )
		{
			player.loseMoney( Long.valueOf( 50 ) );
			player.setInPrision( false );
			player.resetPrisionTime();
		}

		if ( player.isFreeRide() && player.isInPrision() )
		{
			player.setFreeRide( false );
			player.setInPrision( false );
			player.resetPrisionTime();
		}
		return !player.isInPrision();
	}

	public static void createBoard()
	{
		final List<AbstractTile> board = Game.getTiles();
		if ( !board.isEmpty() )
		{
			board.clear();
		}
		Integer boardPosition = 0;
		board.add( new StartTile() );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 50 ),
					Long.valueOf( 6 ),
					TileColorEnum.PINK,
					new Long[]{	Long.valueOf( 30 ),
								Long.valueOf( 90 ),
								Long.valueOf( 270 ),
								Long.valueOf( 400 ),
								Long.valueOf( 500 )},
					Long.valueOf( 50 ) ) );
		boardPosition++;
		board.add( new DrawCardTile( boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 30 ),
					Long.valueOf( 2 ),
					TileColorEnum.PINK,
					new Long[]{	Long.valueOf( 10 ),
								Long.valueOf( 30 ),
								Long.valueOf( 90 ),
								Long.valueOf( 160 ),
								Long.valueOf( 250 )},
					Long.valueOf( 50 ) ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 30 ),
					Long.valueOf( 4 ),
					TileColorEnum.PINK,
					new Long[]{	Long.valueOf( 20 ),
								Long.valueOf( 60 ),
								Long.valueOf( 180 ),
								Long.valueOf( 320 ),
								Long.valueOf( 450 )},
					Long.valueOf( 50 ) ) );
		boardPosition++;
		board.add( new CompanyTile( Long.valueOf( 100 ), 50, boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 150 ),
					Long.valueOf( 20 ),
					TileColorEnum.BLUE,
					new Long[]{	Long.valueOf( 100 ),
								Long.valueOf( 300 ),
								Long.valueOf( 750 ),
								Long.valueOf( 925 ),
								Long.valueOf( 1100 )},
					Long.valueOf( 150 ) ) );
		boardPosition++;
		board.add( new CompanyTile( Long.valueOf( 100 ), 50, boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 110 ),
					Long.valueOf( 18 ),
					TileColorEnum.BLUE,
					new Long[]{	Long.valueOf( 90 ),
								Long.valueOf( 250 ),
								Long.valueOf( 700 ),
								Long.valueOf( 875 ),
								Long.valueOf( 1050 )},
					Long.valueOf( 150 ) ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 110 ),
					Long.valueOf( 18 ),
					TileColorEnum.BLUE,
					new Long[]{	Long.valueOf( 90 ),
								Long.valueOf( 250 ),
								Long.valueOf( 700 ),
								Long.valueOf( 875 ),
								Long.valueOf( 1050 )},
					Long.valueOf( 150 ) ) );
		boardPosition++;
		board.add( new Prision( boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 100 ),
					Long.valueOf( 16 ),
					TileColorEnum.PURPLE,
					new Long[]{	Long.valueOf( 80 ),
								Long.valueOf( 220 ),
								Long.valueOf( 600 ),
								Long.valueOf( 800 ),
								Long.valueOf( 1000 )},
					Long.valueOf( 100 ) ) );
		boardPosition++;
		board.add( new DrawCardTile( boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 90 ),
					Long.valueOf( 14 ),
					TileColorEnum.PURPLE,
					new Long[]{	Long.valueOf( 70 ),
								Long.valueOf( 200 ),
								Long.valueOf( 550 ),
								Long.valueOf( 750 ),
								Long.valueOf( 950 )},
					Long.valueOf( 100 ) ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 90 ),
					Long.valueOf( 14 ),
					TileColorEnum.PURPLE,
					new Long[]{	Long.valueOf( 70 ),
								Long.valueOf( 200 ),
								Long.valueOf( 550 ),
								Long.valueOf( 750 ),
								Long.valueOf( 950 )},
					Long.valueOf( 100 ) ) );
		boardPosition++;
		board.add( new CompanyTile( Long.valueOf( 75 ), 40, boardPosition ) );
		boardPosition++;
		board.add( new DrawCardTile( boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 175 ),
					Long.valueOf( 35 ),
					TileColorEnum.ORANGE,
					new Long[]{	Long.valueOf( 175 ),
								Long.valueOf( 500 ),
								Long.valueOf( 1100 ),
								Long.valueOf( 1300 ),
								Long.valueOf( 1500 )},
					Long.valueOf( 200 ) ) );
		boardPosition++;
		board.add( new ProfitTile( boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 200 ),
					Long.valueOf( 50 ),
					TileColorEnum.ORANGE,
					new Long[]{	Long.valueOf( 200 ),
								Long.valueOf( 600 ),
								Long.valueOf( 1400 ),
								Long.valueOf( 1700 ),
								Long.valueOf( 2000 )},
					Long.valueOf( 200 ) ) );
		boardPosition++;
		board.add( new FreeStop( boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 60 ),
					Long.valueOf( 8 ),
					TileColorEnum.PUMPKIN_ORANGE,
					new Long[]{	Long.valueOf( 40 ),
								Long.valueOf( 100 ),
								Long.valueOf( 300 ),
								Long.valueOf( 450 ),
								Long.valueOf( 600 )},
					Long.valueOf( 50 ) ) );
		boardPosition++;
		board.add( new DrawCardTile( boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 50 ),
					Long.valueOf( 6 ),
					TileColorEnum.PUMPKIN_ORANGE,
					new Long[]{	Long.valueOf( 30 ),
								Long.valueOf( 90 ),
								Long.valueOf( 270 ),
								Long.valueOf( 400 ),
								Long.valueOf( 500 )},
					Long.valueOf( 50 ) ) );
		boardPosition++;
		board.add( new TaxesTile( boardPosition ) );
		boardPosition++;
		board.add( new CompanyTile( Long.valueOf( 75 ), 40, boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 80 ),
					Long.valueOf( 12 ),
					TileColorEnum.YELLOW,
					new Long[]{	Long.valueOf( 60 ),
								Long.valueOf( 180 ),
								Long.valueOf( 500 ),
								Long.valueOf( 700 ),
								Long.valueOf( 900 )},
					Long.valueOf( 100 ) ) );
		boardPosition++;
		board.add( new DrawCardTile( boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 70 ),
					Long.valueOf( 10 ),
					TileColorEnum.YELLOW,
					new Long[]{	Long.valueOf( 50 ),
								Long.valueOf( 150 ),
								Long.valueOf( 450 ),
								Long.valueOf( 650 ),
								Long.valueOf( 750 )},
					Long.valueOf( 100 ) ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 70 ),
					Long.valueOf( 10 ),
					TileColorEnum.YELLOW,
					new Long[]{	Long.valueOf( 50 ),
								Long.valueOf( 150 ),
								Long.valueOf( 450 ),
								Long.valueOf( 650 ),
								Long.valueOf( 750 )},
					Long.valueOf( 100 ) ) );
		boardPosition++;
		board.add( new GoToPrision( boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 130 ),
					Long.valueOf( 22 ),
					TileColorEnum.GREEN,
					new Long[]{	Long.valueOf( 110 ),
								Long.valueOf( 330 ),
								Long.valueOf( 800 ),
								Long.valueOf( 975 ),
								Long.valueOf( 1150 )},
					Long.valueOf( 150 ) ) );
		boardPosition++;
		board.add( new CompanyTile( Long.valueOf( 100 ), 50, boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 160 ),
					Long.valueOf( 28 ),
					TileColorEnum.GREEN,
					new Long[]{	Long.valueOf( 150 ),
								Long.valueOf( 450 ),
								Long.valueOf( 1000 ),
								Long.valueOf( 1200 ),
								Long.valueOf( 1400 )},
					Long.valueOf( 200 ) ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 150 ),
					Long.valueOf( 26 ),
					TileColorEnum.GREEN,
					new Long[]{	Long.valueOf( 130 ),
								Long.valueOf( 390 ),
								Long.valueOf( 900 ),
								Long.valueOf( 1100 ),
								Long.valueOf( 1275 )},
					Long.valueOf( 200 ) ) );
		boardPosition++;
		board.add( new CompanyTile( Long.valueOf( 100 ), 50, boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 150 ),
					Long.valueOf( 26 ),
					TileColorEnum.GREEN,
					new Long[]{	Long.valueOf( 130 ),
								Long.valueOf( 390 ),
								Long.valueOf( 900 ),
								Long.valueOf( 1100 ),
								Long.valueOf( 1275 )},
					Long.valueOf( 200 ) ) );
		boardPosition++;
		board.add( new DrawCardTile( boardPosition ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 140 ),
					Long.valueOf( 24 ),
					TileColorEnum.DEEP_PURPLE,
					new Long[]{	Long.valueOf( 120 ),
								Long.valueOf( 360 ),
								Long.valueOf( 850 ),
								Long.valueOf( 1025 ),
								Long.valueOf( 1200 )},
					Long.valueOf( 150 ) ) );
		boardPosition++;
		board
			.add(
				new Property(
					boardPosition,
					Long.valueOf( 130 ),
					Long.valueOf( 22 ),
					TileColorEnum.DEEP_PURPLE,
					new Long[]{	Long.valueOf( 110 ),
								Long.valueOf( 330 ),
								Long.valueOf( 800 ),
								Long.valueOf( 975 ),
								Long.valueOf( 1150 )},
					Long.valueOf( 150 ) ) );
	}

	public static void createPlayers( Observer o)
	{
		if ( Game.getPlayerList().size() == 0 )
		{
			final List<Player> players = new ArrayList<Player>( 6 );
			Integer playerSize = 0;
			final Integer numPlayers = Game.getNumPlayers();
			while ( playerSize < numPlayers )
			{
				final Player temp = new Player( PlayerColorEnum.values()[playerSize] );
				players.add( temp );
				playerSize++;
				players
					.get( playerSize - 1 )
					.add( o );
			}
			// Instantiate a list of players on position 0
			Game.setPlayerList( players );
		}
	}

	public static List<Integer> forceMove( final Integer playerId, final List<Integer> simulatedDice, Observer o )
	{
		final Player player = Game.getPlayerList().get( playerId );
		player.setInPrision( simulatedDice.size() > 6 );
		if ( player.isInPrision() )
		{
			simulatedDice.clear();
		}
		movePlayer( player, simulatedDice, o );
		return simulatedDice;
	}

	public static Integer getPlayerPositionByNumber( final Integer playerNum )
	{
		final Integer boardPosition = Game
			.getPlayerList()
			.stream()
			.filter( player -> player.getColor().equals( PlayerColorEnum.values()[playerNum] ) )
			.findAny()
			.get()
			.getBoardPosition();
		return boardPosition;
	}

	private static void movePlayer( final Player player, final List<Integer> dice, Observer o )
	{
		if ( player.isInPrision() )
		{
			dice.clear();
		}
		final Integer moveSum = dice.stream().mapToInt( Integer::intValue ).sum();
		if ( !player.isInPrision() )
		{
			player.setBoardPosition( ( player.getBoardPosition() + moveSum ) );
			if ( player.getBoardPosition() >= 40 )
			{
				player.loseMoney( Long.valueOf( -200 ) );
				player.setBoardPosition( player.getBoardPosition() % 40 );
			}
		}
		else
		{
			player.setBoardPosition( 10 );
		}
		for ( final Player players : Game.getPlayerList() )
		{
			players.update( o );
		}
	}

	public static List<Integer> moveRollDice( final Integer playerId, Observer o )
	{
		final List<Integer> diceRoll = Dice.getInstance().moveRoll();
		final Player player = Game.getPlayerList().get( playerId );
		
		if ( !player.isInPrision() )
		{
			player.setInPrision( diceRoll.size() > 6 );
		}
		movePlayer( player, new ArrayList<>(diceRoll), o );
		return diceRoll;
	}

	public static Integer getColorIndexByPlayerTurn(Integer playerTurn)
	{
		return Game.getPlayerList().get(playerTurn).getColor().getIndex();
	}

	public static void sellBuilding( final Integer playerId, final Integer propertyPos )
	{
		final Player player = Game.getPlayerList().get( playerId );
		final Property sellingTile = ( Property ) Game.getTiles().get( propertyPos );
		player.loseMoney( -sellingTile.sellBuilding() );
	}

	public static void sellTile( final Integer playerId, final Integer propertyPos )
		throws IllegalRuleException
	{
		final Player player = Game.getPlayerList().get( playerId );
		final AbstractTile sellingTile = Game.getTiles().get( propertyPos );
		if ( sellingTile.getCanPurchase() || !sellingTile.getOwner().equals( player ) )
		{
			throw new IllegalRuleException( "Esta casa não pertence a você!!!" );
		}
		player.loseMoney( -sellingTile.getValue() );
		sellingTile.setCanPurchase( true );
		sellingTile.setOwner( null );
	}

	public static void nextTurn(Observer o)
	{
		Game.setTurn((Game.getTurn() + 1) % Game.getNumPlayers());
		if (Game.getInstance().hasObservver(o))
		{
			Game.getInstance().update(o);
		}
		else
		{
			Game.getInstance().add(o);
		}
		
	}

	public static void shuffleDeck()
	{
		final List<AbstractCard> deck = Game.getCards();
		if ( !deck.isEmpty() )
		{
			deck.clear();
		}
		final String contextPath = "../data/sorteReves";
		Integer id = 0;
		deck.add( new DefaultCard( id, true, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 25 ) ) );
		id++;
		deck.add( new DefaultCard( id, true, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 150 ) ) );
		id++;
		deck.add( new DefaultCard( id, true, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 80 ) ) );
		id++;
		deck.add( new DefaultCard( id, true, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 200 ) ) );
		id++;
		deck.add( new DefaultCard( id, true, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 50 ) ) );
		id++;
		deck.add( new DefaultCard( id, true, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 50 ) ) );
		//^6 -v7
		id++;
		deck.add( new DefaultCard( id, true, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 100 ) ) );
		id++;
		deck.add( new DefaultCard( id, true, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 100 ) ) );
		id++;
		deck.add( new FreeRideCard( id, contextPath + "chance" + (id+1) + ".png" ) );
		id++;
		deck.add( new StartPosCard( id, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 200 ) ) );
		id++;
		deck.add( new StealCard( id, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 50 ) ) );
		id++;
		deck.add( new DefaultCard( id, true, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 45 ) ) );
		//^12 -v13
		id++;
		deck.add( new DefaultCard( id, true, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 100 ) ) );
		id++;
		deck.add( new DefaultCard( id, true, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 100 ) ) );
		id++;
		deck.add( new DefaultCard( id, true, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 20 ) ) );
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 15 ) ) );
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 25 ) ) );
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 45 ) ) );
		//^18 -v19
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 30 ) ) );
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 100 ) ) );
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 100 ) ) );
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 40 ) ) );
		id++;
		deck.add( new PrisionCard( id, false, contextPath + "chance" + (id+1) + ".png" ) );
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 30 ) ) );
		//^24 -v25
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 50 ) ) );
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 25 ) ) );
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 30 ) ) );
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 45 ) ) );
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 50 ) ) );
		id++;
		deck.add( new DefaultCard( id, false, contextPath + "chance" + (id+1) + ".png", Long.valueOf( 50 ) ) );

	}

}
