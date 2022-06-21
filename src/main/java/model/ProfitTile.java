package model;

import enums.TileEnum;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 20, 2022
 */
class ProfitTile
	extends AbstractTile
{

	/**
	 * <p>
	 * </p>
	 * @param boardPosition 
	 */
	public ProfitTile(Integer boardPosition)
	{
		setBoardPosition(boardPosition);
		setCanPurchase( false );
		setGorup( null );
		setOwner( null );
		setSpecialProperty( TileEnum.GET );
		setValue( Long.valueOf( 200 ) );
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
		final Long amount = player.getMoney() + getValue();
		player.setMoney( amount );
	}
}
