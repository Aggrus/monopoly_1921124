package model;

import enums.TileEnum;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 20, 2022
 */
class GoToPrision
	extends AbstractTile
{

	public GoToPrision(Integer boardPosition)
	{
		setBoardPosition(boardPosition);
		setOwner( null );
		setCanPurchase( false );
		setGorup( null );
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
		Player player = Game.getPlayerList().get(playerId);
		player.setInPrision( true );
		player.setBoardPosition(10);
	}

}
