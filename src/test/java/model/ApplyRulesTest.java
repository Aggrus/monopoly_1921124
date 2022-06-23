package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Controller.Observer.Observer;
import View.GameScreen;
import enums.BuildingEnum;
import enums.PlayerColorEnum;
import enums.TileColorEnum;

public class ApplyRulesTest
{

	@Test
	public void testCreateBoard()
	{
		Game.getInstance();
		ApplyRules.createBoard();
		assertTrue("Tamanho deveria ser 40!",Game.getTiles().size() == 40);
		assertTrue("Primeira casa deveria ser StartTile!",Game.getTiles().get(0) instanceof StartTile);
		assertTrue("Segunda casa deveria ser propriedade!",Game.getTiles().get(1) instanceof Property);
		Property leblon = new Property(
				1,
				Long.valueOf( 50 ),
				Long.valueOf( 6 ),
				TileColorEnum.PINK,
				new Long[]{	Long.valueOf( 30 ),
							Long.valueOf( 90 ),
							Long.valueOf( 270 ),
							Long.valueOf( 400 ),
							Long.valueOf( 500 )},
				Long.valueOf( 50 ) );
		String checkProperty = String.format("Terreno errado na posição 1! \n\tposição: %d \n\tvalor: %d \n\tcor: %s \n\tàvenda: %b \n\taluguel: %d", Game.getTiles().get(1).getBoardPosition(), Game.getTiles().get(1).getValue(),  Game.getTiles().get(1).getGorup().toString(),  Game.getTiles().get(1).getCanPurchase(), ((Property)Game.getTiles().get(1)).getRent());
		assertTrue(checkProperty,((Property)Game.getTiles().get(1)).getBoardPosition().equals(leblon.getBoardPosition()));
		assertTrue(checkProperty,((Property)Game.getTiles().get(1)).getBuildings().equals(leblon.getBuildings()));
		assertTrue(checkProperty,((Property)Game.getTiles().get(1)).getBuildingValue().equals(leblon.getBuildingValue()));
		assertTrue(checkProperty,((Property)Game.getTiles().get(1)).getCanPurchase() == (leblon.getCanPurchase()));
		assertTrue(checkProperty,((Property)Game.getTiles().get(1)).getGorup().equals(leblon.getGorup()));
		assertTrue(checkProperty,((Property)Game.getTiles().get(1)).getRent().equals(leblon.getRent()));
		assertTrue(checkProperty,((Property)Game.getTiles().get(1)).getSpecialProperty().equals(leblon.getSpecialProperty()));
		assertTrue(checkProperty,((Property)Game.getTiles().get(1)).getValue().equals(leblon.getValue()));
	}
	
	
	
