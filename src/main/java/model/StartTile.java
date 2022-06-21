package model;

import enums.TileEnum;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 20, 2022
 */
class StartTile
	extends AbstractTile
{

	/**
	 * <p>
	 * </p>
	 */
	public StartTile()
	{
		setBoardPosition( 0 );
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
		player.setRoundTrips( player.getRoundTrips() + 1 );
	}

}
