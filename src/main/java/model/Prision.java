package model;

import java.util.List;

import enums.TileEnum;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 20, 2022
 */
class Prision
	extends AbstractTile
{

	/**
	 * <p>
	 * </p>
	 * @param boardPosition 
	 */
	public Prision(Integer boardPosition)
	{
		setBoardPosition(boardPosition);
		setCanPurchase( false );
		setGorup( null );
		setOwner( null );
		setSpecialProperty( TileEnum.NONE );
		setValue( Long.valueOf( 0 ) );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param player
	 * @see model.AbstractTile#tileRule(model.Player)
	 */
	@Override
	public void tileRule( final Integer playerId )
	{
		final List<Integer> lastRoll = Dice.getDie();
		ApplyRules.checkPlayerFreed(lastRoll, playerId);
	}

}