	@Test
	public void testApplyTileRuleToPlayerById()
	{
		Game.getInstance();
		Game.setPlayerList(new ArrayList<Player>(6));
		ApplyRules.createBoard();
		ApplyRules.shuffleDeck();
		final Player player = new Player(PlayerColorEnum.RED);
		final Player secondPlayer =  new Player(PlayerColorEnum.BLUE);
		player.add(GameScreen.getInstance(0, 0, 2));
		secondPlayer.add(GameScreen.getInstance(0, 0, 2));
		Game.getPlayerList().add(player);
		Game.getPlayerList().add(secondPlayer);
		Game.setNumPlayers(Game.getPlayerList().size());
		System.out.println(Game.getPlayerList().size());
		List<Integer> dice = new ArrayList<Integer>();
		dice.add(0);
		dice.add(1);
		Dice.getInstance().setDie(dice);
		//Test position 0 - StartTile
		assertNull(ApplyRules.applyTileRuleToPlayerById(Game.getPlayerList().get(0).getColor().getIndex(), GameScreen.getInstance(0, 0, 2)));
		assertEquals((long)4000, (long)Game.getPlayerList().get(0).getMoney());
		Game.getPlayerList().get(0).setBoardPosition(Game.getPlayerList().get(0).getBoardPosition() + 1);
		//Test position 1 no Owner - Property
		assertNull(ApplyRules.applyTileRuleToPlayerById(Game.getPlayerList().get(0).getColor().getIndex(), GameScreen.getInstance(0, 0, 2)));
		assertEquals((long)4000, (long)Game.getPlayerList().get(0).getMoney());
		//Test position 1 but bought by another player
		Game.getTiles().get(1).setOwner(Game.getPlayerList().get(1));
		assertNull(ApplyRules.applyTileRuleToPlayerById(Game.getPlayerList().get(0).getColor().getIndex(), GameScreen.getInstance(0, 0, 2)));
		assertFalse(String.format("Dinheiro = %d", Game.getPlayerList().get(0).getMoney()), Game.getPlayerList().get(0).getMoney().equals(Long.valueOf(4000)));
		assertTrue(Game.getPlayerList().get(1).getMoney() > (long)4000);
		Game.getPlayerList().get(0).setMoney((long)4000);
		Game.getPlayerList().get(1).setMoney((long)4000);
		//Test position 1 but bought by main player
		Game.getTiles().get(1).setOwner(Game.getPlayerList().get(0));
		assertNull(ApplyRules.applyTileRuleToPlayerById(Game.getPlayerList().get(0).getColor().getIndex(), GameScreen.getInstance(0, 0, 2)));
		assertTrue("Jogador que tem a propireade não deveria perder dinheiro nela",Game.getPlayerList().get(0).getMoney().equals(Long.valueOf(4000)));
		Game.getPlayerList().get(0).setBoardPosition(Game.getPlayerList().get(0).getBoardPosition() + 1);
		//Test position 2 - DrawCard
		assertNotNull("Deveria retornar indice da carta!",ApplyRules.applyTileRuleToPlayerById(Game.getPlayerList().get(0).getColor().getIndex(), GameScreen.getInstance(0, 0, 2)));
		Game.getPlayerList().get(0).setMoney((long)4000);
		Game.getPlayerList().get(1).setMoney((long)4000);
		Game.getPlayerList().get(0).setBoardPosition(5);
		//Test position 5 - CompanyTile
		assertNull(ApplyRules.applyTileRuleToPlayerById(Game.getPlayerList().get(0).getColor().getIndex(), GameScreen.getInstance(0, 0, 2)));
		assertEquals((long)4000, (long)Game.getPlayerList().get(0).getMoney());
		//Test position 5 player Owner
		Game.getTiles().get(5).setOwner(Game.getPlayerList().get(0));
		assertNull(ApplyRules.applyTileRuleToPlayerById(Game.getPlayerList().get(0).getColor().getIndex(), GameScreen.getInstance(0, 0, 2)));
		assertTrue("Jogador que tem a propireade não deveria perder dinheiro nela",Game.getPlayerList().get(0).getMoney().equals(Long.valueOf(4000)));
		//Test position 5 secondPlayer Owner
		Game.getTiles().get(5).setOwner(Game.getPlayerList().get(1));
		assertNull(ApplyRules.applyTileRuleToPlayerById(Game.getPlayerList().get(0).getColor().getIndex(), GameScreen.getInstance(0, 0, 2)));
		assertFalse(Game.getPlayerList().get(0).getMoney().equals(Long.valueOf(4000)));
		assertTrue(Game.getPlayerList().get(1).getMoney() > (long)4000);
		Game.getPlayerList().get(0).setMoney((long)4000);
		Game.getPlayerList().get(1).setMoney((long)4000);
		//TestGoToPrisionTile
		Game.getPlayerList().get(0).setBoardPosition(30);
		assertNull(ApplyRules.applyTileRuleToPlayerById(Game.getPlayerList().get(0).getColor().getIndex(), GameScreen.getInstance(0, 0, 2)));
		assertTrue(Game.getPlayerList().get(0).isInPrision());
		assertEquals(Integer.valueOf(0), Game.getPlayerList().get(0).getPrisionTime());
	}
	
	@Test
	public void testForceMove()
	{
		Game.getInstance();
		Observer o = GameScreen.getInstance(0, 0, 1);
		Game.setPlayerList(new ArrayList<Player>(6));
		Game.setNumPlayers(1);
		ApplyRules.createPlayers(o);		
		final List<Integer> roll = new ArrayList<Integer>();
		roll.add( 1 );
		roll.add( 2 );
		assertEquals(roll, ApplyRules.forceMove(Game.getPlayerList().get(0).getColor().getIndex(), roll, o));
		roll.clear();
		assertTrue(Integer.valueOf(3).equals(Game.getPlayerList().get(0).getBoardPosition()));
		
		//Teste prisão
		roll.add( 3 );
		roll.add( 3 );
		roll.add( 3 );
		roll.add( 3 );
		roll.add( 3 );
		roll.add( 3 );
		roll.add( 1 );
		roll.add( 2 );
		assertTrue(ApplyRules.forceMove(Game.getPlayerList().get(0).getColor().getIndex(), roll, o).isEmpty());
		assertEquals(Integer.valueOf(10), Game.getPlayerList().get(0).getBoardPosition());
	}

