package model;

import enums.TileEnum;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 20, 2022
 */
class TaxesTile
	extends AbstractTile
{

	/**
	 * <p>
	 * </p>
	 * @param boardPosition 
	 */
	public TaxesTile(Integer boardPosition)
	{
		setBoardPosition(boardPosition);
		setCanPurchase( false );
		setGorup( null );
		setOwner( null );
		setSpecialProperty( TileEnum.GIVE );
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
		player.loseMoney( getValue() );
	}
}