	@Test
	public void testCheckPlayerFreed()
	{
		final List<Integer> roll = new ArrayList<Integer>();
		roll.add(1);
		roll.add(2);
		Game.getInstance();
		Observer o = GameScreen.getInstance(0, 0, 1);
		Game.setNumPlayers(1);
		ApplyRules.createPlayers(o);
		Game.getPlayerList().get(0).setInPrision(true);
		//Teste base
		assertFalse(ApplyRules.checkPlayerFreed( roll, Game.getPlayerList().get(0).getColor().getIndex() ) );
		assertTrue( Game.getPlayerList().get(0).isInPrision() );
		assertEquals(Integer.valueOf(1), Game.getPlayerList().get(0).getPrisionTime());
		//Testar se quando joga 2 dados iguais é liberado
		roll.clear();
		roll.add(3);
		roll.add(3);
		roll.add(1); //Pois o teste está se ele rolou mais de uma vez, não se rolou números iguais
		assertTrue(ApplyRules.checkPlayerFreed( roll, Game.getPlayerList().get(0).getColor().getIndex() ) );
		assertFalse( Game.getPlayerList().get(0).isInPrision() );
		assertEquals(Integer.valueOf(0), Game.getPlayerList().get(0).getPrisionTime());
		//Testar se é liberado quando já está na prisão a 4 turnos
		roll.clear();
		roll.add(1);
		Game.getPlayerList().get(0).setInPrision(true);
		Game.getPlayerList().get(0).addPrisionTime();
		Game.getPlayerList().get(0).addPrisionTime();
		Game.getPlayerList().get(0).addPrisionTime();
		assertEquals(Integer.valueOf(3), Game.getPlayerList().get(0).getPrisionTime());
		assertTrue(ApplyRules.checkPlayerFreed( roll, Game.getPlayerList().get(0).getColor().getIndex() ) );
		assertFalse("em prisão?"+Game.getPlayerList().get(0).isInPrision()+" quanto tempo?"+Game.getPlayerList().get(0).getPrisionTime(), Game.getPlayerList().get(0).isInPrision() );
		assertEquals(Integer.valueOf(0), Game.getPlayerList().get(0).getPrisionTime());
		assertTrue(Game.getPlayerList().get(0).getMoney().toString(),Long.valueOf(3950).equals(Game.getPlayerList().get(0).getMoney()));
		//Testar se é liberado quando tem passe livre de prisão
		Game.getPlayerList().get(0).setInPrision(true);
		Game.getPlayerList().get(0).addPrisionTime();
		Game.getPlayerList().get(0).addPrisionTime();
		Game.getPlayerList().get(0).setFreeRide(true);
		assertTrue( "Deveria estar solto!", ApplyRules.checkPlayerFreed( roll, Game.getPlayerList().get(0).getColor().getIndex() ) );
		assertFalse( Game.getPlayerList().get(0).isInPrision() );
		assertEquals(Integer.valueOf(0), Game.getPlayerList().get(0).getPrisionTime());
	}

	@Test
	public void testbuyBuilding()
	{
		Game.getInstance();
		ApplyRules.createBoard();
		Observer o = GameScreen.getInstance(0, 0, 2);
		Game.setNumPlayers(2);
		Game.setPlayerList(new ArrayList<Player>(6));
		ApplyRules.createPlayers(o);
		Player p1 = Game.getPlayerList().get(0);
		Player p2 = Game.getPlayerList().get(1);
		String testString = "Sucesso! Contrução comprada!";
		try
		{
			ApplyRules.buyBuilding(0, p1.getBoardPosition(), BuildingEnum.HOUSE, o).equals(testString);
			fail();
		}
		catch(java.lang.ClassCastException e)
		{
			assertEquals("model.StartTile cannot be cast to model.Property", e.getMessage());
		}
		p1.setBoardPosition(1);
		Game.getTiles().get(1).setOwner(p2);
		assertFalse(ApplyRules.buyBuilding(0, p1.getBoardPosition(), BuildingEnum.HOUSE, o).equals(testString));
		Game.getTiles().get(1).setOwner(p1);
		assertTrue(ApplyRules.buyBuilding(0, p1.getBoardPosition(), BuildingEnum.HOUSE, o).equals(testString));
		assertFalse(ApplyRules.buyBuilding(0, p1.getBoardPosition(), BuildingEnum.HOTEL, o).equals(testString));
		ApplyRules.buyBuilding(0, p1.getBoardPosition(), BuildingEnum.HOUSE, o);
		assertTrue(ApplyRules.buyBuilding(0, p1.getBoardPosition(), BuildingEnum.HOTEL, o).equals(testString));
	}

	@Test
	public void testDeckShuffle()
	{
		Game.getInstance();
		ApplyRules.shuffleDeck();
		assertEquals(30, Game.getCards().size());
		assertTrue(Game.getCards().get(0) instanceof DefaultCard);
		assertTrue(Game.getCards().get(10) instanceof StealCard);

	}
}
